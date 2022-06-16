//
// Created by bogdan on 4/15/2022.
//

#ifndef A8_9_914_MODOLEA_BOGDAN_FILEREPOSITORY_H
#define A8_9_914_MODOLEA_BOGDAN_FILEREPOSITORY_H

#include "Domain.h"
#include "bits/stdc++.h"

using namespace std;

class FileRepository {
protected:

    string path;

    void save(vector<Movie> movies);

public:

    FileRepository();

    void add_movie(Movie mov);

    void remove_movie(string title);

    void update_movie(Movie mov);

    vector<Movie> get_movies();

    void set_filename(string path);

    vector<Movie> load_movies();


};

#endif //A8_9_914_MODOLEA_BOGDAN_FILEREPOSITORY_H
