import copy
from copy import deepcopy
from domain.entities import Person
from services.undo_service import UndoService
from services.undo_service import UndoObject
from domain.entities import Activity
from  itertools import chain


class Services:
    def __init__(self, person_repository, activity_repository):
        self.__person_repository = person_repository
        self.__activity_repository = activity_repository
        self._undo_service = UndoService(self.__person_repository, self.__activity_repository)

    def add_person_random(self, person_id, name, phone):
        new_person = Person(person_id, name, phone)
        self.__person_repository.save(new_person)

    def add_person(self, person_id, name, phone):
        """
        Adding a person
        :param person_id:
        :param name:
        :param phone:
        :return:
        """
        new_person = Person(person_id, name, phone)
        self.__person_repository.save(new_person)
        self._undo_service.register_operation(UndoObject(lambda: self.__person_repository.delete_by_id(new_person.id),
                                                         lambda: self.__person_repository.save(new_person)))


    def remove_person(self, person_id):
        """
        Removing a person by id
        :param person_id:
        :return:
        """
        person = self.__person_repository.find_by_id(person_id)
        activities = []
        my_act = self.get_all_activities()
        saved = dict()
        for each_activity in my_act:
            if person_id in each_activity.person_id:
                activities.append(each_activity)
                saved[each_activity.id] = each_activity.person_id[:]

        def undo_function():
            self.__person_repository.save(person)
            keys = saved.keys()
            for each_activity in activities:
                if each_activity.id in keys:
                    each_activity.person_id = deepcopy(saved[each_activity.id])
                self.__activity_repository.update(each_activity.id, each_activity)

        def redo_function():
            self.__person_repository.delete_by_id(person.id)
            self.update_removed_person(person_id)


        self._undo_service.register_operation(UndoObject(undo_function, redo_function))
        self.__person_repository.delete_by_id(person_id)
        self.update_removed_person(person_id)


    def update_person(self, person_id, name, phone):
        """
        Updating a person
        :param person_id:
        :param name:
        :param phone:
        :return:
        """
        now_person = self.__person_repository.find_by_id(person_id)
        new_person = Person(person_id, name, phone)
        old_person = Person(person_id, now_person.name, new_person.phone)
        self.__person_repository.update(person_id, new_person)
        self._undo_service.register_operation(UndoObject(lambda: self.__person_repository.update(person_id, old_person),
                                                         lambda: self.__person_repository.update(person_id, new_person)))

    def get_all_persons(self):
        """
        Returning all persons as a list
        :return:
        """
        return self.__person_repository.get_all()


    def get_names_to_print(self, name):
        """
        Returns a list with all persons that contains that name
        :param name:
        :return:
        """
        my_persons = self.get_all_persons()
        my_names = []
        for each_person in my_persons:
            if name.lower() in each_person.name.lower():
                my_names.append(each_person)
        return my_names

    def get_phones_to_print(self, phone):
        """
        Returns a list with all persons that contains that phone number
        :param phone:
        :return:
        """
        my_persons = self.get_all_persons()
        my_phones = []
        for each_person in my_persons:
            if phone in each_person.phone:
                my_phones.append(each_person)
        return my_phones


    def add_activity_random(self, activity_id, person_id, date, time, description):
        new_activity = Activity(activity_id, person_id, date, time, description)
        self.__activity_repository.save(new_activity)

    def add_activity(self, activity_id, person_id, date, time, description):
        """
        Adding an activity as an object
        :param activity_id:
        :param person_id:
        :param date:
        :param time:
        :param description:
        :return:
        """
        new_activity = Activity(activity_id, person_id, date, time, description)
        self.__activity_repository.save(new_activity)
        self._undo_service.register_operation(UndoObject(lambda: self.__activity_repository.delete_by_id(new_activity.id),
                                                         lambda: self.__activity_repository.save(new_activity)))

    def remove_activity_auto(self, activity_id):
        """
        Removing an activity by id -> for update_removed_person()
        :param activity_id:
        :return:
        """
        self.__activity_repository.delete_by_id(activity_id)


    def remove_activity(self, activity_id):
        """
        Removing an activity by id
        :param activity_id:
        :return:
        """
        activity = self.__activity_repository.find_by_id(activity_id)
        saved = activity.person_id[:]

        def undo_function():
            self.__activity_repository.save(activity)
            activity.person_id = saved
            self.__activity_repository.update(activity.id, activity)

        def redo_function():
            self.__activity_repository.delete_by_id(activity_id)

        self._undo_service.register_operation(UndoObject(undo_function, redo_function))
        self.__activity_repository.delete_by_id(activity_id)

    def update_activity(self, activity_id, person_id, date, time, description):
        """
        Updating an activity by id
        :param activity_id:
        :param person_id:
        :param date:
        :param time:
        :param description:
        :return:
        """

        now_activity = self.__activity_repository.find_by_id(activity_id)
        new_activity = Activity(activity_id, person_id, date, time, description)
        old_activity = Activity(activity_id, now_activity.person_id[:], now_activity.date, now_activity.time, now_activity.description)
        self.__activity_repository.update(activity_id, new_activity)
        self._undo_service.register_operation(UndoObject(lambda: self.__activity_repository.update(activity_id, old_activity),
                                                         lambda: self.__activity_repository.update(activity_id, new_activity)))

    def get_all_activities(self):
        """
        Returning a list of all activities
        :return:
        """
        return self.__activity_repository.get_all()


    def update_removed_person(self, removed_id):
        """
        Auto-updating activities after removing a person
        :param removed_id:
        :return:
        """
        activities = self.get_all_activities()
        for each_activity in activities:
            for each_person in each_activity.person_id:
                if each_person == removed_id:
                    each_activity.person_id.remove(removed_id)
            if len(each_activity.person_id) == 0:
                self.remove_activity_auto(each_activity.id)

    def check_add(self, my_persons, date, time, description):
        """
        Checking if we can add an activity: a person is not already occupied
        :param my_persons:
        :param date:
        :param time:
        :param description:
        :return:
        """
        check = False
        activities = self.get_all_activities()
        for each_person in my_persons:
            for each_activity in activities:
                if each_person in each_activity.person_id:
                    if each_activity.date == date and each_activity.time == time:
                        check = True

        return check


    def check_update(self, now_act, curr_id, my_persons, new_date, new_time, new_description):
        """
        Checking if we can update an activity: a person is not already occupied
        :param now_act:
        :param curr_id:
        :param my_persons:
        :param new_date:
7        :param new_time:
        :param new_description:
        :return:
        """
        check = False
        for each_person in my_persons:
            for each_activity in now_act:
                if each_person in each_activity.person_id:
                    if each_activity.date == new_date and each_activity.time == new_time and each_activity.id != curr_id:
                        check = True
        if check == False:
            for activities in now_act:
                if activities.id == curr_id:
                    self.update_activity(curr_id, my_persons, new_date, new_time, new_description)
                    break
        return check


    def get_dates_to_print(self, date):
        """
        Returns a list of activities that contains input date
        :param date:
        :return:
        """
        my_activities = self.get_all_activities()
        my_dates = []
        for each_activity in my_activities:
            if date in each_activity.date:
                my_dates.append(each_activity)
        return my_dates

    def get_times_to_print(self, time):
        """
        Returns a list of activities that contains input time
        :param time:
        :return:
        """
        my_activities = self.get_all_activities()
        my_times = []
        for each_activity in my_activities:
            if time in each_activity.time:
                my_times.append(each_activity)
        return my_times

    def get_descriptions_to_print(self, description):
        """
        Returns a list of activities that contains input description
        :param description:
        :return:
        """
        my_activities = self.get_all_activities()
        my_descriptions = []
        for each_activity in my_activities:
            if description.lower() in each_activity.description.lower():
                my_descriptions.append(each_activity)
        return my_descriptions


    def get_sorted_dates(self, date):
        """
        Returns a list of activities that contains input date, sorted by time
        :param date:
        :return:
        """
        my_act = self.get_dates_to_print(date)
        my_activities_to_sort = sorted(my_act, key=lambda x: x.time)
        return my_activities_to_sort

    def get_busiest_day(self):
        """
        Returns a list of activities that contains the days in order of the number of the activities
        :return:
        """
        my_act = self.get_all_activities()
        my_busiest_day = []
        for each_activity in my_act:
            now = sum(each_activity.date == act.date for act in my_act)
            if each_activity.date not in chain(*my_busiest_day):
                my_busiest_day.append([each_activity.date, now])

        my_busiest_day = sorted(my_busiest_day, key=lambda x: x[1], reverse=True)
        return my_busiest_day


    def get_activities_by_name(self, name, my_persons):
        """
        Returns a list of activities that contains the persons that do that activity (and the person has a specific name)
        :param name:
        :param my_persons:
        :return:
        """
        my_activities = self.get_all_activities()
        my_names = []
        person_id = []
        for each_person in my_persons:
            if name.lower() in each_person.name.lower():
                person_id.append(each_person.id)

        for each_activity in my_activities:
            for each_id in person_id:
                if each_id in each_activity.person_id:
                    my_names.append(each_activity)
        return my_names


    def undo(self):
        """
        Undo the last operation
        :return:
        """
        self._undo_service.undo()

    def redo(self):
        """
        Redo the last operation
        :return:
        """
        self._undo_service.redo()