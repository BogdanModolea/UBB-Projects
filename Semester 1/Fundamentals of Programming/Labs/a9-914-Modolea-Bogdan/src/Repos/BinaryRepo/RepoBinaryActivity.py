import json

from repository.repository import Repository
from domain.entities import Activity
from repository.repository import RepositoryException
import pickle

class RepoBinaryActivity(Repository):
    def __init__(self, file_name):
        super().__init__()
        self.file_name = file_name
        self.read_data()

    def read_data(self):
        file = open(self.file_name, "rb")
        try:
            activities = pickle.load(file)
            data = activities.split(", ")
            my_data = data[0].split(",")
            my_data[4] = my_data[4].removesuffix("\n")
            my_data[1] = my_data[1].replace("/", ", ")
            my_persons = json.loads(my_data[1])
            super().save(Activity(int(my_data[0]), my_persons, my_data[2], my_data[3], my_data[4]))
        except Exception as ex:
            raise ex
        while activities:
            try:
                persons = pickle.load(file)
                data = persons.split(", ")
                my_data = data[0].split(",")
                my_data[4] = my_data[4].removesuffix("\n")
                my_data[1] = my_data[1].replace("/", ", ")
                my_persons = json.loads(my_data[1])
                super().save(Activity(int(my_data[0]), my_persons, my_data[2], my_data[3], my_data[4]))
            except:
                break

    def write_data(self):
        file = open(self.file_name, "wb")
        for activity in super().get_all():
            persons_id = "["
            for each_id in activity.person_id:
                persons_id += str(each_id) + "/"
            lng = len(persons_id)
            persons_id = persons_id[:lng - 1]
            persons_id += "]"
            pickle.dump(f"{activity.id},{persons_id},{activity.date},{activity.time},{activity.description}\n", file)
        file.close()

    def find_by_id(self, entity_id):
        return super(RepoBinaryActivity, self).find_by_id(entity_id)

    def save(self, entity):
        super(RepoBinaryActivity, self).save(entity)
        self.write_data()

    def delete_by_id(self, entity_id):
        super(RepoBinaryActivity, self).delete_by_id(entity_id)
        self.write_data()

    def update(self, entity_id, entity):
        super(RepoBinaryActivity, self).update(entity_id, entity)
        self.write_data()

    def get_all(self):
        return super(RepoBinaryActivity, self).get_all()