% flow (i, o), (i, i)
% L = list, R = list

subset([],[]).
subset([H|T],[H|R]):-
    subset(T,R).
subset([_|T],R):-
    subset(T,R).



% flow (i)
% L = list

ascending([_]).
ascending([H1,H2|T]):-
    H1 < H2,
    ascending([H2|T]).



% flow (i,o), (i, i)
% L = list, R = list

process([],[]).
process(L,R):-
    subset(L,R),
    ascending(R).
    

testprocess:-
    main([1, 2, 2, 3], [[1, 2, 3], [1, 2], [1, 2, 3], [1, 2], [1, 3], [1], [2, 3], [2], [2, 3], [2], [3]]),
    main([1, 2, 3], [[1, 2, 3], [1, 2], [1, 3], [1], [2, 3], [2], [3]]),
    main([1, 7, 2], [[1, 7], [1, 2], [1], [7], [2]]).

main(L, RALL):-
    findall(R, process(L, R), RALL).