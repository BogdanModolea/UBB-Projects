% flow (i, i)

prime(N, N).

prime(N, P):-
    P < N,
    \+ N mod P =:= 0,
    P1 is P + 1,
    prime(N, P1).

% flow (i, o), (i, i)

new_list([], []).

new_list([H|T], [H|R]):-
    prime(H, 2),
    new_list(T, R).

new_list([H|T], R):-
    \+ prime(H, 2),
    new_list(T, R).

test:-
    main([2,6,7,3,4,10], [2, 7, 3]),
    main([1, 2, 3, 4, 5, 6, 7, 8], [2, 3, 5, 7]),
    main([2, 4, 6, 8, 10, 173], [2, 173]),
    main([1741, 857, 307], [1741, 857, 307]).

main(L, R):-
    new_list(L, R).