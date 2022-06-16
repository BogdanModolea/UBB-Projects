from repository.repository import Repository
from domain.entities import Person
from repository.repository import RepositoryException
import pickle

class RepoBinary(Repository):
    def __init__(self, file_name):
        super().__init__()
        self.file_name = file_name
        self.read_data()

    def read_data(self):
        file = open(self.file_name, "rb")
        try:
            persons = pickle.load(file)
            data = persons.split(", ")
            my_data = data[0].split(",")
            my_data[2] = my_data[2].removesuffix("\n")
            super().save(Person(int(my_data[0]), my_data[1], my_data[2]))
        except Exception as ex:
            raise ex
        while persons:
            try:
                persons = pickle.load(file)
                data = persons.split(", ")
                my_data = data[0].split(",")
                my_data[2] = my_data[2].removesuffix("\n")
                super().save(Person(int(my_data[0]), my_data[1], my_data[2]))
            except:
                break

    def write_data(self):
        file = open(self.file_name, "wb")
        for person in super().get_all():
            pickle.dump(f"{person.id},{person.name},{person.phone}\n", file)
        file.close()

    def find_by_id(self, entity_id):
        return super(RepoBinary, self).find_by_id(entity_id)

    def save(self, entity):
        super(RepoBinary, self).save(entity)
        self.write_data()

    def delete_by_id(self, entity_id):
        super(RepoBinary, self).delete_by_id(entity_id)
        self.write_data()

    def update(self, entity_id, entity):
        super(RepoBinary, self).update(entity_id, entity)
        self.write_data()

    def get_all(self):
        return super(RepoBinary, self).get_all()
