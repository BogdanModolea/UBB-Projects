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
    a db 1Ah
    b dw 5h
    c dd 17h
    d dq 3h
; our code starts here
segment code use32 class=code
    start:
        ; a-b-(c-d)+d
        
        ;(c - d)
        mov eax, [c]
        cdq
        sub eax, dword [d]
        sbb edx, dword [d+4]
        push eax
        push edx
        
        ;(a - b)
        mov al, [a]
        cbw
        mov cx, [b]
        sub ax, cx
        
        ;a - b - (c - d)
        cwde
        cdq
        pop ecx
        pop ebx
        sub eax, ebx
        sbb edx, ecx
        
        ;a - b - (c - d) + d
        add eax, dword [d]
        adc edx, dword [d+4]
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
