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
    return number[:pos]


def get_imag(number):
    """
    :param number: A string number a+bi
    :return: The imaginary part as a string
    """
    pos = number.find("+")
    if pos == -1:
        pos = number.find("-", 1) - 1
    return number[(pos + 1):]


def create_number(real, imag):
    """
    :return: A number having real part {real} and imaginary part {imag}
    """
    return [real, imag]


def add_number(numbers, number):
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


def insert_number(numbers, number, pos):
    """
    Inserting a number at a specific position
    """
    #if pos <= len(numbers):
    real = get_real(number)
    real = int(real)
    imag = get_imag(number)
    imag = imag.replace("i", "")
    imag = int(imag)
    new_number = create_number(real, imag)
    pos = int(pos)
    numbers.insert(pos, new_number)
    #else:
      #  print("Sorry :( Something went wrong!")


def remove_number(numbers, position):
    """
    Removing a number from a specific position
    """
    position = int(position)
    numbers.pop(position)


def remove_number_pos(numbers, start, stop):
    """
    Removing numbers[start], numbers[start + 1], ..., numbers[stop]
    """
    start = int(start)
    start = max(0, start)
    stop = int(stop)
    stop = min(stop, len(numbers) - 1)
    if start <= stop:
        for i in range(start, stop + 1):
            numbers.pop(start)
    else:
        #print("Something went wrong")
        return


def replace_number(numbers, old_number, new_number):
    """
    We will go through all the values of the list, and if we meet the searched item we will replace it
    :param numbers: The list of numbers
    :param old_number: The number I want to be replace
    :param new_number: The number I want to replace the old one with
    :return: The new list of numbers
    """
    new_real = int(get_real(new_number))
    new_imag = get_imag(new_number)
    new_imag = new_imag.replace("i", "")
    new_imag = int(new_imag)
    real = int(get_real(old_number))
    imag = get_imag(old_number)
    imag = imag.replace("i", "")
    imag = int(imag)
    #print(type(new_real), type(new_imag), type(real), type(imag))
    l = []
    for number in numbers:
        if real == get_real_list(number) and imag == get_imag_list(number):
            new = create_number(new_real, new_imag)
            l.append(new)
        else:
            l.append(number)
    #print(l)
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

    #print(args)


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
        return cmd, args
    except:
        return "wrong", []












#############################
###         Tests         ###
#############################


def test_command_args():
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
    assert create_number(3, 1) == [3, 1]
    assert create_number(-1, -1) == [-1, -1]
    assert create_number(3, -1) == [3, -1]
    assert create_number(-3, 1) == [-3, 1]


def test_get_modulo():
    assert get_modulo("3", "4") == 5
    assert get_modulo("1", "3") == 3.1622776601683795
    assert get_modulo("0", "1") == 1


def test_remove_number():
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    remove_number(l, 3)
    assert l == [[3, 4], [3, 3], [3, 3]]

    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    remove_number_pos(l, 0, 2)
    assert l == [[2, 2]]


def test_replace_number():
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    l = replace_number(l, "3+3i", "-10-10i")
    assert l == [[3, 4], [-10, -10], [-10, -10], [2, 2]]

    l = [[-3, 4], [3, 3], [3, 3], [2, 2]]
    l = replace_number(l, "-3+4i", "10+4i")
    assert l == [[10, 4], [3, 3], [3, 3], [2, 2]]


def test_insert_number():
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    insert_number(l, "-10-10i", 0)
    assert l[0] == [-10, -10]

    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    insert_number(l, "-10-10i", 1)
    assert l == [[3, 4], [-10, -10], [3, 3], [3, 3], [2, 2]]


def test_add_number():
    l = [[3, 4], [3, 3], [3, 3], [2, 2]]
    add_number(l, "3+3i")
    assert l == [[3, 4], [3, 3], [3, 3], [2, 2], [3, 3]]









