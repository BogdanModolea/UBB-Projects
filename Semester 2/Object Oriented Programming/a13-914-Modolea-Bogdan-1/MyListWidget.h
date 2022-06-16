//
// Created by bogdan on 30.05.2022.
//

#ifndef OOP_MYLISTWIDGET_H
#define OOP_MYLISTWIDGET_H

#include "QWidget"
#include "MyListTableModel.h"
#include "GUI.h"


class MyListWidget : public QListWidget{
    Q_OBJECT

public:
    MyListWidget(MyListTableModel* model, QWidget* parent = Q_NULLPTR);

    ~MyListWidget() {};

    void add_watchlist(const string& title){
        model->add_watchlist(title);
    };

private:
    MyListWidget* model;
};

#endif //OOP_MYLISTWIDGET_H
