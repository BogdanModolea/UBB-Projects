//
// Created by bogdan on 5/3/2022.
//

#ifndef T2_914_MODOLEA_BOGDAN_1_REFRIGERATOR_H
#define T2_914_MODOLEA_BOGDAN_1_REFRIGERATOR_H

#include "Appliance.h"

class Refrigerator : public Appliance{
private:
    string electricityUsageClass;
    bool hasFreezer;

public:
    Refrigerator();

    Refrigerator(const string &id, const double &weight, const string &electicityUsageClass, bool hasFreezer);

    ~Refrigerator();

    double consumedElectricity() override;

    string toString();
};

#endif //T2_914_MODOLEA_BOGDAN_1_REFRIGERATOR_H