def auto_generate_numbers(numbers):
    number = "1+1i"
    add_number(numbers, number)

    number = "2-2i"
    add_number(numbers, number)

    number = "-3+3i"
    add_number(numbers, number)

    number = "-4-4i"
    add_number(numbers, number)

    number = "2+0i"
    add_number(numbers, number)

    number = "-3+0i"
    add_number(numbers, number)

    number = "0-1i"
    add_number(numbers, number)

    number = "0+7i"
    add_number(numbers, number)

    number = "3+3i"
    add_number(numbers, number)

    number = "4+5i"
    add_number(numbers, number)













###########################################
###  Write the command-driven UI below  ###
###########################################


def print_cmds(cmd_dict):
    print(*list(cmd_dict.keys()), "exit", sep="\n")


def print_all_numbers(numbers):
    """
    Printing all exising complex numbers in the list
    :param numbers:
    """

    ind = 0
    if len(numbers) >= 1:
        for lst in numbers:
            real = get_real_list(lst)
            imag = get_imag_list(lst)
            if real == 0:
                if imag > 0:
                    if imag == 1:
                        print("z" + str(ind) + " = i")
                    else:
                        print("z" + str(ind) + " = " + str(imag) + "i")
                elif imag == 0:
                    print("z" + str(ind) + " = 0")
                else:
                    if imag == -1:
                        print("z" + str(ind) + " = -i")
                    else:
                        print("z" + str(ind) + " = " + str(imag) + "i")
            elif imag < 0:
                if imag == -1:
                    print("z" + str(ind) + " = " + str(real) + "-i")
                else:
                    print("z" + str(ind) + " = " + str(real) + str(imag) + "i")
            elif imag == 0:
                print("z" + str(ind) + " = " + str(real))
            else:
                if imag == 1:
                    print("z" + str(ind) + " = " + str(real) + "+i")
                else:
                    print("z" + str(ind) + " = " + str(real) + "+" + str(imag) + "i")
            ind += 1
    else:
        print("\nThere are not enough values to be displayed")


def print_all_real(numbers, start, stop):
    start = int(start)
    stop = int(stop)
    index = 0
    for i in range(start, stop + 1):
        real = get_real_list(numbers[i])
        imag = get_imag_list(numbers[i])
        if imag == 0:
            print("z" + str(index) + " = " + str(real))
            index += 1


def print_all_bigger(numbers, index):
    ind = 0
    index = int(index)
    check = False
    if len(numbers) >= 1:
        for lst in numbers:
            real = get_real_list(lst)
            imag = get_imag_list(lst)
            if get_modulo(real, imag) > index:
                if real == 0:
                    if imag > 0:
                        if imag == 1:
                            print("z" + str(ind) + " = i")
                            check = True
                        else:
                            print("z" + str(ind) + " = " + str(imag) + "i")
                            check = True
                    elif imag == 0:
                        print("z" + str(ind) + " = 0")
                        check = True
                    else:
                        if imag == -1:
                            print("z" + str(ind) + " = -i")
                            check = True
                        else:
                            print("z" + str(ind) + " = " + str(imag) + "i")
                            check = True
                elif imag < 0:
                    if imag == -1:
                        print("z" + str(ind) + " = " + str(real) + "-i")
                        check = True
                    else:
                        print("z" + str(ind) + " = " + str(real) + str(imag) + "i")
                        check = True
                elif imag == 0:
                    print("z" + str(ind) + " = " + str(real))
                    check = True
                else:
                    if imag == 1:
                        print("z" + str(ind) + " = " + str(real) + "+i")
                        check = True
                    else:
                        print("z" + str(ind) + " = " + str(real) + "+" + str(imag) + "i")
                        check = True
                ind += 1
    if len(numbers) < 1 or check == False:
        print("\nThere are not enough values to be displayed")


