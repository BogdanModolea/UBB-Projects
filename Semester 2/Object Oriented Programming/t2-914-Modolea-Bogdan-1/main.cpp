//
// Created by bogdan on 5/3/2022.
//

#include "iostream"
#include "UI.h"
#include "Service.h"

int main(){
    Service serv;
    UI ui(serv);
    ui.start();
    return 0;
}
