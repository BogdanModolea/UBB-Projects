//
// Created by bogdan on 3/1/2022.
//


#include "UI.h"
#include "Test.h"
#include "stdio.h"

int main() {
    test_all();
    UI *ui = create_ui();
    start(ui);
    destroy_ui(&ui);
    return 0;
}