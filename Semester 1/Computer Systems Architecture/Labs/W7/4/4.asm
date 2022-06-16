bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
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
        ; fread(buf, size, count, file)
                ;     2      3    -> atempt to read 2 chunks of 3 bytes 
        
        push mode
        push f
        call [fopen]
        add esp, 4 * 2
        mov [fd], eax
        push dword [fd]
        push dword 3
        push dword 2
        push a
        call[fread]
        add esp, 4*4
        push dword[fd]
        call [fclose]
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
