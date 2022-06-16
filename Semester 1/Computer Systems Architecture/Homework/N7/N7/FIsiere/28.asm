bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

extern scanf               ; tell nasm that exit exists even if we won't be defining it
extern printf              ; tell nasm that exit exists even if we won't be defining it
extern fscanf               ; tell nasm that exit exists even if we won't be defining it
extern fopen               ; tell nasm that exit exists even if we won't be defining it
extern fread               ; tell nasm that exit exists even if we won't be defining it
extern fprintf               ; tell nasm that exit exists even if we won't be defining it
extern fclose               ; tell nasm that exit exists even if we won't be defining it
extern exit               ; tell nasm that exit exists even if we won't be defining it
extern strlen
import scanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fscanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fread msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fprintf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import strlen msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...

    a times 20 resb 1
    ;lena equ $ - a
    read_format db "%s", 0
    print_format db "%s", 13, 0
    file db "idk.txt", 0
    mode db "w", 0
    fd resd 1
    apare db 0
    
; our code starts here
segment code use32 class=code
    start:
        push dword mode
        push dword file
        call [fopen]
        mov [fd], eax
        add esp, 4 * 2
        
        repeta:
            push dword a
            push dword read_format    
            call [scanf]
            add esp, 4 * 2
            
            mov byte[apare], 0
            mov esi, 0
            push a
            call [strlen]
            add esp, 4*1
            
            mov ecx, eax
            
            cauta:
                mov al, [a + esi]
                cmp al, 61h
                jb nusalvezmomentan
                
                cmp al, 7Ah
                ja nusalvezmomentan
                
                inc byte[apare]
                
                nusalvezmomentan:
                    inc esi
                    
                cmp byte[apare], 1
            loopne cauta
            
            
            cmp byte[apare], 1
            
            jnz final
                push dword a
                push dword print_format
                push dword [fd]
                call [fprintf]
                add esp, 4 * 2
            
            final
            cmp byte[a], 24h
        jne repeta
        
        push dword [fd]
        call [fclose]
        add esp, 4
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
