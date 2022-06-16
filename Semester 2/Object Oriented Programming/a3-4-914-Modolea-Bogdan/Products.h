//
// Created by bogdan on 3/4/2022.
//

#ifndef A3_4_914_MODOLEA_BOGDAN_PRODUCTS_H
#define A3_4_914_MODOLEA_BOGDAN_PRODUCTS_H

typedef struct{
    char *name, *category, *exp_date;
    int quantity;
}Product;

Product create_product(char *name, char *category, int quantity, char *exp_date);
void destroy_product(Product product);
char* get_name(Product product);
char* get_category(Product product);
int get_quantity(Product product);
char* get_exp_date(Product product);
void set_quantity(Product *product, int new_quantity);
void set_exp_date(Product *product, char* exp_date);
Product copy(Product product);

#endif //A3_4_914_MODOLEA_BOGDAN_PRODUCTS_H
