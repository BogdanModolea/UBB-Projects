bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; [b*c-(e+f)]/(a+d)
    a db 1
    b db 3
    c db 4
    d db 1
    e dw 2
    f dw 2

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ax, [e]
        add ax, [f]
        mov bx, ax
        mov al, [b]
        mov cl, [c]
        mul cl
        cbw
        sub ax, bx
        mov bl, [a]
        add bl, [d]
        div bl
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
