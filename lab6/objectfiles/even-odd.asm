	.data
a:	
	11
	.text
main:
	load %x0, $a, %x4
	andi %x4, 1, %x5
	muli %x5, 2, %x6
	subi %x6, 1, %x10  
	end