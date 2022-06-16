//
// Created by bogdan on 4/15/2022.
//

#ifndef A8_9_914_MODOLEA_BOGDAN_SERVICE_H
#define A8_9_914_MODOLEA_BOGDAN_SERVICE_H
//#include "Repository.h"
#include "Watchlist.h"
#include "FileRepository.h"
#include "SavedMovies.h"

class Service{
private:
    FileRepository repository;
    Watchlist watchlist;
    vector<Movie>movies;
    //vector<Movie>::iterator it;
    SavedMovies *saved_movies;

public:
    Service(const FileRepository &rep) : repository{rep} {this->saved_movies = nullptr;}

    //creates a movie and tries to add it
    void add_movie(const string title, const string genre, const int year, const int likes, const string trailer);

    //creates a movie and tries to remove it
    void delete_movie(const string title);

    //creates a movie and tries to update it
    void update_movie(const string title, const string genre, const int year, const int likes, const string trailer);

    vector<Movie> get_all_movies() { return this->repository.get_movies(); }


    Watchlist get_watchlist() const {
        return watchlist;
    }

    void add_movie_watchlist(const Movie movie);

    void delete_movie_watchlist(const string title);

    //Watchlist filter(const string genre);

    void likes(Movie movie);

    void set_mode(string path);

    void file_loc(string path);

    Watchlist my_filter(const string genre, vector<Movie> all);
};
#endif //A8_9_914_MODOLEA_BOGDAN_SERVICE_H
