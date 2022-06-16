//
// Created by bogdan on 3/19/2022.
//

#ifndef A5_6_914_MODOLEA_BOGDAN_SERVICE_H
#define A5_6_914_MODOLEA_BOGDAN_SERVICE_H

#include "Repository.h"
#include "Watchlist.h"

class Service{
private:
    Repository repository;
    Watchlist watchlist;

public:
    Service(const Repository rep) : repository(rep) {}

    //Repository get_repo() const {return repository;}

    //creates a movie and tries to add it
    void add_movie(const string title, const string genre, const int year, const int likes, const string trailer);

    //creates a movie and tries to remove it
    void delete_movie(const string title);

    //creates a movie and tries to update it
    void update_movie(const string title, const string genre, const int year, const int likes, const string trailer);

    //DynamicArray<Movie> get_all_movies() const{ return this->repository.get_movies();}

    vector<Movie> get_all_movies() const { return this->repository.get_movies(); }

    Watchlist get_watchlist() const {
        return watchlist;
    }

    void add_movie_watchlist(const Movie movie);

    void delete_movie_watchlist(const string title);

    //void play_watchlist();

    //void next_watchlist();

    Watchlist filter(const string genre);

    bool existing_movie(const string title);

    void likes(Movie movie);
};


#endif //A5_6_914_MODOLEA_BOGDAN_SERVICE_H