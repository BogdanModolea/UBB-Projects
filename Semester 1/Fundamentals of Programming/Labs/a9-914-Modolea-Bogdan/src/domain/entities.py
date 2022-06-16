from dataclasses import dataclass

@dataclass
class Person:
    # def __init__(self, id, name, phone):
    #     """
    #     Initializing a Person
    #     :param id:
    #     :param name:
    #     :param phone:
    #     """
    #     self.__id = id
    #     self.__name = name
    #     self.__phone = phone
    #
    # @property
    # def id(self):
    #     return self.__id
    #
    # @id.setter
    # def id(self, value):
    #     self.__id = value
    #
    # @property
    # def name(self):
    #     return self.__name
    #
    # @name.setter
    # def name(self, value):
    #     self.__name = value
    #
    # @property
    # def phone(self):
    #     return self.__phone
    #
    # @phone.setter
    # def phone(self, value):
    #     self.__phone = value

    __person_id: int
    name: str
    phone: str

    @property
    def id(self):
        return self.__person_id

    def __str__(self):
        return str(self.id) + " " + str(self.name) + " " + str(self.phone)

@dataclass
class Activity:
    def __init__(self, id, person_id, date, time, description):
        """
        Initializing an activity object
        :param id:
        :param person_id:
        :param date:
        :param time:
        :param description:
        """
        self.__id = id
        self.__person_id = person_id
        self.__date = date
        self.__time = time
        self.__description = description

    @property
    def id(self):
        return self.__id

    @id.setter
    def id(self, value):
        self.__id = value

    @property
    def person_id(self):
        return self.__person_id

    @person_id.setter
    def person_id(self, value):
        self.__person_id = value

    @property
    def date(self):
        return self.__date

    @date.setter
    def date(self, value):
        self.__date = value

    @property
    def time(self):
        return self.__time

    @time.setter
    def time(self, value):
        self.__time = value

    @property
    def description(self):
        return self.__description

    @description.setter
    def description(self, value):
        self.__description = value

    def __str__(self):
        return str(self.id) + " " + str(self.person_id) + " " + self.date + " " + self.time + " " + self.description