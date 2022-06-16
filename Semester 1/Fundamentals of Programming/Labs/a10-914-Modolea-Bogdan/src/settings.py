from ui.ui import UI
from gui.gui import GUI
from configparser import ConfigParser
from domain.validators import PersonValidator
from domain.validators import ActivityValidator
from services.services import Services
from repository.repository import Repository
from Repos.BinaryRepo.RepoBinary import RepoBinary
from Repos.TextRepo.RepoText import RepoText
from Repos.TextRepo.RepoTextAct import RepoTextAct
from Repos.BinaryRepo.RepoBinaryActivity import RepoBinaryActivity
from Repos.CSVRepo.RepoCSV import RepoCSV
from Repos.CSVRepo.RepoCSVActivity import RepoCSVActivity
from domain.validators import TestValidator

class Settings:
    def __init__(self):
        parser = ConfigParser()
        parser.read("files/settings.properties")
        self.ui = None
        if parser.get("options", "ui") == "ui":
            self.ui = UI
        elif parser.get("options", "ui") == "gui":
            self.ui = GUI
        repo_style = parser.get("options", "repository")
        if repo_style == "memory":
            person_validator = PersonValidator
            person_repository = Repository(person_validator)

            activity_validator = ActivityValidator
            activity_repository = Repository(activity_validator)

            services = Services(person_repository, activity_repository)
            self.ui = self.ui(services, True)

        elif repo_style == "pickle":

            binary_person_repo = RepoBinary(parser.get("options", "persons"))
            binary_activity_repo = RepoBinaryActivity(parser.get("options", "activities"))

            services = Services(binary_person_repo, binary_activity_repo)
            self.ui = self.ui(services, False)

        elif repo_style == "texts":
            text_person_repo = RepoText(parser.get("options", "persons"))
            text_activity_repo = RepoTextAct(parser.get("options", "activities"))

            services = Services(text_person_repo, text_activity_repo)
            self.ui = self.ui(services, False)

        elif repo_style == "csv":
            csv_person_repo = RepoCSV(parser.get("options", "persons"))
            csv_activity_repo = RepoCSVActivity(parser.get("options", "activities"))

            services = Services(csv_person_repo, csv_activity_repo)
            self.ui = self.ui(services, False)


    def get_ui(self):
        return self.ui