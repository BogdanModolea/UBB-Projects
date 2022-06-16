//
// Created by bogdan on 3/1/2022.
//

#include "Repository.h"
#include <stdlib.h>
#include <string.h>
#include "stdio.h"

Repository *create_repo() {
    /*Creates a new element of type repository*/
    Repository *repository = (Repository *) malloc(sizeof(Repository));
    if (repository == NULL)
        return NULL;
    repository->length = 0;
    repository->capacity = 1;
    repository->product_list = (Product *) malloc(repository->capacity * sizeof(Product));
    return repository;
}

void destroy_repo(Repository **repository) {
    /*
     * Free the memory
     * */
    for (int i = 0; i < (*repository)->length; i++) {
        Product *product_list = (*repository)->product_list;
        destroy_product(product_list[i]);
    }
    free((*repository)->product_list);
    free(*repository);
    *repository = NULL;
}

void add_product(Repository *repository, Product product){
    /*
     * Add a new product
     * */
    if(repository->length == repository->capacity){
        repository->capacity *= 2;
        Product *new_list = (Product*) malloc(repository->capacity * sizeof(Product));
        for(int i = 0; i < repository->length; i++){
            new_list[i] = ((Product*)repository->product_list)[i];
        }
        free(repository->product_list);
        repository->product_list = new_list;
    }
    ((Product *)repository->product_list)[repository->length++] = product;
}

void update_product(Repository *repository, char* name, char* category, int quantity, char* exp_date) {
    /*
     * Update a product
     * */
    Product *product_list = repository->product_list;
    int pos = -1;
    for (int i = 0; i < repository->length; i++) {
        if (!strcmp(get_name(product_list[i]), name) && !strcmp(get_category(product_list[i]), category)) {
            pos = i;
            break;
        }
    }
    set_exp_date(&((Product *) repository->product_list)[pos], exp_date);
    set_quantity(&((Product *) repository->product_list)[pos], quantity);
}
int existing_product(Repository *repository, char *name, char *category){
    /*
     * Checks if we have a product in the list
     * */
    Product *product_list = repository->product_list;
    for(int i = 0; i < repository->length; i++){
        if(!strcmp(get_name(product_list[i]), name) && !strcmp(get_category(product_list[i]), category))
            return i;
    }
    return -1;
}

/*
* Getters and setters
* */

int repo_get_quantity(Repository *repository, int pos){
    Product *product_list = repository->product_list;
    return get_quantity(product_list[pos]);
}

char *repo_get_name(Repository *repository, int pos){
    Product *product_list = repository->product_list;
    return get_name(product_list[pos]);
}

char *repo_get_category(Repository *repository, int pos){
    Product *product_list = repository->product_list;
    return get_category(product_list[pos]);
}

char *repo_get_exp_date(Repository *repository, int pos){
    Product *product_list = repository->product_list;
    return get_exp_date(product_list[pos]);
}

int get_product_list_size(Repository *repository){
    return repository->length;
}

Product* get_product_list(Repository *repository){
    return repository->product_list;
}

void set_product_list_length(Repository *repository, int new_length){
    repository->length = new_length;
}

void set_product_list(Repository *repository, Product *new_product_list)
{
    repository->product_list = new_product_list;
}




void remove_product(Repository *repository, char *name, char *category){
    /*
     * Remove a product
     * */
    Product *product_list = repository->product_list;
    int pos = -1;
    for(int i = 0; i < repository->length; i++){
        if(!strcmp(name, get_name(product_list[i])) && !strcmp(category, get_category(product_list[i]))){
            pos = i;
            break;
        }
    }

    destroy_product(product_list[pos]);
    for(int i = pos; i < repository->length - 1; i++)
        product_list[i] = product_list[i + 1];
    repository->length--;
}

Product return_product(Repository *repository, char *name, char *category){
    /*
     * Returns a product
     * */
    Product *product_list = repository->product_list;
    Product my_product;
    my_product.name = NULL;
    my_product.category = NULL;
    for(int i = 0; i < repository->length; i++)
        if(!strcmp(get_name(product_list[i]), name) && !strcmp(get_category(product_list[i]), category))
            my_product = product_list[i];

    return my_product;
}