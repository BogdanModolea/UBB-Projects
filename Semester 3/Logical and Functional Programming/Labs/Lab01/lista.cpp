#include "lista.h"
#include <iostream>

using namespace std;


PNod creare_rec() {
    TElem x;
    cout << "x=";
    cin >> x;
    if (x == 0)
        return NULL;
    else {
        PNod p = new Nod();
        p->e = x;
        p->urm = creare_rec();
        return p;
    }
}

Lista creare() {
    Lista l;
    l._prim = creare_rec();
    return l;
}

PNod creare_rec_custom(int a[], int index) {
    if (a[index] == 0)
        return NULL;
    else {
        PNod p = new Nod();
        p->e = a[index];
        p->urm = creare_rec_custom(a, index + 1);
        return p;
    }
}

Lista creare_custom() {
    Lista l;
    int a[101];
    a[0] = 3;
    a[1] = 5;
    a[2] = 6;
    a[3] = 7;
    a[4] = 1;
    a[5] = 0;
    l._prim = creare_rec_custom(a, 0);
    return l;
}


void tipar_rec(PNod p) {
    if (p != NULL) {
        cout << p->e << " ";
        tipar_rec(p->urm);
    }
}

void tipar(Lista l) {
    tipar_rec(l._prim);
}

void distrug_rec(PNod p) {
    if (p != NULL) {
        distrug_rec(p->urm);
        delete p;
    }
}

void distrug(Lista l) {
    //se elibereaza memoria alocata nodurilor listei
    distrug_rec(l._prim);
}


