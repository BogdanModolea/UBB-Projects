//
// Created by bogdan on 24.05.2022.
//

#ifndef T3_914_MODOLEA_BOGDAN_GUI_H
#define T3_914_MODOLEA_BOGDAN_GUI_H

#include <qwidget.h>
#include "Service.h"
#include <qlistwidget.h>
#include <qlineedit.h>
#include <qpushbutton.h>
#include <qlabel.h>

class GUI:
    public QWidget {
private:
    Service &serv;

    QListWidget *myListWidget;
    QListWidget *anotherList;

    QLineEdit *symptoms;
    QLineEdit *disorder;

    QPushButton *filterButton;
    QPushButton *showButton;

public:
    GUI(Service &srv);

private:
    void initGUI();

    void populate();

    void connectSignalsandSlots();

    int getSelectedIndex() const;

    void filter();

    void show_symps();
};

#endif //T3_914_MODOLEA_BOGDAN_GUI_H
