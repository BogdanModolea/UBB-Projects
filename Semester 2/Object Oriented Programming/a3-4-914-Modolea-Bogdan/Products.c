//
// Created by bogdan on 3/4/2022.
//

#include "Products.h"
#include <stdlib.h>
#include "stdio.h"
#include <string.h>

Product create_product(char *name, char *category, int quantity, char *exp_date) {
    /*Creates a new element of type product */
    Product product;
    product.quantity = quantity;
    product.name = (char *) malloc((strlen(name) + 1) * sizeof(char));
    strcpy(product.name, name);
    product.category = (char *) malloc((strlen(category) + 1) * sizeof(char));
    strcpy(product.category, category);
    product.exp_date = (char *) malloc((strlen(exp_date) + 1) * sizeof(char));
    strcpy(product.exp_date, exp_date);
    return product;
}

void destroy_product(Product product) {
    /*
     * Free the memory
     * */
    free(product.name);
    free(product.category);
    free(product.exp_date);
}


/*Getters and setters */

char* get_name(Product product) {
    return product.name;
}

char* get_category(Product product) {
    return product.category;
}

int get_quantity(Product product) {
    return product.quantity;
}

char* get_exp_date(Product product) {
    return product.exp_date;
}

void set_quantity(Product *product, int new_quantity) {
    product->quantity = new_quantity;
}

void set_exp_date(Product *product, char* exp_date) {
    free(product->exp_date);
    product->exp_date = (char *) malloc((strlen(exp_date) + 1) * sizeof(char));
    strcpy(product->exp_date, exp_date);
}


/* Copy a product */
Product copy(Product product){
    Product cpy;
    cpy.quantity = product.quantity;
    cpy.name = (char*) malloc((strlen(product.name) + 1)  * sizeof(char));
    cpy.category = (char*) malloc((strlen(product.category) + 1) * sizeof(char));
    cpy.exp_date = (char*) malloc((strlen(product.exp_date) + 1) * sizeof(char));

    strcpy(cpy.name, product.name);
    strcpy(cpy.category, product.category);
    strcpy(cpy.exp_date, product.exp_date);


    return cpy;
}