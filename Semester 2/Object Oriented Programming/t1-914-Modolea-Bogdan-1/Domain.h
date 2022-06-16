//
// Created by bogdan on 4/5/2022.
//

#ifndef T1_914_MODOLEA_BOGDAN_1_DOMAIN_H
#define T1_914_MODOLEA_BOGDAN_1_DOMAIN_H

#include "string"
#include "iostream"

using namespace std;

class Protein{
private:
    string organism, name, seq;

public:
    Protein();

    Protein(const string organism, const string name, const string seq);


    string get_name();

    string get_organism();

    Protein &operator=(const Protein &new_protein);

    bool operator==(const Protein &other_protein)const;

    string get_seq();
};

#endif //T1_914_MODOLEA_BOGDAN_1_DOMAIN_H
