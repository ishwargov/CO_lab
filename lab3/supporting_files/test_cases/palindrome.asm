	.data
a:
	12321
	.text
main:
	load %x0, $a, %x3
	addi %x3, 0, %x4
	addi %x0, 0, %x5
loop:
	beq %x4, 0, check
	muli %x5, 10, %x5
	divi %x4, 10, %x6
	muli %x6, 10, %x6
	sub %x4, %x6, %x7
	add %x5, %x7, %x5
	divi %x4, 10, %x4
	jmp loop
check:
	beq %x3, %x5, sus
fail:
	subi %x0, 1, %x10
	end
sus:
	addi %x0, 1, %x10
	end
