//
// Created by bogdan on 4/5/2022.
//

#include "Tests.h"
#include "Service.h"
#include "assert.h"

void Tests::test_service(){
    Repository repo;
    Service service{repo};

    service.service_add("Test", "test", "test");
    assert(service.get_all_proteins().size() == 1);

    bool except = true;
    try{
        service.service_remove("Test1", "test");
    }
    catch (...){
        except = false;
    }
    assert(except == false);

    service.service_remove("Test", "test");
    assert(service.get_all_proteins().size() == 0);

    service.service_add("Test", "test", "test");

    assert(service.filter("idk").size() == 0);

}

void Tests::test_repo(){
    Repository repo;
    Protein prot = Protein("Test", "Test", "IDK");
    repo.repo_add(prot);
    assert(repo.get_proteins().size() == 1);

    repo.repo_remove(prot);
    assert(repo.get_proteins().size() == 0);

}

void Tests::test_all() {
    test_service();
    test_repo();
}