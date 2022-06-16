//
// Created by bogdan on 5/3/2022.
//

#ifndef T2_914_MODOLEA_BOGDAN_1_SERVICE_H
#define T2_914_MODOLEA_BOGDAN_1_SERVICE_H

#include "bits/stdc++.h"
#include "Appliance.h"

class Service{
private:
    vector<Appliance*>store;
public:
    Service();
    ~Service();

    void addAppliance(Appliance* a);

    vector<Appliance*>getAllAppliance();

    vector<Appliance*>getAllWithConsumedElectricityLessThan(const double &maxElectricity);

    void writeToFile(const string &filename, vector<Appliance*>store);

    vector<Appliance *> getAllWithMoreEnergy(const double &elect);
};

#endif //T2_914_MODOLEA_BOGDAN_1_SERVICE_H
