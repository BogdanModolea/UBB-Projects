//
// Created by bogdan on 24.05.2022.
//

#ifndef T3_914_MODOLEA_BOGDAN_SERVICE_H
#define T3_914_MODOLEA_BOGDAN_SERVICE_H

#include "bits/stdc++.h"
#include "Domain.h"

using namespace std;

class Service {
private:
    vector<Domain> all;

protected:
    string path;

    void save(vector<Domain> all);

public:

    Service() = default;

    Service(const string &path);

    vector<Domain> load_content();

};

#endif //T3_914_MODOLEA_BOGDAN_SERVICE_H
