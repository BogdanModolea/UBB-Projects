#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(const SortedBag& b) : bag(b) {
	pos = 0;
}

TComp SortedBagIterator::getCurrent() {
	if(pos >= bag.nr_elements)
        throw std::exception();
    return bag.array[pos];
}

bool SortedBagIterator::valid() {
    return pos < bag.nr_elements;
}

void SortedBagIterator::next() {
	if(pos >= bag.nr_elements)
        throw std::exception();
    pos++;
}

void SortedBagIterator::first() {
	pos = 0;
}

