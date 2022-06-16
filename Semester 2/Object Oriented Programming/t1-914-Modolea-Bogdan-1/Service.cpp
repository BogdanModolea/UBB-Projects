//
// Created by bogdan on 4/5/2022.
//

#include "Service.h"

void Service::service_add(string organism, string name, string seq) {
    Protein prot = Protein(organism, name, seq);
    this->repository.repo_add(prot);
}

void Service::service_remove(string organism, string name){
    //removes a protein if it exists
    Protein prot = Protein(organism, name, "");
    this->repository.repo_remove(prot);
}

DynamicArray<Protein> Service::filter(string seq){
    //filters a list with a given sequence
    DynamicArray<Protein>all;

    for(int i = 0; i < get_all_proteins().size(); i++){
        Protein pr = get_all_proteins().get_element(i);
        if(pr.get_name().find(seq) != std::string::npos)
            all.add(pr);
    }

    return all;
}