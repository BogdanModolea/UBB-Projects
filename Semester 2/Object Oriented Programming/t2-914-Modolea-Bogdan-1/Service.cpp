//
// Created by bogdan on 5/3/2022.
//

#include "Service.h"

Service::Service() {

}

Service::~Service() {
    for(auto &elem : this->store)
        delete elem;
}

void Service::addAppliance(Appliance *a) {
    this->store.push_back(a);
}

vector<Appliance *> Service::getAllAppliance() {
    return this->store;
}

vector<Appliance *> Service::getAllWithConsumedElectricityLessThan(const double &maxElectricity) {
    vector<Appliance*>ans;
    for(auto &elem : this->store)
        if(elem->consumedElectricity() < maxElectricity)
            ans.push_back(elem);

    return ans;
}

bool compare(Appliance* a, Appliance* b){
    return a->get_id() < b->get_id();
}

vector<Appliance*> Service::getAllWithMoreEnergy(const double &elect){
    vector<Appliance*>ans;
    for(auto &elem : this->store)
        if(elem->consumedElectricity() > elect)
            ans.push_back(elem);

    sort(ans.begin(), ans.end(), compare);

    return ans;
}

void Service::writeToFile(const string &filename, vector<Appliance *> store) {
    ofstream fout(filename);
    for(auto &elem : store)
        fout << elem->toString();
}
