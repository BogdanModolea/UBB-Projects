import tkinter as tk
from tkinter import *
from tkinter import END
from tkinter import font as tkfont
from ui.ui import UI
from domain.validators import PersonValidator
from domain.validators import ActivityValidator
from repository.repository import Repository
from services.services import Services

import random
from random import randint

class TextScrollCombo(tk.Frame):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.grid_propagate(False)
        self.grid_rowconfigure(0, weight=1)
        self.grid_columnconfigure(0, weight=1)
        self.txt = tk.Text(self)
        self.txt.grid(row=0, column=0, sticky="nsew", padx=2, pady=2)
        scroll = tk.Scrollbar(self, command=self.txt.yview)
        scroll.grid(row=0, column=1, sticky='nsew')
        self.configure(bg="#446878")
        self.txt.configure(bg="#446878")
        self.txt['yscrollcommand'] = scroll.set
        self.txt.insert(1.0, "Welcome to my activity planner app!")

class StatisticsPage(tk.Frame):
    def __init__(self, parent, controller, gui, textbox):
        tk.Frame.__init__(self, parent)
        self.configure(bg="#C18D31")
        self.controller = controller
        self.button_height = 0.05
        self.button_width = 0.25
        self.color_page = "#C18D31"
        self.color_bg = "#3BA4AA"
        self.color_active = "#bc88fc"
        self.gui = gui
        self.textbox = textbox
        self.id_height = self.button_height
        self.id_width = 0.2
        starting_point = 0.2
        space_between = 0.06
        back_button = tk.Button(self, text="Back", font=40, command=lambda: self.controller.show_frame("StartPage"), bg="#DEDEDE", activebackground="#ffffff", highlightbackground=self.color_page)
        back_button.place(relx=0.03, rely=0.03, relheight=0.05, relwidth=0.1)
        button_busiest_day = tk.Button(self, text="Busiest days", font=40, command=self.busiest_day, bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button_busiest_day.place(relx=0.1, rely=starting_point + space_between, relheight=self.button_height, relwidth=self.button_width)
        self.activity_date_field(starting_point, self.activity_date, "Search an activity by date")
        self.activity_name_field(starting_point + space_between * 2, self.activity_name, "Search an activity by person")


    def activity_date(self, entry_date):
        self.gui.gui_activity_date(self.textbox, entry_date.get())
        entry_date.delete(0, END)
        self.controller.show_frame("StartPage")

    def busiest_day(self):
        self.gui.gui_busiest_day(self.textbox)
        self.controller.show_frame("StartPage")

    def activity_name(self, entry_name):
        self.gui.gui_activity_name(self.textbox, entry_name.get())
        entry_name.delete(0, END)
        self.controller.show_frame("StartPage")

    def activity_date_field(self, position, function, button_text):
        entry_date = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_date.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_date.insert(0, "Date: ")
        entry_date.bind("<FocusIn>", lambda args: entry_date.delete(0, END) if entry_date.get() == "Date: " else None)
        entry_date.bind("<FocusOut>", lambda args: entry_date.insert(0, "Date: ") if entry_date.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_date), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)

    def activity_name_field(self, position, function, button_text):
        entry_name = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_name.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_name.insert(0, "Name: ")
        entry_name.bind("<FocusIn>", lambda args: entry_name.delete(0, END) if entry_name.get() == "Name: " else None)
        entry_name.bind("<FocusOut>", lambda args: entry_name.insert(0, "Name: ") if entry_name.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_name), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)


