//
// Created by bogdan on 5/3/2022.
//

#include "Refrigerator.h"

Refrigerator::Refrigerator() {

}

Refrigerator::Refrigerator(const string &id, const double &weight, const string &electicityUsageClass, bool hasFreezer)
:Appliance(id, weight), electricityUsageClass(electicityUsageClass), hasFreezer(hasFreezer){

}

Refrigerator::~Refrigerator() {

}

double Refrigerator::consumedElectricity() {
    double ans = 30.0;
    if(this->electricityUsageClass == "A")
        ans *= 3.0;
    else if(this->electricityUsageClass == "A+")
        ans *= 2.5;
    else if(this->electricityUsageClass == "A++")
        ans *= 2;
    if(this->hasFreezer)
        ans += 20;

    return ans;
}

string bool_to_string(bool ans){
    if(ans)
        return "Has";
    return "Hasn't";
}

string Refrigerator::toString() {
    return "Id: " + this->id + " | Weight: " + to_string(this->weight) + " | Usage: " + this->electricityUsageClass + " | Freezer: " +
            bool_to_string(this->hasFreezer) + "\n";
}
