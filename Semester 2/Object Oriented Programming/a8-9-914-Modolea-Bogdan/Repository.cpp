//
// Created by bogdan on 3/19/2022.
//

#include "Repository.h"
#include <exception>
#include <iostream>

using namespace std;

//check if a movie exists in the list
int Repository::existing_movie(const string title) {
    Movie mv;
    for(int i = 0; i < this->movies.size(); i++){
        //mv = this->movies.get_element(i);
        mv = this->movies.at(i);
        if(mv.get_title() == title)
            return i;
    }
    return -1;
}

//adds a movie to the list if it doesn't exist
void Repository::repo_add_movie(const Movie movie) {
    int pos = existing_movie(movie.get_title());
    if(pos == -1)
        //this->movies.add(movie);
        this->movies.push_back(movie);
    else
        throw exception();
}

//removes a movie from the list if it exists
void Repository::repo_delete_movie(const Movie movie) {
    int pos = existing_movie(movie.get_title());
    if (pos != -1){
        for(auto it = this->movies.begin(); it != this->movies.end(); it++)
            if(it->get_title() == movie.get_title()) {
                this->movies.erase(it);
                break;
            }
    }
        //this->movies.remove(movie);
    else
        throw exception();
}

//updates a movie from the list if it exists
void Repository::repo_update_movie(const Movie movie) {
    int pos = existing_movie(movie.get_title());
    if (pos != -1){
        for(auto &element : this->movies){
            if(element.get_title() == movie.get_title()){
                element.set_genre(movie.get_genre());
                element.set_likes(movie.get_likes());
                element.set_trailer(movie.get_trailer());
                element.set_year(movie.get_year());
            }
        }
    }
        //this->movies.update(movie, pos);
    else
        throw exception();
}
