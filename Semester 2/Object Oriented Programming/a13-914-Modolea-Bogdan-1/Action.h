//
// Created by bogdan on 30.05.2022.
//

#ifndef OOP_ACTION_H
#define OOP_ACTION_H

#include "FileRepository.h"
#include "Domain.h"

class Action {
public:
    virtual void doUndo() = 0;
    virtual void doRedo() = 0;
};

class ActionAdd : public Action{
private:
    Movie addedMvoie;
    FileRepository* repository;

public:
    ActionAdd(FileRepository* repository1, Movie mov) : addedMvoie {mov}, repository{repository1} {}

    void doUndo() override;

    void doRedo() override;
};


class ActionRemove : public Action{
    Movie deletedMovie;
    FileRepository* repository;

public:
    ActionRemove(FileRepository* repo, Movie movie): deletedMovie{movie}, repository{repo}{}

    void doUndo() override;

    void doRedo() override;
};


class ActionUpdate : public Action{
    Movie new_movie;
    Movie old_movie;
    FileRepository* repository;

public:
    ActionUpdate(FileRepository* repo, Movie old, Movie new_mov) : old_movie{old}, new_movie {new_mov}, repository{repo}{}

    void doUndo() override;

    void doRedo() override;
};

#endif //OOP_ACTION_H
