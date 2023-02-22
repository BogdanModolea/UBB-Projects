% valley(L:list, C:check_number)
% (i,i)

% we will check -with C=1 if sequence is decreasing
%				-with C=0 if sequence is increasing


valley([_],0):-!.

valley([H1, H2 | T], C):-
    H1 > H2,
    C =:= 1,
    valley([H2 | T], 1).

valley([H1, H2 | T], _):-
    H1 < H2,
    valley([H2 | T], 0).

main(L):-
    L=[H1, H2 | _],
    H1 > H2,
    valley(L, 1).