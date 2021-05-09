	.data
n:
	10
	.text
main:
	load %x0, $n, %x4
	addi %x0, 0, %x5
	addi %x0, 1, %x6
	addi %x0, 0, %x8 
	addi %x0, 65535, %x9
loop:
	beq %x8, %x4, endl
	store %x5, 0, %x9
	add %x5, %x6, %x7
	add %x0, %x6, %x5
	add %x0, %x7, %x6
	addi %x8, 1, %x8
	subi %x9, 1, %x9
	jmp loop
endl:
	end	