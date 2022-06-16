//
// Created by bogdan on 3/11/2022.
//

#include "Test.h"
#include "Repository.h"
#include "Service.h"
#include "assert.h"
#include "string.h"
#include "stdio.h"

void test_service(){
    Repository *repository = create_repo();

    Service *service = create_service(repository);
    assert(service_add_product(service, "test", "test", 12, "test") == 0);
    assert(service_update_product(service, "test", "test", 50, "test") == 0);
    assert(service_undo(service) == 1);
    assert(service_redo(service) == 1);
    assert(service_remove_product(service, "test", "test") == 0);
    assert(service_undo(service) == 1);
    assert(service_undo(service) == 1);
    assert(service_undo(service) == 1);
    assert(service_undo(service) == -1);
    assert(service_get_days(5, 15, 5, 30) == 16);

    destroy_service(&service);
}

void test_all() {

    Product product = create_product("Banane", "sweets", 12, "30.05");
    Repository *repository = create_repo();
    add_product(repository, product);
    assert(product.quantity == 12);
    assert(repository->length == 1);
    update_product(repository, "Banane", "sweets", 30, "30.05");
    assert(((Product *) repository->product_list)[0].quantity == 30);
    assert(existing_product(repository, "Banane", "sweets") == 0);
    assert(!strcmp("Banane", repo_get_name(repository, 0)));
    remove_product(repository, "Banane", "sweets");
    assert(repository->length == 0);
    destroy_repo(&repository);
    test_service();
}