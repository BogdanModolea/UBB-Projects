//
// Created by bogdan on 5/3/2022.
//

#ifndef T2_914_MODOLEA_BOGDAN_1_APPLIANCE_H
#define T2_914_MODOLEA_BOGDAN_1_APPLIANCE_H

#include "string"

using namespace std;

class Appliance{
protected:
    string id;
    double weight;
public:
    Appliance();
    Appliance(const string &id, const double &weight);
    ~Appliance();

    virtual double consumedElectricity() = 0;

    virtual string toString() = 0;

    string get_id();
};

#endif //T2_914_MODOLEA_BOGDAN_1_APPLIANCE_H
