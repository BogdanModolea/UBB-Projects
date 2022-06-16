//
// Created by bogdan on 3/1/2022.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "UI.h"

UI* create_ui(){
    UI *ui = (UI*)malloc(sizeof(UI));
    Repository *repository = create_repo();
    Service *service = create_service(repository);
    ui->service = service;
    return ui;
}

void auto_add(UI *ui){
    service_add_product(ui->service, "lapte", "dairy", 12, "12.07");
    service_add_product(ui->service, "oua", "dairy", 1, "12.04");
    service_add_product(ui->service, "branza", "dairy", 10, "18.03");
    service_add_product(ui->service, "milka", "sweets", 2000, "22.11");
    service_add_product(ui->service, "kinder", "sweets", 100, "12.04");
    service_add_product(ui->service, "lollipop", "sweets", 1, "24.12");
    service_add_product(ui->service, "vita", "meat ", 10, "31.07");
    service_add_product(ui->service, "porc", "meat ", 3, "01.09");
    service_add_product(ui->service, "pepene", "fruit", 1, "20.03");
    service_add_product(ui->service, "Me", "sweets", 100000, "30.05");
}

void menu(){
    printf("\n1. Manage a product\n"
           "2. Display all products with a given name\n"
           "3. Display all products of a given category\n"
           "4. Undo\n"
           "5. Redo\n"
           "6. Display all products with a given expire date\n"
           "7. Exit\n");
}

void menu11(){
    printf("\n1. Add a product\n"
           "2. Delete a product\n"
           "3. Update a product\n");
}

