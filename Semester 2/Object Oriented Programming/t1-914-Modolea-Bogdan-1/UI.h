//
// Created by bogdan on 4/5/2022.
//

#ifndef T1_914_MODOLEA_BOGDAN_1_UI_H
#define T1_914_MODOLEA_BOGDAN_1_UI_H

#include "Service.h"

class UI{
private:
    Service service;

public:
    UI(const Service serv) : service(serv) {}

    void start();

    void ui_remove();

    void ui_print_all();

    void ui_print_filtered();
};

#endif //T1_914_MODOLEA_BOGDAN_1_UI_H
