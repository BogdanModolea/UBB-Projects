//
// Created by bogdan on 30.05.2022.
//

#include "MyListTableModel.h"
#include "QPushButton"
#include "iostream"

void MyListTableModel::add_watchlist(const string &title) {
    Movie movie;

    auto all = this->service.get_all_movies();
    for(auto each : all)
        if(each.get_title() == title){
            movie = each;
            break;
        }

    service.add_movie_watchlist(movie);
    insertRows(service.get_watchlist().get_watchlist().size(), 1);
}

bool MyListTableModel::insertRows(int position, int rows, const QModelIndex &index) {
    beginInsertRows(QModelIndex(), position, position + rows - 1);
    endInsertRows();
    return true;
}

bool MyListTableModel::removeRows(int position, int rows, const QModelIndex &index) {
    beginRemoveRows(QModelIndex(), position, position + rows - 1);
    endRemoveRows();
    return true;
}

int MyListTableModel::rowCount(const QModelIndex &parent) const {
    return this->service.get_watchlist().get_watchlist().size();
}

int MyListTableModel::columnCount(const QModelIndex &parent) const {
    return 6;
}

QVariant MyListTableModel::data(const QModelIndex &index, int role) const {
    int row = index.row();
    int col = index.column();

    Movie movie = service.get_watchlist().get_watchlist()[row];
    string likes = to_string(movie.get_likes());
    string year = to_string(movie.get_year());


    if(role == Qt::DisplayRole){
        switch (col) {
            case 0:
                return QString::fromStdString(movie.get_title());
            case 1:
                return QString::fromStdString(movie.get_genre());
            case 2:
                return QString::fromStdString(year);
            case 3:
                return QString::fromStdString(likes);
            case 4:
                return QString::fromStdString(movie.get_trailer());
            default:
                break;
        }
    }

    return QVariant();
}

QVariant MyListTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
    if (role == Qt::DisplayRole)
    {
        if (orientation == Qt::Horizontal)
        {
            switch (section) {
                case 0:
                    return QString("Title");
                case 1:
                    return QString("Genre");
                case 2:
                    return QString("Year");
                case 3:
                    return QString("No. of Likes");
                case 4:
                    return QString("Trailer");
                case 5:
                    return QString("Play trailer");
                default:
                    break;
            }
        }
    }
    return QVariant();
}