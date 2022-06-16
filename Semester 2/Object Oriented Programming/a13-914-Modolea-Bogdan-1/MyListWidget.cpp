//
// Created by bogdan on 30.05.2022.
//

#include "MyListWidget.h"

MyListWidget::MyListWidget(MyListTableModel *model, QWidget *parent)
    : QListWidget(parent), model{reinterpret_cast<MyListWidget *>(model)}
{

}