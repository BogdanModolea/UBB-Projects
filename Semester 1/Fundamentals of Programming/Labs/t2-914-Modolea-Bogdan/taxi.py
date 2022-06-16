class Taxi:
    def __init__(self, id, coord, fare = 0):
        self._id = id
        self._coord = coord
        self._fare = fare

    @property
    def X(self):
        return self.coord[0]

    @property
    def Y(self):
        return self.coord[1]

    @property
    def id(self):
        return self._id

    @property
    def coord(self):
        return self._coord

    @coord.setter
    def coord(self, value):
        self._coord = value

    @property
    def fare(self):
        return self._fare

    @fare.setter
    def fare(self, value):
        self._fare = value

    def __str__(self):
        return str(self.id) + " " + str(self.coord) + " " + str(self.fare)