//
// Created by bogdan on 4/5/2022.
//

#ifndef T1_914_MODOLEA_BOGDAN_1_DYNAMICARRAY_H
#define T1_914_MODOLEA_BOGDAN_1_DYNAMICARRAY_H

#include "algorithm"

template <typename Type>

class DynamicArray {
private:
    Type *array;
    int length, capacity;

public:
    //default constructor for a DynamicArray
    DynamicArray();

    //copy constructor for a DynamicArray
    //DynamicArray(const DynamicArray& a);

    //destructor for a DynamicArray
    ~DynamicArray();

    //the length of the DynamicArray
    int size() const;

    //the capacity of the DynamicArray
    int get_capacity() const {return this->capacity;}

    //adds an element to the DynamicArray
    void add(Type element);

    //removes an element from the DynamicArray
    void remove(Type element);

    //return an element from the DynamicArray
    Type get_element(const int pos) const;

    void change(int pos1, int pos2);
};

template <typename Type>
void DynamicArray<Type>::add(Type element) {
    if (this->capacity == this->length) {
        this->capacity *= 2;
        auto *new_array = new Type[this->capacity];
        for (int i = 0; i < this->length; i++)
            new_array[i] = this->array[i];
        delete[] this->array;
        this->array = new_array;
    }
    this->array[this->length++] = element;
}

//removes an element from the DynamicArray
template <typename Type>
void DynamicArray<Type>::remove(Type element) {
    int pos = -1;
    for (int i = 0; i < this->length; i++) {
        if (this->array[i] == element) {
            pos = i;
            break;
        }
    }

    if (pos == -1)
        return;

    for (int i = pos; i < this->length - 1; i++)
        this->array[i] = this->array[i + 1];

    this->length--;
}

template<typename Type>
Type DynamicArray<Type>::get_element(const int pos) const {
    return this->array[pos];
}

template<typename Type>
int DynamicArray<Type>::size() const {
    return this->length;
}

template<typename Type>
DynamicArray<Type>::~DynamicArray() {
    delete[] this->array;
}

template<typename Type>
DynamicArray<Type>::DynamicArray() {
    this->length = 0;
    this->capacity = 1;
    this->array = new Type[this->capacity];
}

template<typename Type>
void DynamicArray<Type>::change(int pos1, int pos2){
    auto aux = this->array[pos1];
    this->array[pos1] = this->array[pos2];
    this->array[pos2] = aux;
}
#endif //T1_914_MODOLEA_BOGDAN_1_DYNAMICARRAY_H
