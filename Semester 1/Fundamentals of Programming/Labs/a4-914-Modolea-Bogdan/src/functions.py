"""
  Program functionalities module
"""

                                                        ######################################
                                                        ###  Write non-UI functions below  ###
                                                        ######################################





from math import sqrt


def get_real_list(numbers):
    """
    :return: The real part of a number
    """
    return int(numbers[0])


def get_imag_list(numbers):
    """
    :return: The imaginary part of a number
    """
    return int(numbers[1])


def get_modulo(real, imag):
    """
    :return: The modulo of a real number
    """
    real = int(real)
    imag = int(imag)
    return sqrt(real * real + imag * imag)


def get_real(number):
    """
    :param number: A string number a+bi
    :return: The real part as a string
    """
    pos = number.find("+")
    if pos == -1:
        pos = number.find("-", 1)
    if pos == -1:
        if number.find("i") == -1:
            pos = len(number)
        else:
            return "0"
    return number[:pos]


def get_imag(number):
    """
    :param number: A string number a+bi
    :return: The imaginary part as a string
    """
    pos = number.find("+")
    if pos == -1:
        pos = number.find("-", 1) - 1
    if pos == -2:
        if number.find("i") != -1:
            if number.find("i") == 1:
                if number[number.find("i") - 1] == "-":
                    return "-1i"
                else:
                    pos = -1
            elif number.find("i") > 1:
                pos = -1
            else:
                return "1i"
        else:
            return "0i"
    else:
       if number[pos] == "+" and number[pos + 1] == "i":
           return "1i"
       elif number[pos + 1] == "-" and number[pos + 2] == "i":
           return "-1i"
    return number[(pos + 1):]


def create_number(real, imag):
    """
    :return: A number having real part {real} and imaginary part {imag}
    """
    return [real, imag]


def add_number(numbers, all_numbers, number):
    """
    Adding a number as a last element by appending it
    """
    all_numbers.append(numbers[:])
    real = get_real(number)
    real = int(real)
    imag = get_imag(number)
    imag = imag.replace("i", "")
    imag = int(imag)
    new_number = create_number(real, imag)
    numbers.append(new_number)


def insert_number(numbers, all_numbers, number, pos):
    """
    Inserting a number at a specific position
    """
    all_numbers.append(numbers[:])
    real = get_real(number)
    real = int(real)
    imag = get_imag(number)
    imag = imag.replace("i", "")
    imag = int(imag)
    new_number = create_number(real, imag)
    pos = int(pos)
    numbers.insert(pos, new_number)


def remove_number(numbers, all_numbers, position):
    """
    Removing a number from a specific position
    """
    all_numbers.append(numbers[:])
    position = int(position)
    numbers.pop(position)


def remove_number_pos(numbers, all_numbers, start, stop):
    """
    Removing numbers[start], numbers[start + 1], ..., numbers[stop]
    """
    all_numbers.append(numbers[:])
    start = int(start)
    start = max(0, start)
    stop = int(stop)
    stop = min(stop, len(numbers) - 1)
    if start <= stop:
        for i in range(start, stop + 1):
            numbers.pop(start)
    else:
        return


def replace_number(numbers, all_numbers, old_number, new_number):
    """
    We will go through all the values of the list, and if we meet the searched item we will replace it
    :param numbers: The list of numbers
    :param old_number: The number I want to be replace
    :param new_number: The number I want to replace the old one with
    :return: The new list of numbers
    """
    all_numbers.append(numbers[:])
    new_real = int(get_real(new_number))
    new_imag = get_imag(new_number)
    new_imag = new_imag.replace("i", "")
    new_imag = int(new_imag)
    real = int(get_real(old_number))
    imag = get_imag(old_number)
    imag = imag.replace("i", "")
    imag = int(imag)
    l = []
    for number in numbers:
        if real == get_real_list(number) and imag == get_imag_list(number):
            new = create_number(new_real, new_imag)
            l.append(new)
        else:
            l.append(number)
    numbers = l

    return numbers


