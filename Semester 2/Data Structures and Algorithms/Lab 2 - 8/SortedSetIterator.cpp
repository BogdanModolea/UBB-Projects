#include "SortedSetIterator.h"
#include <exception>

using namespace std;

SortedSetIterator::SortedSetIterator(const SortedSet& m) : multime(m)
{
	//TODO - Implementation
    this->current = m.head;
}

// O(1)
void SortedSetIterator::first() {
	// goes to the head of the set
    this->current = multime.head;
}

// O(1)
void SortedSetIterator::next() {
	// goes to the next element in the set
    if(current == NULL)
        throw exception();
    this->current = current->next;
}

//O(1)
TElem SortedSetIterator::getCurrent()
{
	// returns the "info" of the current element
    if(current == NULL)
        throw exception();
    TElem elem = current->info;
    return elem;
}

//O(1)
bool SortedSetIterator::valid() const {
	// checks if the current node is NULL or not
    return (current != NULL);
}