def print_all_equal(numbers, index):
    ind = 0
    index = int(index)
    check = False
    if len(numbers) >= 1:
        for lst in numbers:
            real = get_real_list(lst)
            imag = get_imag_list(lst)
            if get_modulo(real, imag) == index:
                if real == 0:
                    if imag > 0:
                        if imag == 1:
                            print("z" + str(ind) + " = i")
                            check = True
                        else:
                            print("z" + str(ind) + " = " + str(imag) + "i")
                            check = True
                    elif imag == 0:
                        print("z" + str(ind) + " = 0")
                        check = True
                    else:
                        if imag == -1:
                            print("z" + str(ind) + " = -i")
                            check = True
                        else:
                            print("z" + str(ind) + " = " + str(imag) + "i")
                            check = True
                elif imag < 0:
                    if imag == -1:
                        print("z" + str(ind) + " = " + str(real) + "-i")
                        check = True
                    else:
                        print("z" + str(ind) + " = " + str(real) + str(imag) + "i")
                        check = True
                elif imag == 0:
                    print("z" + str(ind) + " = " + str(real))
                    check = True
                else:
                    if imag == 1:
                        print("z" + str(ind) + " = " + str(real) + "+i")
                        check = True
                    else:
                        print("z" + str(ind) + " = " + str(real) + "+" + str(imag) + "i")
                        check = True
                ind += 1
    if len(numbers) < 1 or check == False:
        print("\nThere are not enough values to be displayed")


def print_all_smaller(numbers, index):
    ind = 0
    index = int(index)
    check = False
    if len(numbers) >= 1:
        for lst in numbers:
            real = get_real_list(lst)
            imag = get_imag_list(lst)
            if get_modulo(real, imag) < index:
                if real == 0:
                    if imag > 0:
                        if imag == 1:
                            print("z" + str(ind) + " = i")
                            check = True
                        else:
                            print("z" + str(ind) + " = " + str(imag) + "i")
                            check = True
                    elif imag == 0:
                        print("z" + str(ind) + " = 0")
                        check = True
                    else:
                        if imag == -1:
                            print("z" + str(ind) + " = -i")
                            check = True
                        else:
                            print("z" + str(ind) + " = " + str(imag) + "i")
                            check = True
                elif imag < 0:
                    if imag == -1:
                        print("z" + str(ind) + " = " + str(real) + "-i")
                        check = True
                    else:
                        print("z" + str(ind) + " = " + str(real) + str(imag) + "i")
                        check = True
                elif imag == 0:
                    print("z" + str(ind) + " = " + str(real))
                    check = True
                else:
                    if imag == 1:
                        print("z" + str(ind) + " = " + str(real) + "+i")
                        check = True
                    else:
                        print("z" + str(ind) + " = " + str(real) + "+" + str(imag) + "i")
                        check = True
                ind += 1
    if len(numbers) < 1 or check == False:
        print("\nThere are not enough values to be displayed")


def cmd_menu():
    """
    add 4+2i
    insert 1+1i at 1

    remove 1
    remove 1 to 3
    replace 1+3i with 5-3i

    list
    list real 1 to 5
    list modulo < || = || > 10
    """

    numbers = []
    auto_generate_numbers(numbers)
    cmd_dict = {
        'add': add_number,
        'insert': insert_number,
        'remove': remove_number,
        'remove1': remove_number_pos,
        'list': print_all_numbers,
        'list1': print_all_real,
        'list2': print_all_bigger,
        'list3': print_all_equal,
        'list4': print_all_smaller,
    }
    while True:
        #print_cmds(cmd_dict)
        line = input('Enter a command: ')
        line = line.strip()
        line = line.lower()
        if line == 'exit':
            break
        cmd, args = get_command_args(line)
        try:
            if cmd == "replace":
                numbers = replace_number(numbers, *args)
            else:
                cmd_dict[cmd](numbers, *args)
        except KeyError:
            print("You might have spelled something wrong :(")
        except ValueError as ve:
            print("The following exception was received:", ve)
        except TypeError as te:
            print("Something went wrong:", te)

        #print(numbers)



if __name__ == '__main__':
    test_command_args()
    test_create_number()
    test_get_modulo()
    test_replace_number()
    test_remove_number()
    test_insert_number()
    test_add_number()
    cmd_menu()