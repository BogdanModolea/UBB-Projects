#include "lista.h"
#include "bits/stdc++.h"

using namespace std;

PNod reverse(PNod head) {
    if (!head || !(head->urm))
        return head;

    auto nod = reverse(head->urm);
    head->urm->urm = head;
    head->urm = nullptr;
    return nod;
}

TElem maxim(PNod head){
    if(!head)
        return INT_MIN;
    if(!head->urm)
        return head->e;
    return max(head->e, maxim(head->urm));
}

void testare() {
    Lista l;
    l = creare_custom();
    tipar(l);

    l._prim = reverse(l._prim);

    auto p = l._prim;
    int a[101];
    a[0] = 1;
    a[1] = 7;
    a[2] = 6;
    a[3] = 5;
    a[4] = 3;
    int index = 0;
    while (p) {
        assert(p->e == a[index]);
        index++;
        p = p->urm;
    }

    cout << "\n";
    tipar(l);


    int ans = maxim(l._prim);
    assert(ans == 7);
    cout << "\nMax = " << ans;
}

int main() {
    testare();
}
