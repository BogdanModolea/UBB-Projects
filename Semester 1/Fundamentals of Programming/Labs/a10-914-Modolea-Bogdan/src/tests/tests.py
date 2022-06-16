import copy
from copy import deepcopy
import unittest
from domain.entities import Person
from services.undo_service import UndoService
from services.undo_service import UndoObject
from domain.validators import TestValidator
from repository.repository import Repository
from services.services import Services
from functions import *

test_validator = TestValidator
test_repository_person = Repository(test_validator)
test_repository_activity = Repository(test_validator)
test_service = Services(test_repository_person, test_repository_activity)







def test_update_person():
    test_service.add_person(1, "Bogdan", "0736420118")
    test_service.update_person(1, "Fabian", "0736420118")
    my_list = test_service.get_all_persons()
    assert my_list[0].name == "Fabian"
    assert my_list[0].phone == "0736420118"


def test_get_all_persons():
    my_list = test_service.get_all_persons()
    assert len(my_list) == 1


class PersonServiceTest(unittest.TestCase):
    def setUp(self) -> None:
        test_validator = TestValidator
        test_repository = Repository(test_validator)
        self.test_service_person = Services(test_repository, test_repository)

    def tearDown(self) -> None:
        pass

    def test_add_person(self):
        self.test_service_person.add_person(1, "Bogdan", "0736420118")
        my_list = self.test_service_person.get_all_persons()
        self.assertEqual(my_list[0].name, "Bogdan")
        self.assertEqual(my_list[0].phone, "0736420118")

    # def test_remove_person(self):
    #     self.test_service_person.add_person(22, "Bogdan", "0736420118")
    #     self.test_service_person.add_activity(33, [22], "30.05.2002", "12:07", "Birthday")
    #     self.test_service_person.remove_person(22)
    #     my_list = test_service.get_all_persons()
    #     self.assertEqual(len(my_list), 0)

    def test_get_names_to_print(self):
        self.test_service_person.add_person(1, "Bogdan", "0736420118")
        my_list = self.test_service_person.get_names_to_print("bog")
        self.assertEqual(len(my_list), 1)

    def test_get_phones_to_print(self):
        self.test_service_person.add_person(1, "Bogdan", "0736420118")
        self.test_service_person.add_person(2, "Fabian", "0732495001")
        my_list = self.test_service_person.get_phones_to_print("073")
        self.assertEqual(len(my_list), 2)


def test_add_activity():
    test_service.add_activity(1, [1], "17.05.2020", "07:22", "Fun")
    my_list = test_service.get_all_activities()
    assert my_list[0].date == "17.05.2020"
    assert my_list[0].time == "07:22"

def test_remove_activity():
    test_service.add_activity(2, [1], "17.05.2020", "07:23", "Fun")
    test_service.remove_activity(2)
    test_service.remove_activity(1)
    my_list = test_service.get_all_activities()
    assert my_list == []

def test_update_activity():
    test_service.add_activity(1, [1], "17.05.2020", "07:22", "Fun")
    test_service.update_activity(1, [1], "17.05.2021", "22:07", "Double fun")
    my_list = test_service.get_all_activities()
    assert my_list[0].date == "17.05.2021"
    assert my_list[0].time == "22:07"

def test_get_all_activities():
    my_list = test_service.get_all_activities()
    assert len(my_list) == 1


class ActivityServiceTest(unittest.TestCase):
    def setUp(self) -> None:
        test_validator = TestValidator
        test_repository = Repository(test_validator)
        test_repository_person = Repository(test_validator)
        self.test_service_activity = Services(test_repository_person, test_repository)
        self.test_person_service = Services(test_repository_person, test_repository)

    def tearDown(self) -> None:
        pass

    def test_get_dates_to_print(self):
        self.test_service_activity.add_activity(1, [1], "17.05.2020", "07:22", "Fun")
        my_list = self.test_service_activity.get_dates_to_print("17")
        self.assertEqual(len(my_list), 1)

    def test_get_times_to_print(self):
        self.test_service_activity.add_activity(11, [1], "18.05.2020", "08:22", "Fun")
        self.test_service_activity.add_activity(22, [22], "30.05.2002", "12:05", "Birthday")
        my_list = self.test_service_activity.get_times_to_print("12")
        #self.assertEqual(len(my_list), 0)
        self.assertEqual(my_list[0].id, 22)

    def test_get_descriptions_to_print(self):
        self.test_service_activity.add_activity(111, [111], "17.05.2022", "07:22", "Fun1")
        self.test_service_activity.add_activity(222, [222], "30.05.2022", "12:05", "Fun1ny Birthday")
        my_list = self.test_service_activity.get_descriptions_to_print("Fun1")
        self.assertEqual(len(my_list), 2)

    def test_get_sorted_dates(self):
        self.test_service_activity.add_activity(1111, [1111], "17.05.2023", "07:22", "Fun2")
        self.test_service_activity.add_activity(2222, [2222], "30.05.2032", "12:05", "Fun2ny Birthday")
        self.test_service_activity.add_activity(3333, [3333], "30.05.2032", "05:12", "Birthday Fun2ny")
        my_list = self.test_service_activity.get_sorted_dates("30.05.2032")
        #self.assertEqual(len(my_list), 0)
        self.assertEqual(my_list[0].time, "05:12")
        self.assertEqual(len(my_list), 2)

    def test_get_busiest_day(self):
        self.test_service_activity.add_activity(1122, [1122], "17.05.2020", "07:22", "Fun")
        self.test_service_activity.add_activity(2233, [2233], "30.05.2002", "12:05", "Funny Birthday")
        self.test_service_activity.add_activity(3344, [3344], "30.05.2002", "05:12", "Birthday Funny")
        my_list = self.test_service_activity.get_busiest_day()
        #self.assertEqual(len(my_list), 0)
        self.assertEqual(my_list[0][0], "30.05.2002")
        self.assertEqual(my_list[1][1], 1)

    def test_get_activities_by_name(self):
        self.test_person_service.add_person(11, "Stefan", "0731000111")
        self.test_person_service.add_person(22, "Bogdan", "0732495031")
        self.test_person_service.add_person(33, "Stefan", "0733555111")
        self.test_service_activity.add_activity(12, [11], "17.05.2020", "07:22", "Fun")
        self.test_service_activity.add_activity(23, [22], "30.05.2002", "12:05", "Funny Birthday")
        self.test_service_activity.add_activity(34, [33], "30.05.2002", "05:12", "Birthday Funny")
        my_persons = self.test_person_service.get_all_persons()
        my_list = self.test_service_activity.get_activities_by_name("Stefan", my_persons)
        self.assertEqual(len(my_list), 2)
        self.assertEqual(my_list[0].id, 12)


