//
// Created by bogdan on 5/3/2022.
//

#include "Appliance.h"

Appliance::Appliance() {

}

Appliance::Appliance(const string &id, const double &weight) {
    this->id = id;
    this->weight = weight;
}

Appliance::~Appliance() {

}

string Appliance::get_id(){
    return this->id;
}