void start(UI *ui) {
    printf("Welcome to my app! :DD\n\n");
    auto_add(ui);
    while (1) {
        menu();
        int cmd;
        printf("Enter a command>");
        scanf("%d", &cmd);
        if (cmd == 7)
            return;
        if (cmd == 1) {
            menu11();
            int opt;
            printf("Enter an option>");
            scanf("%d", &opt);
            if(opt == 1){
                char *name, *category, *exp_date;
                name = (char*) malloc(sizeof(char));
                category = (char*) malloc(sizeof(char));
                exp_date = (char*) malloc(sizeof(char));
                int quantity = 0;
                printf("Name:");
                scanf("%s", name);
                printf("Category:");
                scanf("%s", category);
                printf("Quantity:");
                scanf("%d", &quantity);
                printf("Expire date:");
                scanf("%s", exp_date);
                service_add_product(ui->service, name, category, quantity, exp_date);
            }
            else if(opt == 2){
                char *name, *category;
                name = (char*) malloc(sizeof(char));
                category = (char*) malloc(sizeof(char));
                printf("Name to delete:");
                scanf("%s", name);
                printf("Category to delete:");
                scanf("%s", category);
                if(service_remove_product(ui->service, name, category) == -1)
                    printf("Nothing to delete :(\n");
            }
            else if(opt == 3){
                char *name, *category, *exp_date;
                int quantity;
                name = (char*) malloc(sizeof(char));
                category = (char*) malloc(sizeof(char));
                exp_date = (char*) malloc(sizeof(char));
                printf("Name to update:");
                scanf("%s", name);
                printf("Category to update:");
                scanf("%s", category);
                printf("New quantity:");
                scanf("%d", &quantity);
                printf("New expire date:");
                scanf("%s", exp_date);
                if(service_update_product(ui->service, name, category, quantity, exp_date) == -1)
                    printf("Nothing to update :(\n");
            }
        }
        else if(cmd == 2){
            char name[30], sth[1];
            gets(sth);
            printf("Enter a name:");
            gets(name);

            printf("\n");
            if(strlen(name) == 0){
                Product *list_to_sort = (Product*) malloc(ui->service->repository->capacity * sizeof(Product));
                for(int i = 0; i < ui->service->repository->length; i++)
                    list_to_sort[i] = ((Product *)ui->service->repository->product_list)[i];

                my_sort(list_to_sort, ui->service->repository->length);
                for(int i = 0; i < ui->service->repository->length; i++)
                    printf("%s | %s | %d | %s\n", get_name(list_to_sort[i]), get_category(list_to_sort[i]), get_quantity(list_to_sort[i]), get_exp_date(list_to_sort[i]));
                free(list_to_sort);
            }
            else{
                Product *list_to_sort = (Product*) malloc(ui->service->repository->capacity * sizeof(Product));
                int cnt = 0;
                for(int i = 0; i < ui->service->repository->length; i++)
                    if(!strcmp(repo_get_name(ui->service->repository, i), name))
                        list_to_sort[cnt++] = ((Product*)ui->service->repository->product_list)[i];
                        //printf("%s | %s | %d | %s\n", repo_get_name(ui->service->repository, i), repo_get_category(ui->service->repository, i), repo_get_quantity(ui->service->repository, i), repo_get_exp_date(ui->service->repository, i))

                my_sort(list_to_sort, cnt);
                for(int i = 0; i < cnt; i++)
                    printf("%s | %s | %d | %s\n", get_name(list_to_sort[i]), get_category(list_to_sort[i]), get_quantity(list_to_sort[i]), get_exp_date(list_to_sort[i]));
                free(list_to_sort);
            }
            printf("\n");
        }
        else if(cmd == 3){
            char category[30], sth[1];
            gets(sth);
            printf("Enter a category:");
            gets(category);

            int days;
            printf("Enter a number of days:");
            scanf("%d", &days);


            printf("\n");
            int cnt = 0;
            if(strlen(category) == 0){
                Product *new_list = (Product*) malloc(ui->service->repository->capacity * sizeof(Product));
                for(int i = 0; i < ui->service->repository->length; i++){
                    int exp_day, exp_month;
                    char x[2];
                    x[0] = ((Product*)ui->service->repository->product_list)[i].exp_date[0];
                    x[1] = ((Product*)ui->service->repository->product_list)[i].exp_date[1];
                    exp_day = atoi(x);
                    x[0] = ((Product*)ui->service->repository->product_list)[i].exp_date[3];
                    x[1] = ((Product*)ui->service->repository->product_list)[i].exp_date[4];
                    exp_month = atoi(x);
                    int my_days = service_get_days(exp_month, exp_day, 3, 15);

                    if(my_days <= days)
                        new_list[cnt++] = ((Product*)ui->service->repository->product_list)[i];
                }
                int srt;
                printf("How would you like to sort the list? (1 for ascending / 2 for descending)");
                scanf("%d", &srt);

                my_other_sort(new_list, cnt);

                if(srt == 1)
                    for(int i = 0; i < cnt; i++)
                        printf("%s | %s | %d | %s\n", get_name(new_list[i]), get_category(new_list[i]), get_quantity(new_list[i]), get_exp_date(new_list[i]));
                else
                    for(int i = cnt - 1; i >- 0; i--)
                        printf("%s | %s | %d | %s\n", get_name(new_list[i]), get_category(new_list[i]), get_quantity(new_list[i]), get_exp_date(new_list[i]));


                free(new_list);
            }
            else{
                Product *new_list = (Product*) malloc(ui->service->repository->capacity * sizeof(Product));
                for(int i = 0; i < ui->service->repository->length; i++){
                    int exp_day, exp_month;
                    char x[2];
                    x[0] = ((Product*)ui->service->repository->product_list)[i].exp_date[0];
                    x[1] = ((Product*)ui->service->repository->product_list)[i].exp_date[1];
                    exp_day = atoi(x);
                    x[0] = ((Product*)ui->service->repository->product_list)[i].exp_date[3];
                    x[1] = ((Product*)ui->service->repository->product_list)[i].exp_date[4];
                    exp_month = atoi(x);
                    int my_days = service_get_days(exp_month, exp_day, 3, 15);

                    if(my_days <= days && !strcmp(category, ((Product*)ui->service->repository->product_list)[i].category))
                        new_list[cnt++] = ((Product*)ui->service->repository->product_list)[i];
                }
                int srt;
                printf("How would you like to sort the list? (1 for ascending / 2 for descending)");
                scanf("%d", &srt);

                my_other_other_sort(new_list, cnt);

                if(srt == 1)
                    for(int i = 0; i < cnt; i++)
                        printf("%s | %s | %d | %s\n", get_name(new_list[i]), get_category(new_list[i]), get_quantity(new_list[i]), get_exp_date(new_list[i]));
                else
                    for(int i = cnt - 1; i >- 0; i--)
                        printf("%s | %s | %d | %s\n", get_name(new_list[i]), get_category(new_list[i]), get_quantity(new_list[i]), get_exp_date(new_list[i]));


                free(new_list);
            }
        }
        else if(cmd == 4){
            if(service_undo(ui->service) == -1)
                printf("No operation to undo\n");
        }
        else if(cmd == 5){
            if(service_redo(ui->service) == -1)
                printf("No operation to redo\n");
        }
        else if(cmd == 6){
            char exp_date[30], sth[1];
            gets(sth);
            printf("Enter a date:");
            gets(exp_date);

            printf("\n");
            if(strlen(exp_date) == 0){
                Product *list_to_sort = (Product*) malloc(ui->service->repository->capacity * sizeof(Product));
                for(int i = 0; i < ui->service->repository->length; i++)
                    list_to_sort[i] = ((Product *)ui->service->repository->product_list)[i];

                my_sort(list_to_sort, ui->service->repository->length);
                for(int i = 0; i < ui->service->repository->length; i++)
                    printf("%s | %s | %d | %s\n", get_name(list_to_sort[i]), get_category(list_to_sort[i]), get_quantity(list_to_sort[i]), get_exp_date(list_to_sort[i]));
                free(list_to_sort);
            }
            else{
                Product *list_to_sort = (Product*) malloc(ui->service->repository->capacity * sizeof(Product));
                int cnt = 0;
                for(int i = 0; i < ui->service->repository->length; i++)
                    if(!strcmp(repo_get_exp_date(ui->service->repository, i), exp_date))
                        list_to_sort[cnt++] = ((Product*)ui->service->repository->product_list)[i];

                my_sort(list_to_sort, cnt);
                for(int i = 0; i < cnt; i++)
                    printf("%s | %s | %d | %s\n", get_name(list_to_sort[i]), get_category(list_to_sort[i]), get_quantity(list_to_sort[i]), get_exp_date(list_to_sort[i]));
                free(list_to_sort);
            }
            printf("\n");
        }
        else{
            printf("Something went wrong!\n");
            cmd = 0;
        }
    }
}


void destroy_ui(UI **ui) {
    destroy_service(&(**ui).service);
    free(*ui);
    *ui = NULL;
}