class SearchPage(tk.Frame):
    def __init__(self, parent, controller, gui, textbox):
        tk.Frame.__init__(self, parent)
        self.configure(bg="#53B8BB")
        self.controller = controller
        self.button_height = 0.05
        self.button_width = 0.25
        self.color_page = "#53B8BB"
        self.color_bg = "#F3F2C9"
        self.color_active = "#f5f3a4"
        self.id_height = self.button_height
        self.id_width = 0.15
        self.name_width = 0.3
        self.gui = gui
        self.textbox = textbox
        starting_point = 0.2
        space_between = 0.06
        back_button = tk.Button(self, text="Back", font=40, command=lambda: self.controller.show_frame("StartPage"), bg="#055052", activebackground="#ffffff", highlightbackground=self.color_page)
        back_button.place(relx=0.03, rely=0.03, relheight=0.05, relwidth=0.1)
        self.name_field(starting_point, self.search_person_name, "Search person name")
        self.phone_field(starting_point + space_between, self.search_person_phone, "Search person phone number")
        self.date_field(starting_point + space_between * 2, self.search_activity_date, "Search activity date")
        self.time_field(starting_point + space_between * 3, self.search_activity_time, "Search activity time")
        self.description_field(starting_point + space_between * 4, self.search_activity_description, "Search activity description")

    def search_person_name(self, entry_name):
        self.gui.gui_person_name(self.textbox, entry_name.get())
        entry_name.delete(0, END)
        self.controller.show_frame("StartPage")

    def search_person_phone(self, entry_phone):
        self.gui.gui_person_phone(self.textbox, entry_phone.get())
        entry_phone.delete(0, END)
        self.controller.show_frame("StartPage")

    def search_activity_date(self, entry_date):
        self.gui.gui_search_activity_date(self.textbox, entry_date.get())
        entry_date.delete(0, END)
        self.controller.show_frame("StartPage")

    def search_activity_time(self, entry_time):
        self.gui.gui_search_activity_time(self.textbox, entry_time.get())
        entry_time.delete(0, END)
        self.controller.show_frame("StartPage")

    def search_activity_description(self, entry_description):
        self.gui.gui_search_activity_description(self.textbox, entry_description.get())
        entry_description.delete(0, END)
        self.controller.show_frame("StartPage")

    def name_field(self, position, function, button_text):
        entry_name = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_name.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_name.insert(0, "Name: ")
        entry_name.bind("<FocusIn>", lambda args: entry_name.delete(0, END) if entry_name.get() == "Name: " else None)
        entry_name.bind("<FocusOut>", lambda args: entry_name.insert(0, "Name: ") if entry_name.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_name), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)

    def phone_field(self, position, function, button_text):
        entry_phone = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_phone.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_phone.insert(0, "Phone: ")
        entry_phone.bind("<FocusIn>", lambda args: entry_phone.delete(0, END) if entry_phone.get() == "Phone: " else None)
        entry_phone.bind("<FocusOut>", lambda args: entry_phone.insert(0, "Phone: ") if entry_phone.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_phone), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)

    def date_field(self, position, function, button_text):
        entry_date = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_date.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_date.insert(0, "Date: ")
        entry_date.bind("<FocusIn>", lambda args: entry_date.delete(0, END) if entry_date.get() == "Date: " else None)
        entry_date.bind("<FocusOut>", lambda args: entry_date.insert(0, "Date: ") if entry_date.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_date), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)

    def time_field(self, position, function, button_text):
        entry_time = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_time.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_time.insert(0, "Time: ")
        entry_time.bind("<FocusIn>", lambda args: entry_time.delete(0, END) if entry_time.get() == "Time: " else None)
        entry_time.bind("<FocusOut>", lambda args: entry_time.insert(0, "Time: ") if entry_time.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_time), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)

    def description_field(self, position, function, button_text):
        entry_description = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_description.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_description.insert(0, "Description: ")
        entry_description.bind("<FocusIn>", lambda args: entry_description.delete(0, END) if entry_description.get() == "Description: " else None)
        entry_description.bind("<FocusOut>", lambda args: entry_description.insert(0, "Description: ") if entry_description.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_description), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)


