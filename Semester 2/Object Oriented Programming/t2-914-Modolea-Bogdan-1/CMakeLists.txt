cmake_minimum_required(VERSION 3.22)
project(t2_914_Modolea_Bogdan_1)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(t2_914_Modolea_Bogdan_1
        Service.cpp
        Service.h
        Refrigerator.cpp
        Refrigerator.h
        DishWasher.cpp
        DishWasher.h
        Appliance.cpp
        Appliance.h
        UI.cpp
        UI.h
        main.cpp)
