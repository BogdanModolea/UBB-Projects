//
// Created by bogdan on 16.05.2022.
//


#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include "GUI.h"
#include <qformlayout.h>
#include <qgridlayout.h>
#include "Domain.h"
#include <vector>
#include <qmessagebox.h>
#include <QString>
#include <qlabel.h>
#include <qimage.h>
#include "QPalette"
#include "QTableView"
#include <QtCore/QDebug>
#include <QtCharts/QChartView>
#include <QtCharts/QPieSeries>
#include <QtCharts/QPieSlice>
#include <QSignalMapper>

#include "MyListTableModel.h"
QT_USE_NAMESPACE


using namespace std;



GUI::GUI(Service& srv) : serv{srv}
{
    this->serv.file_loc("input.in");
    this->initGUI();
    this->populate();
    this->connectSignalsandSlots();
}

void GUI::initGUI(){
    this->moviesListWidget = new QListWidget{};
    this->watchlistWidget = new QListWidget{};
    this->userModel = new MyListTableModel(this->serv);
    //this->moviesListWidget = dynamic_cast<MyListWidget *>(new QListWidget{});
    //this->watchlistWidget = dynamic_cast<MyListWidget *>(new QListWidget{});
    this->titleLineEdit = new QLineEdit{};
    this->genreLineEdit = new QLineEdit{};
    this->yearLineEdit = new QLineEdit{};
    this->likesLineEdit = new QLineEdit{};
    this->trailerLineEdit = new QLineEdit{};
    this->addButton = new QPushButton("Add Movie");
    this->deleteButton = new QPushButton("Delete Movie");
    this->updateButton = new QPushButton("Update Movie");
    this->filterButton = new QPushButton("Filter");
    this->addWatchlistButton = new QPushButton("Add to Watchlist");
    this->playButton = new QPushButton("Play");
    this->nextButton = new QPushButton("Next");
    this->removeWatchlistButton = new QPushButton("Remove");
    this->diagramButton = new QPushButton("Chart");
    this->HTMLButton = new QPushButton("HTML");
    this->CSVButton = new QPushButton("CSV");
    this->ChoseCSV = new QPushButton("CSV Choice");
    this->ChoseHTML = new QPushButton("HTML Choice");

    this->undoButton = new QPushButton("Undo");
    this->redoButton = new QPushButton("Redo");
    this->tableView = new QPushButton("TableView");

    //addButton->setStyleSheet("background-color: #ffe0ac;");
    addButton->setStyleSheet(QString("background-color: qlineargradient(x1:0, y1:0, x2:1, y2:0, stop:0 white, stop: 0.4 ""gray, stop:1 green)"));
    QFont serifFont("Times", 16, QFont::Bold);
    addButton->setFont(serifFont);

    updateButton->setStyleSheet("background-color: #ffe0ac;");
    updateButton->setFont(serifFont);
    deleteButton->setStyleSheet("background-color:#ffacb7;");
    deleteButton->setFont(serifFont);

    QLabel* label = new QLabel{ "My movies :D" };
    QLabel* info = new QLabel{ "Press a movie to see details" };
    info->setAlignment(Qt::AlignCenter);

    QFont labelFont("Times", 12, QFont::Bold);
    labelFont.setItalic(true);

    label->setAlignment(Qt::AlignCenter);
    label->setFont(serifFont);
    info->setFont(labelFont);

    QVBoxLayout* main = new QVBoxLayout(this);

    main->addWidget(label);
    main->addWidget(info);
    main->addWidget(this->moviesListWidget);

    QFormLayout* det = new QFormLayout();
    QLabel* label1 = new QLabel{"Movies details"};
    label1->setAlignment(Qt::AlignCenter);

    QFont type("Times", 14, QFont::Bold);
    type.setItalic(true);

    label1->setFont(type);
    main->addWidget(label1);

    det->addRow("Title", this->titleLineEdit);
    det->addRow("Genre", this->genreLineEdit);
    det->addRow("Year", this->yearLineEdit);
    det->addRow("Likes", this->likesLineEdit);
    det->addRow("Trailer", this->trailerLineEdit);

    main->addLayout(det);

    QGridLayout* buttonLayout = new QGridLayout();
    buttonLayout->addWidget(this->ChoseHTML, 0, 0);
    buttonLayout->addWidget(this->ChoseCSV, 0, 1);
    buttonLayout->addWidget(this->addButton, 1, 0);
    buttonLayout->addWidget(this->deleteButton, 1, 1);
    main->addLayout(buttonLayout);

    QGridLayout* buttonLayout2 = new QGridLayout{};
    buttonLayout2->addWidget(this->updateButton, 0, 0);
    buttonLayout2->setAlignment(Qt::AlignCenter);
    main->addLayout(buttonLayout2);

    QGridLayout* buttonLayout3 = new QGridLayout{};
    buttonLayout3->addWidget(this->filterButton, 0, 0);
    buttonLayout3->addWidget(this->addWatchlistButton, 0, 1);
    buttonLayout3->setAlignment(Qt::AlignCenter);
    main->addLayout(buttonLayout3);

    main->addWidget(this->watchlistWidget);

    QGridLayout* buttonLayout4 = new QGridLayout{};
    buttonLayout4->addWidget(this->playButton, 0, 0);
    buttonLayout4->addWidget(this->nextButton, 0, 1);
    buttonLayout4->addWidget(this->removeWatchlistButton, 1, 0);
    buttonLayout4->addWidget(this->diagramButton, 1, 1);
    buttonLayout4->addWidget(this->HTMLButton, 2, 0);
    buttonLayout4->addWidget(this->CSVButton, 2, 1);
    buttonLayout4->setAlignment(Qt::AlignCenter);

    main->addLayout(buttonLayout4);


    QGridLayout* buttonLayout5 = new QGridLayout{};
    buttonLayout5->addWidget(this->undoButton, 0, 0);
    buttonLayout5->addWidget(this->redoButton, 0, 1);
    buttonLayout5->addWidget(this->tableView, 1, 0);

    main->addLayout(buttonLayout5);

}

