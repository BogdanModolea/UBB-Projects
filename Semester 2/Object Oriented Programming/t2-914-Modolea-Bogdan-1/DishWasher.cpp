//
// Created by bogdan on 5/3/2022.
//

#include "DishWasher.h"

DishWasher::DishWasher() {

}

DishWasher::DishWasher(const string &id, const double &weight, const double &washingCycleLenght, const double &consumedElectricityForOneHour)
:Appliance(id, weight), washingCycleLenght(washingCycleLenght), consumedElectricityForOneHour(consumedElectricityForOneHour){

}

DishWasher::~DishWasher() {

}

double DishWasher::consumedElectricity() {
    return this->consumedElectricityForOneHour * this->washingCycleLenght * 20;
}

string DishWasher::toString() {
    return "Id: " + this->id + " | Weight: " + to_string(this->weight) + " | Cycle: " + to_string(this->washingCycleLenght) + " | Electricity: " +
            to_string(this->consumedElectricityForOneHour) + "\n";
}

