import copy

from domain.validators import PersonValidator
from domain.validators import ActivityValidator
from domain.validators import TestValidator
from repository.repository import Repository

import random
from random import randint
import time
import datetime


class UI:
    # def __init__(self, person_service, activity_service, person_repository, activity_repository):
    #     self.__service = person_service
    #     self.__service = activity_service

    def __init__(self, services):
        self.__services = services
        
    def print_menu(self):
        print("\n1 Add \n"
              "2 Remove \n"
              "3 Update \n"
              "4 Print \n"
              "5 Search \n"
              "6 Statistics \n"
              "7 Undo \n"
              "8 Redo \n"
              "x Exit")

    def print_menu1(self):
        print("\n1 Add a person \n"
              "2 Add an activity \n"
              "x Exit")

    def print_menu2(self):
        print("\n1 Remove a person \n"
              "2 Remove an activity \n"
              "x Exit")

    def print_menu3(self):
        print("\n1 Update a person \n"
              "2 Update an activity \n"
              "x Exit")

    def print_menu4(self):
        print("\n1 Print all persons \n"
              "2 Print all activities \n"
              "x Exit")

    def print_menu5(self):
        print("\n1 Search a persons \n"
              "2 Search an activities \n"
              "x Exit")

    def print_menu51(self):
        print("\n1 Search a persons by name \n"
              "2 Search a persons by phone \n"
              "x Exit")

    def print_menu52(self):
        print("\n1 Search an activity by date \n"
              "2 Search an activity by time \n"
              "3 Search an activity by description \n"
              "x Exit")

    def print_menu6(self):
        print("\n1 Activities for a given date \n"
              "2 Busiest days \n"
              "3 Activities with a given person")

    def __print_all_persons(self):
        persons = self.__services.get_all_persons()
        self.__print_list(persons)

    def __print_all_activities(self):
        activities = self.__services.get_all_activities()
        self.__print_list(activities)

    def __print_list(self, lists):
        for element in lists:
            print(element)

    def print_names(self, name):
        my_names = self.__services.get_names_to_print(name)
        self.__print_list(my_names)

    def print_phones(self, phone):
        my_phones = self.__services.get_phones_to_print(phone)
        self.__print_list(my_phones)

    def print_dates(self, date):
        my_dates = self.__services.get_dates_to_print(date)
        self.__print_list(my_dates)

    def print_times(self, time):
        my_times = self.__services.get_times_to_print(time)
        self.__print_list(my_times)

    def print_descriptions(self, description):
        my_descriptions = self.__services.get_descriptions_to_print(description)
        self.__print_list(my_descriptions)

    def populate_persons(self):
        names = ["Bogdan", "Fabian", "Ingrid", "Anna", "Edy", "Ionut", "Robert", "Madalina", "Stefan", "Matei", "Rares",
                 "Flaviu", "Lavinia", "Tabita", "Laura", "Giorgia", "Alex", "Razvan", "Paul", "Vlad", "Mircea",
                 "Melon Musk", "Bil Gheitz", "Traian", "Decebal", "Adolf"]
        phones = ["0736420118", "0732495033", "0721386410", "0755901831", "0733989001", "0799112112", "0112112112",
                  "0732555911", "0712345689", "0722123098", "0754665984", "0744120932"]
        used_ids = []
        while len(used_ids) < 20:
            person_id = randint(1, 30)
            person_name = random.choice(names)
            person_phone = random.choice(phones)
            if person_id not in used_ids:
                self.__services.add_person_random(person_id, person_name, person_phone)
                used_ids.append(person_id)

        return used_ids

    def populate_activities(self, used_ids):
        dates = ["30.05.2002", "01.01.2022", "25.12.2021", "17.04.2023", "11.10.1999", "01.12.1918", "16.12.1989",
                 "01.09.1939", "02.09.1945", "27.09.2020", "28.06.2021", "13.04.2002", "14.02.2025", "19.07.2021",
                 "01.05.2022"]
        times = ["12:05", "17:04", "01:00", "16:30", "21:45", "19:44", "23:59", "00:00", "14:30", "09:20", "12:00",
                 "19:05", "22:22", "06:44"]
        descriptions = ["Birthday", "Matchday", "G2", "WW2", "Bring Me The Horizon Concert", "Fun", "Ski", "Gratar",
                        "Something", "Just being lazy", "Art School"]
        used = []
        while len(used) < 20:
            try:
                act_id = randint(1, 30)
                no_of_persons = randint(1, 4)
                person_id = []
                for i in range(0, no_of_persons):
                    now_id = random.choice(used_ids)
                    if now_id not in person_id:
                        person_id.append(now_id)
                date = random.choice(dates)
                time = random.choice(times)
                description = random.choice(descriptions)
                check = False
                activities = self.__services.get_all_activities()
                for each_person_id in person_id:
                    for each_activity in activities:
                        if each_person_id in each_activity.person_id:
                            if each_activity.date == date and each_activity.time == times:
                                check = True
                if check == False:
                    self.__services.add_activity_random(act_id, person_id, date, time, description)
                    used.append(act_id)
            except Exception:
                continue

    def main_menu(self):
        used = self.populate_persons()
        self.populate_activities(used)
        while True:
            self.print_menu()
            line = input("> ")
            try:
                if line == "x":
                    break
                if line == "1":
                    self.print_menu1()
                    now = input("> ")
                    if now == "1":
                        id = input("Enter an id: ")
                        name = input("Enter a name: ")
                        phone = input("Enter a phone number: ")
                        id = int(id)
                        self.__services.add_person(id, name, phone)
                        used.append(id)
                    elif now == "2":
                        id = input("Enter an id: ")
                        number_of_persons = int(input("Enter the number of persons that perform that activity: "))
                        my_persons = []
                        for i in range(0, number_of_persons):
                            person_id = input("Enter an id for a person: ")
                            person_id = int(person_id)
                            if person_id in used:
                                my_persons.append(person_id)
                        if len(my_persons) == number_of_persons:
                            date = input("Enter a date [dd.mm.yyyy]: ")
                            time = input("Enter a time [hh:mm]: ")
                            description = input("Enter a description: ")
                            id = int(id)
                            check = self.__services.check_add(my_persons, date, time, description)
                            if check == True:
                                print("Sorry! A person is already busy and cannot participate at this activity")
                            else:
                                self.__services.add_activity(id, my_persons, date, time, description)
                        else:
                            print("There was some error")
                elif line == "2":
                    self.print_menu2()
                    now = input("> ")
                    if now == "1":
                        delete_id = int(input("Enter the id of a person you want to remove: "))
                        used.remove(delete_id)
                        self.__services.remove_person(delete_id)
                        # self.__services.update_removed_person(delete_id)
                    elif now == "2":
                        delete_id = int(input("Enter the id of an activity you want to remove: "))
                        self.__services.remove_activity(delete_id)
                elif line == "3":
                    self.print_menu3()
                    now = input("> ")
                    if now == "1":
                        curr_id = int(input("Enter the id you want to update: "))
                        new_name = input("Enter the new name: ")
                        new_phone = input("Enter the new phone number: ")
                        if curr_id in used:
                            self.__services.update_person(curr_id, new_name, new_phone)
                        else:
                            print("There is not any person with that id")
                    elif now == "2":
                        curr_id = int(input("Enter the id you want to update: "))
                        new_number_person = int(input("Enter the new number of persons that perform this activity: "))
                        my_persons = []
                        for i in range(0, new_number_person):
                            person_id = input("Enter an id for a person: ")
                            person_id = int(person_id)
                            if person_id in used and person_id not in my_persons:
                                my_persons.append(person_id)
                        if new_number_person == len(my_persons):
                            new_date = input("Enter the new date: ")
                            new_time = input("Enter the new time: ")
                            new_description = input("Enter the new description: ")
                            now_act = self.__services.get_all_activities()
                            check = self.__services.check_update(now_act, curr_id, my_persons, new_date, new_time, new_description)
                            if check == True:
                                print("Sorry! A person is already busy and cannot participate at this activity")
                        else:
                            print("Something went wrong!")
                            print(my_persons)
                elif line == "4":
                    self.print_menu4()
                    now = input("> ")
                    if now == "1":
                        self.__print_all_persons()
                    elif now == "2":
                        self.__print_all_activities()
                elif line == "5":
                    self.print_menu5()
                    now = input("> ")
                    if now == "1":
                        self.print_menu51()
                        cmd = input("> ")
                        if cmd == "1":
                            name = input("Enter the name you want to search for: ")
                            self.print_names(name)
                        elif cmd == "2":
                            phone = input("Enter the phone number you want to search for: ")
                            self.print_phones(phone)
                    elif now == "2":
                        self.print_menu52()
                        cmd = input("> ")
                        if cmd == "1":
                            date = input("Enter the date you want to search for: ")
                            self.print_dates(date)
                        elif cmd == "2":
                            time = input("Enter the time you want to search for: ")
                            self.print_times(time)
                        elif cmd == "3":
                            description = input("Enter the description you want to search for: ")
                            self.print_descriptions(description)
                elif line == "6":
                    self.print_menu6()
                    cmd = input("> ")
                    if cmd == "1":
                        date = input("Enter a date: ")
                        my_act = self.__services.get_sorted_dates(date)
                        self.__print_list(my_act)
                    elif cmd == "2":
                        my_act = self.__services.get_busiest_day()
                        for each_activity in my_act:
                            if each_activity[1] > 1:
                                print("The date " + each_activity[0] + " has " + str(
                                    each_activity[1]) + " activities planned.")
                            else:
                                print(
                                    "The date " + each_activity[0] + " has " + str(each_activity[1]) + " activity planned.")
                    elif cmd == "3":
                        name = input("Enter a name: ")
                        my_persons = self.__services.get_all_persons()
                        my_act = self.__services.get_activities_by_name(name, my_persons)
                        for each_activity in my_act:
                            print(name + " does activity with id " + str(each_activity.id) + ", at the date and time " + each_activity.date + ", " + each_activity.time)
                elif line == "7":
                    self.__services.undo()
                elif line == "8":
                    self.__services.redo()
            except Exception as ex:
                print("Something went wrong. Maybe check again?")
                print(ex)

# person_validator = PersonValidator
# person_repository = Repository(person_validator)
#
# activity_validator = ActivityValidator
# activity_repository = Repository(activity_validator)
#
# person_service = PersonService(person_repository, activity_repository)
# activity_service = ActivityService(activity_repository)

# ui = UI(person_service, activity_service)
# ui.main_menu()


# test_validator = TestValidator
# test_repository = Repository(test_validator)
# test_service = PersonService(test_repository)
# test_service.test_add_person()
# test_service.test_remove_person()
# test_service.test_update_person()
# test_service.test_get_all_persons()
#
#
# test_validator = TestValidator
# test_repository = Repository(test_validator)
# test_service = ActivityService(test_repository)
# test_service.test_add_activity()
# test_service.test_remove_activity()
# test_service.test_update_activity()
# test_service.test_get_all_activities()
#
#
# test_validator = TestValidator
# test_repository = Repository(test_validator)
# test_service = PersonService(test_repository)
# test_repository.test_find_by_id()
# test_repository.test_save()
# test_repository.test_update()
# test_repository.test_delete()
# test_repository.test_get_all()
