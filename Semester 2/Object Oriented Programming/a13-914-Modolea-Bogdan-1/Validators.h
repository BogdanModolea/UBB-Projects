//
// Created by bogdan on 4/17/2022.
//

#ifndef A8_9_914_MODOLEA_BOGDAN_VALIDATORS_H
#define A8_9_914_MODOLEA_BOGDAN_VALIDATORS_H

#include "bits/stdc++.h"
#include "Domain.h"
#include "FileRepository.h"
#include "Service.h"
#include "UI.h"

using namespace std;

class ValidatorsException{
private:
    string message;
public:
    ValidatorsException(string msg);

    string get_message() const;
};



class MovieValidator{
public:
    static void validate(const string &title, const string &genre, const string &year, const string &likes, const string &trailer);
    static void validate_title(string title);
};



class Exceptions{
public:
    virtual string get_message() const = 0;
};





class RepositoryException : public Exceptions{
private:
    string message;
public:
    RepositoryException(string msg) { this->message = msg;}

    string get_message() const;
};



class UIException : public Exceptions{
private:
    string message;
public:
    UIException(string msg) { this->message = msg; }

    string get_message() const;
};

#endif //A8_9_914_MODOLEA_BOGDAN_VALIDATORS_H