void GUI::play_table(int i){
    auto all = this->serv.get_watchlist().get_watchlist();
    //string trailer = all[i].get_trailer();
    all[i].play_trailer();
    cout << i << "\n";
}

void GUI::watchlistTableViewHandler(){
    auto* tableViewWindow = new QWidget;
    auto* tableView = new QTableView;
    tableView->setModel(this->userModel);

    QSignalMapper* signalMapper = new QSignalMapper(this);

    for(int i = 0; i <= this->userModel->rowCount(); i++){
        auto item = userModel->index(i, 5);
        QPushButton* btn = new QPushButton("=^.^=");
        tableView->setIndexWidget(item, btn);

        signalMapper->setMapping(btn, i);
        connect(btn, SIGNAL(clicked()), signalMapper, SLOT(map()));

    }

    connect(signalMapper, SIGNAL(mapped(int)), this, SLOT(play_table(int)));


    auto* tableViewLayout = new QVBoxLayout(tableViewWindow);
    tableViewLayout->addWidget(tableView);
    tableViewWindow->setLayout(tableViewLayout);
    tableViewWindow->show();
}

void GUI::populate() {
    QLinearGradient linearGrad(QPointF(100, 100), QPointF(200, 200));
    linearGrad.setColorAt(0, Qt::black);
    linearGrad.setColorAt(1, Qt::white);
    this->moviesListWidget->setStyleSheet("background-color:#f1d1d1;");
    QFont serifFont("Times", 14);

    this->moviesListWidget->setFont(serifFont);

    this->moviesListWidget->clear();

    vector<Movie> movies = this->serv.get_all_movies();
    for(Movie& each : movies)
        this->moviesListWidget->addItem(QString::fromStdString(each.get_title() + " | " + each.get_genre()));
}

