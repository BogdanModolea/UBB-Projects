//
// Created by bogdan on 3/1/2022.
//

#ifndef A3_4_914_MODOLEA_BOGDAN_REPOSITORY_H
#define A3_4_914_MODOLEA_BOGDAN_REPOSITORY_H

#include "Products.h"

typedef struct {
    void *product_list;
    int length, capacity;
}Repository;

Repository *create_repo();
void destroy_repo(Repository **repository);
int existing_product(Repository *repository, char *name, char *category);
void add_product(Repository *repository, Product product);
void update_product(Repository *repository, char* name, char* category, int quantity, char* exp_date);
void remove_product(Repository *repository, char *name, char *category);

int repo_get_quantity(Repository *repository, int pos);
char *repo_get_name(Repository *repository, int pos);
char *repo_get_category(Repository *repository, int pos);
char *repo_get_exp_date(Repository *repository, int pos);
int get_product_list_size(Repository *repository);
Product* get_product_list(Repository *repository);
void set_product_list_length(Repository *repository, int new_length);
void set_product_list(Repository *repository, Product *new_product_list);

Product return_product(Repository *repository, char *name, char *category);
#endif //A3_4_914_MODOLEA_BOGDAN_REPOSITORY_H
