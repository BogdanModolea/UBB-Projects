//
// Created by bogdan on 3/19/2022.
//

#ifndef A5_6_914_MODOLEA_BOGDAN_DYNAMICARRAY_H
#define A5_6_914_MODOLEA_BOGDAN_DYNAMICARRAY_H

#include "algorithm"

template <typename Type>

class DynamicArray {
public:
    Type *array;
    int length, capacity;

public:
    //default constructor for a DynamicArray
    DynamicArray();

    //copy constructor for a DynamicArray
    DynamicArray(const DynamicArray& a);

    //destructor for a DynamicArray
    ~DynamicArray();

    //the length of the DynamicArray
    int size() const;

    //the capacity of the DynamicArray
    int get_capacity() const {return this->capacity;}

    //adds an element to the DynamicArray
    void add(const Type element);

    //removes an element from the DynamicArray
    void remove(const Type element);

    //updates an element from the DynamicArray
    void update(const Type element, const int pos);

    //return an element from the DynamicArray
    Type get_element(const int pos) const;

    //void operator+(DynamicArray<Type> arr, const Type &element);
    //void operator+(const Type &element);

};

template <typename Type>
void operator+(DynamicArray<Type>& arr, Type element){
    if (arr.capacity == arr.length) {
        arr.capacity *= 2;
        auto *new_array = new Type[arr.capacity];
        for (int i = 0; i < arr.length; i++)
            new_array[i] = arr.array[i];
        delete arr.array;
        arr.array = new_array;
    }
    arr.array[arr.length++] = element;
}

template <typename Type>
void operator+(Type element, DynamicArray<Type>& arr){
    if (arr.capacity == arr.length) {
        arr.capacity *= 2;
        auto *new_array = new Type[arr.capacity];
        for (int i = 0; i < arr.length; i++)
            new_array[i] = arr.array[i];
        delete arr.array;
        arr.array = new_array;
    }
    arr.array[arr.length++] = element;
}

//adds an element to the DynamicArray
template <typename Type>
void DynamicArray<Type>::add(const Type element) {
    if (this->capacity == this->length) {
        this->capacity *= 2;
        auto *new_array = new Type[this->capacity];
        for (int i = 0; i < this->length; i++)
            new_array[i] = this->array[i];
        delete this->array;
        this->array = new_array;
    }
    this->array[this->length++] = element;
    //this + element;
}

//removes an element from the DynamicArray
template <typename Type>
void DynamicArray<Type>::remove(const Type element) {
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

//updates an element from the DynamicArray
template <typename Type>
void DynamicArray<Type>::update(const Type element, const int pos) {
    this->array[pos] = element;
}

//returns an element from the DynamicArray
template <typename Type>
Type DynamicArray<Type>::get_element(const int pos) const {
    return this->array[pos];
}

//returns the size of the DynamicArray
template <typename Type>
int DynamicArray<Type>::size() const {
    return this->length;
}



//constructor for DynamicArray
template <typename Type>
DynamicArray<Type>::DynamicArray() {
    this->length = 0;
    this->capacity = 1;
    this->array = new Type[this->capacity];
}

//copy constructor for DynamicArray
template <typename Type>
DynamicArray<Type>::DynamicArray(const DynamicArray<Type>& a) {
    this->length = a.size();
    this->capacity = a.get_capacity();
    this->array = new Type[this->capacity];
    for (int i = 0; i < this->length; i++)
        this->array[i] = a.get_element(i);

}

//destructor for the DynamicArray
template <typename Type>
DynamicArray<Type>::~DynamicArray(){
    delete[] this->array;
}

//template <typename Type>
//void DynamicArray<Type>::operator+(const Type &element) {
//    if (this->capacity == this->length) {
//        this->capacity *= 2;
//        auto *new_array = new Type[this->capacity];
//        for (int i = 0; i < this->length; i++)
//            new_array[i] = this->array[i];
//        delete this->array;
//        this->array = new_array;
//    }
//    this->array[this->length++] = element;
//}

//template <typename Type>
//void DynamicArray<Type>::operator+(DynamicArray &arr, const Type &element) {
//    if (this->capacity == this->length) {
//        this->capacity *= 2;
//        auto *new_array = new Type[this->capacity];
//        for (int i = 0; i < this->length; i++)
//            new_array[i] = this->array[i];
//        delete this->array;
//        this->array = new_array;
//    }
//    this->array[this->length++] = element;
//}
//
//template <typename Type>
//void DynamicArray<Type>::operator+(const Type &element, DynamicArray &arr) {
//    arr.add(element);
//    if (this->capacity == this->length) {
//        this->capacity *= 2;
//        auto *new_array = new Type[this->capacity];
//        for (int i = 0; i < this->length; i++)
//            new_array[i] = this->array[i];
//        delete this->array;
//        this->array = new_array;
//    }
//    this->array[this->length++] = element;
//}

#endif //A5_6_914_MODOLEA_BOGDAN_DYNAMICARRAY_H