bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; Se dau cuvintele A si B. Se cere dublucuvantul C:
    ; bitii 0-3 ai lui C coincid cu bitii 5-8 ai lui B
    ; bitii 4-10 ai lui C sunt invers fata de bitii 0-6 ai lui B
    ; bitii 11-18 ai lui C sunt 1
    ; bitii 19-31 ai lui C coincid cu bitii 3-15 ai lui B

    a dw 0111011101010111b
    b dw 1001101110111110b
    c dd 0
    
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov bx, 0
        
        mov ax, [b]                                 
        and ax, 0000000111100000b                   ; isolating bits 10-12 of b
        mov cl, 5 
        ror ax, cl                                  ; rotating 5 positions to the right
        or bx, ax                                   ; putting the bits in the result
        
        mov ax, [b]
        not ax                                      ; inverting the value of b
        and ax, 0000000001111111b                   ; isolating bits 0-6 of b
        mov cl, 4
        rol ax, cl                                  ; rotating bits 4 positions to the left
        or bx, ax 
        
        push bx
        mov ebx, 0
        pop bx
        or ebx, 00000000000001111111100000000000b   ; setting bits 11-18 of the result to have the value 1
        
        mov ax, [b]
        and ax, 1111111111111000b                   ; isolating bits 3-15 of b
        push ax
        mov eax, 0
        pop ax
        mov cl, 16                                  
        rol eax, cl                                 ; rotating bits 16 positions to the left
        or ebx, eax                                 ; putting the bits in the result
        
        mov [c], ebx                                ; we put the value from the register in the result variable
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
