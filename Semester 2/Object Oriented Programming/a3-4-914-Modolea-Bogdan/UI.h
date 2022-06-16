//
// Created by bogdan on 3/1/2022.
//

#ifndef A3_4_914_MODOLEA_BOGDAN_UI_H
#define A3_4_914_MODOLEA_BOGDAN_UI_H

#include "Service.h"

typedef struct{
    Service *service;
}UI;

void start(UI *ui);
UI* create_ui();
void destroy_ui(UI **ui);

#endif //A3_4_914_MODOLEA_BOGDAN_UI_H
