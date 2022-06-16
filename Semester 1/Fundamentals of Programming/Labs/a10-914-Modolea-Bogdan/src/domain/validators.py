import datetime
import time

class PersonException(Exception):
    pass

class PersonValidatorException(PersonException):
    pass

class ActivityException(Exception):
    pass

class ActivityValidatorException(ActivityException):
    pass

class PersonValidator:
    @staticmethod
    def validate(person):
        """
        Checking if a person is valid
        :param person:
        :return:
        """
        if len(person.phone) != 10:
            raise PersonValidatorException("There was some error with the phone number")

def isTimeFormat(input):
    """
    Returning time format as HH:MM
    :param input:
    :return:
    """
    try:
        time.strptime(input, '%H:%M')
        return True
    except ValueError:
        return False

class ActivityValidator:
    @staticmethod
    def validate(activity):
        """
        Checking if an activity is valid
        :param activity:
        :return:
        """
        if len(activity.description) == 0:
            raise ActivityValidatorException("There was some error with the description")
        format = "%d.%m.%Y"
        try:
            datetime.datetime.strptime(activity.date, format)
        except Exception:
            raise ActivityValidatorException("Date is not correct")
        if not isTimeFormat(activity.time):
            raise ActivityValidatorException("Time is not correct")


class TestValidator:
    @staticmethod
    def validate(test):
        return True