class StartPage(tk.Frame):
    def __init__(self, parent, controller, gui):
        tk.Frame.__init__(self, parent)
        color_page = "#970747"
        color_bg = "#FEF4E8"
        color_active = "#13445A"
        self.configure(bg=color_page)
        self.controller = controller
        self.textbox = TextScrollCombo(self)
        self.textbox.place(relx=0.1, rely=0.5, relwidth=0.8, relheight=0.4)
        button_height = 0.05
        button_width = 0.24
        button1 = tk.Button(self, text="Manage infos", font=40, command=lambda: controller.show_frame("ManagePage"), bg=color_bg, activebackground=color_active, highlightbackground=color_page)
        button1.place(relx=0.1, rely=0.05, relheight=button_height, relwidth=button_width)
        button2 = tk.Button(self, text="Show all persons", font=40, command=lambda: gui.gui_print_persons(self.textbox), bg=color_bg, activebackground=color_active, highlightbackground=color_page)
        button2.place(relx=0.1, rely=0.11, relheight=button_height, relwidth=0.24)
        button3 = tk.Button(self, text="Show all activities", font=40, command=lambda: gui.gui_print_activities(self.textbox), bg=color_bg, activebackground=color_active, highlightbackground=color_page)
        button3.place(relx=0.1, rely=0.17, relheight=button_height, relwidth=button_width)
        button4 = tk.Button(self, text="Search", font=40, command=lambda: controller.show_frame("SearchPage"), bg=color_bg, activebackground=color_active, highlightbackground=color_page)
        button4.place(relx=0.1, rely=0.23, relheight=button_height, relwidth=button_width)
        button6 = tk.Button(self, text="Statistics", font=40, command=lambda: controller.show_frame("StatisticsPage"), bg=color_bg, activebackground=color_active, highlightbackground=color_page)
        button6.place(relx=0.1, rely=0.29, relheight=button_height, relwidth=button_width)


