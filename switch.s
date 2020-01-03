.section .text
.globl switch2
switch2:
movq $0,%rax
movq $21,%rdx
cmpq $10,%rdx
ja .Ldefault
jmp *.Ljunp(, %rdx , 8)
.L27 :
movq (%rdi),%rcx
movq %rcx,(%rsi)
movq (%rsi),%rax
jmp .Ldone
.L25 :
movq (%rsi),%rcx
addq %rcx,(%rdi)
movq (%rdi),%rax
jmp .Ldone
.L29 :
.L30 :
subq $59,(%rdi)
movq (%rdi),%rcx
subq %rcx,(%rsi)
jmp .Ldone
.L23 :
addq $60,(%rdi)
.L21 :
movq (%rsi),%rax
imulq %rax,%rax
jmp .Ldone
.L31 :
movq (%rsi),%rax
movq (%rdi),%rcx
sarq %cl,%rax
jmp .Ldone
.L22:
jmp .Ldefault
.L24:
jmp .Ldefault
.L26:
jmp .Ldefault
.L28:
jmp .Ldefault
.Ldefault:
movq $12,%rax
salq $3,%rax
.Ldone:
ret
	.section	.rodata
	.align 8
.Ljunp:
.quad .L21
.quad .L22
.quad .L23
.quad .L24
.quad .L25
.quad .L26
.quad .L27
.quad .L28
.quad .L29
.quad .L30
.quad .L31
