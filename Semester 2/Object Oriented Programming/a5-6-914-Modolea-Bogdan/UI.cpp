//
// Created by bogdan on 3/19/2022.
//

#include "UI.h"
#include <iostream>
using namespace std;

void menu(){
    cout << "\n1. Manage a movie\n"
            "2. Print all movies\n"
            "0. Exit\n";
}

void menu1(){
    cout << "\n1. Add a movie\n"
            "2. Remove a movie\n"
            "3. Update a movie\n";
}

void menu_user() {
    cout << "\n1. Watch a movie by genre\n"
            "2. Remove a movie from watchlist\n"
            "3. Display the watchlist\n"
            "0. Exit\n";
}

void menu_watchlist(){
    cout << "\nnext ~ go to the next movie\n"
            "add ~ add the movie to the watchlist\n"
            "exit\n";
}

void UI::ui_add_movie(){
    string title, genre, year, likes, trailer;
    cout << "Enter a title:";
    getline(cin, title);
    cout << "Enter a genre:";
    getline(cin, genre);
    cout << "Enter a year:";
    getline(cin, year);
    cout << "Enter number of likes:";
    getline(cin, likes);
    cout << "Enter the link to trailer:";
    getline(cin, trailer);

    try {
        this->service.add_movie(title, genre, stoi(year), stoi(likes), trailer);
    }
    catch(...) {
        cout << "We can't add that film. Sorry\n";
    }

}

void UI::ui_remove_movie(){
    string title;
    cout << "Enter a title:";
    getline(cin, title);
    try{
        this->service.delete_movie(title);
    }
    catch (...) {
        cout << "We couldn't delete the film. Sorry\n";
    }

}

void UI::ui_update_movie() {
    string title, genre, year, likes, trailer;
    cout << "Enter the title you want to update:";
    getline(cin, title);
    cout << "Enter a new genre:";
    getline(cin, genre);
    cout << "Enter a new year:";
    getline(cin, year);
    cout << "Enter the new number of likes:";
    getline(cin, likes);
    cout << "Enter the new link to trailer:";
    getline(cin, trailer);
    try {
        this->service.update_movie(title, genre, stoi(year), stoi(likes), trailer);
    }
    catch (...) {
        cout << "We couldn't update the film. Sorry\n";
    }
}

void UI::print_all(){
    //DynamicArray<Movie> my_movies = this->service.get_all_movies();
    vector<Movie> my_movies = this->service.get_all_movies();
    for(int i = 0; i < my_movies.size(); i++) {
        Movie mov = my_movies.at(i);
        cout << i + 1 << ". Title: " << mov.get_title() << " ~ Genre: " << mov.get_genre() << " ~ Year: " << mov.get_year() << " ~ Likes: " << mov.get_likes() << " \nTrailer: " << mov.get_trailer() << "\n\n";
    }
}

void UI::ui_watchlist_start(){
    string genre, cmd;
    cout << "Enter a genre:";
    getline(cin, genre);
    Watchlist wl = this->service.filter(genre);

    while (!wl.is_empty()){
        menu_watchlist();
        wl.watchlist_play();

        Movie mov = wl.watchlist_get_current_movie();
        cout << "\nTitle: " << mov.get_title() << " ~ Genre: " << mov.get_genre() << " ~ Year: " << mov.get_year() << " ~ Likes: " << mov.get_likes() << " \nTrailer: " << mov.get_trailer() << "\n\n";

        cout << ">";
        getline(cin, cmd);
        if(cmd == "exit")
            break;
        if(cmd == "add"){
            this->service.add_movie_watchlist(mov);
            wl.watchlist_remove(mov);
        }
        if(cmd == "next")
            wl.next();

    }
}

void UI::ui_watchlist_remove(){
    string title, enjoy;
    cout << "Enter the title:";
    getline(cin, title);

    cout << "Did you enjoy the movie? [y/n]";
    getline(cin, enjoy);

    if(enjoy == "y"){
        Movie movie = this->service.get_watchlist().find_infos(title);
        this->service.likes(movie);
    }

    this->service.delete_movie_watchlist(title);

}

void UI::print_wl(Watchlist wl) {
    if (wl.is_empty()) {
        cout << "No movies in your watchlist\n";
        return;
    }

    Movie mov;
    int cnt = 0;

    while (!wl.is_empty()) {
        mov = wl.watchlist_get_current_movie();
        cout << "\nTitle: " << mov.get_title() << " ~ Genre: " << mov.get_genre() << " ~ Year: " << mov.get_year()
             << " ~ Likes: " << mov.get_likes() << " \nTrailer: " << mov.get_trailer() << "\n\n";

        cnt++;
        if (cnt == wl.get_size())
            break;
        wl.next();
    }
}

void UI::start() {
    cout << "Welcome to my app! UwU\n";
    while (true) {
        string cmd, user, pass;

        cout << "Please enter your login details\n"
                "Username:";
        getline(cin, user);
        cout << "Password:";
        getline(cin, pass);

        if (user == "admin" && pass == "admin") {
            while (true) {
                menu();
                cout << ">";
                getline(cin, cmd);
                if (cmd == "0") {
                    return;
                } else if (cmd == "1") {
                    menu1();
                    string opt;
                    getline(cin, opt);
                    if (opt == "1")
                        ui_add_movie();
                    else if (opt == "2")
                        ui_remove_movie();
                    else if (opt == "3")
                        ui_update_movie();
                } else if (cmd == "2")
                    print_all();
            }
        } else {
            while (true){
                menu_user();
                cout << ">";
                getline(cin, cmd);
                if(cmd == "0")
                    return;
                else if(cmd == "1"){
                    ui_watchlist_start();
                }
                else if(cmd == "2"){
                    ui_watchlist_remove();
                }
                else if(cmd == "3"){
                    print_wl(this->service.get_watchlist());
                }
                else{
                    cout << "Invalid command\n";
                }
            }
        }
    }
}