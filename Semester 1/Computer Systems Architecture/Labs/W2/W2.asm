bits 32 

global start        

extern exit               
import exit msvcrt.dll    
                          
segment data use32 class=data

    a db 10

segment code use32 class=code
    start:
        
        ;mov al, [a]  
        ;move the value of a to al
        
        ;mov ah, al
        
        ;mov ebx, a   
        ;move the address of a to ebx
        
        
        
        ;add <dest> <src> dest <= dest + src
        
        ;sub <dest> <src> dest <= dest - src
        
        ;mov ah, [a]
        
        ;add ah, 0bh
        ;
        ;mov al, ah
        
        ;sub al, [a]
        
        
        
        ;mul <src>
        
        ;div <src>
        
        mul al
        
        mul eax
        
        mul byte [a]
        
        div word [a]
        
        push    dword 0      
        call    [exit]       
