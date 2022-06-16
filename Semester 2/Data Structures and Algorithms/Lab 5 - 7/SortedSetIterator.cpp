#include "SortedSetIterator.h"
#include <exception>
#include "iostream"

using namespace std;

SortedSetIterator::SortedSetIterator(const SortedSet& m) : multime(m)
{
	BST* node = multime.root;
    this->stack = new BST* [multime.length + 2];
    this->top = -1;

    while (node){
        this->stack[++top] = node;
        node = node->left;
    }

    if(this->top < 0)
        this->current = nullptr;
    else
        this->current = this->stack[top];
}

void SortedSetIterator::first() {
    BST* node = multime.root;
    this->stack = new BST* [multime.length + 2];
    this->top = -1;

    while (node){
        this->stack[++top] = node;
        node = node->left;
    }

    if(this->top < 0)
        this->current = nullptr;
    else
        this->current = this->stack[top];
}


void SortedSetIterator::next() {
	if(!valid())
        throw exception();

    BST* node = this->stack[top--];
    if(node->right){
        node = node->right;
        while (node){
            this->stack[++top] = node;
            node = node->left;
        }
    }
    if(this->top < 0)
        this->current = nullptr;
    else
        this->current = this->stack[this->top];

}

void SortedSetIterator::jumpForward(int k){
    if(k <= 0 || !this->valid())
        throw exception();
    for(int i = 0; i < k; i++)
        next();
}

TElem SortedSetIterator::getCurrent()
{
    if(!this->valid())
        throw  exception();
    return this->current->info;
}

bool SortedSetIterator::valid() const {
    return this->current != nullptr;
}

