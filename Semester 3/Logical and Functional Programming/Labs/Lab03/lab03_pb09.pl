% addElement(L - list, E - number, P - number, R - list)
% flow (i,i,i,o)

addElement([], _, _, []).

addElement([H | T], E, P, [H, E | R]):-
    (P =:= 1; P =:= 3; P =:= 7; P =:= 15),
    !,
    P1 is P + 1,
    addElement(T, E, P1, R).

addElement([H | T], E, P, [H | R]):-
    P1 is P + 1,
    addElement(T, E, P1, R).

testAddElement():-
    addElement([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15], 99, 1, [1, 99, 2, 3, 99, 4, 5, 6, 7, 99, 8, 9, 10, 11, 12, 13, 14, 15, 99]),
    addElement([1, 2, 3, 4], 150, 1, [1, 150, 2, 3, 150, 4]).



% process(L - list, E - number, R - list)
% flow (i,i,o)

process([], _, []).

process([H | T], E, [RH | RT]):-
    is_list(H),
    !,
    addElement(H, E, 1, RH),
    process(T, E, RT).

process([H | T], _, [H | RT]):-
    process(T, H, RT).

testHeterogeneousList():-
    process([1, [2, 3], 7, [4, 1, 4], 3, 6, [7, 5, 1, 3, 9, 8, 2, 7], 5], _, [1, [2, 1, 3], 7, [4, 7, 1, 4, 7], 3, 6, [7, 6, 5, 1, 6, 3, 9, 8, 2, 6, 7], 5]).