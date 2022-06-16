import random
import unittest

from repository import Repository
from taxi import Taxi

class Simulation:
    def __init__(self):
        self.repository = Repository()


    def generate_random_taxis(self, number):
        index = 1
        while len(self.repository.entities) < number:
            x = random.randint(0, 100)
            y = random.randint(0, 100)
            check = False
            for each_taxi in self.repository.entities:
                if abs(each_taxi.coord[0] - x) + abs(each_taxi.coord[1] - y) <= 5:
                    check = True

            if check == False:
                self.repository.entities.append(Taxi(index, [x, y]))
                index += 1

    def get_all_taxis(self):
        return self.repository.get_all()

    def distance(self, start_x, start_y, end_x, end_y):
        """
        The distance between [x1, y1] and [x2, y2]
        :param start_x: x1
        :param start_y: y1
        :param end_x: x2
        :param end_y: y2
        :return:
        """
        return abs(start_x - end_x) + abs(start_y - end_y)

    def move(self, start, finish):
        """
        Moving a taxi from start location [x, y] to the finish [x, y]
        :param start: A list of initial position
        :param finish: A list of ending position
        :return:
        """
        taxis = self.get_all_taxis()
        closest = None
        minim = 99999999999
        for each_taxi in taxis:
            dist = self.distance(each_taxi.X, each_taxi.Y, start[0], start[1])
            if dist < minim:
                minim = dist
                closest = each_taxi.id

        for each_taxi in taxis:
            if each_taxi.id == closest:
                now = self.distance(each_taxi.X, each_taxi.Y, finish[0], finish[1])
                each_taxi.fare += now
                each_taxi.coord = finish



    def get_id(self, finish):
        taxis = self.get_all_taxis()
        for each_taxi in taxis:
            if each_taxi.X == finish[0] and each_taxi.Y == finish[1]:
                return each_taxi.id



class TestRide(unittest.TestCase):
    def setUp(self) -> None:
        self.ride = Simulation()
        self.ride.repository.entities.append(Taxi(1, [1, 1]))
        self.ride.repository.entities.append(Taxi(2, [2, 2]))
        self.ride.repository.entities.append(Taxi(3, [99, 99]))

    def tearDown(self) -> None:
        pass

    def test_move(self):
        self.ride.move([5, 5], [77, 78])
        self.assertEqual(self.ride.repository.entities[1].X, 77)
        self.assertEqual(self.ride.repository.entities[1].Y, 78)
        self.assertEqual(self.ride.repository.entities[0].X, 1)
        self.assertEqual(self.ride.repository.entities[0].Y, 1)