void GUI::connectSignalsandSlots() {
    QObject::connect(this->moviesListWidget, &QListWidget::itemSelectionChanged, [this](){
       int selectedIndex = this->getSelectedIndex();

       if(selectedIndex < 0)
           return;

       Movie mov = this->serv.get_all_movies()[selectedIndex];
       this->titleLineEdit->setText(QString::fromStdString(mov.get_title()));
       this->genreLineEdit->setText(QString::fromStdString(mov.get_genre()));
       this->yearLineEdit->setText(QString::fromStdString(to_string(mov.get_year())));
       this->likesLineEdit->setText(QString::fromStdString(to_string(mov.get_likes())));
       this->trailerLineEdit->setText(QString::fromStdString(mov.get_trailer()));
    });

    QObject::connect(this->addButton, &QPushButton::clicked, this, &GUI::addMovie);
    QObject::connect(this->deleteButton, &QPushButton::clicked, this, &GUI::deleteMovie);
    QObject::connect(this->updateButton, &QPushButton::clicked, this, &GUI::updateMovie);
    QObject::connect(this->filterButton, &QPushButton::clicked, this, &GUI::filterMovie);
    QObject::connect(this->addWatchlistButton, &QPushButton::clicked, this, &GUI::addWatchlist);
    QObject::connect(this->playButton, &QPushButton::clicked, this, &GUI::play);
    QObject::connect(this->nextButton, &QPushButton::clicked, this, &GUI::next);
    QObject::connect(this->removeWatchlistButton, &QPushButton::clicked, this, &GUI::removeWatchlist);
    QObject::connect(this->HTMLButton, &QPushButton::clicked, this, &GUI::openHTML);
    QObject::connect(this->CSVButton, &QPushButton::clicked, this, &GUI::openCSV);
    QObject::connect(this->ChoseHTML, &QPushButton::clicked, this, &GUI::choose_html);
    QObject::connect(this->ChoseCSV, &QPushButton::clicked, this, &GUI::choose_csv);

    QObject::connect(this->diagramButton, &QPushButton::clicked, this, &GUI::show_diagram);

    QObject::connect(this->undoButton, &QPushButton::clicked, this, &GUI::undo);
    QObject::connect(this->redoButton, &QPushButton::clicked, this, &GUI::redo);
    QObject::connect(this->tableView, &QPushButton::clicked, this, &GUI::watchlistTableViewHandler);
}

void GUI::undo(){
    this->serv.undo();
    this->populate();
}

void GUI::redo(){
    this->serv.redo();
    this->populate();
}

void GUI::show_diagram() {
    map<string, int> elems;

    vector<Movie> all = this->serv.get_all_movies();

    for (auto &each: all)
        elems[each.get_genre()]++;



    QPieSeries *series = new QPieSeries();
    //QPieSlice *slice = new QPieSlice();

    series->setName("Genre and Appearances");

    for (const auto &[key, value]: elems) {
        //slice =
        series->append(QString::fromStdString(key), value);
        //slice->setExploded();
    }

    series->setLabelsVisible();
    series->setLabelsPosition(QPieSlice::LabelInsideHorizontal);

    //series->chart()->legend()->setVisible(true);
    for(auto slice : series->slices()) {
        slice->setLabel(QString("%1%").arg(100 * slice->percentage(), 0, 'f', 1));
    }
    //slice->setExploded();


    QChartView* chartView = new QChartView();
    chartView->chart()->addSeries(series);

    chartView->show();


//    QMainWindow window;
//    window.setCentralWidget(chartView);
//    window.show();


}

int GUI::getSelectedIndex() const {
    QModelIndexList selectedIndexes = this->moviesListWidget->selectionModel()->selectedIndexes();
    if(selectedIndexes.size() == 0){
        this->titleLineEdit->clear();
        this->genreLineEdit->clear();
        this->yearLineEdit->clear();
        this->likesLineEdit->clear();
        this->trailerLineEdit->clear();
        return -1;
    }

    int selected = selectedIndexes.at(0).row();
    return selected;
}

void GUI::addMovie() {
    string title = this->titleLineEdit->text().toStdString();
    string genre = this->genreLineEdit->text().toStdString();
    string year = this->yearLineEdit->text().toStdString();
    string likes = this->likesLineEdit->text().toStdString();
    string trailer = this->trailerLineEdit->text().toStdString();

    if(serv.existingMovie(title)){
        QMessageBox::information(this, "Duplicated!", "Please try again!");
    }
    else{
        this->serv.add_movie(title, genre, stoi(year), stoi(likes), trailer);
        QMessageBox::information(this, "Added", "The movie was added");
        this->populate();
        int last = this->serv.get_all_movies().size() - 1;
        this->moviesListWidget->setCurrentRow(last);
    }
}

void GUI::deleteMovie() {
    int selected = this->getSelectedIndex();
    if(selected < 0){
        QMessageBox::critical(this, "Not deleted", "No movie was selected");
        return;
    }

    Movie mov = this->serv.get_all_movies()[selected];
    this->serv.delete_movie(mov.get_title());
    QMessageBox::information(this, "Deleted", "The movie was deleted");

    this->populate();

    int last = this->serv.get_all_movies().size() - 1;
    this->moviesListWidget->setCurrentRow(last);
}

