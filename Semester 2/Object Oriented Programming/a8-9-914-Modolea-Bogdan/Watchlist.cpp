//
// Created by bogdan on 3/27/2022.
//

#include "iostream"
#include "Watchlist.h"
#include "vector"
#include "bits/stdc++.h"

using namespace std;

// constructor
Watchlist::Watchlist(){
    this->current = 0;
}

Watchlist::Watchlist(vector<Movie>vec) {
    this->current = 0;
    this->movies = vec;
}

// add a movie to the watchlist
void Watchlist::watchlist_add(const Movie movie){
    this->movies.push_back(movie);
}

// remove a movie from the watchlist
void Watchlist::watchlist_remove(const Movie movie){
    int pos = find_movie(movie);
    if (pos != -1){
        for(auto it = this->movies.begin(); it != this->movies.end(); it++)
            if(it->get_title() == movie.get_title()) {
                this->movies.erase(it);
                break;
            }
    }
}

Watchlist Watchlist::my_filter(vector<Movie> wl, const string &genre){
    vector<Movie>fil;
    if(genre.empty()){
        copy_if(wl.begin(), wl.end(), back_inserter(fil), [genre](auto mov){return mov.get_genre() != genre;});
        Watchlist new_wl = Watchlist(fil);
        return new_wl;
    }

    copy_if(wl.begin(), wl.end(), back_inserter(fil), [genre](auto mov){return mov.get_genre() == genre;});

    Watchlist new_wl = Watchlist(fil);
    return new_wl;
}

//returns the current movie
Movie Watchlist::watchlist_get_current_movie(){
    return this->movies.at(this->current);
}

//play the trailer in the browser
void Watchlist::watchlist_play(){
    if(this->movies.empty())
        return;
    Movie now_movie = this->watchlist_get_current_movie();
    now_movie.play_trailer();
}

//goes to the next movie in the watchlist
void Watchlist::next() {
    if (this->current + 1 == this->movies.size()) {
        this->current = 0;
        return;
    }
    this->current++;
}

//checks if a movie exists in the watchlist or not
int Watchlist::find_movie(const Movie movie){
    for(int i = 0; i < this->movies.size(); i++){
        if(this->movies.at(i).get_title() == movie.get_title())
            return i;
    }
    return -1;
}

//find a movie by title in the watchlist
Movie Watchlist::find_infos(const string title){
    for(auto & movie : this->movies){
        if(movie.get_title() == title)
            return movie;
    }

    Movie movie;
    return movie;
}

// checks if the watchlist is empty or not
bool Watchlist::is_empty() {
    return this->movies.empty();
}

