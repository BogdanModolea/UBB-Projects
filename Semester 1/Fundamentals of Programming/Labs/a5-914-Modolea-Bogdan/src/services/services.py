from domain.domain import Student
import random


class ServicesException(Exception):
    pass


class Services:
    def __init__(self):
        self.__data = dict()

    def add(self, entity):
        """
        Adding a new student to the student list
        :param entity: The student I want to add with respect to to his id
        :return:
        """
        self.__data[entity.id] = entity

    def __getitem__(self, id):
        """
        :param id: The id for the student I want to return
        :return: The student with a specific id
        """
        return self.__data[id]

    def auto_add(self, fr):
        names = ["Bogdan Modolea", "Alex", "Jankos", "Fabian", "Laura", "Lavinia", "Anna", "Robert Vulpe", "Ionut",
                 "Edy Nastase", "Ingrid", "Razvan", "Dana", "Catalin Modolea", "Martin Larsson", "Elon Musk",
                 "Gef Bezos", "Bil Gheitz", "Traian Basescu", "Vadim", "Marius", "Rafael", "Livia"]

        used_names = []

        while len(fr) < 10:
            id = random.randint(1, 1000)
            name = random.choice(names)
            group = random.randint(1, 1000)
            if not id in fr and not name in used_names:
                self.add(Student(id, name, group))
                fr.append(id)
                used_names.append(name)

        # self.add(Student(3166, "Modolea Bogdan", 914))
        # self.add(Student(123, "Alex", 917))
        # self.add(Student(62, "Jankos", 112))
        # self.add(Student(3109, "Fabian", 141))
        # self.add(Student(3000, "Laura", 941))
        # self.add(Student(3001, "Lavinia", 914))
        # self.add(Student(888, "Robert", 313))
        # self.add(Student(999, "Ionut", 313))
        # self.add(Student(54, "Ingrid", 531))
        # self.add(Student(11, "Edy", 1))

    def filter_students(self, students, all_students, fr, group, all_fr):
        """
        :param students: List of students
        :param all_students: List of lists of students (all students that we had during the programme)
        :param fr: The list of ids
        :param index: The group we want to filter by
        :return: The new list of students after filter, the new history students list, the new ids list and the new history list of all ids
        """
        group = int(group)
        all_students.append(students)
        all_fr.append(fr[:])

        l = Services()
        now = []
        for each_id in fr:
            if students[each_id].group != group:
                l.add(Student(students[each_id].id, students[each_id].name, students[each_id].group))
                now.append(students[each_id].id)
        return l, all_students, now, all_fr

    def undo(self, students, all_students, fr, all_fr):
        """
        Computing the last list of students that I had before applying a specific transformation
        :param students: The current list of complex numbers
        :param all_students: A list containing all previous "steps" (all list of numbers that we had in history)
        :return: The last list of numbers that I had before applying a specific transformation and a corresponding message
        """
        try:
            now = []
            l = all_students[-1]
            all_students.pop(-1)
            now = all_fr[-1]
            all_fr.pop(-1)
            return l, now, "\nYou have successfully returned to the previous step"
        except:
            return students, fr, "\nSorry, but you've reached the first step"


def test_add():
    serv = Services()
    l = []
    serv.add(Student(3166, "Modolea Bogdan", 914))
    assert serv[3166].group == 914


def test_filter():
    student = Services()
    service = Services()
    student.add(Student(3166, "Modolea Bogdan", 914))
    student.add(Student(123, "Alex", 917))
    student.add(Student(62, "Jankos", 112))
    all_students = []
    fr = [3166, 123, 62]
    index = "917"
    all_fr = []
    student, all_students, fr, all_fr = service.filter_students(student, all_students, fr, index, all_fr)
    assert student[62].name == "Jankos"
    assert fr == [3166, 62]
    assert all_fr == [[3166, 123, 62]]


def test_undo():
    student = Services()
    service = Services()
    all_fr = []
    all_students = []
    fr = []
    student.add(Student(3166, "Modolea Bogdan", 914))
    all_students.append(student)
    all_fr.append(fr[:])
    fr.append(3166)
    student.add(Student(123, "Alex", 917))
    all_students.append(student)
    all_fr.append(fr[:])
    fr.append(123)
    student.add(Student(62, "Jankos", 112))
    all_students.append(student)
    all_fr.append(fr[:])
    fr.append(62)

    l = student
    student, fr, msg = service.undo(l, all_students, fr, all_fr)
    assert msg == "\nYou have successfully returned to the previous step"
    assert fr == [3166, 123]

    index = "917"
    student, all_students, fr, all_fr = service.filter_students(student, all_students, fr, index, all_fr)
    assert student[3166].name == "Modolea Bogdan"
    assert fr == [3166]
    assert all_fr == [[], [3166], [3166, 123]]

    student, fr, msg = service.undo(l, all_students, fr, all_fr)
    assert msg == "\nYou have successfully returned to the previous step"
    assert fr == [3166, 123]


test_add()
test_filter()
test_undo()
