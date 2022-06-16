from ui import UI
from gui import GUI
from configparser import ConfigParser


class Settings:
    def __init__(self):
        parser = ConfigParser()
        parser.read("settings.properties")
        self.ui = None
        if parser.get("options", "ui") == "ui":
            self.ui = UI
        elif parser.get("options", "ui") == "gui":
            self.ui = GUI
        self.ui = self.ui()

    def get_ui(self):
        return self.ui
