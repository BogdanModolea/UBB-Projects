from services.services import Services, ServicesException
from domain.domain import Student

class UI:
    def __init__(self):
        self._services = Services()
        self._domain = Student()

    def print_all_students(self, students, fr):
        print("-------------------------------------------------")
        print("| ID   |   NAME               |      GROUP      |")
        print("-------------------------------------------------")
        for each_id in fr:
                print("| " + str(students[each_id].id) + " " * (5 - len(str(students[each_id].id))) + "|   " + str(students[each_id].name.title()) + " " * (19 - len(students[each_id].name)) + "|      " + str(students[each_id].group) + " " * (11 - len(str(students[each_id].group))) + "|")
        print("-------------------------------------------------")


    def print_menu(self):
        print("\n1 Read a new student \n"
              "2 Print all students \n"
              "3 Filter the list of students by deleting a group \n"
              "4 Undo \n"
              "x Exit")

    def main_menu(self):
        students_list = Services()
        fr = []
        students_list.auto_add(fr)
        all_students = []
        all_students.append(students_list)
        all_fr = []
        while True:
            self.print_menu()
            line = input('Enter a command: ')
            if line == 'x':
                break
            try:
                opt = "y"
                while line == "1":
                    try:
                        if opt == "y":
                            id = input("What is the id of the student you want to add? ")
                            id = int(id)
                            name = input("What is the name of the student you want to add? ")
                            group = input("What is the group of the student you want to add? ")
                            group = int(group)
                            if not id in fr:
                                all_students.append(students_list)
                                students_list.add(Student(id, name, group))
                                all_fr.append(fr[:])
                                fr.append(id)
                                opt = "n"
                            else:
                                opt = input("\nThere was already a student introduced with that ID. Do you still want to add a student? [y/n] ")
                        else:
                            break
                    except ServicesException:
                        opt = input("\nThere was already a student introduced with that ID. Do you still want to add a student? [y/n] ")
                if line == "2":
                    self.print_all_students(students_list, fr)
                elif line == "3":
                    grp = input("What group would you like to remove? ")
                    students_list, all_students, fr, all_fr = self._services.filter_students(students_list, all_students, fr, grp, all_fr)
                elif line == "4":
                    l = students_list
                    students_list, fr, msg = self._services.undo(l, all_students, fr, all_fr)
                    print(msg)

            except ValueError as ve:
                print("\nThe following exception was received:", ve)
            except TypeError as te:
                print("\nSomething went wrong:", te)


ui = UI()
ui.main_menu()