from domain.validators import PersonException
from domain.entities import Person
import copy

class RepositoryException(PersonException):
    pass

class Repository:
    def __init__(self, validator_class):
        self.__validator_class = validator_class
        self.__entities = {}

    def find_by_id(self, entity_id):
        """
        Returning a person with an specific id
        :param entity_id:
        :return:
        """
        if entity_id in self.__entities:
            return self.__entities[entity_id]
        return None

    def save(self, entity):
        """
        Saving an object in repository
        :param entity:
        :return:
        """
        if self.find_by_id(entity.id) is not None:
            raise RepositoryException("Duplicate id {0}.".format(entity.id))
        self.__validator_class.validate(entity)
        self.__entities[entity.id] = entity


    def update(self, entity_id, entity):
        """
        Updating an object by id
        :param entity_id:
        :param entity:
        :return:
        """
        self.__entities[entity_id] = entity


    def delete_by_id(self, entity_id):
        """
        Deleting an entity by id
        :param entity_id:
        :return:
        """
        del self.__entities[entity_id]

    def get_all(self):
        """
        Returning a list of entities
        :return:
        """
        return list(self.__entities.values())





            #########################
            ##        TESTS        ##
            #########################





