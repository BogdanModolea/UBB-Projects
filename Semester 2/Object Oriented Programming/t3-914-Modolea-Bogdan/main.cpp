#include <QApplication>
#include <QPushButton>
#include "GUI.h"
#include "Service.h"

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);
    Service service{"input.in"};
    GUI gui{service};
    gui.show();
    return a.exec();
}