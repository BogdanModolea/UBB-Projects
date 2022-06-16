#include <QApplication>
#include <QPushButton>

#include "UI.h"
#include "GUI.h"
#include "FileRepository.h"

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);

    //FileRepository repository{};
    //Service service{repository};
//    UI ui{service};
//    ui.start();
//    return 0;

    unique_ptr<FileRepository> repository = make_unique<FileRepository>();
    Service service{ repository.get() };

    GUI gui{service};
    QFont serifFont("Times", 12);
    gui.setFont(serifFont);
    gui.setStyleSheet("background-color:#faf2f2;");
    gui.show();
    return a.exec();
}
