//
// Created by bogdan on 30.05.2022.
//

#include "Action.h"

void ActionAdd::doUndo(){
    repository->remove_movie(addedMvoie.get_title());
}

void ActionAdd::doRedo() {
    repository->add_movie(addedMvoie);
}

void ActionRemove::doUndo() {
    repository->add_movie(deletedMovie);
}

void ActionRemove::doRedo() {
    repository->remove_movie(deletedMovie.get_title());
}

void ActionUpdate::doUndo() {
    repository->remove_movie(new_movie.get_title());
    repository->add_movie(old_movie);
}

void ActionUpdate::doRedo() {
    repository->remove_movie(old_movie.get_title());
    repository->add_movie(new_movie);
}