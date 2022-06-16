from ui.ui import UI
from gui.gui import GUI

from domain.validators import PersonValidator
from domain.validators import ActivityValidator
from services.services import Services
from repository.repository import Repository
from domain.validators import TestValidator

person_validator = PersonValidator
person_repository = Repository(person_validator)

activity_validator = ActivityValidator
activity_repository = Repository(activity_validator)

services = Services(person_repository, activity_repository)



if __name__ == '__main__':
    while True:
        print("How would you like to interact with the app? [UI/ GUI]")
        try:
            cmd = input("> ")
            if cmd.lower() == "ui":
                #ui = UI(person_service, activity_service, person_repository, activity_repository)
                ui = UI(services)
                ui.main_menu()
                break
            elif cmd.lower() == "gui":
                gui = GUI()
                gui.start()
                break
        except Exception as ex:
            print(ex)

