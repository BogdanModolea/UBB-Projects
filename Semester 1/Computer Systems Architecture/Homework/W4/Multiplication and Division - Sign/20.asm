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
    a dw 5
    b db 2
    e dd 1
    x dq 1

; our code starts here
segment code use32 class=code
    start:
        ; x-b+8+(2*a-b)/(b*b)+e
        
        ;x - b
        cdq
        mov eax, dword [x]
        mov edx, dword [x+4]
        mov ebx, eax
        mov ecx, edx
        mov al, [b]
        cbw
        cwd
        cdq
        sub ebx, eax
        sbb ecx, edx
        
        ;x - b + 8
        add ebx, 8
        adc ecx, 0
        push ebx
        push ecx
        
        ;(2 * a - b)
        mov al, 2
        mul byte [a]
        mov bl, [b]
        cbw
        sub ax, bx
        mov bx, ax
        
        ;(b * b)
        mov ax, 0
        mov al, [b]
        mul byte [b]
        
        ;(2 * a - b) / (b * b)
        push ax
        mov ax, bx
        pop bx
        div bx
        push ax
        mov ecx, 0
        mov cx, bx
        mov ebx, ecx
        
        ;x - b + 8 + (2 * a - b) / (b * b)
        cwde
        mov ecx, eax
        mov ebx, eax
        pop bx
        pop edx
        pop eax
        add eax, ebx
        adc edx, ecx
        
        ;x - b + 8 + (2 * a - b) / (b * b) + e
        mov ebx, [e]
        mov ecx, 0
        add eax, ebx
        adc edx, ecx
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program