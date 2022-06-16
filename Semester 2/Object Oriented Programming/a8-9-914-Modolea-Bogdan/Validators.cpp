//
// Created by bogdan on 4/17/2022.
//

#include "Validators.h"

ValidatorsException::ValidatorsException(string msg) : message{msg}
{

}


// returns the message error
string ValidatorsException::get_message() const {
    return this->message;
}


//checks if a movie is valid or not
void MovieValidator::validate(const string &title, const string &genre, const string &year, const string &likes, const string &trailer) {
    string error;
    FileRepository repo;
    Service service {repo};

    vector<Movie> movies = repo.get_movies();

    if(title.empty())
        error += string("Enter a valid title\n");

    if(genre.empty())
        error += string("Enter a valid genre\n");

    bool found = false;
    for(auto c : year)
        if(isalpha(c))
            found = true;
    if(found || year.empty())
        error += string("Enter a valid year\n");

    found = false;
    for(auto c : likes)
        if(isalpha(c))
            found = true;
    if(found || likes.empty())
        error += string("Enter a valid number of likes\n");

    if(trailer.empty())
        error += string("Enter a valid trailer\n");

    if(!error.empty())
        throw ValidatorsException(error);
}

void MovieValidator::validate_title(string title) {
    string error;
    FileRepository repo;
    Service serv {repo};

    vector<Movie>all = serv.get_all_movies();

    bool found = false;
    for(const auto &mov : all)
        if(mov.get_title() == title)
            found = true;

    if(!found)
        error += string("The title does not exist\n");
    if(!error.empty())
        throw ValidatorsException(error);
}


// returns the error message
string RepositoryException::get_message() const {
    return this->message;
}


// returns the error message
string UIException::get_message() const {
    return this->message;
}