% Five friends are side by side drinking juice and talking about the deals they got during the Black Friday sales.
% Follow the clues to find out who bought the laptop.


/*

		1. The man drinking the Orange juice is exactly to the right of the man who got the 70% discount.
		2. Keith is 45 years old.
		3. The man who bought the TV is exactly to the left of the man wearing the Red shirt.
		4. At the third position is the man who got the 50% discount.
		5. Keith is next to the man wearing the White shirt.
		6. The 25-year-old man is somewhere between the 35-year-old man and the 40-year-old man, in that order.
		7. The man drinking Apple juice bought the Smartphone.
		8. The 30-year-old man is exactly to the left of the man that bought the Beard trimmer.
		9. Sean is the youngest.
		10. The man that got the 40% discount is exactly to the right of the man who bought the Beard trimmer.
		11. Keith is next to the 35-year-old man.
		12. Eugene is 40 years old.
		13. Sean is wearing the Black shirt.
		14. At the fourth position is the man who got the biggest discount.
		15. Dustin got 60% off.
		16. The man drinking the Lemon juice is exactly to the right of the man drinking the Grape juice.
		17. Keith bought a Game console.
		18. The man who got the 80% discount is exactly to the left of the man who is wearing the Blue shirt.
		19. The man drinking Grape juice bought the Beard trimmer.
		20. The man wearing the Black shirt is somewhere to the right of Keith.
		21. The man that bought the Smartphone is next to the man wearing the Black shirt.

*/


:- use_rendering(table,
		 [header(man('Shirt','Name','Deal','Discount','Age','Juice' ))]).

nextToLeft(A, B, Ls) :- append(_, [A,B|_], Ls).

nextTo(A,B, Ls):-append(_,[B,A|_], Ls).
nextTo(A,B, Ls):-append(_,[A,B|_], Ls).
    
somewhereLeft(A,B, Ls):- append(_, [A, B|_], Ls).
somewhereLeft(A,B, Ls):- append(_, [A,_, B|_], Ls).
somewhereLeft(A,B, Ls):- append(_, [A,_,_, B|_], Ls).
somewhereLeft(A,B, Ls):- append(_, [A,_,_,_, B|_], Ls).

somewhereBetween(A, B, C, Ls):- somewhereLeft(A, B, Ls), somewhereLeft(B, C, Ls).

  

getMen(Men) :-
    % each man in the group is represented as:
    % man('Shirt','Name','Deal','Discount','Age','Juice' )
    length(Men, 5),                                            
    nextToLeft(man(_,_,_,70,_,_), man(_,_,_,_,_,orange), Men),                           		% 1
    member(man(_,keith,_,_,45,_), Men),												% 2
    nextToLeft(man(_,_,tv,_,_,_), man(red,_,_,_,_,_), Men),								% 3
    Men=[_,_,man(_,_,_,50,_,_),_,_],												% 4
    nextTo(man(_,keith,_,_,_,_), man(white,_,_,_,_,_), Men),							% 5
    somewhereBetween(man(_,_,_,_,35,_), man(_,_,_,_,25,_), man(_,_,_,_,40,_), Men),			% 6
    member(man(_,_,smartphone,_,_,apple), Men),										% 7
    nextToLeft(man(_,_,_,_,30,_), man(_,_,beardtrimmer,_,_,_), Men),						% 8
    member(man(_,sean,_,_,25,_), Men),												% 9
    nextToLeft(man(_,_,beardtrimmer,_,_,_), man(_,_,_,40,_,_), Men),						% 10
    nextTo(man(_,keith,_,_,_,_), man(_,_,_,_,35,_), Men),								% 11
    member(man(_,eugen,_,_,40,_), Men),												% 12
    member(man(black,sean,_,_,_,_), Men),											% 13
    Men=[_,_,_,man(_,_,_,80,_,_),_],												% 14
    member(man(_,dustin,_,60,_,_), Men),											% 15
    nextToLeft(man(_,_,_,_,_,grape), man(_,_,_,_,_,lemon), Men),							% 16
    member(man(_,keith,gameconsole,_,_,_), Men),										% 17
    nextToLeft(man(_,_,_,80,_,_), man(blue,_,_,_,_,_), Men),							% 18
    member(man(_,_,beardtrimmer,_,_,grape), Men),										% 19
    somewhereLeft(man(_,keith,_,_,_,_), man(black,_,_,_,_,_), Men),						% 20
    nextTo(man(_,_,smartphone,_,_,_), man(black,_,_,_,_,_), Men),						% 21
    member(man(green,_,_,_,_,_), Men),												% one man wears green shirt
    member(man(_,hank,_,_,_,_), Men),												% one amn is called hank
    member(man(_,_,laptop,_,_,_), Men),												% one man bought a laptop
    member(man(_,_,_,_,_,cranberry), Men).											% one man drinks cranberry juice




bought_laptop(Man):-
    getMen(MenList),
    member(man(_,Man,laptop,_,_,_), MenList),
    !.