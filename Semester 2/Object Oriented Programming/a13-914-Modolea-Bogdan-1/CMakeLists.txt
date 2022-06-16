cmake_minimum_required(VERSION 3.22)
project(OOP)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(CMAKE_PREFIX_PATH "/home/bogdan/Qt/6.2.4/gcc_64/lib/cmake")

find_package(Qt6 COMPONENTS
        Charts
        Core
        Gui
        Widgets
        REQUIRED)

add_executable(OOP main.cpp
        Domain.cpp
        Domain.h
        FileRepository.cpp
        FileRepository.h
        Repository.cpp
        Repository.h
        SavedMovies.cpp
        SavedMovies.h
        Service.cpp
        Service.h
        UI.cpp
        UI.h
        Validators.cpp
        Validators.h
        Watchlist.cpp
        Watchlist.h
        GUI.cpp
        GUI.h
        Action.cpp
        Action.h
        MyListTableModel.cpp
        MyListTableModel.h
        MyListWidget.cpp
        MyListWidget.h
        )


target_link_libraries(OOP
        Qt::Charts
        Qt::Core
        Qt::Gui
        Qt::Widgets
        )

