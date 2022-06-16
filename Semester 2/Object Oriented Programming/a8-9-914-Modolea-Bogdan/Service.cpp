//
// Created by bogdan on 3/19/2022.
//

#include "Service.h"
#include "iostream"

using namespace std;

//creates a movie and tries to add it
void Service::add_movie(const string title, const string genre, const int year, const int likes, const string trailer) {
    Movie movie = Movie(title, genre, year, likes, trailer);
    this->repository.add_movie(movie);
}

//creates a movie and tries to remove it
void Service::delete_movie(const string title) {
    this->repository.remove_movie(title);
}

//creates a movie and tries to update it
void Service::update_movie(const string title, const string genre, const int year, const int likes, const string trailer){
    Movie movie = Movie(title, genre, year, likes, trailer);
    this->repository.update_movie(movie);
}

void Service::add_movie_watchlist(const Movie movie){
    this->watchlist.watchlist_add(movie);
    this->saved_movies->save_file(this->watchlist.get_watchlist());
}

void Service::delete_movie_watchlist(const string title){
    Movie movie = Movie(title, "", 0, 0, "");
    this->watchlist.watchlist_remove(movie);
    this->saved_movies->save_file(this->watchlist.get_watchlist());
}

//returns a watchlist with specific movies
//Watchlist Service::filter(const string genre){
//    Watchlist wl;
//    Movie movie;
//    wl = this->watchlist.my_filter(this->get_all_movies(), genre);
//    return wl;
//}

Watchlist Service::my_filter(const string genre, vector<Movie>all){
    Watchlist wl;
    Movie movie;
    vector<Movie>to_filter;
    vector<Movie>current_wl = this->watchlist.get_watchlist();

    for(auto mov : this->get_all_movies()){
        auto iterator = find(current_wl.begin(), current_wl.end(), mov);
        if(iterator == current_wl.end())
            to_filter.push_back(mov);
    }

    wl = this->watchlist.my_filter(to_filter, genre);
    return wl;
}

//thumbs up a movie
void Service::likes(Movie movie){
    Movie mov = Movie(movie.get_title(), movie.get_genre(), movie.get_year(), movie.get_likes() + 1, movie.get_trailer());
    this->repository.update_movie(mov);
}

void Service::file_loc(string path){
    this->repository.set_filename(path);
}

void Service::set_mode(string path){
    string ext = path.substr(path.size() - 4);
    if(ext == ".csv")
        this->saved_movies = new CSVFile{};
    else
        this->saved_movies = new HTMLFile{};

    this->saved_movies->set_path(path);
}