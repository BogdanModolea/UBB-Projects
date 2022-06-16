//
// Created by bogdan on 4/5/2022.
//

#ifndef T1_914_MODOLEA_BOGDAN_1_REPOSITORY_H
#define T1_914_MODOLEA_BOGDAN_1_REPOSITORY_H

#include "Domain.h"
#include "DynamicArray.h"

class Repository{
private:
    DynamicArray<Protein>proteins;

public:
    Repository() {}

    void repo_add(Protein prot);

    void repo_remove(Protein prot);

    int existing_protein(string name, string organism);

    DynamicArray<Protein> get_proteins() { return proteins; }
};

#endif //T1_914_MODOLEA_BOGDAN_1_REPOSITORY_H
