//
// Created by bogdan on 24.05.2022.
//

#ifndef T3_914_MODOLEA_BOGDAN_DOMAIN_H
#define T3_914_MODOLEA_BOGDAN_DOMAIN_H

#include "bits/stdc++.h"

using namespace std;

class Domain{
private:
    string category;
    string name;
    vector<string> symptoms;

public:
    Domain();

    Domain(const string &category, const string &name, vector<string>symps);

    string get_name() const;

    string output() const;

    string file_output() const;

    vector<string> get_symps() const;
};

#endif //T3_914_MODOLEA_BOGDAN_DOMAIN_H