class ManagePage(tk.Frame):
    def __init__(self, parent, controller, gui, textbox):
        tk.Frame.__init__(self, parent)
        self.configure(bg="#39d668")
        self.controller = controller
        self.button_height = 0.05
        self.button_width = 0.25
        self.color_page = "#39d668"
        self.color_bg = "#d2b0fc"
        self.color_active = "#bc88fc"
        self.id_height = self.button_height
        self.id_width = 0.1
        self.gui = gui
        self.textbox = textbox
        back_button = tk.Button(self, text="Back", font=40, command=lambda: self.controller.show_frame("StartPage"), bg="#fcb088", activebackground="#fc9d6a", highlightbackground=self.color_page)
        back_button.place(relx=0.03, rely=0.03, relheight=0.05, relwidth=0.1)
        starting_point = 0.2
        space_between = 0.06
        self.id_person_field(starting_point, self.person_add, "Add a person")
        self.id_activity_field(starting_point + space_between, self.activity_add, "Add an activity")
        self.id_field(starting_point + space_between * 2, self.person_remove, "Remove a person")
        self.id_field(starting_point + space_between * 3, self.activity_remove, "Remove an activity")
        self.id_person_field(starting_point + space_between * 4, self.person_update, "Update a person")
        self.id_activity_field(starting_point + space_between * 5, self.activity_update, "Update an activity")
        button_undo = tk.Button(self, text="Undo", font=40, command=self.undo, bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button_undo.place(relx=0.1, rely=starting_point + space_between * 7, relheight=0.05, relwidth=0.1)
        button_redo = tk.Button(self, text="Redo", font=40, command=self.redo, bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button_redo.place(relx=0.21, rely=starting_point + space_between * 7, relheight=0.05, relwidth=0.1)

    def person_add(self, entry_id, entry_name, entry_phone):
        self.gui.gui_add_person(self.textbox, entry_id.get(), entry_name.get(), entry_phone.get())
        entry_id.delete(0, END)
        entry_name.delete(0, END)
        entry_phone.delete(0, END)
        self.controller.show_frame("StartPage")

    def activity_add(self, entry_id, entry_person_id, entry_date, entry_time, entry_description):
        self.gui.gui_add_activity(self.textbox, entry_id.get(), entry_person_id.get(), entry_date.get(), entry_time.get(), entry_description.get())
        entry_id.delete(0, END)
        entry_person_id.delete(0, END)
        entry_date.delete(0, END)
        entry_time.delete(0, END)
        entry_description.delete(0, END)
        self.controller.show_frame("StartPage")

    def person_remove(self, entry_id):
        self.gui.gui_remove_person(self.textbox, entry_id.get())
        entry_id.delete(0, END)
        self.controller.show_frame("StartPage")

    def activity_remove(self, entry_id):
        self.gui.gui_remove_activity(self.textbox, entry_id.get())
        entry_id.delete(0, END)
        self.controller.show_frame("StartPage")

    def person_update(self, entry_id, entry_name, entry_phone):
        self.gui.gui_update_person(self.textbox, entry_id.get(), entry_name.get(), entry_phone.get())
        entry_id.delete(0, END)
        entry_name.delete(0, END)
        self.controller.show_frame("StartPage")

    def activity_update(self, entry_id, entry_person_id, entry_date, entry_time, entry_description):
        self.gui.gui_update_activity(self.textbox, entry_id.get(), entry_person_id.get(), entry_date.get(), entry_time.get(), entry_description.get())
        entry_id.delete(0, END)
        entry_person_id.delete(0, END)
        entry_date.delete(0, END)
        entry_time.delete(0, END)
        entry_description.delete(0, END)
        self.controller.show_frame("StartPage")

    def undo(self):
        self.gui.gui_undo(self.textbox)
        self.controller.show_frame("StartPage")

    def redo(self):
        self.gui.gui_redo(self.textbox)
        self.controller.show_frame("StartPage")

    def id_field(self, position, function, button_text):
        entry_id = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_id.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_id.insert(0, "Id: ")
        entry_id.bind("<FocusIn>", lambda args: entry_id.delete(0, END) if entry_id.get() == "Id: " else None)
        entry_id.bind("<FocusOut>", lambda args: entry_id.insert(0, "Id: ") if entry_id.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_id), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)

    def id_person_field(self, position, function, button_text):
        entry_id = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_id.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_id.insert(0, "Id: ")
        entry_id.bind("<FocusIn>", lambda args: entry_id.delete(0, END) if entry_id.get() == "Id: " else None)
        entry_id.bind("<FocusOut>", lambda args: entry_id.insert(0, "Id: ") if entry_id.get() == "" else None)
        entry_name = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_name.place(relx=0.12 + self.button_width + self.id_width, rely=position, relheight=self.button_height, relwidth=0.22)
        entry_name.insert(0, "Name: ")
        entry_name.bind("<FocusIn>", lambda args: entry_name.delete(0, END) if entry_name.get() == "Name: " else None)
        entry_name.bind("<FocusOut>", lambda args: entry_name.insert(0, "Name: ") if entry_name.get() == "" else None)
        entry_phone = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_phone.place(relx=0.345 + self.button_width + self.id_width, rely=position, relheight=self.button_height, relwidth=0.25)
        entry_phone.insert(0, "Phone: ")
        entry_phone.bind("<FocusIn>", lambda args: entry_phone.delete(0, END) if entry_phone.get() == "Phone: " else None)
        entry_phone.bind("<FocusOut>", lambda args: entry_phone.insert(0, "Phone: ") if entry_phone.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_id, entry_name, entry_phone), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)

    def id_activity_field(self, position, function, button_text):
        entry_id = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_id.place(relx=0.11 + self.button_width, rely=position, relheight=self.id_height, relwidth=self.id_width)
        entry_id.insert(0, "Id: ")
        entry_id.bind("<FocusIn>", lambda args: entry_id.delete(0, END) if entry_id.get() == "Id: " else None)
        entry_id.bind("<FocusOut>", lambda args: entry_id.insert(0, "Id: ") if entry_id.get() == "" else None)
        entry_id_person = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_id_person.place(relx=0.12 + self.button_width + self.id_width, rely=position, relheight=self.button_height, relwidth=0.12)
        entry_id_person.insert(0, "Persons: ")
        entry_id_person.bind("<FocusIn>", lambda args: entry_id_person.delete(0, END) if entry_id_person.get() == "Persons: " else None)
        entry_id_person.bind("<FocusOut>", lambda args: entry_id_person.insert(0, "Persons: ") if entry_id_person.get() == "" else None)
        entry_date = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_date.place(relx=0.245 + self.button_width + self.id_width, rely=position, relheight=self.button_height, relwidth=0.12)
        entry_date.insert(0, "Date: ")
        entry_date.bind("<FocusIn>", lambda args: entry_date.delete(0, END) if entry_date.get() == "Date: " else None)
        entry_date.bind("<FocusOut>", lambda args: entry_date.insert(0, "Date: ") if entry_date.get() == "" else None)
        entry_time = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_time.place(relx=0.37 + self.button_width + self.id_width, rely=position, relheight=self.button_height, relwidth=0.07)
        entry_time.insert(0, "Time: ")
        entry_time.bind("<FocusIn>", lambda args: entry_time.delete(0, END) if entry_time.get() == "Time: " else None)
        entry_time.bind("<FocusOut>", lambda args: entry_time.insert(0, "Time: ") if entry_time.get() == "" else None)
        entry_description = tk.Entry(self, font=40, bg=self.color_bg, highlightbackground=self.color_page)
        entry_description.place(relx=0.445 + self.button_width + self.id_width, rely=position, relheight=self.button_height, relwidth=0.20)
        entry_description.insert(0, "Description: ")
        entry_description.bind("<FocusIn>", lambda args: entry_description.delete(0, END) if entry_description.get() == "Description: " else None)
        entry_description.bind("<FocusOut>", lambda args: entry_description.insert(0, "Description: ") if entry_description.get() == "" else None)
        button = tk.Button(self, text=button_text, font=40, command=lambda: function(entry_id, entry_id_person, entry_date, entry_time, entry_description), bg=self.color_bg, activebackground=self.color_active, highlightbackground=self.color_page)
        button.place(relx=0.1, rely=position, relheight=self.button_height, relwidth=self.button_width)

