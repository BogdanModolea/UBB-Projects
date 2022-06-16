//
// Created by bogdan on 24.05.2022.
//

#include "Service.h"

Service::Service(const string& path) {
    this->path = path;
}

void Service::save(vector<Domain> all) {
    ofstream fout(this->path);
    for(const auto &elem : all)
        fout << elem.file_output();
    fout.close();
}

vector<Domain> Service::load_content() {
    ifstream fin(this->path);
    vector<Domain>store;
    string category, name, symp;

    string all;
    while (getline(fin, all)) {
        vector<string> mines;

        string turn = "";
        for(auto x : all){
            if(x == '|'){
                mines.push_back(turn);
                turn = "";
            }
            else
                turn += x;
        }
        mines.push_back(turn);
        category = mines[0];
        name = mines[1];
        symp = mines[2];
        //cout << category << " " << name << " " << symp << "\n";


        vector<string>symps;
        string now = "";

        for(auto x : symp){
            if(x == ','){
                symps.push_back(now);
                now = "";
            }
            else
                now += x;
        }
        symps.push_back(now);

        store.push_back(Domain(category, name, symps));

    }
    fin.close();
    return store;
}
