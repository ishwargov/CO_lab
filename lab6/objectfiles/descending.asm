	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	load %x0, $n, %x3
	add %x0, %x0, %x4
loopi:
	bgt %x4, %x3, endi
	add %x0, %x4, %x5
	add %x0, %x4, %x6
	load %x6, $a, %x7
loopj:
	bgt %x5, %x3, endj
	load %5, $a, %x8
	bgt %x8, %x7, update
updated:
	addi %x5, 1, %x5
	jmp loopj
endj:
	load %x4, $a, %x9
	store %x7, $a, %x4
	store %x9, $a, %x6
	addi %x4, 1, %x4
	jmp loopi
update:
	add %x8, %x0, %x7
	add %x5, %x0, %x6
	jmp updated
endi:
	end