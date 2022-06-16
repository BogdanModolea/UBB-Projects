from domain.validators import PersonException
from functions import Container
from domain.entities import Person
import copy

class RepositoryException(PersonException):
    pass

class Repository:
    def __init__(self, validator_class = True):
        self.__validator_class = validator_class
        #self._entities = {}
        self._container = Container()
        self._entities = self._container.get_data()

        #self._data = []

    def find_by_id(self, entity_id):
        """
        Returning a person with an specific id
        :param entity_id:
        :return:
        """
        if entity_id in self._entities:
            return self._entities[entity_id]
        return None

    def used_ids(self):
        return self._entities.keys()



    def save(self, entity):
        """
        Saving an object in repository
        :param entity:
        :return:
        """
        if self.find_by_id(entity.id) is not None:
            raise RepositoryException("Duplicate id {0}.".format(entity.id))
        #self.__validator_class.validate(entity)
        self._entities[entity.id] = entity


    def update(self, entity_id, entity):
        """
        Updating an object by id
        :param entity_id:
        :param entity:
        :return:
        """
        self._container.__setitem__(entity_id, entity)
        self._entities = self._container.get_data()
        #self._entities[entity_id] = entity


    def delete_by_id(self, entity_id):
        """
        Deleting an entity by id
        :param entity_id:
        :return:
        """
        self._container.__delitem__(entity_id)
        self._entities = self._container.get_data()
        #del self._entities[entity_id]

    def get_all(self):
        """
        Returning a list of entities
        :return:
        """
        return list(self._entities.values())
