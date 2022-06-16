//
// Created by bogdan on 3/19/2022.
//

#ifndef A5_6_914_MODOLEA_BOGDAN_REPOSITORY_H
#define A5_6_914_MODOLEA_BOGDAN_REPOSITORY_H

#include "Domain.h"
#include "DynamicArray.h"
#include <vector>

class Repository {
private:
    //DynamicArray<Movie> movies;
    vector<Movie>movies;
public:
    Repository() {}

    //adds a movie to the list if it doesn't exist
    void repo_add_movie(const Movie movie);

    //removes a movie from the list if it exists
    void repo_delete_movie(const Movie movie);

    //updates a movie from the list if it exists
    void repo_update_movie(const Movie movie);

    //check if a movie exists in the list
    int existing_movie(const string title);

    //returns the list of movies
    //DynamicArray<Movie> get_movies() const { return movies; }
    vector<Movie> get_movies() const { return movies; }


    //DynamicArray<Movie> operator+(const Movie &new_movie);
};

#endif //A5_6_914_MODOLEA_BOGDAN_REPOSITORY_H