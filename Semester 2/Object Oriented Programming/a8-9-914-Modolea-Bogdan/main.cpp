//
// Created by bogdan on 3/19/2022.
//
#include "UI.h"
#include "Tests.h"
#include "FileRepository.h"

int main() {
    Test::test_all();
    FileRepository repository{};
    Service service{repository};
    UI ui{service};
    ui.start();
    return 0;
}