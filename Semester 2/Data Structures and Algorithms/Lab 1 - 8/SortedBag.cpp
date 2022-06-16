#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <iostream>
#include <exception>

using namespace std;

SortedBag::SortedBag(Relation r) {
    comp = r;
    nr_elements = 0;
    capacity = 1;
    array = new TComp[1];
}

void SortedBag::add(TComp e) {
    /*
     * Time Complexity: O(n)
     *
     * If we reached with the maximum capacity, we increase it by creating another array and moving all element there.
     * After that, we copy back the elements in the original list
     * */
    if (nr_elements == capacity) {
        capacity *= 2;
        auto *temp = new TComp[capacity];
        for (int i = 0; i < nr_elements; i++)
            temp[i] = array[i];
        delete[] array;
        array = temp;
    }
    int pos = 0;
    for (int i = 0; i < nr_elements; i++) {
        if (comp(array[i], e)) {
            pos = i + 1;
        } else
            break;
    }
    for (int i = nr_elements; i > pos; i--)
        array[i] = array[i - 1];
    array[pos] = e;
    nr_elements++;
}


bool SortedBag::remove(TComp e) {
    /*
     * Time Complexity: O(n)
     *
     * We iterate through the array and when we find the element we every position, starting with the one where we found the element,
     * with the one in the right
     * */
    if (!search(e))
        return false;
    bool found = false;
    for (int i = 0; i < nr_elements - 1; i++) {
        if (array[i] == e)
            found = true;
        if (found)
            array[i] = array[i + 1];
    }
    --nr_elements;
    return true;
}


bool SortedBag::search(TComp elem) const {
    /*
     * Time Complexity: O(log n)
     *
     * We apply the binary search algorithm to check if an element belongs to the list or not
     * */
    int lb = 0, rb = nr_elements - 1, mid;
    while (lb <= rb) {
        mid = (lb + rb) / 2;
        if (array[mid] == elem)
            return true;
        else if (comp(array[mid], elem))
            lb = mid + 1;
        else
            rb = mid - 1;
    }
    return false;
}


int SortedBag::nrOccurrences(TComp elem) const {
    /*
     * Time Complexity: O(n)
     *
     * We iterate through the array and we count the number of appearances of an element
     * */
    int cnt = 0;
    for (int i = 0; i < nr_elements; i++)
        if (array[i] == elem)
            cnt++;

    return cnt;
}



int SortedBag::size() const {
    /*
     * Time Complexity: O(1)
     *
     * We return the number of elements in the array
     * */
    return nr_elements;
}


bool SortedBag::isEmpty() const {
    /*
     * Time Complexity: O(1)
     *
     * We check if number of elements is 0 or not
     */
    return nr_elements == 0;
}


SortedBagIterator SortedBag::iterator() const {
	return SortedBagIterator(*this);
}


SortedBag::~SortedBag() {
    delete[] array;
}

int SortedBag::removeAll() {
    int cnt = 0;
    TComp e;
    for(int i = 0; i < nr_elements - 1; i++){
        if(array[i] == array[i + 1]){
            cnt++;
            e = array[i];
            while (remove(e));
        }
    }

    return cnt;
}