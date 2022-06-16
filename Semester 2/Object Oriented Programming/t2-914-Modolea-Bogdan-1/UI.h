//
// Created by bogdan on 5/3/2022.
//

#ifndef T2_914_MODOLEA_BOGDAN_1_UI_H
#define T2_914_MODOLEA_BOGDAN_1_UI_H

#include "Service.h"
#include "Appliance.h"
#include "Refrigerator.h"
#include "DishWasher.h"

class UI{
private:
    Service service;
public:
    UI();

    UI(Service service);

    ~UI();

    void start();

    void ui_display_all();

    void ui_add();

    void ui_show_efficient();

    void save_to_file();

    void populate();
};

#endif //T2_914_MODOLEA_BOGDAN_1_UI_H
