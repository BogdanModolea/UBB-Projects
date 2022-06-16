#
# Write the implementation for A2 in this file
#

# Function section
# (write all non-UI functions in this section)
# There should be no print or input statements below this comment
# Each function should do one thing only
# Functions communicate using input parameters and their return values
from math import sqrt


def create_number(real, imag):
    return [real, imag]


def set_number(numbers, real, imag):
    number = create_number(real, imag)
    numbers.append(number)

def get_real(numbers):
    return numbers[0]

def get_imag(numbers):
    return numbers[1]

#=================== Option 3 =================== #

def get_modulus(real, imag):
    return real * real + imag * imag


def all_modulus(numbers):
    module = []
    for lst in numbers:
        module.append(get_modulus(lst[0], lst[1]))
    return module


def compute_modulus(numbers):
    """
    At each step we update the maximum length of the sequence and save the starting position
    :param numbers: the list of numbers
    :return: the length of the longest sequence and the starting position to display the sequence in initial number list
    """

    module = all_modulus(numbers)
    maxi = 1
    start = 0
    max_start = 0
    k = 1
    if len(numbers) >= 1:
        for i in range(len(module) - 1):
            if module[i] < module[i + 1]:
                k += 1
            else:
                k = 1
                start = i + 1
            if k > maxi:
                maxi = k
                max_start = start
        return [maxi, max_start]
    else:
        return [0, 0]


def consecutive_sum(numbers, i):
    real1 = get_real(numbers[i])
    imag1 = get_imag(numbers[i])
    real2 = get_real(numbers[i + 1])
    imag2 = get_imag(numbers[i + 1])
    return [real1 + real2, imag1 + imag2]


#=================== Option 4 =================== #

def get_sum(numbers):
    """
    At each step we update the maximum length of the sequence and save the starting position
    :param numbers: the list of complex numbers
    :return: the length of the longest sequence and the starting position to display the sequence in initial number list
    """

    maxi = -1
    k = 1
    start = 0
    max_start = 0
    sum = []
    if len(numbers) >= 2:
        sum = consecutive_sum(numbers, 0)
        prev_sum = consecutive_sum(numbers, 0)
        for i in range(len(numbers) - 1):
            sum = consecutive_sum(numbers, i)
            if sum == prev_sum:
                k += 1
            else:
                k = 2
                start = i
            if k > maxi:
                maxi = k
                max_start = start
            prev_sum = sum

        return [maxi, max_start]
    else:
        return [0, 0]



def auto_set_numbers(numbers):
    """
    Setting 10 initial numbers in the list
    :param numbers:
    """

    set_number(numbers, 1, 4)
    set_number(numbers, 2, -1)
    set_number(numbers, 1, 4)
    set_number(numbers, 2, 4)
    set_number(numbers, 3, -4)
    set_number(numbers, 1, -6)
    set_number(numbers, 0, 2)
    set_number(numbers, -7, 0)
    set_number(numbers, 2, 1)
    set_number(numbers, 1, -2)







# UI section
# (write all functions that have input or print statements here). 
# Ideally, this section should not contain any calculations relevant to program functionalities



def ui_add_numbers(numbers):
    real = int(input("Enter the real part of the number: "))
    imag = int(input("Enter the imaginary part of the number: "))
    set_number(numbers, real, imag)

#=================== Option 2 =================== #

def print_all_numbers(numbers):
    """
    Printing all exising complex numbers in the list
    :param numbers:
    """

    ind = 1
    if len(numbers) >= 1:
        for lst in numbers:
            # print(separator.join(map(str, lst)))
            if lst[0] == 0:
                if lst[1] > 0:
                    if lst[1] == 1:
                        print("z" + str(ind) + " = i")
                    else:
                        print("z" + str(ind) + " = " + str(lst[1]) + "i")
                elif lst[1] == 0:
                    print("z" + str(ind) + " = 0")
                else:
                    if lst[1] == -1:
                        print("z" + str(ind) + " = -i")
                    else:
                        print("z" + str(ind) + " = " + str(lst[1]) + "i")
            elif lst[1] < 0:
                if lst[1] == -1:
                    print("z" + str(ind) + " = " + str(lst[0]) + "-i")
                else:
                    print("z" + str(ind) + " = " + str(lst[0]) + str(lst[1]) + "i")
            elif lst[1] == 0:
                print("z" + str(ind) + " = " + str(lst[0]))
            else:
                if lst[1] == 1:
                    print("z" + str(ind) + " = " + str(lst[0]) + "+i")
                else:
                    print("z" + str(ind) + " = " + str(lst[0]) + "+" + str(lst[1]) + "i")
            ind += 1
    else:
        print("\nThere are not enough values to be displayed")


