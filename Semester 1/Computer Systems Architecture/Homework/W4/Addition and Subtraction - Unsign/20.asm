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
    a db 2
    b dw 15
    c dd 80
    d dq 125
    
; our code starts here
segment code use32 class=code
    start:
        ; (a+c)- b+a + (d-c)
        
        ;(a + c)
        mov eax, 0
        mov al, [a]
        mov ecx, 0
        mov ecx, [c]
        add eax, ecx
        
        ;(d - c)
        mov ebx, 0
        mov ebx, eax
        mov edx, 0
        mov eax, dword [d]
        mov edx, dword [d+4]
        sub eax, dword [c]
        push edx
        push eax
        
        ;(a + c) - b
        mov ecx, 0
        mov cx, [b]
        sub ebx, ecx
        
        ;(a + c) - b + a
        mov al, [a]
        mov ah, 0
        mov ecx, 0
        mov cx, ax
        add ebx, ecx
        mov eax, ebx
        
        ;(a + c) - b + a + (d - c)
        mov edx, 0
        pop ecx
        pop edx
        mov ebx, 0
        add eax, ecx
        adc ebx, edx
            
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
