bits 32

global f        

segment data use32 class=data


segment code use32 public code

; return address
; a
; b


    f:
        
        mov eax, [esp + 4]
        
        sub eax, [esp + 8]
        
        ret 8
        ;pop eip
        ;add esp, <arg>