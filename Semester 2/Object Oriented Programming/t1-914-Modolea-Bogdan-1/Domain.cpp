//
// Created by bogdan on 4/5/2022.
//

#include "Domain.h"

Protein::Protein() = default;

Protein::Protein(const string organism, const string name, const string seq){
    this->name = name;
    this->organism = organism;
    this->seq = seq;
}

string Protein::get_name(){
    return this->name;
}

string Protein::get_organism(){
    return this->organism;
}

string Protein::get_seq(){
    return this->seq;
}

Protein &Protein::operator=(const Protein &new_protein) {
    this->name = new_protein.name;
    this->organism = new_protein.organism;
    this->seq = new_protein.seq;

    return *this;
}


bool Protein::operator==(const Protein &other_protein) const {
    return other_protein.name == this->name && other_protein.organism == this->organism;
}