#=================== Option 3 =================== #

def print_increasing_modulus(numbers):
    """
    Printing the longest sequence that has numbers having increasing modulus with some conversion like 0i -> 0; 1i -> i
    :param numbers:
    :return:
    """

    my_set = compute_modulus(numbers)
    index = 1
    if my_set[0] >= 1:
        print("\nThe longest sequence that has numbers having increasing modulus has " + str(my_set[0]) + " numbers and it is made up of numbers: ")
        for i in range(my_set[1], my_set[1] + my_set[0]):
            # print("z" + str(index) + " = " + str(numbers[i][0]) + " " + str(numbers[i][1]))
            real = get_real(numbers[i])
            imag = get_imag(numbers[i])
            if real == 0:
                if imag > 0:
                    if imag == 1:
                        print("z" + str(index) + " = i, with modulus equal to " + str(sqrt(get_modulus(real, imag))))
                    else:
                        print("z" + str(index) + " = " + str(imag) + "i, with modulus equal to " + str(sqrt(get_modulus(real, imag))))
                elif imag == 0:
                    print("z" + str(index) + " = 0, with modulus equal to " + str(sqrt(get_modulus(real, imag))))
                else:
                    if imag == -1:
                        print("z" + str(index) + " = -i, with modulus equal to " + str(sqrt(get_modulus(real, imag))))
                    else:
                        print("z" + str(index) + " = " + str(imag) + "i, with modulus equal to " + str(sqrt(get_modulus(real, imag))))
            elif imag < 0:
                if imag == -1:
                    print("z" + str(index) + " = " + str(real) + "-i, with modulus equal to " + str(sqrt(get_modulus(real, imag))))
                else:
                    print("z" + str(index) + " = " + str(real) + str(imag) + "i, with modulus equal to " + str(sqrt(get_modulus(real, imag))))
            elif imag == 0:
                print("z" + str(index) + " = " + str(real) + ", with modulus equal to " + str(sqrt(get_modulus(real, imag))))
            else:
                if imag == 1:
                    print("z" + str(index) + " = " + str(real) + "+i, with modulus equal to " + str(sqrt(get_modulus(real, imag))))
                else:
                    print("z" + str(index) + " = " + str(real) + "+" + str(imag) + "i, with modulus equal to " + str(sqrt(get_modulus(real, imag))))
            index += 1
    else:
        print("\nThere are not enough values to be displayed")


#=================== Option 4 =================== #

def print_equal_sum(numbers):
    """
    Printing the longest sequence that has consecutive number pairs having equal sum with some conversion like 0i -> 0; 1i -> i
    :param numbers:
    """

    my_set = get_sum(numbers)
    index = 1
    if my_set[0] >= 2:
        print("\nThe longest sequence that has consecutive number pairs having equal sum has " + str(my_set[0]) + " numbers and it is made up of numbers: ")
        for i in range(my_set[1], my_set[1] + my_set[0]):
            real = get_real(numbers[i])
            imag = get_imag(numbers[i])
            if real == 0:
                if imag > 0:
                    if imag == 1:
                        print("z" + str(index) + " = i")
                    else:
                        print("z" + str(index) + " = " + str(imag) + "i")
                elif imag == 0:
                    print("z" + str(index) + " = 0")
                else:
                    if imag == -1:
                        print("z" + str(index) + " = -i")
                    else:
                        print("z" + str(index) + " = " + str(imag) + "i")
            elif imag < 0:
                if imag == -1:
                    print("z" + str(index) + " = " + str(real) + "-i")
                else:
                    print("z" + str(index) + " = " + str(real) + str(imag) + "i")
            elif imag == 0:
                print("z" + str(index) + " = " + str(real))
            else:
                if imag == 1:
                    print("z" + str(index) + " = " + str(real) + "+i")
                else:
                    print("z" + str(index) + " = " + str(real) + "+" + str(imag) + "i")
            index += 1
    else:
        print("\nThere are not enough values to be displayed")


def print_menu():
    print("\n1 Read a new complex number \n"
          "2 Print all numbers \n"
          "3 Print the longest sequence that has numbers having increasing modulus \n"
          "4 Print the longest sequence that has consecutive number pairs having equal sum \n"
          "x Exit")


def main_menu():
    numbers = []
    options = {1: ui_add_numbers, 2: print_all_numbers, 3: print_increasing_modulus, 4: print_equal_sum}
    auto_set_numbers(numbers)
    while True:
        print_menu()
        option = input("Enter an option: ")
        if option == "x":
            break
        option = int(option)
        options[option](numbers)






if __name__ == '__main__':
    main_menu()



# print('Hello A2'!) -> prints aren't allowed here!
# print(" :( ")
