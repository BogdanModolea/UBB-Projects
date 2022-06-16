//
// Created by bogdan on 24.05.2022.
//

#include "Domain.h"

#include "Domain.h"

Domain::Domain() = default;

Domain::Domain(const string& category, const string &name, vector<string> symps) {
    this->category = category;
    this->name = name;
    this->symptoms = symps;
}

string Domain::get_name() const {
    return name;
}

vector<string> Domain::get_symps() const{
    return symptoms;
}

string Domain::file_output() const {
    string ans;
    ans = this->category + " " + this->name + " ";
    for (auto each : this->symptoms)
        ans = ans + each + ", ";

    return ans;
}

string Domain::output() const {
    string ans;
    ans = this->category + " | " + this->name + " | ";
    for (auto each : this->symptoms)
        ans = ans + each + ", ";

    return ans;
}
