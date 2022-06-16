bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    
    ;dd $
    
    a db '1', '2', '3', '4', '5'
    
    ;la db $ - a

    b dw '1', '2', '3', '4', '5'
    lb equ ($ - b) / 2   ; -> constant
    
    c dd '1', '2', '3', '4', '5'
    lc db ($ - c) / 4
    
    ;la db b - a
    ;lb db (c-b)/2
    
    dd a            ; -> the address of a in memory
    
    ;dd $
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
