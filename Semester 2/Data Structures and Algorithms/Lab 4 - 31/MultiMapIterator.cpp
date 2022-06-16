#include "MultiMapIterator.h"
#include "MultiMap.h"


//BC: O(n)
//WC: O(n)
//AC: O(n)
MultiMapIterator::MultiMapIterator(const MultiMap& c): col(c) {
    for(int i = 0; i < col.h_size; i++){
        if(col.hashtable[i] != nullptr){
            this->current_hash_pos = i;
            break;
        }
    }
    if(this->current_hash_pos < col.h_size){
        this->current = col.hashtable[this->current_hash_pos];
        this->current_arr_pos = 0;
    }
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
TElem MultiMapIterator::getCurrent() const{
	if(!valid())
        throw exception();
    return TElem {this->current->key, this->current->values[this->current_arr_pos]};
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
bool MultiMapIterator::valid() const {
    return this->current_hash_pos < col.h_size && this->current != nullptr;
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
void MultiMapIterator::next() {
	if(!valid())
        throw exception();

    if(this->current->next != nullptr){
        if(this->current_arr_pos == this->current->length - 1){
            this->current = this->current->next;
            this->current_arr_pos = 0;
        }
        else
            this->current_arr_pos++;
    }
    else{
        if(this->current_arr_pos < this->current->length - 1)
            this->current_arr_pos++;
        else{
            this->current_hash_pos++;
            while (this->current_hash_pos < col.h_size && col.hashtable[this->current_hash_pos] == nullptr)
                this->current_hash_pos++;
            if(this->current_hash_pos == col.h_size)
                this->current = nullptr;
            else{
                this->current = col.hashtable[this->current_hash_pos];
                this->current_arr_pos = 0;
            }
        }
    }
}

//BC: O(1)
//WC: O(n)
//AC: O(n)
void MultiMapIterator::first() {
    for(int i = 0; i < col.h_size; i++){
        if(col.hashtable[i] != nullptr){
            this->current_hash_pos = i;
            break;
        }
    }
    if(this->current_hash_pos < col.h_size){
        this->current = col.hashtable[this->current_hash_pos];
        this->current_arr_pos = 0;
    }
}

