//
// Created by bogdan on 4/5/2022.
//

#ifndef T1_914_MODOLEA_BOGDAN_1_SERVICE_H
#define T1_914_MODOLEA_BOGDAN_1_SERVICE_H

#include "Repository.h"

class Service{
private:
    Repository repository;

public:
    Service(const Repository rep) : repository(rep) {}

    void service_add(string organism, string name, string seq);

    void service_remove(string organism, string name);

    DynamicArray<Protein> get_all_proteins() {return this->repository.get_proteins();}

    DynamicArray<Protein> filter(string seq);
};

#endif //T1_914_MODOLEA_BOGDAN_1_SERVICE_H
