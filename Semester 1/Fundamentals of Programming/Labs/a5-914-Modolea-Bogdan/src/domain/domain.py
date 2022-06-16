class Student:
    def __init__(self, _id=0, name="n/a", group=0):
        """
        Create a new student
        :param _id:
        :param name:
        :param group:
        """
        self.__id = _id
        self.__name = name
        self.group = group

    @property
    def id(self):
        """
        :return: The id of the student
        """
        return self.__id

    @property
    def name(self):
        """
        :return: The name of the student
        """
        return self.__name

    @property
    def group(self):
        """
        :return: The group of the student
        """
        return self.__group

    @group.setter
    def group(self, new_group):
        if new_group < 0:
            raise ValueError("Group must be >= 0")
        self.__group = new_group


def test_students():
    stud = Student(3166, "Modolea Bogdan", 914)
    assert stud.id == 3166
    assert stud.name == "Modolea Bogdan"
    assert stud.group == 914

    try:
        stud.group -= 1000
        assert False
    except ValueError as ve:
        assert str(ve) == "Group must be >= 0"

    try:
        stud = Student(300, "Modolea Bogdan", -914)
        assert False
    except ValueError as ve:
        assert str(ve) == "Group must be >= 0"


test_students()
