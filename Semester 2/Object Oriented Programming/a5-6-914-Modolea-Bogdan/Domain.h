//
// Created by bogdan on 3/19/2022.
//

#ifndef A5_6_914_MODOLEA_BOGDAN_DOMAIN_H
#define A5_6_914_MODOLEA_BOGDAN_DOMAIN_H

#include <string>
using namespace std;

class Movie {
private:
    string title, genre, trailer;
    int year, likes;
public:
    Movie();

    //construct a movie
    Movie(const string title, const string genre, const int year, const int likes, const string trailer);

    //returns the title of the movie
    string get_title() const;

    //returns the genre of the movie
    string get_genre() const;

    //returns the release year of the movie
    int get_year() const;

    //returns the number of likes of the movie
    int get_likes() const;

    //returns the trailer of the movie
    string get_trailer() const;

    //define "=" operator between two movies
    Movie& operator=(const Movie &new_movie);

    //define "==" operator between two movies
    bool operator==(const Movie &other_movie)const;

    void play_trailer();

    void set_title(const string &new_title);

    void set_genre(const string &new_genre);

    void set_year(const int &new_year);

    void set_likes(const int &new_likes);

    void set_trailer(const string &new_trailer);
};

#endif //A5_6_914_MODOLEA_BOGDAN_DOMAIN_H