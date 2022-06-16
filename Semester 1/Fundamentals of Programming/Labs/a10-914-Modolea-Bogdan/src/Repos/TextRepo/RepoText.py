from repository.repository import Repository
from domain.entities import Person
from repository.repository import RepositoryException
import pickle

class RepoText(Repository):
    def __init__(self, file_name):
        super().__init__()
        self.file_name = file_name
        self.read_data()

    def read_data(self):
        file = open(self.file_name, "rt")
        for line in file.readlines():
            data = line.split(", ")
            my_data = data[0].split(",")
            my_data[2] = my_data[2].removesuffix("\n")
            super().save(Person(int(my_data[0]), my_data[1], my_data[2]))
        file.close()

    def write_data(self):
        file = open(self.file_name, "wt")
        for person in super().get_all():
            file.writelines(f"{person.id},{person.name},{person.phone}\n")
        file.close()

    def find_by_id(self, entity_id):
        return super(RepoText, self).find_by_id(entity_id)

    def save(self, entity):
        super(RepoText, self).save(entity)
        self.write_data()

    def delete_by_id(self, entity_id):
        super(RepoText, self).delete_by_id(entity_id)
        self.write_data()

    def update(self, entity_id, entity):
        super(RepoText, self).update(entity_id, entity)
        self.write_data()

    def get_all(self):
        return super(RepoText, self).get_all()

    def used_ids(self):
        return super(RepoText, self).used_ids()