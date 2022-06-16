//
// Created by bogdan on 16.05.2022.
//

#ifndef OOP_GUI_H
#define OOP_GUI_H

#include <qwidget.h>
#include "Service.h"
#include <qlistwidget.h>
#include <qlineedit.h>
#include <qpushbutton.h>
#include <qlabel.h>
#include "QtCharts/QChartView"
#include "QtCharts/QPieSeries"
#include "QtCharts/QPieSlice"
#include "QtCore/QDebug"
//#include <QtCharts>

#include "MyListWidget.h"
#include "MyListTableModel.h"

//using namespace QtCharts;


class GUI:
        public QWidget
{
private:
    Service &serv;

    QListWidget *moviesListWidget;
    QListWidget *watchlistWidget;

    MyListTableModel* userModel;

    QLineEdit* titleLineEdit;
    QLineEdit* genreLineEdit;
    QLineEdit* yearLineEdit;
    QLineEdit* likesLineEdit;
    QLineEdit* trailerLineEdit;

    QPushButton* addButton;
    QPushButton* deleteButton;
    QPushButton* updateButton;

    QPushButton* addWatchlistButton;
    QPushButton* removeWatchlistButton;
    QPushButton* diagramButton;
    QPushButton* filterButton;

    QPushButton* playButton;
    QPushButton* nextButton;

    QPushButton* HTMLButton;
    QPushButton* CSVButton;

    QPushButton* ChoseHTML;
    QPushButton* ChoseCSV;

    QPushButton* undoButton;
    QPushButton* redoButton;
    QPushButton* tableView;

public:
    GUI(Service& srv);

private:
    void initGUI();

    void populate();

    void connectSignalsandSlots();

    int getSelectedIndex() const;

    void addMovie();

    void deleteMovie();

    void updateMovie();

    void addWatchlist();

    void filterMovie();

    void show_wl();

    void play();

    void next();

    int getSelectedIndexWL() const;

    void removeWatchlist();

    void openHTML();

    void openCSV();

    void choose_html();

    void choose_csv();

    void show_diagram();

    void undo();

    void redo();

    void watchlistTableViewHandler();

    void play_table(int i);
};

#endif //OOP_GUI_H
