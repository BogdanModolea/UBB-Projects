//
// Created by bogdan on 3/21/2022.
//

#ifndef A3_4_914_MODOLEA_BOGDAN_UNDOLIST_H
#define A3_4_914_MODOLEA_BOGDAN_UNDOLIST_H

struct Servicee;

typedef struct {
    void **undo_list;
    int *undo_list_len;
    int undo_length, undo_point, undo_capacity;
}Undo_Redo_Service;

Undo_Redo_Service* create_undo_redo_service(struct Servicee *service);
void destroy_undo_list_at_pos(Undo_Redo_Service *undoRedoService, int pos);
void destroy_undo(Undo_Redo_Service **undoRedoService);
void stack(struct Servicee *service);
int undo(struct Servicee *service);
int redo(struct Servicee *service);

#endif //A3_4_914_MODOLEA_BOGDAN_UNDOLIST_H
