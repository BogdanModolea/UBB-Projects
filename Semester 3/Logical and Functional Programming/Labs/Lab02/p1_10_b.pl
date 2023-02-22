%sum(L: list, S: sum)
%(i, o)

sum([], 0).
sum([H], H).

sum([H1, H2 | T], S):-
    S1 is H1 - H2,
    sum(T, S2),
	S is S1 + S2.