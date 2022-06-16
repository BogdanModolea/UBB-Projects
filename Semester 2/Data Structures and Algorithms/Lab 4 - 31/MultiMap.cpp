#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

using namespace std;


MultiMap::MultiMap() {
    hashtable = new Node*[10];

    for(int i = 0; i < 10; i++)
        hashtable[i] = nullptr;

    cnt = 10;
    h_size = 10;
    elems = 0;
}

//BC: O(1)
//WC: O(nr nodes in hashtable[pos])
//AC: O(nr nodes in hashtable[pos])
void MultiMap::add(TKey c, TValue v) {
    int pos = hash(c);
    bool found = false;

    // empty
    if(this->hashtable[pos] == nullptr){
        Node* new_node = new Node;
        new_node->key = c;
        new_node->values[new_node->length] = v;
        new_node->length++;
        new_node->next = this->hashtable[pos];
        hashtable[pos] = new_node;
    }
    else{
        // we have the key and insert the value
        Node* current = this->hashtable[pos];
        while (current != nullptr){
            if(current->key == c){
                if(current->length == current->capacity)
                    resize(current);
                current->values[current->length] = v;
                current->length++;
                found = true;
            }
            current = current->next;
        }
        if(!found){
            // we don't have the key
            Node *new_node = new Node;
            new_node->key = c;
            new_node->values[new_node->length] = v;
            new_node->length++;
            new_node->next = this->hashtable[pos];
            this->hashtable[pos] = new_node;
        }
    }
    this->elems++;
    if(overload())
        resize_hash();
}


//BC: O(1)
//WC: O(number of elements having the key c + nr of nodes in list)
//AC: O(number of elements having the key c + nr of nodes in list)
bool MultiMap::remove(TKey c, TValue v) {
    int pos = this->hash(c);
    if (this->hashtable[pos] == nullptr)
        return false;

    // head
    if (this->hashtable[pos]->key == c) {
        // more than 1 elements
        if (this->hashtable[pos]->length >= 2) {
            for (int i = 0; i < this->hashtable[pos]->length; i++) {
                if (this->hashtable[pos]->values[i] == v) {
                    this->hashtable[pos]->values[i] = this->hashtable[pos]->values[this->hashtable[pos]->length - 1];
                    this->hashtable[pos]->length--;
                    this->elems--;
                    return true;
                }
            }
        } else {
            // one element
            if (this->hashtable[pos]->values[0] == v) {
                Node *nextnode = this->hashtable[pos]->next;
                delete[] this->hashtable[pos]->values;
                delete this->hashtable[pos];
                this->hashtable[pos] = nextnode;
                this->elems--;
                return true;
            }
            return false;
        }
    }
    // the node is somewhere lost in the middle
    Node *current = this->hashtable[pos];
    Node *prev = current;
    while (current) {
        if (current->key == c) {
            if (current->length >= 2) {
                // at least 2 elements
                for (int i = 0; i < current->length; i++) {
                    if (current->values[i] == v) {
                        current->values[i] = current->values[current->length - 1];
                        current->length--;
                        elems--;
                        return true;
                    }
                }
            } else {
                // just one element
                if(current->values[0] == v) {
                    prev->next = current->next;
                    delete[] current->values;
                    delete current;
                    elems--;
                    return true;
                }
            }
        }
        prev = current;
        current = current->next;
    }
    return false;
}


//BC: O(1)
//WC: O(n)
//AC: O(n)
vector<TValue> MultiMap::search(TKey c) const {
	vector<TValue>ans;
    int pos = hash(c);

    if(this->hashtable[pos] == nullptr)
        return ans;

    // searching the key
    Node* current = hashtable[pos];
    while (current){
        if(current->key == c){
            for(int i = 0; i < current->length; i++)
                ans.push_back(current->values[i]);
            return ans;
        }
        current = current->next;
    }
    return ans;
}


void MultiMap::empty() {
    Node** new_arr = new Node*[1];
    new_arr[0] = nullptr;

    for (int i = 0; i < h_size; i++) {
        Node *node;
        if (this->hashtable[i] != nullptr)
            delete[] this->hashtable[i]->values;
        while (this->hashtable[i] != nullptr) {
            node = this->hashtable[i]->next;
            delete this->hashtable[i];
            this->hashtable[i] = node;
        }
    }
    delete[] hashtable;
    this->hashtable = new_arr;
    this->h_size = this->cnt = 1;
    elems = 0;
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
int MultiMap::size() const {
    return this->elems;
}


//BC: O(1)
//WC: O(1)
//AC: O(1)
bool MultiMap::isEmpty() const {
    return this->elems == 0;
}



MultiMapIterator MultiMap::iterator() const {
	return MultiMapIterator(*this);
}


MultiMap::~MultiMap() {
    for(int i = 0; i < h_size; i++){
        Node* node;
        // deleting values from node
        if(this->hashtable[i] != nullptr)
            delete[] this->hashtable[i]->values;
        // deleting the node
        while (this->hashtable[i] != nullptr){
            node = this->hashtable[i]->next;
            delete this->hashtable[i];
            this->hashtable[i] = node;
        }
    }
    delete[] this->hashtable;
}

//BC: O(n)
//WC: O(n)
//AC: O(n)
void MultiMap::resize(Node*& node){
    auto* new_elem = new TValue[node->capacity * 2];
    for(int i = 0; i < node->length; i++)
        new_elem[i] = node->values[i];
    node->capacity = node->capacity * 2;
    delete[] node->values;
    node->values = new_elem;
}

//BC: O(number of pairs added to the map + number of has table slots)
//WC: O(number of pairs added to the map + number of has table slots)
//AC: O(number of pairs added to the map + number of has table slots)
void MultiMap::resize_hash() {
    Node** new_arr = new Node*[this->h_size * 2];
    cnt *= 2;
    for (int i = 0; i < h_size * 2; i++)
        new_arr[i] = nullptr;

    Node *current;
    for (int i = 0; i < h_size; i++) {
        current = hashtable[i];
        while (current) {
            int new_pos = this->hash(current->key);
            Node *new_node = new Node;
            new_node->key = current->key;
            new_node->length = current->length;

            while (current->length > new_node->capacity)
                resize(new_node);

            for (int j = 0; j < current->length; j++)
                new_node->values[j] = current->values[j];

            new_node->next = new_arr[new_pos];
            new_arr[new_pos] = new_node;

            current = current->next;
        }
    }

    for (int i = 0; i < h_size; i++) {
        Node *node;
        if (this->hashtable[i] != nullptr)
            delete[] this->hashtable[i]->values;
        while (this->hashtable[i] != nullptr) {
            node = this->hashtable[i]->next;
            delete this->hashtable[i];
            this->hashtable[i] = node;
        }
    }
    delete[] hashtable;
    this->h_size *= 2;
    this->hashtable = new_arr;
}