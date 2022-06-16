from repository.repository import Repository
from domain.entities import Activity
import json

class RepoCSVActivity(Repository):
    def __init__(self, file_name):
        super().__init__()
        self.file_name = file_name
        self.read_data()

    def read_data(self):
        file = open(self.file_name, "r")
        index = 0
        for line in file.readlines():
            line = line.strip("\n")
            something = line.split(",")
            if index == 1:
                index += 1
                something[0] = something[0][3:len(something[0])]
            something[1] = something[1].replace("/", ", ")
            my_persons = json.loads(something[1])
            super().save(Activity(int(something[0]), my_persons, something[2], something[3], something[4]))
        file.close()

    def write_data(self):
        file = open(self.file_name, "w")
        for activity in super().get_all():
            persons_id = "["
            for each_id in activity.person_id:
                persons_id += str(each_id) + "/"
            lng = len(persons_id)
            persons_id = persons_id[:lng - 1]
            persons_id += "]"
            file.write("{0},{1},{2},{3},{4}\n".format(activity.id, persons_id, activity.date, activity.time, activity.description))
        file.close()

    def save(self, entity):
        super(RepoCSVActivity, self).save(entity)
        self.write_data()

    def find_by_id(self, entity_id):
        return super(RepoCSVActivity, self).find_by_id(entity_id)

    def delete_by_id(self, entity_id):
        super(RepoCSVActivity, self).delete_by_id(entity_id)
        self.write_data()

    def update(self, entity_id, entity):
        super(RepoCSVActivity, self).update(entity_id, entity)
        self.write_data()

    def get_all(self):
        return super(RepoCSVActivity, self).get_all()
