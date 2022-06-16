//
// Created by bogdan on 4/15/2022.
//

#ifndef A8_9_914_MODOLEA_BOGDAN_UI_H
#define A8_9_914_MODOLEA_BOGDAN_UI_H

#include "Service.h"

class UI{
private:
    Service service;

    void ui_add_movie();
    void ui_remove_movie();
    void ui_update_movie();
    void print_all();
    void ui_watchlist_start();
    void ui_watchlist_remove();
    void print_wl();


public:
    UI(const Service serv) : service(serv) {}

    void start();
};

#endif //A8_9_914_MODOLEA_BOGDAN_UI_H
