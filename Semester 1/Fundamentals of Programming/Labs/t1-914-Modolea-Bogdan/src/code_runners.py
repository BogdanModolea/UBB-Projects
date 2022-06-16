###########################################
###  Write the command-driven UI below  ###
###########################################

from funtions import auto_generate_number, valid_number, generate_codes_runners
import os
import time
from datetime import datetime
from threading import Timer


def exitfunc():
    print ("\nOh oh.. Be careful! Time's up! Computer has won this time!")
    print(datetime.now())
    os._exit(0)


if __name__ == '__main__':
    computer_number = auto_generate_number()
    Timer(60, exitfunc).start()
    while True:
        my_number = input("You are now free to enter a number> ")
        try:
            my_number = int(my_number)
            if my_number == 8086:
                print("Computer's number was " + str(computer_number))
                os._exit(0)
            if valid_number(my_number) == False:
                print("\nThe computer has won The number was " + str(computer_number))
                os._exit(0)
            codes, runners = generate_codes_runners(my_number, computer_number)
            print("\ncomputer reports " + str(codes) + " codes and " + str(runners) + " runners\n")

            if codes == 4:
                print("\nYou have won! Congrats! The number was " + str(my_number) + "\n")
                os._exit(0)
        except Exception as ex:
            print("\nComputer has won! The number was " + str(computer_number))
            os._exit(0)