#ProgramNode
.data
ARRAY_OUT_OF_BOUNDS_ERROR_MESSAGE: .asciiz "You may not access an index in an array out of it's range.\nPrepare to die."
.text
main:
	#DeclarationsNode
	move $t0, $sp	#saves stack pointer for later useage
	addi $sp, $sp, -40	#move stack pointer for function start
	addi $s0, $sp, -4
	sw $s0, ($sp)	#set up stack pointer for new function
	sw $ra, 4($sp)	#save return address
	sw $t0, 8($sp)	#save old stack pointer
	li $t0, 0
	sw $t0, 16($sp)	#save scope level of this function
	#CompoundStatementNode
		#VariableAssignmentStatementNode
			#LiteralNode
			lw $t0, ($sp)
			li $t1, 4
			sw $t1, ($t0)
		#putting ptr to fee's function call in V0
		lw $v0, ($sp)
		addi $v0, $v0, 24	#offset to variable
		lw $t0, ($sp)	#put stack head in t0
		lw $t1, ($t0)	#pop last value on stack into t1
		sw $t1, ($v0)	#store value where it goes
		#VariableAssignmentStatementNode
			#LiteralNode
			lw $t0, ($sp)
			li $t1, 5
			sw $t1, ($t0)
		#putting ptr to fi's function call in V0
		lw $v0, ($sp)
		addi $v0, $v0, 28	#offset to variable
		lw $t0, ($sp)	#put stack head in t0
		lw $t1, ($t0)	#pop last value on stack into t1
		sw $t1, ($v0)	#store value where it goes
		#VariableAssignmentStatementNode
			#BinaryOperationNode
				#BinaryOperationNode
					#LiteralNode
					lw $t0, ($sp)
					li $t1, 3
					sw $t1, ($t0)
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
					#VariableNode
						#putting ptr to fee's function call in V0
						lw $v0, ($sp)
						addi $v0, $v0, 24	#offset to variable
					addi $t0, $v0, 24	#put ptr to variable in t0
					lw $t0, ($t0)
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
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#VariableNode
					#putting ptr to fi's function call in V0
					lw $v0, ($sp)
					addi $v0, $v0, 28	#offset to variable
				addi $t0, $v0, 28	#put ptr to variable in t0
				lw $t0, ($t0)
				lw $t1, ($sp)
				sw $t0, ($t1)
			lw $t0, ($sp)
			addi $t0, $t0, 4
			sw $t0, ($sp)
			lw $t1, ($t0)	#put first operand in t1
			lw $t2, -4($t0)	#put second operand in t1
			add $t1, $t1, $t2
			sw $t1, ($t0)	#save computation
		#putting ptr to fo's function call in V0
		lw $v0, ($sp)
		addi $v0, $v0, 32	#offset to variable
		lw $t0, ($sp)	#put stack head in t0
		lw $t1, ($t0)	#pop last value on stack into t1
		sw $t1, ($v0)	#store value where it goes
		#IfStatementNode
			#BinaryOperationNode
				#VariableNode
					#putting ptr to fo's function call in V0
					lw $v0, ($sp)
					addi $v0, $v0, 32	#offset to variable
				addi $t0, $v0, 32	#put ptr to variable in t0
				lw $t0, ($t0)
				lw $t1, ($sp)
				sw $t0, ($t1)
			lw $t0, ($sp)
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#LiteralNode
				lw $t0, ($sp)
				li $t1, 13
				sw $t1, ($t0)
			lw $t0, ($sp)
			addi $t0, $t0, 4
			sw $t0, ($sp)
			lw $t1, ($t0)	#put first operand in t1
			lw $t2, -4($t0)	#put second operand in t1
			slt $t1, $t1, $t2
			sw $t1, ($t0)	#save computation
		lw $t0, ($sp)
		lw $s0, ($t0)
		beqz $s0, GoToFalseStatement
			#VariableAssignmentStatementNode
				#LiteralNode
				lw $t0, ($sp)
				li $t1, 13
				sw $t1, ($t0)
			#putting ptr to fo's function call in V0
			lw $v0, ($sp)
			addi $v0, $v0, 32	#offset to variable
			lw $t0, ($sp)	#put stack head in t0
			lw $t1, ($t0)	#pop last value on stack into t1
			sw $t1, ($v0)	#store value where it goes
		j SkipFalseStatement
		GoToFalseStatement:
			#VariableAssignmentStatementNode
				#LiteralNode
				lw $t0, ($sp)
				li $t1, 26
				sw $t1, ($t0)
			#putting ptr to fo's function call in V0
			lw $v0, ($sp)
			addi $v0, $v0, 32	#offset to variable
			lw $t0, ($sp)	#put stack head in t0
			lw $t1, ($t0)	#pop last value on stack into t1
			sw $t1, ($v0)	#store value where it goes
		SkipFalseStatement:
		#ConsoleWriteNode
			#VariableNode
				#putting ptr to fo's function call in V0
				lw $v0, ($sp)
				addi $v0, $v0, 32	#offset to variable
			addi $t0, $v0, 32	#put ptr to variable in t0
			lw $t0, ($t0)
			lw $t1, ($sp)
			sw $t0, ($t1)
		lw $t0, ($sp)
		lw $a0, ($t0)
		li $v0, 1
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

