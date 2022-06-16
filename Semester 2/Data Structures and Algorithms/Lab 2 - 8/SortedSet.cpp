#include "SortedSet.h"
#include "SortedSetIterator.h"

#include "iostream"

SortedSet::SortedSet(Relation r) {
    this->r = r;
    this->head = nullptr;
    this->tail = nullptr;
    this->length = 0;
}

/*
 * BC: O(1)
 * WC: O(n)
 * AC: O(n)
 * */
bool SortedSet::add(TComp elem) {
    //if there are no nodes or we have to insert the element at the beginning
    if(this->head == nullptr || !this->r(this->head->info, elem)){
        node* nod = new node;
        nod->prev = nullptr;
        nod->next = this->head;
        nod->info = elem;

        // if there are no nodes, the head and the tail will both point to the one we add
        if(this->tail == nullptr)
            this->tail = nod;

        // if there is an existing node, we will make it's previous value point to the one we add
        if(this->head != nullptr)
            this->head->prev = nod;

        this->head = nod;
        this->length++;
        return true;
    }

    //if the element already exists
    if(search(elem))
        return false;

    // we have to insert the node somewhere between other 2 nodes or at the end
    node* current = this->head;
    while (current){
        // we add the node in the place determined by the given relation
        if(!this->r(current->info, elem)){
            node *nod = new node;
            current->prev->next = nod;
            nod->prev = current->prev;
            nod->next = current;
            nod->info = elem;
            current->prev = nod;
            this->length++;
            return true;
        }
        current = current->next;
    }

    // we add the node at the tail
    node *nod = new node;
    this->tail->next = nod;
    nod->prev = this->tail;
    nod->next = nullptr;
    nod->info = elem;
    this->tail = nod;
    this->length++;
    return true;

}

/*
 * BC: O(1)
 * WC: O(n)
 * AC: O(n)
 * */
bool SortedSet::remove(TComp elem) {
    node *current = new node;
    current = this->head;
    // if there are no nodes, return false
    if(current == nullptr)
        return false;

    while (current){
        // if we found the node we were looking for
        if(current->info == elem){
            // if we only have 1 node, both the head and the tail will point to nullptr
            if(this->size() == 1){
                this->head = nullptr;
                this->tail = nullptr;
                this->length--;
                delete current;
                return true;
            }
            else if(current == this->tail){
                // if the found node is the tail, we'll change the tail to the previous node
                this->tail = current->prev;
                current->prev->next = nullptr;
                this->length--;
                delete current;
                return true;
            }
            else if(current == this->head){
                // if the node is the head, we'll change the head to the next node
                this->head = current->next;
                current->next->prev = nullptr;
                this->length--;
                delete current;
                return true;
            }
            else{
                current->prev->next = current->next;
                current->next->prev = current->prev;
                this->length--;
                delete current;
                return true;
            }
        }
        current = current->next;
    }
    return false;
}

/*
 * BC: O(1)
 * WC: O(n)
 * AC: O(n)
 * */
bool SortedSet::search(TComp elem) const {
    // checks if an element exits in the list or not
	if(this->length == 0)
	    return false;

    node* current;
    current = this->head;
    while (current){
        if(current->info == elem)
            return true;
        current = current->next;
    }
    return false;
}


/*
 * O(1)
 * */
int SortedSet::size() const {
    // returns the number of elements in the set
	return this->length;
}

/*
 * O(1)
 * */
bool SortedSet::isEmpty() const {
    // checks if the number of elements in the set is 0 or not
	return (this->length == 0);
}

SortedSetIterator SortedSet::iterator() const {
	return SortedSetIterator(*this);
}


SortedSet::~SortedSet() {
    //frees the memory
    node* current;
    while (this->head){
        current = this->head;
        this->head = this->head->next;
        delete current;
    }
}

void SortedSet::unionn(const SortedSet &s){
    SortedSetIterator it = s.iterator();
    while (it.valid()){
        TElem e = it.getCurrent();
        add(e);
        it.next();
    }
}