class UndoServiceTest(unittest.TestCase):
    def setUp(self) -> None:
        test_validator = TestValidator
        test_repository = Repository(test_validator)
        test_repository_person = Repository(test_validator)
        self.test_service = Services(test_repository_person, test_repository)

    def tearDown(self) -> None:
        pass

    def test_undo(self):
        self.test_service.add_person(1, "Bogdan", "0736420118")
        my_list = self.test_service.get_all_persons()
        self.assertEqual(my_list[0].name, "Bogdan")
        self.assertEqual(my_list[0].phone, "0736420118")
        self.test_service.undo()
        my_list = self.test_service.get_all_persons()
        self.assertEqual(len(my_list), 0)

    def test_redo(self):
        self.test_service.add_person(1, "Bogdan", "0736420118")
        my_list = self.test_service.get_all_persons()
        self.assertEqual(my_list[0].name, "Bogdan")
        self.assertEqual(my_list[0].phone, "0736420118")
        self.test_service.undo()
        my_list = self.test_service.get_all_persons()
        self.assertEqual(len(my_list), 0)
        self.test_service.redo()
        my_list = self.test_service.get_all_persons()
        self.assertEqual(my_list[0].name, "Bogdan")
        self.assertEqual(my_list[0].phone, "0736420118")


class RepositoryTest(unittest.TestCase):
    def setUp(self) -> None:
        test_validator = TestValidator
        self._repo = Repository(test_validator)

    def tearDown(self) -> None:
        pass

    def test_find_by_id(self):
        now_entity = Person(1, "Bogdan", "0736420119")
        self._repo.save(now_entity)
        entity = self._repo.find_by_id(2)
        self.assertEqual(entity, None)
        entity = self._repo.find_by_id(1)
        self.assertEqual(entity.name, "Bogdan")

    def test_save(self):
        entity = Person(2, "Bogdan", "0736420118")
        self._repo.save(entity)
        entity = self._repo.find_by_id(2)
        self.assertEqual(entity.name, "Bogdan")

    def test_update(self):
        entity = Person(3, "Bogdan", "0736420119")
        self._repo.save(entity)
        self._repo.update(3, Person(3, "Fabian", "0736420118"))
        entity = self._repo.find_by_id(3)
        self.assertEqual(entity.name, "Fabian")

    def test_delete(self):
        entity = Person(4, "Bogdan", "0736420118")
        self._repo.save(entity)
        self._repo.delete_by_id(4)
        assert self._repo.find_by_id(4) == None

    def test_get_all(self):
        entity = Person(5, "Bogdan", "0736420118")
        self._repo.save(entity)
        my_list = self._repo.get_all()
        assert my_list[0].name == "Bogdan"

test_update_person()
test_get_all_persons()

test_add_activity()
test_remove_activity()
test_update_activity()
test_get_all_activities()



class TestUtilities(unittest.TestCase):
    def setUp(self) -> None:
        self._container = Container()

    def tearDown(self) -> None:
        pass

    def test_sort(self):
        my_list = [3, 1, 2, 10]
        new_list = sort(my_list)
        self.assertEqual(new_list, [1, 2, 3, 10])
        new_list = sort(my_list, reverse = True)
        self.assertEqual(new_list, [10, 3, 2, 1])
        my_list = [12, 34, 54, 2, 3]
        new_list = sort(my_list)
        self.assertEqual(new_list, [2, 3, 12, 34, 54])

    def test_filer(self):
        my_list = [[1, 1], [1, 2], [1, 3], [1, 1]]
        new_list = some_filter(my_list, lambda x: x[1] == 1)
        self.assertEqual(len(new_list), 2)

    def test_set_item(self):
        self._container._something[0] = 1
        self._container.__setitem__(0, 99)
        self.assertEqual(self._container._something[0], 99)

    def test_del_item(self):
        self._container._something[0] = 1
        self._container._something[1] = 2
        self._container.__delitem__(0)
        self.assertEqual(len(self._container._something), 1)

    def test_get_item(self):
        self._container._something[0] = 1
        self.assertEqual(self._container.__getitem__(0), 1)

    def test_next(self):
        self._container._something[0] = 1
        self._container._something[1] = 2
        self._container._something[2] = 3
        self._container.__iter__()
        self.assertEqual(self._container._something[self._container.__next__()], 3)