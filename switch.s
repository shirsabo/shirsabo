.section .text
.globl switch2
switch2:
movq $0,%rax
movq $21,%rdx
cmpq $10,%rdx
ja .Ldefualt
jmp *.Ljunp(, %rdx , 8)
.L27 :
movq (%rdi),%rcx
movq %rcx,(%rsi)
movq (%rsi),%rax
jump .Ldone
.L25 :
movq (%rsi),%rcx
addq %rcx,(%rdi)
movq (%rdi),%rax
jump .Ldone
.L29 :
.L30 :
subq $59,(%rdi)
movq (%rdi),%rcx
subq %rcx,(%rsi)
jump .Ldone
.L23 :
addq $60,(%rdi)
.L21 :
movq (%rsi),%rax
imulq %rax,%rax
jump .Ldone
.L31 :
movq (%rsi),%rax
movq (%rdi),%rcx
sarq %cl,%rax
jump .Ldone
.L22:
jump .Ldefault
.L24:
jump .Ldefault
.L26:
jump .Ldefault
.L28:
jump .Ldefault
.Ldefualt:
movq $12,%rax
salq $3,%rax
.Ldone:
ret
	.section	.rodata
	.alig 8
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