class MainMenu(tk.Tk):
    def __init__(self, gui):
        tk.Tk.__init__(self)
        self.geometry("900x600")
        self.title_font = tkfont.Font(family='Helvetica', size=18, weight="bold", slant="italic")
        self.title("Activity planner application")
        container = tk.Frame(self)
        container.pack(side="top", fill="both", expand=True)
        container.grid_rowconfigure(0, weight=1)
        container.grid_columnconfigure(0, weight=1)
        self.frames = {}
        for now_frame in (StartPage, ManagePage, SearchPage, StatisticsPage):
            page_name = now_frame.__name__
            if now_frame == StartPage:
                frame = now_frame(parent=container, controller=self, gui=gui)
            else:
                frame = now_frame(parent=container, controller=self, gui=gui, textbox=self.frames["StartPage"].textbox)
            self.frames[page_name] = frame
            frame.grid(row=0, column=0, sticky="nsew")
        self.show_frame("StartPage")

    def show_frame(self, page_name):
        frame = self.frames[page_name]
        frame.tkraise()


class GUI:
    def __init__(self, services, check):
        person_validator = PersonValidator
        person_repository = Repository(person_validator)
        
        activity_validator = ActivityValidator
        activity_repository = Repository(activity_validator)
        
        self._services = services
        self.__check = check

    def gui_print_persons(self, textbox):
        textbox.txt.delete(1.0, END)
        show = "There are " + str(len(self._services.get_all_persons())) + " persons in total.\n"
        for i in self._services.get_all_persons():
            show += str(i) + "\n"
        textbox.txt.insert(1.0, show)

    def gui_print_activities(self, textbox):
        textbox.txt.delete(1.0, END)
        show = "There are " + str(len(self._services.get_all_activities())) + " activities in total.\n"
        for i in self._services.get_all_activities():
            show += str(i) + "\n"
        textbox.txt.insert(1.0, show)

    def populate_persons(self):
        names = ["Bogdan", "Fabian", "Ingrid", "Anna", "Edy", "Ionut", "Robert", "Madalina", "Stefan", "Matei", "Rares", "Flaviu", "Lavinia", "Tabita", "Laura", "Giorgia", "Alex", "Razvan", "Paul", "Vlad", "Mircea", "Melon Musk", "Bil Gheitz", "Traian", "Decebal", "Adolf"]
        phones = ["0736420118", "0732495033", "0721386410", "0755901831", "0733989001", "0799112112", "0112112112", "0732555911", "0712345689", "0722123098", "0754665984", "0744120932"]
        used_ids = []
        while len(used_ids) < 20:
            person_id = randint(1, 30)
            person_name = random.choice(names)
            person_phone = random.choice(phones)
            if person_id not in used_ids:
                self._services.add_person_random(person_id, person_name, person_phone)
                used_ids.append(person_id)

        return used_ids

    def populate_activities(self, used_ids):
        dates = ["30.05.2002", "01.01.2022", "25.12.2021", "17.04.2023", "11.10.1999", "01.12.1918", "16.12.1989", "01.09.1939", "02.09.1945", "27.09.2020", "28.06.2021", "13.04.2002", "14.02.2025", "19.07.2021", "01.05.2022"]
        times = ["12:05", "17:04", "01:00", "16:30", "21:45", "19:44", "23:59", "00:00", "14:30", "09:20", "12:00", "19:05", "22:22", "06:44"]
        descriptions = ["Birthday", "Matchday", "G2", "WW2", "Bring Me The Horizon Concert", "Fun", "Ski", "Gratar", "Something", "Just being lazy", "Art School"]
        used = []
        while len(used) < 20:
            try:
                act_id = randint(1, 30)
                no_of_persons = randint(1, 4)
                person_id = []
                for i in range(0, no_of_persons):
                    now_id = random.choice(used_ids)
                    if now_id not in person_id:
                        person_id.append(now_id)
                date = random.choice(dates)
                time = random.choice(times)
                description = random.choice(descriptions)
                check = False
                activities = self._services.get_all_activities()
                for each_person_id in person_id:
                    for each_activity in activities:
                        if each_person_id in each_activity.person_id:
                            if each_activity.date == date and each_activity.time == times:
                                check = True
                if check == False:
                    self._services.add_activity_random(act_id, person_id, date, time, description)
                    used.append(act_id)
            except Exception:
                continue

    def gui_person_name(self, textbox, name):
        textbox.txt.delete(1.0, END)
        try:
            persons = self._services.get_names_to_print(name)
            if persons:
                for each_person in persons:
                    textbox.txt.insert(END, each_person)
                    textbox.txt.insert(END, "\n")
            else:
                textbox.txt.insert(1.0, "There is no person with the given name")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_person_phone(self, textbox, phone):
        textbox.txt.delete(1.0, END)
        try:
            persons = self._services.get_phones_to_print(phone)
            if persons:
                for each_person in persons:
                    textbox.txt.insert(END, each_person)
                    textbox.txt.insert(END, "\n")
            else:
                textbox.txt.insert(1.0, "There is no person with the given phone number")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_search_activity_date(self, textbox, date):
        textbox.txt.delete(1.0, END)
        try:
            activities = self._services.get_dates_to_print(date)
            if activities:
                for each_activity in activities:
                    textbox.txt.insert(END, each_activity)
                    textbox.txt.insert(END, "\n")
            else:
                textbox.txt.insert(1.0, "There is no activity planned at that date")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_search_activity_time(self, textbox, time):
        textbox.txt.delete(1.0, END)
        try:
            activities = self._services.get_times_to_print(time)
            if activities:
                for each_activity in activities:
                    textbox.txt.insert(END, each_activity)
                    textbox.txt.insert(END, "\n")
            else:
                textbox.txt.insert(1.0, "There is no activity planned at that time")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_search_activity_description(self, textbox, description):
        textbox.txt.delete(1.0, END)
        try:
            activities = self._services.get_descriptions_to_print(description)
            if activities:
                for each_activity in activities:
                    textbox.txt.insert(END, each_activity)
                    textbox.txt.insert(END, "\n")
            else:
                textbox.txt.insert(1.0, "There is no activity planned at that time")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_add_person(self, textbox, id, name, phone):
        textbox.txt.delete(1.0, END)
        try:
            id = int(id)
            self._services.add_person(id, name, phone)
            textbox.txt.insert(1.0, "Person successfully added!")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_add_activity(self, textbox, id, person_id, date, time, description):
        textbox.txt.delete(1.0, END)
        try:
            id = int(id)
            my_person_id = person_id.split(",")
            persons = []
            for each_person in my_person_id:
                persons.append(int(each_person))
            my_person_id = self._services.get_all_persons()
            verify = False
            for each_id in persons:
                verify = False
                for each_person in my_person_id:
                    if int(each_id) == int(each_person.id):
                        verify = True
                if verify == False:
                    break
            check = True
            if verify == True:
                check = self._services.check_add(persons, date, time, description)
            if check == False:
                self._services.add_activity(id, persons, date, time, description)
                textbox.txt.insert(1.0, "Activity successfully added!")
            else:
                textbox.txt.insert(1.0, "There was some error regarding this activity")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_remove_person(self, textbox, id):
        textbox.txt.delete(1.0, END)
        try:
            id = int(id)
            self._services.remove_person(id)
            self._services.update_removed_person(id)
            textbox.txt.insert(1.0, "Person successfully removed!")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_update_person(self, textbox, id, name, phone):
        textbox.txt.delete(1.0, END)
        try:
            id = int(id)
            self._services.update_person(id, name, phone)
            textbox.txt.insert(1.0, "Person successfully updated!")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_update_activity(self, textbox, id, person_id, date, time, description):
        textbox.txt.delete(1.0, END)
        try:
            id = int(id)
            my_person_id = person_id.split(",")
            persons = []
            for each_person in my_person_id:
                persons.append(int(each_person))
            now_act = self._services.get_all_activities()
            my_person_id = self._services.get_all_persons()
            verify = False
            for each_id in persons:
                verify = False
                for each_person in my_person_id:
                    if int(each_id) == int(each_person.id):
                        verify = True
                if verify == False:
                    break
            check = True
            if verify == True:
                check = self._services.check_update(now_act, id, persons, date, time, description)
            if check == False:
                self._services.update_activity(id, persons, date, time, description)
                textbox.txt.insert(1.0, "Activity successfully updated!")
            else:
                textbox.txt.insert(1.0, "There was some error with this update")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_remove_activity(self, textbox, id):
        textbox.txt.delete(1.0, END)
        try:
            id = int(id)
            self._services.remove_activity(id)
            textbox.txt.insert(1.0, "Activity successfully removed!")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_busiest_day(self, textbox):
        textbox.txt.delete(1.0, END)
        my_act = self._services.get_busiest_day()
        for each_activity in my_act:
            textbox.txt.insert(END, "The date " + each_activity[0] + " has " + str(each_activity[1]) + " activities.\n")

    def gui_activity_name(self, textbox, name):
        textbox.txt.delete(1.0, END)
        my_persons = self._services.get_all_persons()
        my_act = self._services.get_activities_by_name(name, my_persons)
        ok = False
        for each_activity in my_act:
            ok = True
            textbox.txt.insert(END, name + " participates in activity with id " + str(each_activity.id) + ", at the date and time " + each_activity.date + ", " + each_activity.time + "\n")
        if ok == False:
            for each_person in my_persons:
                if name == each_person.name:
                    ok = True
            if ok == False:
                textbox.txt.insert(END, "That person doesn't even exist :(")
            else:
                textbox.txt.insert(END, name + " is too lazy to do something o.O")

    def gui_activity_date(self, textbox, date):
        textbox.txt.delete(1.0, END)
        my_act = self._services.get_sorted_dates(date)
        ok = False
        for each_activity in my_act:
            ok = True
            textbox.txt.insert(END, str(each_activity) + "\n")
        if ok == False:
            textbox.txt.insert(END, "There isn't any activity at that date. Isn't that unlucky? You may plan something ;)")

    def gui_undo(self, textbox):
        textbox.txt.delete(1.0, END)
        try:
            self._services.undo()
            textbox.txt.insert(1.0, "Operation successfully undone!")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def gui_redo(self, textbox):
        textbox.txt.delete(1.0, END)
        try:
            self._services.redo()
            textbox.txt.insert(1.0, "Operation successfully redone!")
        except Exception as ex:
            textbox.txt.insert(1.0, ex)

    def start(self):
        if self.__check == True:
            used = self.populate_persons()
            self.populate_activities(used)
        root = MainMenu(self)
        root.mainloop()