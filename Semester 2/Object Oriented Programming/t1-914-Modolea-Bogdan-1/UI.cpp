//
// Created by bogdan on 4/5/2022.
//

#include "UI.h"

void menu(){
    cout << "1. Remove a protein\n"
            "2. Print all proteins\n"
            "3. Show all proteins that contain a sequence\n"
            "0. Exit";
}

void UI::ui_remove(){
    string name, organims;
    cout << "Enter an organism:";
    getline(cin, organims);
    cout << "Enter a name";
    getline(cin, name);

    try{
        this->service.service_remove(organims, name);
        cout << "Done\n";
    }
    catch (...){
        cout << "We couldn't delete that\n";
    }
}

void UI::ui_print_all(){
    for(int i = 0; i < this->service.get_all_proteins().size(); i++){
        Protein prot = this->service.get_all_proteins().get_element(i);
        cout << prot.get_organism() << " | " << prot.get_name() << " | " << prot.get_seq() << "\n";
    }
}

void UI::ui_print_filtered(){
    //
    string seq;
    cout << "Enter a sequence:";
    getline(cin, seq);

    DynamicArray<Protein>all;

    all = service.filter(seq);


    for(int i = 0; i < all.size() - 1; i++){
        for(int j = i + 1; j < all.size(); j++){
            if(all.get_element(i).get_name() < all.get_element(j).get_name())
                all.change(i, j);
        }
    }

    for(int i = 0; i < all.size(); i++){
        Protein prot = all.get_element(i);
        cout << prot.get_organism() << " | " << prot.get_name() << " | " << prot.get_seq() << "\n";
    }

}

void UI::start(){
    cout << "Welcome to my app! :D\n";
    while (true){
        menu();
        string cmd;
        getline(cin, cmd);
        if(cmd == "0")
            return;
        if(cmd == "1")
            ui_remove();
        else if(cmd == "2")
            ui_print_all();
        else if(cmd == "3")
            ui_print_filtered();
        else
            cout << "Enter again\n";
    }
}