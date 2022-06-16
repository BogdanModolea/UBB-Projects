cmake_minimum_required(VERSION 3.22)
project(t3_914_Modolea_Bogdan)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(CMAKE_PREFIX_PATH "/home/bogdan/Qt/6.2.4/gcc_64/lib/cmake")

find_package(Qt6 COMPONENTS
        Core
        Gui
        Widgets
        REQUIRED)

add_executable(t3_914_Modolea_Bogdan main.cpp GUI.cpp GUI.h Service.cpp Service.h Domain.cpp Domain.h)
target_link_libraries(t3_914_Modolea_Bogdan
        Qt::Core
        Qt::Gui
        Qt::Widgets
        )

