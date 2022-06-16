######################################
###  Write non-UI functions below  ###
######################################

import random
import os
import time
from datetime import datetime
from threading import Timer

def get_pos0(number):
    return number % 10

def get_pos1(number):
    return (number // 10) % 10

def get_pos2(number):
    return (number // 100) % 10

def get_pos3(number):
    return (number // 1000) % 10


def get_first(number):
    return number[3]

def get_second(number):
    return number[2]

def get_third(number):
    return number[1]

def get_fourth(number):
    return number[0]

def auto_generate_number():
    """
    We just compute a random number and we check using used_digits if the computere already had used a digit or not
    :return: A random valid number
    """
    digits = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    used_digits = []
    number = []

    while len(number) < 4:
        dig = random.choice(digits)
        if not dig in used_digits:
            if len(number) == 0:
                if dig != 0:
                    used_digits.append(dig)
                    number.append(dig)
            else:
                used_digits.append(dig)
                number.append(dig)

    return number


def valid_number(number):
    used_numbers = []
    d0 = get_pos0(number)
    used_numbers.append(d0)

    d1 = get_pos1(number)
    if d1 in used_numbers:
        return False
    used_numbers.append(d1)

    d2 = get_pos2(number)
    if d2 in used_numbers:
        return False
    used_numbers.append(d2)

    d3 = get_pos3(number)
    if d3 in used_numbers or d3 == 0:
        return False
    used_numbers.append(d3)


def generate_codes_runners(my_number, computer):
    mine = []
    mine.append(get_pos3(my_number))
    mine.append(get_pos2(my_number))
    mine.append(get_pos1(my_number))
    mine.append(get_pos0(my_number))

    codes = 0
    runners = 0

    d00 = get_first(mine)
    d10 = get_first(computer)

    d01 = get_second(mine)
    d11 = get_second(computer)

    d02 = get_third(mine)
    d12 = get_third(computer)

    d03 = get_fourth(mine)
    d13 = get_fourth(computer)

    if d00 == d10:
        codes += 1
    if d01 == d11:
        codes += 1
    if d02 == d12:
        codes += 1
    if d03 == d13:
        codes += 1

    my_list = [d11, d12, d13]
    if d00 in my_list:
        runners += 1

    my_list = [d10, d12, d13]
    if d01 in my_list:
        runners += 1

    my_list = [d10, d11, d13]
    if d02 in my_list:
        runners += 1

    my_list = [d10, d11, d12]
    if d03 in my_list:
        runners += 1

    return codes, runners


