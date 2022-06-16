//
// Created by bogdan on 30.05.2022.
//

#ifndef OOP_MYLISTTABLEMODEL_H
#define OOP_MYLISTTABLEMODEL_H

#include "qabstractitemmodel.h"
#include "Service.h"
#include "Watchlist.h"

class MyListTableModel : public QAbstractTableModel{
private:
    Service& service;

public:
    MyListTableModel(Service& service) : service{service}{};

    void add_watchlist(const string& title);

    bool insertRows(int position, int rows, const QModelIndex& index = QModelIndex()) override;

    bool removeRows(int position, int rows, const QModelIndex& index = QModelIndex()) override;

    int rowCount(const QModelIndex& parent = QModelIndex()) const;

    int columnCount(const QModelIndex& parent = QModelIndex()) const;

    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const;

    QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const;

};

#endif //OOP_MYLISTTABLEMODEL_H