void GUI::updateMovie() {
    string title = this->titleLineEdit->text().toStdString();
    string genre = this->genreLineEdit->text().toStdString();
    string year = this->yearLineEdit->text().toStdString();
    string likes = this->likesLineEdit->text().toStdString();
    string trailer = this->trailerLineEdit->text().toStdString();

    if(!this->serv.existingMovie(title)){
        QMessageBox::information(this, "Not updated", "The movie doesn't exist");
    }
    else{
        this->serv.update_movie(title, genre, stoi(year), stoi(likes), trailer);
        QMessageBox* box;
        box->information(this, "Updated", "The movie was updated");
        this->populate();
        int last = this->serv.get_all_movies().size() - 1;
        this->moviesListWidget->setCurrentRow(last);
    }
}

void GUI::show_wl(){
    this->watchlistWidget->clear();
    vector<Movie> movies = this->serv.get_watchlist().get_watchlist();
    for(Movie& each : movies)
        this->watchlistWidget->addItem(QString::fromStdString(each.get_title() + " | " + each.get_genre()));
}

int GUI::getSelectedIndexWL() const {
    QModelIndexList selectedIndexes = this->watchlistWidget->selectionModel()->selectedIndexes();
    if(selectedIndexes.size() == 0){
        this->titleLineEdit->clear();
        this->genreLineEdit->clear();
        this->yearLineEdit->clear();
        this->likesLineEdit->clear();
        this->trailerLineEdit->clear();
        return -1;
    }

    int selected = selectedIndexes.at(0).row();
    return selected;
}

void GUI::addWatchlist() {


    string title = this->titleLineEdit->text().toStdString();
    string genre = this->genreLineEdit->text().toStdString();
    string year = this->yearLineEdit->text().toStdString();
    string likes = this->likesLineEdit->text().toStdString();
    string trailer = this->trailerLineEdit->text().toStdString();


//    string title = mov.get_title();
//    string genre = mov.get_genre();
//    string year = to_string(mov.get_year());
//    string likes = to_string(mov.get_likes());
//    string trailer = mov.get_trailer();

    this->serv.add_movie_watchlist(Movie(title, genre, stoi(year), stoi(likes), trailer));
    this->show_wl();
}

void GUI::removeWatchlist(){
    int selected = this->getSelectedIndexWL();
    if(selected < 0){
        QMessageBox::critical(this, "Not deleted", "No movie was selected");
        return;
    }

    Movie mov = this->serv.get_watchlist().get_watchlist()[selected];
    this->serv.delete_movie_watchlist(mov.get_title());
    QMessageBox::information(this, "Deleted", "The movie was deleted");

    this->show_wl();

    int last = this->serv.get_watchlist().get_watchlist().size() - 1;
    this->watchlistWidget->setCurrentRow(last);
}

void GUI::filterMovie() {
    string genre = this->genreLineEdit->text().toStdString();
    this->moviesListWidget->clear();

    vector<Movie>all = this->serv.get_all_movies();
    vector<Movie> movies = this->serv.my_filter(genre, all).get_watchlist();
    for(Movie& each : movies)
        this->moviesListWidget->addItem(QString::fromStdString(each.get_title() + " | " + each.get_genre()));
}

void GUI::play() {
    int selected = this->getSelectedIndex();
    if(selected < 0){
        QMessageBox::critical(this, "Not deleted", "No movie was selected");
        return;
    }

    Movie mov = this->serv.get_all_movies()[selected];
    mov.play_trailer();
}

void GUI::next(){
    int selected = this->getSelectedIndex();
    if(selected < 0){
        QMessageBox::critical(this, "Not deleted", "No movie was selected");
        return;
    }
    this->watchlistWidget->setCurrentRow(selected + 1);
}

void GUI::openHTML(){
    this->serv.set_mode("app.html");
    string link = "D:\\.OOP\\a8-9-914-Modolea-Bogdan\\cmake-build-debug\\app.html";
    //ShellExecute(NULL, NULL, "chrome.exe", link.c_str(), NULL, SW_SHOWMAXIMIZED);
}

void GUI::openCSV(){
    this->serv.set_mode("app.csv");
    system("app.csv");

}

void GUI::choose_html(){
    this->serv.set_mode("app.html");
}

void GUI::choose_csv(){
    this->serv.set_mode("app.csv");
}