def get_command_args(line):
    """
    add 4+2i
    insert 1+1i at 1
    print
    get_command_args("print") -> "print"
    """
    pos = line.find(" ")
    if pos == -1:
        return line, []
    cmd = line[:pos]
    args = line[(pos + 1):]
    args = args.strip()
    args = args.split(" ")

    l = []
    for arg in args:
        if arg != "":
            l.append(arg.strip())
    args = l

    # print(args)

    try:
        if cmd == "insert":
            args.remove("at")
        elif cmd == "remove":
            if len(args) > 1:
                cmd = cmd + "1"
                args.remove("to")
        elif cmd == "replace":
            args.remove("with")
        elif cmd == "list":
            if len(args) > 0:
                if args[0] == "real":
                    cmd = cmd + "1"
                    args.remove("to")
                    args.remove("real")
                elif args[0] == "modulo":
                    if args[1] == ">":
                        cmd = cmd + "2"
                        args.remove(">")
                    elif args[1] == "=":
                        cmd = cmd + "3"
                        args.remove("=")
                    elif args[1] == "<":
                        cmd = cmd + "4"
                        args.remove("<")
                    args.remove("modulo")
        elif cmd == "sum":
            args.remove("to")
        elif cmd == "product":
            args.remove("to")
        elif cmd == "filter":
            if args[0] == "real":
                return cmd, []
            if args[0] == "modulo":
                if args[1] == ">":
                    cmd = cmd + "1"
                    args.remove(">")
                elif args[1] == "=":
                    cmd = cmd + "2"
                    args.remove("=")
                elif args[1] == "<":
                    cmd = cmd + "3"
                    args.remove("<")
                args.remove("modulo")
        return cmd, args
    except:
        return "wrong", []


def get_sum(numbers, start, stop):
    """
    Computin the sum of complex numbers between 2 positions
    :param numbers: The list numbers where I want to compute the sum between 2 positions
    :param start: The initial position for the sum
    :param stop: The last position for the sum
    :return: The real part and the imaginary part of the sum
    """
    real_sum = 0
    imag_sum = 0
    start = int(start)
    start = max(0, start)
    stop = int(stop)
    stop = min(stop, len(numbers) - 1)
    for i in range(start, stop + 1):
        real = get_real_list(numbers[i])
        imag = get_imag_list(numbers[i])
        real_sum += real
        imag_sum += imag
    return real_sum, imag_sum


def get_product(numbers, start, stop):
    """
    Computin the product of complex numbers between 2 positions
    :param numbers: The list numbers where I want to compute the product between 2 positions
    :param start: The initial position for the product
    :param stop: The last position for the product
    :return: The real part and the imaginary part of the product
    """
    start = int(start)
    start = max(start, 0)
    stop = int(stop)
    stop = min(stop, len(numbers) - 1)
    if start == stop:
        return get_real_list(numbers[start]), get_imag_list(numbers[start])
    real1 = get_real_list(numbers[start])
    imag1 = get_imag_list(numbers[start])
    real2 = get_real_list(numbers[start + 1])
    imag2 = get_imag_list(numbers[start + 1])
    real = (real1 * real2 - imag1 * imag2)
    imag = (real1 * imag2 + imag1 * real2)

    for i in range(start + 2, stop + 1):
        real1 = real
        imag1 = imag
        real2 = get_real_list(numbers[i])
        imag2 = get_imag_list(numbers[i])
        real = (real1 * real2 - imag1 * imag2)
        imag = (real1 * imag2 + imag1 * real2)

    return real, imag


def undo(numbers, all_numbers):
    """
    Computing the last list of complex numbers that I had before applying a specific transformation
    :param numbers: The current list of complex numbers
    :param all_numbers: A list containing all previous "steps" (all list of numbers that we had in history)
    :return: The last list of numbers that I had before applying a specific transformation and a corresponding message
    """
    l = []
    try:
        l = all_numbers[-1]
        all_numbers.pop(-1)
        return l, "You have successfully returned to the previous step"
    except:
        return numbers, "Sorry, but you've reached the first step"


