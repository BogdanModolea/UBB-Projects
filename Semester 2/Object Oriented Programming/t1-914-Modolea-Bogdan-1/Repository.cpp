//
// Created by bogdan on 4/5/2022.
//

#include "Repository.h"

int Repository::existing_protein(string name, string organism){
    Protein pr;
    for(int i = 0; i < this->proteins.size(); i++){
        pr = this->proteins.get_element(i);
        if(pr.get_name() == name && pr.get_organism() == organism)
            return i;
    }
    return -1;
}

void Repository::repo_add(Protein prot) {
    int pos = existing_protein(prot.get_name(), prot.get_organism());

    if(pos == -1)
        this->proteins.add(prot);
    else
        throw exception();
}

void Repository::repo_remove(Protein prot) {
    //removes a protein if it exists
    int pos = existing_protein(prot.get_name(), prot.get_organism());

    if(pos != -1)
        this->proteins.remove(prot);
    else
        throw exception();
}