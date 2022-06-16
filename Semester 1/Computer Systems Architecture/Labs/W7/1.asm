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
    ; ...

; our code starts here
segment code use32 class=code
    start:
        ; printf("int = %d\n", int)
        ; printf("%d + %d = %d\n", a, b, a + b)
        ; %d - int
        ; %x - hex int
        ; %c - char
        ; %s - string
        ; %ld - large int
        ; %ud - unsign int
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
