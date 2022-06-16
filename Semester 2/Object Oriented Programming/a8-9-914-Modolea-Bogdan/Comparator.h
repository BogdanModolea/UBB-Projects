//
// Created by bogdan on 4/19/2022.
//

#ifndef A8_9_914_MODOLEA_BOGDAN_COMPARATOR_H
#define A8_9_914_MODOLEA_BOGDAN_COMPARATOR_H

#include "Domain.h"

template <typename Type>
class Comparator{
public:
    //virtual bool compare(const Type &a, const Type &b) = 0;
    virtual bool operator()(const Type &a, const Type &b) = 0;
};


template <typename Type>
class CompareAscendingByTitle : public Comparator<Type>{
public:
    //bool compare(const Type &a, const Type &b) override;
    bool operator()(const Type &a, const Type &b) override;
};

//template<typename Type>
//bool CompareAscendingByTitle<Type>::compare(const Type &a, const Type &b) {
//    return a.get_title() < b.get_title();
//}

template<typename Type>
bool CompareAscendingByTitle<Type>::operator()(const Type &a, const Type &b) {
    return a.get_title() < b.get_title();
}


template <typename Type>
class CompareDescendingByYear : public Comparator<Type>{
    bool operator()(const Type &a, const Type &b) override;
};

//template<typename Type>
//bool CompareDescendingByYear<Type>::compare(const Type &a, const Type &b) {
//    return a.get_year() > b.get_year();
//}

template<typename Type>
bool CompareDescendingByYear<Type>::operator()(const Type &a, const Type &b) {
    return a.get_year() > b.get_year();
}


#endif //A8_9_914_MODOLEA_BOGDAN_COMPARATOR_H
