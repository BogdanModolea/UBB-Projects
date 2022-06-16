bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern scanf               ; tell nasm that exit exists even if we won't be defining it
extern printf              ; tell nasm that exit exists even if we won't be defining it
extern fscanf               ; tell nasm that exit exists even if we won't be defining it
extern fopen               ; tell nasm that exit exists even if we won't be defining it
extern fread               ; tell nasm that exit exists even if we won't be defining it
extern fprintf               ; tell nasm that exit exists even if we won't be defining it
extern fclose               ; tell nasm that exit exists even if we won't be defining it
extern exit               ; tell nasm that exit exists even if we won't be defining it
import scanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fscanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fread msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fprintf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a resd 1
    b resd 1
    read_format db "%d", 0
    
    print_format db "Elementul maxim este: %o", 13, 0
    
; our code starts here
segment code use32 class=code
    start:
        ; Se citesc de la tastatura numere (in baza 10) pana cand se introduce cifra 0. Determinaţi şi afişaţi cel mai mare număr dintre cele citite.
        mov ecx, 0
        mov [b], ecx
        repeta:
            push a
            push read_format    
            call [scanf]
            add esp, 4 * 2
            
            mov ecx, [b]
            cmp ecx, [a]
            ja maimic
                mov ecx, [a]
                mov [b], ecx
            maimic:
                mov ebx, 0
                cmp ebx, [a]
        jnz repeta
        
        push dword [b]
        push print_format
        call [printf]
        add esp, 4 * 2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
