//
// Created by bogdan on 3/1/2022.
//

#include "Service.h"
#include <stdlib.h>
#include <string.h>
#include "stdio.h"

Service *create_service(Repository *repository){
    /*
     * Creates a new service
     * */

    Service *service = (Service*)malloc(sizeof(Service));
    if(service == NULL)
        return NULL;
    service->repository = repository;
    service->undo_redo_service = create_undo_redo_service(service);
    service->reverse_op = NULL;
    service->last_op = NULL;
    service->product_to_undo.name = NULL;
    service->product_to_undo.category = NULL;
    service->product_to_undo.exp_date = NULL;

    service->product_to_redo.name = NULL;
    service->product_to_redo.category = NULL;
    service->product_to_redo.exp_date = NULL;


    return service;
}

int service_add_product(Service *service, char *name, char *category, int quantity, char *exp_date){
    /*
     * Add a new product if it doesn't exist, otherwise we update the quantity
     * */
    if(existing_product(service->repository, name, category) != -1){
        int pos = existing_product(service->repository, name, category);
        service->reverse_op = "update";
        service->last_op = "update";
        destroy_product(service->product_to_undo);
        destroy_product(service->product_to_redo);
        Product product = create_product(name, category, repo_get_quantity(service->repository, pos), repo_get_exp_date(service->repository, pos));
        service->product_to_undo = copy(product);
        service->product_to_redo = create_product(name, category, repo_get_quantity(service->repository, pos) + quantity, exp_date);
        update_product(service->repository, name, category, quantity + repo_get_quantity(service->repository, pos), exp_date);
        stack(service);
        return -1;
    }
    Product product = create_product(name, category, quantity, exp_date);
    service->reverse_op = "remove";
    service->last_op = "add";
    destroy_product(service->product_to_undo);
    destroy_product(service->product_to_redo);
    service->product_to_undo = copy(product);
    service->product_to_redo = copy(product);
    add_product(service->repository, product);
    stack(service);
    return 0;
}

int service_update_product(Service *service, char* name, char* category, int quantity, char* exp_date){
    /*
     * Updates a product
     * */
    if(existing_product(service->repository, name, category) == -1)
        return -1;

    service->last_op = "update";
    service->reverse_op = "update";

    destroy_product(service->product_to_undo);
    destroy_product(service->product_to_redo);

    service->product_to_undo = copy(return_product(service->repository, name, category));
    service->product_to_redo = create_product(name, category, quantity, exp_date);

    update_product(service->repository, name, category, quantity, exp_date);
    stack(service);
    return 0;
}

int service_remove_product(Service *service, char *name, char *category){
    /*
     * Remove a product
     * */
    if(existing_product(service->repository, name, category) == -1)
        return -1;

    service->last_op = "remove";
    service->reverse_op = "add";

    destroy_product(service->product_to_undo);
    destroy_product(service->product_to_redo);

    service->product_to_undo = copy(return_product(service->repository, name, category));
    service->product_to_redo = copy(return_product(service->repository, name, category));

    remove_product(service->repository, name, category);
    stack(service);
    return 0;
}

int service_get_days(int exp_month, int exp_day, int curr_month, int curr_day) {
    /*
     * Transform a date into days
     * */
    int total_days = 0;
    for (int i = curr_month + 1; i < exp_month; i++) {
        if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12)
            total_days += 31;
        else if (i == 2)
            total_days += 28;
        else
            total_days += 30;
    }
    total_days += exp_day;
    if (curr_month == 1 || curr_month == 3 || curr_month == 5 || curr_month == 7 || curr_month == 8 ||
        curr_month == 10 || curr_month == 12)
        total_days += (31 - curr_day);
    else if (curr_month == 2)
        total_days += (28 - curr_day);
    else
        total_days += (30 - curr_day);

    return total_days;
}


void my_sort(Product *my_list, int n){
    /*
     * Sort a list by quantity
     * */
    for(int i = 0; i < n - 1; i++){
        for(int j = i + 1; j < n; j++){
            if(my_list[i].quantity > my_list[j].quantity){
                Product aux = my_list[i];
                my_list[i] = my_list[j];
                my_list[j] = aux;
            }
        }
    }
}

void my_other_sort(Product *my_list, int n){
    /*
     * Sort a list by quantity
     * */
    for(int i = 0; i < n - 1; i++){
        for(int j = i + 1; j < n; j++){
            if(strcmp(my_list[i].category, my_list[j].category) > 0){
                Product aux = my_list[i];
                my_list[i] = my_list[j];
                my_list[j] = aux;
            }
        }
    }
}

void my_other_other_sort(Product *my_list, int n){
    for(int i = 0; i < n - 1; i++){
        for(int j = i + 1; j < n; j++){
            if(my_list[i].quantity > my_list[j].quantity){
                Product aux = my_list[i];
                my_list[i] = my_list[j];
                my_list[j] = aux;
            }
        }
    }
}


void destroy_service(Service **service){
    /*
     * Free the memory
     * */
    destroy_repo(&(**service).repository);
    destroy_undo(&(**service).undo_redo_service);
    destroy_product((**service).product_to_undo);
    destroy_product((**service).product_to_redo);
    free(*service);
    *service = NULL;
}

int service_undo(Service *service){
    /*
     * Undo an operation
     * */
    return undo(service);
}

int service_redo(Service *service){
    /*
     * Redo an operation
     * */
    return redo(service);
}