//
// Created by bogdan on 3/19/2022.
//

#include "Service.h"
#include "iostream"

using namespace std;

//creates a movie and tries to add it
void Service::add_movie(const string title, const string genre, const int year, const int likes, const string trailer) {
    Movie movie = Movie(title, genre, year, likes, trailer);
    this->repository.repo_add_movie(movie);
}

//creates a movie and tries to remove it
void Service::delete_movie(const string title) {
    Movie movie = Movie(title, "", 0, 0, "");
    this->repository.repo_delete_movie(movie);
}

//creates a movie and tries to update it
void Service::update_movie(const string title, const string genre, const int year, const int likes, const string trailer){
    Movie movie = Movie(title, genre, year, likes, trailer);
    this->repository.repo_update_movie(movie);
}

void Service::add_movie_watchlist(const Movie movie){
    this->watchlist.watchlist_add(movie);
}

void Service::delete_movie_watchlist(const string title){
    Movie movie = Movie(title, "", 0, 0, "");
    this->watchlist.watchlist_remove(movie);
}

// returns a watchlist with specific movies
Watchlist Service::filter(const string genre){
    Watchlist wl;
    Movie movie;

//    if(genre == "") {
////        for (int i = 0; i < this->repository.get_movies().size(); i++) {
////            movie = this->repository.get_movies().at(i);
////            if (!existing_movie(movie.get_title()))
////                wl.watchlist_add(movie);
////        }
//
//        //copy_if(this->repository.get_movies().begin(), this->repository.get_movies().end(), wl.get_watchlist().begin(), [](const Movie& mov){return true;});
//
//
//    }

    wl = this->watchlist.my_filter(this->get_all_movies(), genre);

    return wl;

//    for(int i = 0; i < this->repository.get_movies().size(); i++){
//        movie = this->repository.get_movies().at(i);
//        if(movie.get_genre() == genre && !existing_movie(movie.get_title()))
//            wl.watchlist_add(movie);
//    }

    return wl;
}

//checks if a movie exists in the watchlist or not
bool Service::existing_movie(const string title){
    for(int i = 0; i < this->watchlist.get_size(); i++)
        if(this->watchlist.find_infos(title).get_title() == title)
            return true;

    return false;
}


//thumbs up a movie
void Service::likes(Movie movie){
    Movie mov = Movie(movie.get_title(), movie.get_genre(), movie.get_year(), movie.get_likes() + 1, movie.get_trailer());
    this->repository.repo_update_movie(mov);
}