//
// Created by bogdan on 3/21/2022.
//

#include "UndoList.h"
#include "Products.h"
#include "Service.h"
#include "Repository.h"
#include "stdlib.h"

Undo_Redo_Service* create_undo_redo_service(struct Servicee *service) {
    Undo_Redo_Service *undoRedoService = (Undo_Redo_Service *) malloc(sizeof(Undo_Redo_Service));
    undoRedoService->undo_length = 1;
    undoRedoService->undo_capacity = 1;
    undoRedoService->undo_point = 0;
    undoRedoService->undo_list = malloc(undoRedoService->undo_capacity * sizeof(Product *));
    undoRedoService->undo_list_len = malloc(undoRedoService->undo_capacity * sizeof(int));

    Product* copy_products = (Product*) malloc(get_product_list_size(service->repository) * sizeof(Product));
    for(int i = 0; i < get_product_list_size(service->repository); i++)
        copy_products[i] = copy(get_product_list(service->repository)[i]);

    undoRedoService->undo_list_len[0] = get_product_list_size(service->repository);
    undoRedoService->undo_list[0] = copy_products;

    return undoRedoService;
}

void destroy_undo_list_at_pos(Undo_Redo_Service *undoRedoService, int pos) {
    for (int i = 0; i < undoRedoService->undo_list_len[pos]; i++) {
        Product curr_prod = ((Product **) undoRedoService->undo_list)[pos][i];
        destroy_product(curr_prod);
    }
    free(((Product **) undoRedoService->undo_list)[pos]);
}

void destroy_undo(Undo_Redo_Service **undoRedoService){
    for(int i = 0; i < (**undoRedoService).undo_length; i++)
        destroy_undo_list_at_pos(*undoRedoService, i);
    free((**undoRedoService).undo_list);
    free((**undoRedoService).undo_list_len);
    free(*undoRedoService);
    *undoRedoService = NULL;
}

void stack(Service *service) {
    while (service->undo_redo_service->undo_length > service->undo_redo_service->undo_point + 1) {
        destroy_undo_list_at_pos(service->undo_redo_service, service->undo_redo_service->undo_length - 1);
        service->undo_redo_service->undo_length--;
    }

    if (service->undo_redo_service->undo_length == service->undo_redo_service->undo_capacity) {
        service->undo_redo_service->undo_capacity *= 2;
        Product **new_undo_list = (Product **) malloc(service->undo_redo_service->undo_capacity * sizeof(Product *));
        for (int i = 0; i < service->undo_redo_service->undo_length; i++)
            new_undo_list[i] = service->undo_redo_service->undo_list[i];
        free(service->undo_redo_service->undo_list);
        service->undo_redo_service->undo_list = (void **) new_undo_list;

        int *new_undo_list_len = (int *) malloc(service->undo_redo_service->undo_capacity * sizeof(int));
        for (int i = 0; i < service->undo_redo_service->undo_length; i++)
            new_undo_list_len[i] = service->undo_redo_service->undo_list_len[i];
        free(service->undo_redo_service->undo_list_len);
        service->undo_redo_service->undo_list_len = new_undo_list_len;
    }

    service->undo_redo_service->undo_list[service->undo_redo_service->undo_length] = malloc(get_product_list_size(service->repository) * sizeof(Product));
    service->undo_redo_service->undo_list_len[service->undo_redo_service->undo_length] = get_product_list_size(service->repository);

    for (int i = 0; i < service->undo_redo_service->undo_list_len[service->undo_redo_service->undo_length]; i++)
        ((Product **) service->undo_redo_service->undo_list)[service->undo_redo_service->undo_length][i] = copy(
                get_product_list(service->repository)[i]);

    service->undo_redo_service->undo_length++;
    service->undo_redo_service->undo_point = service->undo_redo_service->undo_length - 1;
}

void set_product_list_to_current_pointer(Service *service){
    for(int i = 0; i < get_product_list_size(service->repository); i++){
        Product* product_list = get_product_list(service->repository);
        destroy_product(product_list[i]);
    }

    free(get_product_list(service->repository));

    set_product_list_length(service->repository, service->undo_redo_service->undo_list_len[service->undo_redo_service->undo_point]);
    Product *new_product_list = (Product*) malloc(service->undo_redo_service->undo_list_len[service->undo_redo_service->undo_point] * sizeof(Product));
    for(int i = 0; i < service->undo_redo_service->undo_list_len[service->undo_redo_service->undo_point]; i++)
        new_product_list[i] = copy(((Product**)service->undo_redo_service->undo_list)[service->undo_redo_service->undo_point][i]);
    set_product_list(service->repository, new_product_list);
}

int undo(Service *service) {
    if (service->undo_redo_service->undo_point == 0)
        return -1;
    service->undo_redo_service->undo_point--;
    set_product_list_to_current_pointer(service);
    return 1;
}

int redo(Service *service) {
    if (service->undo_redo_service->undo_point + 1 == service->undo_redo_service->undo_length)
        return -1;
    service->undo_redo_service->undo_point++;
    set_product_list_to_current_pointer(service);
    return 1;
}