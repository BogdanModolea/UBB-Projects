//
// Created by bogdan on 4/15/2022.
//

#include "FileRepository.h"
#include "Validators.h"

// load all the movies from a specific path
vector<Movie> FileRepository::load_movies() {
    ifstream fin(this->path);
    vector<Movie>movies;
    Movie mov;
    while (fin >> mov)
        movies.push_back(mov);
    fin.close();
    return movies;
}

// saves all the movies to a specific path
void FileRepository::save(vector<Movie> movies) {
    ofstream fout(this->path);
    for(const auto &mov : movies)
        fout << mov << "\n";
    fout.close();
}

FileRepository::FileRepository() {}


// adds a movie
void FileRepository::add_movie(Movie mov) {
    vector<Movie>movies = this->load_movies();
    for(auto & movie : movies){
        if(movie.get_title() == mov.get_title())
            throw RepositoryException("Sorry, this movie already exists\n");
    }
    movies.push_back(mov);
    save(movies);
}


// updates a movie from the file
void FileRepository::update_movie(Movie mov) {
    bool found = false;
    vector<Movie>movies = this->load_movies();
    for(auto & movie : movies){
        if(movie.get_title() == mov.get_title()){
            found = true;
            movie = mov;
        }
    }
    if(!found)
        throw RepositoryException("Sorry, the movie does not exist\n");
    this->save(movies);
}


// removes a movie from the file
void FileRepository::remove_movie(string title) {
    bool found = false;
    vector<Movie>movies = this->load_movies();
    for(int i = 0; i < movies.size(); i++){
        if(movies.at(i).get_title() == title){
            found = true;
            movies.erase(movies.begin() + i);
        }
    }
    if(!found)
        throw RepositoryException("Sorry, the movie does not exist\n");
    this->save(movies);
}


// returns all the movies in the file
vector<Movie> FileRepository::get_movies() {
    vector<Movie>all = this->load_movies();
    return all;
}


// sets the path for the input file
void FileRepository::set_filename(string path) {
    this->path = path;
}