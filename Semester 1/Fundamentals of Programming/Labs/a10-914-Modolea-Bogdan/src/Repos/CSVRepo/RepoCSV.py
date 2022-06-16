from repository.repository import Repository
from domain.entities import Person
import csv

class RepoCSV(Repository):
    def __init__(self, file_name):
        super().__init__()
        self.file_name = file_name
        self.read_data()

    def read_data(self):
        file = open(self.file_name, "r")
        index = 0
        for line in file.readlines():
            line = line.strip(" \n")
            something = line.split(",")
            super().save(Person(int(something[0]), something[1], something[2]))
        file.close()

    def write_data(self):
        file = open(self.file_name, "w")
        for person in super().get_all():
            file.write("{0},{1},{2}\n".format(person.id, person.name, person.phone))
        file.close()

    def save(self, entity):
        super(RepoCSV, self).save(entity)
        self.write_data()

    def delete_by_id(self, entity_id):
        super(RepoCSV, self).delete_by_id(entity_id)
        self.write_data()

    def update(self, entity_id, entity):
        super(RepoCSV, self).update(entity_id, entity)
        self.write_data()

    def get_all(self):
        return super(RepoCSV, self).get_all()

    def used_ids(self):
        return super(RepoCSV, self).used_ids()

    def find_by_id(self, entity_id):
        return super(RepoCSV, self).find_by_id(entity_id)