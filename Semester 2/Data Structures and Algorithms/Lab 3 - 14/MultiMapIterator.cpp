#include "MultiMapIterator.h"
#include "MultiMap.h"


MultiMapIterator::MultiMapIterator(const MultiMap& c): col(c) {
    pos = col.elems.head;
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
TElem MultiMapIterator::getCurrent() const{
    if(!this->valid())
        throw exception();
    return col.elems.lst[pos].info;
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
bool MultiMapIterator::valid() const {
    return pos != -1;
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
void MultiMapIterator::next() {
	if(!this->valid())
        throw exception();
    pos = col.elems.lst[pos].next;
}

//BC: O(1)
//WC: O(1)
//AC: O(1)
void MultiMapIterator::first() {
	pos = col.elems.head;
}

void MultiMapIterator::previous(){
    if(pos == col.elems.head)
        pos = -1;
    if(!this->valid())
        throw exception();
    pos = col.elems.lst[pos].prev;
}
