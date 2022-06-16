//
// Created by bogdan on 5/3/2022.
//

#include "UI.h"

void menu(){
    cout << "1. Add an appliance\n"
            "2. Show all appliances\n"
            "3. Show efficient appliances\n"
            "4. Save to a file appliances\n"
            "0. Exit\n"
            ">";
}

void UI::ui_add(){
    string type;
    cout << "What do you want to add? [Refrigerator / Dish Washing]\n";
    getline(cin, type);
    if(type == "Refrigerator"){
        string id, weight, usage, freezer;
        bool has;

        cout << "Id:";
        getline(cin, id);

        cout << "Weight:";
        getline(cin, weight);

        cout << "Usage:";
        getline(cin, usage);

        cout << "Has freezer? [y/n]:";
        getline(cin, freezer);
        if(freezer == "y")
            has = true;
        else
            has = false;

        Appliance *a;
        auto b = new Refrigerator(id, stod(weight), usage, has);
        a = b;
        this->service.addAppliance(a);
    }
    else{
        string id, weight, cycle, hour;

        cout << "Id:";
        getline(cin, id);

        cout << "Weight:";
        getline(cin, weight);

        cout << "Cycle Lenght:";
        getline(cin, cycle);

        cout << "Electricity per Hour:";
        getline(cin, hour);

        Appliance *a;
        auto b = new DishWasher(id, stod(weight), stod(cycle), stod(hour));
        a = b;
        this->service.addAppliance(a);
    }
}

void UI::ui_display_all(){
    vector<Appliance*> all = this->service.getAllAppliance();
    for(auto &elem : all)
        cout << elem->toString();
}

void UI::ui_show_efficient(){
    string less;
    cout << "How efficient should it be?";
    getline(cin, less);
    auto all = this->service.getAllWithConsumedElectricityLessThan(stod(less));

    for(auto &elem : all)
        cout << elem->toString();
}

void UI::save_to_file(){
    string more;
    cout << "What would you like to save?\n";
    getline(cin, more);
    auto a = this->service.getAllWithMoreEnergy(stod(more));
    this->service.writeToFile("test.txt", a);
}

void UI::populate(){
    Appliance *a;
    auto b = new Refrigerator("3", 25, "A", false);
    a = b;
    this->service.addAppliance(a);

    auto c = new Refrigerator("2", 100, "A+", true);
    a = c;
    this->service.addAppliance(a);

    auto d = new DishWasher("1", 102.5, 12, 2);
    a = d;
    this->service.addAppliance(d);
}

void UI::start(){
    this->populate();
    cout << "Welcome to my app! :D\n";
    string cmd;
    while (true){
        menu();
        getline(cin, cmd);
        if(cmd == "0")
            return;
        if(cmd == "1")
            this->ui_add();
        else if(cmd == "2")
            this->ui_display_all();
        else if(cmd == "3")
            this->ui_show_efficient();
        else if(cmd == "4")
            this->save_to_file();
    }

}

UI::UI() {

}

UI::UI(Service service) {
    this->service = service;
}

UI::~UI() {

}
