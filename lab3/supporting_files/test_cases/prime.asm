	.data
a:
	6469
	.text
main:
	load %x0, $a, %x3
	addi %x0, 1, %x5
	beq %x3, %x5, notprime
loop:
	addi %x5, 1, %x5
	mul %x5, %x5, %x8
	bgt %x8, %x3, prime
modcheck:
	div %x3, %x5, %x6
	mul %x5, %x6, %x6
	sub %x3, %x6, %x6
	beq %x6, %x0, notprime
	jmp loop
prime:
	addi %x0, 1, %x10
	end
notprime:
	subi %x0, 1, %x10
	end
