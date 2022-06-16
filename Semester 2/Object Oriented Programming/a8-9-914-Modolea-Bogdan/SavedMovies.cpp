//
// Created by bogdan on 4/16/2022.
//

#include "SavedMovies.h"

// sets the path for the input file
void SavedMovies::set_path(string path) {
    this->path = path;
}

// load all the movies from a specific CSV file
vector<Movie> CSVFile::load_file() {
    vector<Movie> all;
    Movie mov;
    ifstream fin(this->path);
    while (fin >> mov)
        all.push_back(mov);
    fin.close();
    return all;
}

// saves all the movies to a specific CSV file
void CSVFile::save_file(vector<Movie> movies) {
    ofstream fout(this->path);
    for(const auto &mov : movies)
        fout << mov << "\n";
    fout.close();
}

// saves all the movies to a specific HTML file
void HTMLFile::save_file(vector<Movie> movies) {
    ofstream fout(this->path);
    fout << "<!DOCTYPE html>\n<html>\n<head>\n";
    fout << "<title>SAVED MOVIES</title>\n";
    fout << "</head>\n<body style=\"background-color:#C9E7DD;\">\n<table border=\"1\"\n";

    fout << "<tr>\n<td><b>Title</td></b>\n<td><b>Genre</td></b>\n<td><b>Year</td></b>\n<td><b>Likes</td></b>\n<td><b>Link</td></b>\n</tr>\n";

    for(auto movie : movies)
        fout << movie.HTML() << "\n";

    fout << "</table>\n</body>\n</html>";
    fout.close();
}

// load all the movies from a specific HTML file
vector<Movie> HTMLFile::load_file() {
    vector<Movie>all;
    string line;
    ifstream fin(this->path);
    string title, genre, year, likes, trailer;

    for(int i = 0; i <= 13; i++)
        getline(fin, line);

    getline(fin, line);
    while (line == "<tr>"){
        getline(fin, line);
        title = line.substr(4, line.size() - 9);

        getline(fin, line);
        genre = line.substr(4, line.size() - 9);

        getline(fin, line);
        year = line.substr(4, line.size() - 9);

        getline(fin, line);
        likes = line.substr(4, line.size() - 9);

        getline(fin, line);
        trailer = line.substr(4, line.size() - 9);

        Movie mov {title, genre, stoi(year), stoi(likes), trailer};
        all.push_back(mov);

        getline(fin, line);
        getline(fin, line);
    }
    fin.close();
    return all;
}

