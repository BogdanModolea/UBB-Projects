//
// Created by bogdan on 3/19/2022.
//

#include "Domain.h"
#include <iostream>
#include <string>
#include <sstream>
#include "Windows.h"
#include "shellapi.h"

using namespace std;

Movie::Movie() = default;

//construct a movie
Movie::Movie(const string title, const string genre, const int year, const int likes, const string trailer){
    this->title = title;
    this->genre = genre;
    this->year = year;
    this->likes = likes;
    this->trailer = trailer;
}

//returns the title of the movie
string Movie::get_title() const{
    return title;
}

void Movie::set_title(const string &new_title){
    this->title = new_title;
}

//returns the genre of the movie
string Movie::get_genre() const{
    return genre;
}

void Movie::set_genre(const string &new_genre){
    this->genre = new_genre;
}

//returns the release year of the movie
int Movie::get_year() const{
    return year;
}

void Movie::set_year(const int &new_year){
    this->year = new_year;
}

//returns the number of likes of the movie
int Movie::get_likes() const{
    return likes;
}

void Movie::set_likes(const int &new_likes){
    this->likes = new_likes;
}

//returns the trailer of the movie
string Movie::get_trailer() const{
    return trailer;
}

void Movie::set_trailer(const string &new_trailer){
    this->trailer = new_trailer;
}

//define "=" operator between two movies
Movie &Movie::operator=(const Movie &new_movie) {
    this->title = new_movie.title;
    this->genre = new_movie.genre;
    this->year = new_movie.year;
    this->likes = new_movie.likes;
    this->trailer = new_movie.trailer;

    return *this;
}

//define "==" operator between two movies
bool Movie::operator==(const Movie &other_movie) const {
    return other_movie.title == this->title;
}

void Movie::play_trailer() {
    ShellExecute(NULL, NULL, "chrome.exe", this->get_trailer().c_str(), NULL, SW_SHOWMAXIMIZED);
}
