#ProgramNode
.data
ARRAY_OUT_OF_BOUNDS_ERROR_MESSAGE: .asciiz "You may not access an index in an array out of it's range.\nPrepare to die."
.text
main:
	#DeclarationsNode
	move $t0, $sp	#saves stack pointer for later useage
	addi $sp, $sp, -32	#move stack pointer for function start
	addi $s0, $sp, -4
	sw $s0, ($sp)	#set up stack pointer for new function
	sw $ra, 4($sp)	#save return address
	sw $t0, 8($sp)	#save old stack pointer
	li $t0, 0	#load function level to t0
	sw $t0, 16($sp)	#save scope level of this function
	#CompoundStatementNode
		#VariableAssignmentStatementNode
			#ConsoleReadNode
			lw $t0, ($sp)
			li $v0, 5
			syscall
			sw $v0, ($t0)
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#AssignVariableNode			#putting ptr to a's function call in V0
			move $v0, $sp
			addi $v0, $v0, 24	#offset to variable
			lw $t0, ($sp)
			sw $v0, ($t0)	#save ptr to stack
		lw $t0, ($sp)	#has stack head
		lw $t1, ($t0)	#has ptr to assign to
		lw $t2, 4($t0)	#has value to assign
		sw $t2, ($t1)	#make assignment
		addi $t0, $t0, 4
		sw $t0, ($sp)	#pop off stack values
		#VariableAssignmentStatementNode
			#ConsoleReadNode
			lw $t0, ($sp)
			li $v0, 5
			syscall
			sw $v0, ($t0)
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#AssignVariableNode			#putting ptr to b's function call in V0
			move $v0, $sp
			addi $v0, $v0, 28	#offset to variable
			lw $t0, ($sp)
			sw $v0, ($t0)	#save ptr to stack
		lw $t0, ($sp)	#has stack head
		lw $t1, ($t0)	#has ptr to assign to
		lw $t2, 4($t0)	#has value to assign
		sw $t2, ($t1)	#make assignment
		addi $t0, $t0, 4
		sw $t0, ($sp)	#pop off stack values
		#ConsoleWriteNode
			#BinaryOperationNode
				#AccessVariableNode
				#putting ptr to a's function call in V0
				move $v0, $sp
				addi $v0, $v0, 24	#offset to variable
				lw $t0, ($v0)
				lw $t1, ($sp)
				sw $t0, ($t1)
			lw $t0, ($sp)
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#AccessVariableNode
				#putting ptr to b's function call in V0
				move $v0, $sp
				addi $v0, $v0, 28	#offset to variable
				lw $t0, ($v0)
				lw $t1, ($sp)
				sw $t0, ($t1)
			lw $t0, ($sp)
			addi $t0, $t0, 4
			sw $t0, ($sp)
			lw $t1, ($t0)	#put first operand in t1
			lw $t2, -4($t0)	#put second operand in t1
			mul $t1, $t1, $t2
			sw $t1, ($t0)	#save computation
		lw $t0, ($sp)
		lw $a0, ($t0)
		li $v0, 1
		syscall
		li $v0, 11	#prepare syscall for print character
		li $a0, 10	#put newline to be printed
		syscall
li $v0, 10
syscall
	#SubProgramDeclarationsNode
#ERRORs
ARRAY_OUT_OF_BOUNDS_ERROR:
la $a0, ARRAY_OUT_OF_BOUNDS_ERROR_MESSAGE
li $v0, 4
syscall
li $v0, 17
li $a0, 1
syscall

