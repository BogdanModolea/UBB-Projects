//
// Created by bogdan on 3/1/2022.
//

#ifndef A3_4_914_MODOLEA_BOGDAN_SERVICE_H
#define A3_4_914_MODOLEA_BOGDAN_SERVICE_H

#include "Repository.h"
//#include "UndoOperation.h"
#include "UndoList.h"

struct Servicee{
    Repository *repository;

    Undo_Redo_Service *undo_redo_service;

    char *reverse_op, *last_op;
    Product product_to_undo, product_to_redo;
};

typedef struct Servicee Service;

int service_add_product(Service *service, char *name, char *category, int quantity, char *exp_date);
int service_update_product(Service *service, char* name, char* category, int quantity, char* exp_date);
int service_remove_product(Service *service, char *name, char *category);

int service_get_days(int exp_month, int exp_day, int curr_month, int curr_day);

void my_sort(Product *my_list, int n);
void my_other_sort(Product *my_list, int n);
void my_other_other_sort(Product *my_list, int n);

Service *create_service(Repository *repository);
void destroy_service(Service **service);

int service_undo(Service *service);
int service_redo(Service *service);

#endif //A3_4_914_MODOLEA_BOGDAN_SERVICE_H
