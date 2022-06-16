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
    
    a resd 1
    b resd 1
    
    read_format db "%d", 0
    
    print_format db "%d + %d = %d", 13, 0
    
    file db "1.txt", 0
    
    mode db "w", 0
    fd resd 1
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        ; fd = fopen(path, mod)
        ; EAX
        ; w - write -> position my cursor at the beginning of the file
        ; a - apent -> position my cursour at the end of the file
        ; r - read
        
        push a
        push read_format    
        call [scanf]
        
        add esp, 4 * 2
    
        push b
        push read_format    
        call [scanf]
        add esp, 4 * 2
    
        push mode
        push file
        call [fopen]
        mov [fd], eax
        add esp, 4 * 2
    
        mov eax, [a]
        add eax, [b]
        push eax
        push dword [b]
        push dword [a]
        push print_format
        push dword [fd]
        call [fprintf]
        add esp, 4 * 5
        call [fclose]
        
        add esp, 4
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
