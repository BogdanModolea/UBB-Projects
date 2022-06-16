bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data

    a db '1', '2', '3', '4', '5'
    b dw '1', '2', '3', '4', '5'
    c dd '1', '2', '3', '4', '5'

; our code starts here
segment code use32 class=code
    start:
        ;loasb lodsw lodsd  \\  load string byte  \\ load string word ...
        
        mov esi, a
        
        lodsb
        ;mov al, [esi]
        ;if df == 0 esi++
        ;else esi--
        
        
        mov esi, b
        
        lodsw
        ; mov ax, [esi]
        ;if df == 0 esi = esi + 2
        ;else esi = esi - 2
        
        
        
        ;stosb, stosw, stosd
        
        stosb
        ;mov [edi], al
        ;if df == 0 edi++
        ;else edi--
        
        stosw
        ;mov [edi], ax
        ;if df == 0 edi = edi + 2
        ;else edi = edi - 2
        
        stosd
        ;mov [edi], eax
        ;if df == 0 edi = edi + 4
        ;else edi = edi - 4
        
    

        movsb                      
        ;mov [edi], byte [esi]
        ;if df == 0 edi++, esi++
        ;else edi--, esi--
        
        movsw
        ;mov [edi], word [esi]
    
        
        movsd
        ;mov [edi], dword [esi]
        
        
        cmpsb    ; compare strings
        
        
        scasb
        ;cmp al, [edi]
        
        scasw
        ;cmp ax, [edi]
        
        scasd
        ;cmp eax, [edi]
        
    
        ;mov [mem] ===> [mem]
        ;store reg ===> [mem]
        ;load [mem] ===> [reg]
        ;cmp [mem] ? [mem]
        ;scan reg ? [mem]
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
