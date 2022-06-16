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
    ;a db 011h       ;[a + 0]
    ;db 22h          ;[a + 1]
    ;db 33h          ;[a + 2]
    ;dw 4455h        ;[a + 3]
    ;dd 0aabbccddh   ;[a + 5]
    
    
    db 011h         ;[a - 3]
    db 22h          ;[a - 2]
    db 33h          ;[a - 1]
    a dw 4455h      ;[a + 0]
    dd 0aabbccddh   ;[a + 2]
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        mov ax, [a-2]   ; AX = 3322, AH = 33, AL = 22
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
