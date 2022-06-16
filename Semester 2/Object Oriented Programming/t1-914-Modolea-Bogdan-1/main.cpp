//
// Created by bogdan on 4/5/2022.
//

#include "UI.h"
#include "Tests.h"

void auto_add(Repository repository){
    Protein prot = Protein("Human", "Myosin-2", "MSSDSEKAVFGGEZ");
    repository.repo_add(prot);

    prot = Protein("Human", "Keratin", "IDKWHATTOWRITE");
    repository.repo_add(prot);

    prot = Protein("Mouse", "Keratin", "SOMETHINGELSEHERE");
    repository.repo_add(prot);

    prot = Protein("E_Coli", "cspE", "JANKOSIDKGG");
    repository.repo_add(prot);

    prot = Protein("E_Coli", "idk", "JANKOSIDKGG");
    repository.repo_add(prot);
}

int main(){
    Tests::test_all();
    Repository repository;
    auto_add(repository);
    Service service{repository};
    UI ui{service};
    ui.start();
    return 0;
}