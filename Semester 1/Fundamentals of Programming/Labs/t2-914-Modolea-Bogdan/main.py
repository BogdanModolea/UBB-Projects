import random

from simulate import Simulation

class UI:
    def __init__(self):
        self.simulation = Simulation()

    def generate(self):
        number = int(input("Enter the number of taxis to generate: "))
        self.simulation.generate_random_taxis(number)

        #for each in self.simulation.get_all_taxis():
            #print(each)

    def ride(self):
        start_x = int(input("Enter the starting point (X): "))
        start_y = int(input("Enter the starting point (Y): "))

        end_x = int(input("Enter the ending point (X): "))
        end_y = int(input("Enter the starting point (Y): "))

        self.simulation.move([start_x, start_y], [end_x, end_y])

    def print_menu(self):
        print()
        print("1. Add a ride \n"
              "2. Simulate rides \n"
              "3. Exit")


    def simulate(self):
        number = int(input("Enter the number of rides you want to simulate: "))
        for step in range(number):
            finish_coord = []
            start_coord = []
            while len(finish_coord) == 0:
                start_x = random.randint(0, 100)
                start_y = random.randint(0, 100)

                end_x = random.randint(0, 100)
                end_y = random.randint(0, 100)

                check = False
                if self.simulation.distance(start_x, start_y, end_x, end_y) < 10:
                    check = True

                if check == False:
                    start_coord = [start_x, start_y]
                    finish_coord = [end_x, end_y]

            self.simulation.move(start_coord, finish_coord)
            taxi = self.simulation.get_id(finish_coord)


            print(str(taxi) + " " + str(start_coord) + " " + str(finish_coord))
        print()


    def situation(self):
        taxis = self.simulation.get_all_taxis()
        for each_taxi in taxis:
            print(str(each_taxi.id).ljust(20, ' ') + str(each_taxi.coord) + str(each_taxi.fare).rjust(20, ' '))


    def main_menu(self):
        self.generate()
        while True:
            self.print_menu()
            cmd = int(input("> "))
            if cmd == 1:
                self.ride()
            elif cmd == 2:
                self.simulate()
            elif cmd == 3:
                break
            self.situation()

ui = UI()
ui.main_menu()