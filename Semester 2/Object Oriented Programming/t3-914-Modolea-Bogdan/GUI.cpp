//
// Created by bogdan on 24.05.2022.
//

#include "GUI.h"

#include "GUI.h"
#include <qlayout.h>
#include <qformlayout.h>
#include <qgridlayout.h>
#include "Domain.h"
#include <vector>
#include <QString>
#include "bits/stdc++.h"


GUI::GUI(Service& srv) : serv{srv} {
    this->initGUI();
    this->populate();
    this->connectSignalsandSlots();
}

void GUI::initGUI() {
    this->myListWidget = new QListWidget{};
    this->anotherList = new QListWidget{};

    this->symptoms = new QLineEdit{};
    this->disorder = new QLineEdit{};

    this->filterButton = new QPushButton{"Filter"};
    this->showButton = new QPushButton{"Show"};

    QVBoxLayout* main = new QVBoxLayout(this);
    main->addWidget(this->myListWidget);


    QFormLayout* detail = new QFormLayout();
    detail->addRow("Symptoms", this->symptoms);
    main->addLayout(detail);


    QGridLayout* buttonLayout = new QGridLayout();
    buttonLayout->addWidget(this->filterButton, 0, 0);
    buttonLayout->addWidget(this->showButton, 0, 1);
    main->addLayout(buttonLayout);

    QFormLayout* detail2 = new QFormLayout();
    detail2->addRow("Disorder", this->disorder);
    main->addLayout(detail2);

    main->addWidget(anotherList);

}

bool cmp(Domain a, Domain b){
    return a.get_name() < b.get_name();
}

void GUI::populate() {
    this->myListWidget->clear();
    vector<Domain>something = this->serv.load_content();

    sort(something.begin(), something.end(), cmp);

    for(auto& each : something)
        this->myListWidget->addItem(QString::fromStdString(each.output()));
}

void GUI::filter(){
    string symp = this->symptoms->text().toStdString();

    vector<Domain>all = this->serv.load_content();

    this->myListWidget->clear();

    for(auto each : all){
        vector<string>mine = each.get_symps();
        for(auto now : mine){
            std::size_t found;
            found=now.find(symp);
            if (found!=std::string::npos){
                this->myListWidget->addItem(QString::fromStdString(each.output()));
                break;
            }
        }
    }
}

void GUI::show_symps(){
    string dis = this->disorder->text().toStdString();

    vector<Domain>all = this->serv.load_content();

    this->anotherList->clear();

    for(auto each : all){
        std::size_t found;
        found=each.get_name().find(dis);
        if(found!=std::string::npos){
            vector<string> mine = each.get_symps();
            for(auto el : mine){
                this->anotherList->addItem(QString::fromStdString(each.get_name() + " | " + el));
            }
        }
    }

}

int GUI::getSelectedIndex() const {
    QModelIndexList selectedIndexes = this->myListWidget->selectionModel()->selectedIndexes();
    if(selectedIndexes.size() == 0){
        this->disorder->clear();
        return -1;
    }

    int selected = selectedIndexes.at(0).row();
    return selected;
}


void GUI::connectSignalsandSlots() {
    QObject::connect(this->myListWidget, &QListWidget::itemSelectionChanged, [this](){
        int selectedIndex = this->getSelectedIndex();

        if(selectedIndex < 0)
            return;

        Domain elem = this->serv.load_content()[selectedIndex];
        this->disorder->setText(QString::fromStdString(elem.get_name()));
    });

    QObject::connect(this->showButton, &QPushButton::clicked, this, &GUI::show_symps);
    QObject::connect(this->filterButton, &QPushButton::clicked, this, &GUI::filter);
}





