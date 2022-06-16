bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern scanf               ; tell nasm that exit exists even if we won't be defining it
extern printf              ; tell nasm that exit exists even if we won't be defining it
extern exit, fread, fprintf, fopen, fclose              ; tell nasm that exit exists even if we won't be defining it
import scanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import exit msvcrt.dll
import fread msvcrt.dll  
import fopen msvcrt.dll  
import fclose msvcrt.dll  
import fprintf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    fisier resb 20
    c resd 1
    n resd 1
    read_format db "%s", 0
    char_read_format db "%c", 0
    no_read_format db "%d", 0
    egale_print_format db "Numarul de aparitii al caracterului %c este egal cu cu numarul %d citit", 13, 0
    print_format db "Numarul de aparitii al caracterului %c este nu egal cu cu numarul %d citit", 13, 0
    char_print_format db "%c", 13, 0
    no_print_format db "%d", 13, 0
    fis db "input.txt", 0
    nume_fisier times 20 db 0
    len_fisier equ 20
    fileRDescription dd 0
    fileRAccess db 'r', 0
    fileWAccess db 'w', 0
    len equ 1
    len_text equ 50
    ;text db "ana are alte mere alina are doar pere"
    text times 50 db 0
    fr db 0
    
    

; our code starts here
segment code use32 class=code
    start:
        push dword nume_fisier
        push read_format
        call [scanf]
        add esp, 4 * 2
    
        
        push dword fileRAccess
        push dword nume_fisier
        call [fopen] 
        add esp, 4 * 2
        
        mov dword [fileRDescription], eax
        
        cmp eax, 0
        je final
        
        push dword [fileRDescription]
        push dword len_text
        push dword 1
        push dword text
        call [fread]
        add esp, 4 * 4
        
        
        
        push c
        push char_read_format    
        call [scanf]
        add esp, 4 * 2
        
        push c
        push char_read_format    
        call [scanf]
        add esp, 4 * 2
        
        
        push n
        push no_read_format    
        call [scanf]
        add esp, 4 * 2
        
        
        ;mov bl, byte[n]
        
        
        
        
        mov ecx, 50
        
        mov edi, 0
        
        repeta:
            
            mov al, [c]
            cmp [text + edi], al
            jne char_not_egal
                inc byte[fr]
            char_not_egal:
            inc edi
        
        loop repeta
        
        
        mov al, [n]
        cmp byte[fr], al
        je egale
            push dword [n]
            push dword [c]
            push print_format
            call [printf]
            add esp, 4 * 2
            jmp final
        egale:
            push dword [n]
            push dword [c]
            push egale_print_format
            call [printf]
            add esp, 4 * 2
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