def filter_real(numbers, all_numbers):
    """
    Replacing all numbers in the list with the ones that have imaginary part = 0
    :param numbers: The list of numbers
    :param all_numbers: The list where I store all lists of numbers in the history of the program
    """
    all_numbers.append(numbers[:])
    numbers[:] = [i for i in numbers if get_imag_list(i) == 0]


def filter_modulo_smaller(numbers, all_numbers, index):
    """
    Replacing all numbers in the list with the ones that have modulo < that a given number
    :param numbers: The list of numbers
    :param all_numbers: The list where I store all lists of numbers in the history of the program
    """
    all_numbers.append(numbers[:])
    index = int(index)
    numbers[:] = [i for i in numbers if get_modulo(get_real_list(i), get_imag_list(i)) < index]


def filter_modulo_equal(numbers, all_numbers, index):
    """
    Replacing all numbers in the list with the ones that have modulo = that a given number
    :param numbers: The list of numbers
    :param all_numbers: The list where I store all lists of numbers in the history of the program
    """
    all_numbers.append(numbers[:])
    index = int(index)
    numbers[:] = [i for i in numbers if get_modulo(get_real_list(i), get_imag_list(i)) == index]


def filter_modulo_bigger(numbers, all_numbers, index):
    """
    Replacing all numbers in the list with the ones that have modulo > that a given number
    :param numbers: The list of numbers
    :param all_numbers: The list where I store all lists of numbers in the history of the program
    """
    all_numbers.append(numbers[:])
    index = int(index)
    numbers[:] = [i for i in numbers if get_modulo(get_real_list(i), get_imag_list(i)) > index]


def auto_add_number(numbers, number):
    """
    Adding a number as a last element by appending it
    """
    real = get_real(number)
    real = int(real)
    imag = get_imag(number)
    imag = imag.replace("i", "")
    imag = int(imag)
    new_number = create_number(real, imag)
    numbers.append(new_number)


def auto_generate_numbers(numbers):
    """
    Just adding some random numbers to the list
    :param numbers: My list of numbers
    :return:
    """
    number = "1+1i"
    auto_add_number(numbers, number)

    number = "2-2i"
    auto_add_number(numbers, number)

    number = "-3+3i"
    auto_add_number(numbers, number)

    number = "-4-4i"
    auto_add_number(numbers, number)

    number = "2+0i"
    auto_add_number(numbers, number)

    number = "-3+0i"
    auto_add_number(numbers, number)

    number = "0-1i"
    auto_add_number(numbers, number)

    number = "0+7i"
    auto_add_number(numbers, number)

    number = "3+3i"
    auto_add_number(numbers, number)

    number = "4+5i"
    auto_add_number(numbers, number)









                                                            #############################
                                                            ###         Tests         ###
                                                            #############################







def test_command_args():
    """
    Testing some stuff regarding the command that I read from the keyboard
    """
    cmd, args = get_command_args("print")
    assert cmd == "print" and args == []
    cmd, args = get_command_args("insert 3+14i at 0")
    assert cmd == "insert" and args == ["3+14i", "0"]
    cmd, args = get_command_args("remove 1")
    assert cmd == "remove" and args == ["1"]
    cmd, args = get_command_args("remove 1 to 3")
    assert cmd == "remove1" and args == ["1", "3"]
    cmd, args = get_command_args("replace 1+3i with 5-3i")
    assert cmd == "replace" and args == ["1+3i", "5-3i"]
    cmd, args = get_command_args("list")
    assert cmd == "list" and args == []
    cmd, args = get_command_args("list real 1 to 5")
    assert cmd == "list1" and args == ["1", "5"]
    cmd, args = get_command_args("list modulo < 10")
    assert cmd == "list4" and args == ["10"]
    cmd, args = get_command_args("list modulo = 5")
    assert cmd == "list3" and args == ["5"]


def test_create_number():
    """
    Testing if the function create_number(param1, param2) returns a list with that numbers
    :return:
    """
    assert create_number(3, 1) == [3, 1]
    assert create_number(-1, -1) == [-1, -1]
    assert create_number(3, -1) == [3, -1]
    assert create_number(-3, 1) == [-3, 1]


