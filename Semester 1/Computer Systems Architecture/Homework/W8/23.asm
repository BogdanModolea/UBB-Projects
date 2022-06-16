bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    s db 2, 4, 2, 5, 2, 2, 4, 4
    ;s db 1, 2, 3, 4, 5, 6, 7, 8
    lens equ $ - s
    d times lens dw 0
    lend db 0
    apr db 0
; our code starts here
segment code use32 class=code
    start:
        mov esi, s
        mov edi, d
        mov ecx, lens                           ; pastram in ecx lungimea sirului S pentru a putea opera bucla
        repeta:                                 ; bucla principala pentru a parcurge toate valorile lui S
            mov byte[apr], 0                    ; pastram in [apr] numarul de aparitii ale unui nr din S in sirul D
            cmp esi, s                          ; comparam pentru a pastra primul element din S                  
            jz next1                            
                mov eax, d                      ; pastram in eax, pentru a putea compara, valoare sirului D pana in acel moment
                inc eax                         
                push ecx                        ; salvam lungimea sirului S in stiva pentru a o recupera mai tarziu
                mov ecx, [lend]                 ; mutam in ecx lungimea sirului D pentru a-l pargurge cu loop si pentru a verifica daca exista in sirul D valoare curenta din S
                next3:
                    mov bl, [esi]               ; mutam in bl valoarea curenta din S
                    cmp bl, [eax]               ; verificm bl cu valoaraea curenta din D
                    jnz next4                   ; verificam daca valoarea curenta din S exista in sirul D
                        inc byte[apr]           ; incrementam numarul de aparitii a valorii din S in sirul D
                    next4:
                    inc eax                     ; calculam urmatorul element din sirul D
                    inc eax                     
                    cmp bl, [eax-2]             
                loopne next3                    ; repetam bucla pentru elementul din D determinat anterior doar daca nu am intalnit deja elementul din S in D
                pop ecx                         ; dupa incheierea buclei, recuperam valoarea sirului S pentru a obtine urmatorul element
            next1:
            lodsb                               
            cmp byte[apr], 0                    ; verificam daca elementul curent din S are sau nu aparitii in sirul D
            jnz next2                           ; sarim peste adaugare daca exista deja elementul in sirul D
                inc edi
                stosb                               
                inc byte[lend]                  ; marim lungimea sirului D
            next2:
        loop repeta                             ; bucla pentru parcurgerea sirului S
        
        
        mov esi, s                              ; repetam aceeasi procedura ca mai sus, doar ca de aceasta data
        mov edi, d                              ; parcurgem sirul D si calculam de cate ori apare in sirul S, salvand astfel numarul de aparitii
        mov ecx, lens                           ; corespunzatoare fiecarui element
        repeta1:
            mov byte[apr], 0
            mov eax, s
            push ecx
            mov ecx, lens
            next5:
                mov bl, [edi + 1]
                cmp bl, [eax]
                jnz next6
                    inc byte[apr]
                next6:
                inc eax
            loop next5
            pop ecx
            cmp byte[apr], 0
            jz next7
                mov dl, byte[apr]
                mov [edi], dl
                inc edi
                inc edi
            next7:
        loop repeta1
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
