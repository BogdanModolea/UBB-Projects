#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>
#include "vector"

using namespace std;


MultiMap::MultiMap() {
    this->length = 0;
}

//add an elements into the map
//BC: O(1)
//WC: O(1)
//AC: O(1)
void MultiMap::add(TKey c, TValue v) {
    DLLA &elements = this->elems;
    if (elements.size == elements.capacity && elements.first_empty == -1) {
        elements.capacity *= 2;
        auto *aux = new DLLANode[elements.capacity];
        for (int i = 0; i < elements.size; i++)
            aux[i] = elements[i];
        delete[] elements.lst;
        elements.lst = aux;
    }
    if (elements.first_empty == -1) {
        elements.first_empty = elements.size;
        elements[elements.first_empty] = DLLANode(NULL_TELEM, -1, -1);
        elements.size++;
    }
    if (this->length == 0) {
        int copy = elements.first_empty;
        elements.first_empty = elements[elements.first_empty].next;
        elements[copy] = DLLANode(make_pair(c, v), -1, -1);
        elements.tail = elements.head = copy;
        this->length++;
        return;
    }
    int copy = elements.first_empty;
    elements.first_empty = elements[elements.first_empty].next;
    elements[copy] = DLLANode(make_pair(c, v), elements.tail, -1);
    elements[elements.tail].next = copy;
    elements.tail = copy;
    this->length++;
}

//removes an element from the map
//BC: O(1)
//WC: O(n)
//AC: O(n)
bool MultiMap::remove(TKey c, TValue v) {
	DLLA &elements = this->elems;
    if(this->length == 0)
	    return  false;
    if(this->length == 1){
        if(elements[elements.head].info != make_pair(c, v))
            return false;
        elements[elements.head].next = elements.first_empty;
        elements.first_empty = elements.head;
        elements.head = elements.tail = -1;
        this->length--;
        return true;
    }
    if(elements[elements.head].info == make_pair(c, v)){
        int copy = elements[elements.head].next;
        elements[elements.head].next = elements.first_empty;
        elements.first_empty = elements.head;
        elements.head = copy;
        elements[elements.head].prev = -1;
        this->length--;
        return true;
    }
    if(elements[elements.tail].info == make_pair(c, v)){
        int copy = elements[elements.tail].prev;
        elements[elements.tail].next = elements.first_empty;
        elements.first_empty = elements.tail;
        elements.tail = copy;
        elements[elements.tail].next = -1;
        this->length--;
        return true;
    }
    int pos = -1;
    for(int node = elements.head; node != -1; node = elements[node].next){
        if(make_pair(c, v) == elements[node].info){
            pos = node;
            break;
        }
    }
    if(pos == -1)
        return false;

    elements[elements[pos].prev].next = elements[pos].next;
    elements[elements[pos].next].prev = elements[pos].prev;
    elements[pos].next = elements.first_empty;
    elements.first_empty = pos;
    this->length--;
    return true;
}

//searches a key in the map
//BC: O(1)
//WC: O(n)
//AC: O(n)
vector<TValue> MultiMap::search(TKey c) const {
	vector<TValue> values;
    for(int node = this->elems.head; node != -1; node = this->elems.lst[node].next)
        if(this->elems.lst[node].info.first == c)
            values.push_back(this->elems.lst[node].info.second);

    return values;
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
int MultiMap::size() const {
    return this->length;
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
bool MultiMap::isEmpty() const {
    return this->length == 0;
}

MultiMapIterator MultiMap::iterator() const {
	return MultiMapIterator(*this);
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
MultiMap::~MultiMap() {
    delete[] elems.lst;
}

MultiMap::DLLA::DLLA() {
    this->capacity = 1;
    this->size = 1;
    this->lst = new DLLANode[this->capacity];
    this->lst[0] = DLLANode(NULL_TELEM, -1, -1);
    this->first_empty = 0;
    this->head = -1;
    this->tail = -1;
}

MultiMap::DLLANode &MultiMap::DLLA::operator[](int pos) {
    return this->lst[pos];
}

MultiMap::DLLANode::DLLANode(TElem info, int prev, int next): info{std::move(info)}, prev{prev}, next{next} {}

MultiMap::DLLANode::DLLANode(): info{NULL_TELEM}, prev{0}, next{0}{}

