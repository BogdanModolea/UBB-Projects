//
// Created by bogdan on 4/15/2022.
//

#ifndef A8_9_914_MODOLEA_BOGDAN_WATCHLIST_H
#define A8_9_914_MODOLEA_BOGDAN_WATCHLIST_H
#include "Domain.h"
#include "vector"

class Watchlist{
private:
    Watchlist(vector<Movie> vec);

    vector<Movie>movies;
    int current;
public:
    Watchlist();

    void watchlist_add(const Movie movie);

    void watchlist_remove(const Movie movie);

    Movie watchlist_get_current_movie();

    void watchlist_play();

    void next();

    int find_movie(const Movie movie);

    Movie find_infos(const string title);

    vector<Movie> get_watchlist() const { return this->movies; }

    bool is_empty();

    static Watchlist my_filter(vector<Movie> wl, const string &genre);
};
#endif //A8_9_914_MODOLEA_BOGDAN_WATCHLIST_H