def test_get_modulo():
    """
    Testing that the function computes the modulo correctly
    :return:
    """
    assert get_modulo("3", "4") == 5
    assert get_modulo("1", "3") == 3.1622776601683795
    assert get_modulo("0", "1") == 1


def test_remove_number():
    """"
    Checking both the remove functions
        - remove a position
        - remove between 2 positions
    """
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    test = []
    remove_number(l, test, 3)
    assert l == [[3, 4], [3, 3], [3, 3]]

    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    test = []
    remove_number_pos(l, test, 0, 2)
    assert l == [[2, 2]]


def test_replace_number():
    """
    This one should replace all numbers in the list equal to a given one with another one
    :return:
    """
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    test = []
    l = replace_number(l, test, "3+3i", "-10-10i")
    assert l == [[3, 4], [-10, -10], [-10, -10], [2, 2]]

    l = [[-3, 4], [3, 3], [3, 3], [2, 2]]
    test = []
    l = replace_number(l, test, "-3+4i", "10+4i")
    assert l == [[10, 4], [3, 3], [3, 3], [2, 2]]


def test_insert_number():
    """
    Inserting a number at a specific position
    :return:
    """
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    test = []
    insert_number(l, test, "-10-10i", 0)
    assert l[0] == [-10, -10]

    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    test = []
    insert_number(l, test, "-10-10i", 1)
    assert l == [[3, 4], [-10, -10], [3, 3], [3, 3], [2, 2]]


def test_add_number():
    """
    Adding a number at the final of the list
    :return:
    """
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    test = []
    add_number(l, test, "3+3i")
    assert l == [[3, 4], [3, 3], [3, 3], [2, 2], [3, 3]]


def test_sum():
    """
    Testing if the sum between 2 positions is computed correctly
    :return:
    """
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    assert get_sum(l, 0, 2) == (9, 10)

    l = [[0, 4], [-3, 3], [3, 3], [2, 2]]
    assert get_sum(l, 1, 2) == (0, 6)


def test_product():
    """
    Testing if the product between 2 positions is computed correctly
    :return:
    """
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    assert get_product(l, 0, 1) == (-3, 21)

    l = [[3, 4], [-1, 0], [2, 2], [3, -3]]
    assert get_product(l, 1, 3) == (-12, 0)


def test_filter():
    """
    Testing all the filter functions:
        - filtering real numbers -> getting only the ones that have imaginary part = 0
        - filter modulo:
            * bigger than given number
            * equal to a given number
            * smaller than a given number
    :return:
    """
    test = []
    l = [[3, 0], [3, 0], [3, 2], [2, 2]]
    filter_real(l, test)
    assert l == [[3, 0], [3, 0]]
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    filter_real(l, test)
    assert l == []

    l = [[3, 4], [-4, 3], [3, 3], [2, 2]]
    filter_modulo_equal(l, test, 5)
    assert l == [[3, 4], [-4, 3]]

    l = [[3, -4], [12, 3], [3, 3], [2, 2]]
    filter_modulo_bigger(l, test, 5)
    assert l == [[12, 3]]

    l = [[3, -4], [12, 3], [3, 3], [2, 2]]
    filter_modulo_smaller(l, test, 12)
    assert l == [[3, -4], [3, 3], [2, 2]]


def test_undo():
    """
    Check if my function gets the user to the list of numbers that I had before applying last transformation
    :return:
    """
    undo_list = []
    l = [[3, 0], [3, 0], [3, 2], [2, 2]]
    filter_real(l, undo_list)
    test, msg = undo(l, undo_list)
    assert test == [[3, 0], [3, 0], [3, 2], [2, 2]] and msg == 'You have successfully returned to the previous step'
    filter_real(l, undo_list)
    filter_modulo_bigger(l, undo_list, 5)
    test, msg = undo(l, undo_list)
    assert test == [[3, 0], [3, 0]] and msg == 'You have successfully returned to the previous step'


