//
// Created by bogdan on 5/3/2022.
//

#ifndef T2_914_MODOLEA_BOGDAN_1_DISHWASHER_H
#define T2_914_MODOLEA_BOGDAN_1_DISHWASHER_H

#include "Appliance.h"

class DishWasher : public Appliance{
private:
    double washingCycleLenght;
    double consumedElectricityForOneHour;

public:
    DishWasher();

    DishWasher(const string &id, const double &weight, const double &washingCycleLenght, const double &consumedElectricityForOneHour);

    ~DishWasher();

    double consumedElectricity() override;

    string toString();
};

#endif //T2_914_MODOLEA_BOGDAN_1_DISHWASHER_H
