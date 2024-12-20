#pragma once
#include "SortedSet.h"

//DO NOT CHANGE THIS PART
class SortedSetIterator
{
	friend class SortedSet;
private:
	const SortedSet& multime;
	SortedSetIterator(const SortedSet& m);

    BST** stack;
	int top;
    BST* current;

public:
	void first();
	void next();
	TElem getCurrent();
	bool valid() const;

    void jumpForward(int k);
};

