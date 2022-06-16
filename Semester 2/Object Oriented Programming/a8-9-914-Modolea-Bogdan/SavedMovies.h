//
// Created by bogdan on 4/16/2022.
//

#ifndef A8_9_914_MODOLEA_BOGDAN_SAVEDMOVIES_H
#define A8_9_914_MODOLEA_BOGDAN_SAVEDMOVIES_H

#include "Domain.h"
#include "bits/stdc++.h"

using namespace std;

class SavedMovies{
protected:
    string path;
public:

    void set_path(string path);

    virtual void save_file(vector<Movie>movies){};

    virtual vector<Movie>load_file() { return vector<Movie>(); };

};

class CSVFile : public SavedMovies{
public:
    void save_file(vector<Movie>movies) override;

    vector<Movie> load_file() override;
};

class HTMLFile : public SavedMovies{
    void save_file(vector<Movie>movies) override;

    vector<Movie> load_file() override;
};

#endif //A8_9_914_MODOLEA_BOGDAN_SAVEDMOVIES_H
