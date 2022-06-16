"""
  User interface module
"""
                                                ###########################################
                                                ###  Write the command-driven UI below  ###
                                                ###########################################


from functions import get_modulo, get_real_list, get_real, get_imag_list, get_imag, get_command_args, add_number, \
    remove_number, replace_number, insert_number, remove_number_pos, get_sum, get_product, undo, create_number, \
    filter_real, filter_modulo_smaller, filter_modulo_equal, filter_modulo_bigger, auto_generate_numbers


def print_cmds(cmd_dict):
    print(*list(cmd_dict.keys()), "exit", sep="\n")


def print_all_numbers(numbers, all_numbers):
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


def print_all_real(numbers, all_numbers, start, stop):
    """
    Printing the numbers that have the imaginary part = 0 between 2 positions
    :param numbers: List of numbers at the moment
    :param start: The initial position where I start displaying the numbers
    :param stop: The final position where I stop displaying the numbers
    """
    start = int(start)
    stop = int(stop)
    index = 0
    for i in range(start, stop + 1):
        real = get_real_list(numbers[i])
        imag = get_imag_list(numbers[i])
        if imag == 0:
            print("z" + str(index) + " = " + str(real))
            index += 1


def print_all_bigger(numbers, all_numbers, index):
    """
    Printing the numbers that have the modulo bigger than a given number
    :param numbers: List of numbers at the moment
    :param index: The number I compare the modulo to
    """
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


def print_all_equal(numbers, all_numbers, index):
    """
    Printing the numbers that have the modulo equal than a given number
    :param numbers: List of numbers at the moment
    :param index: The number I compare the modulo to
    """
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


def print_all_smaller(numbers, all_numbers, index):
    """
    Printing the numbers that have the modulo smaller than a given number
    :param numbers: List of numbers at the moment
    :param index: The number I compare the modulo to
    """
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


def print_sum_all_numbers(numbers, all_numbers, start, stop):
    """
    Printing the sum of the numbers between 2 positions
    :param numbers: List of numbers at the moment
    :param start: First position where I start to compute the sum
    :param stop: Last position where I stop to compute the sum
    """
    real, imag = get_sum(numbers, start, stop)
    if real == 0:
        if imag > 0:
            if imag == 1:
                print("sum = i")
            else:
                print("sum = " + str(imag) + "i")
        elif imag == 0:
            print("sum = 0")
        else:
            if imag == -1:
                print("sum = -i")
            else:
                print("sum = " + str(imag) + "i")
    elif imag < 0:
        if imag == -1:
            print("sum = " + str(real) + "-i")
        else:
            print("sum = " + str(real) + str(imag) + "i")
    elif imag == 0:
        print("sum = " + str(real))
    else:
        if imag == 1:
            print("sum = " + str(real) + "+i")
        else:
            print("sum = " + str(real) + "+" + str(imag) + "i")


def print_product_all_numbers(numbers, all_numbers, start, stop):
    """
    Printing the product of the numbers between 2 positions
    :param numbers: List of numbers at the moment
    :param start: First position where I start to compute the product
    :param stop: Last position where I stop to compute the product
    """
    real, imag = get_product(numbers, start, stop)
    if real == 0:
        if imag > 0:
            if imag == 1:
                print("product = i")
            else:
                print("product = " + str(imag) + "i")
        elif imag == 0:
            print("product = 0")
        else:
            if imag == -1:
                print("product = -i")
            else:
                print("product = " + str(imag) + "i")
    elif imag < 0:
        if imag == -1:
            print("product = " + str(real) + "-i")
        else:
            print("product = " + str(real) + str(imag) + "i")
    elif imag == 0:
        print("product = " + str(real))
    else:
        if imag == 1:
            print("product = " + str(real) + "+i")
        else:
            print("product = " + str(real) + "+" + str(imag) + "i")


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
    all_numbers = []
    auto_generate_numbers(numbers)
    # all_numbers.append(numbers)
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
        'sum': print_sum_all_numbers,
        'product': print_product_all_numbers,
        'filter': filter_real,
        'filter1': filter_modulo_bigger,
        'filter2': filter_modulo_equal,
        'filter3': filter_modulo_smaller,
        'undo': undo,
    }
    while True:
        # print_cmds(cmd_dict)
        line = input('Enter a command: ')
        line = line.strip()
        line = line.lower()
        if line == 'exit':
            break
        cmd, args = get_command_args(line)
        try:
            if cmd == "replace":
                numbers = replace_number(numbers, all_numbers, *args)
            elif cmd == "undo":
                l = numbers
                numbers, msg = undo(l, all_numbers)
                print(msg)
            else:
                cmd_dict[cmd](numbers, all_numbers, *args)
        except KeyError:
            print("You might have spelled something wrong :(")
        except ValueError as ve:
            print("The following exception was received:", ve)
        except TypeError as te:
            print("Something went wrong:", te)

        # print(numbers)

