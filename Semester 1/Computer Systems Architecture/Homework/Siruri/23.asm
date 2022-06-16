bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s db 1, 4, 2, 4, 8, 2, 1, 1
    ;s db '1', '4', '2', '8'
    ;s db '1', '1', '1', '1'
    lens equ ($ - s)
    d times lens db 0
    lend db 0
    apr db 0

; our code starts here
segment code use32 class=code
    start:
        ; Se da un sir de octeti S. Sa se obtina in sirul D multimea elementelor din S.
        mov esi, s
        mov edi, d
        mov ecx, lens                           ; pastram in ecx lungimea sirului S pentru a putea opera bucla
        repeta:                                 ; bucla principala pentru a parcurge toate valorile lui S
            mov byte[apr], 0                    ; pastram in [apr] numarul de aparitii ale unui nr din S in sirul D
            cmp esi, s                          ; comparam pentru a pastra primul element din S                      
            jz next1                            
                mov eax, d                      ; pastram in eax, pentru a putea compara, valoare sirului D pana in acel moment
                push ecx                        ; salvam lungimea sirului S in stiva pentru a o recupera mai tarziu
                mov ecx, [lend]                 ; mutam in ecx lungimea sirului D pentru a-l pargurge cu loop si pentru a verifica daca exista in sirul D valoare curenta din S
                next3:
                    mov bl, [esi]               ; mutam in bl valoarea curenta din S
                    cmp bl, [eax]               ; verificm bl cu valoaraea curenta din D
                    jnz next4                   ; verificam daca valoarea curenta din S exista in sirul D
                        inc byte[apr]           ; incrementam numarul de aparitii a valorii din S in sirul D
                    next4:
                    inc eax                     ; calculam urmatorul element din sirul D
                    cmp bl, [eax-1]
                loopne next3                    ; repetam bucla pentru elementul din D determinat anterior doar daca nu am intalnit deja elementul din S in D
                pop ecx                         ; dupa incheierea buclei, recuperam valoarea sirului S pentru a obtine urmatorul element
            next1:
            mov al, [esi]                       ; mutam in al byte-ul curent din S
            inc esi                             ; mutam pe urmatorul numar din S
            cmp byte[apr], 0                    ; verificam daca elementul curent din S are sau nu aparitii in sirul D
            jnz next2                           ; sarim peste adaugare daca exista deja elementul in sirul D
                mov [edi], al                   ; pastram elementul curent din S, deoarece nu a fost intalnit in D pana acum
                inc edi                         ; incrementam pe urmatoarea pozitie in D
                inc byte[lend]                  ; marim lungimea sirului D
            next2:
        loop repeta                             ; bucla pentru parcurgerea sirului S
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
