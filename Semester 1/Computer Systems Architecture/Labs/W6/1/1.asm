bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    
    ;db 'A string.'
    ;db 'A', '2', 'n', 'd', 's', 't', 'g'
    ;a db 34, 0fh, 1010101b, 234, 23h, 123
    
    
    ;a db '1', '2', '3', '4', '5'
    ;b dw '1', '2', '3', '4', '5'
    ;c dd '1', '2', '3', '4', '5'
    ;d dq '1', '2', '3', '4', '5'
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;mov al, [a+4]
        
        ;mov ax, [b + 5]
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
