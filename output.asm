#ProgramNode
.data
ARRAY_OUT_OF_BOUNDS_ERROR_MESSAGE: .asciiz "You may not access an index in an array out of it's range.\nPrepare to die."
.text
main:
	#DeclarationsNode
	move $t0, $sp	#saves stack pointer for later useage
	addi $sp, $sp, -436	#move stack pointer for function start
	addi $s0, $sp, -4
	sw $s0, ($sp)	#set up stack pointer for new function
	sw $ra, 4($sp)	#save return address
	sw $t0, 8($sp)	#save old stack pointer
	li $t0, 0	#load function level to t0
	sw $t0, 16($sp)	#save scope level of this function
	#CompoundStatementNode
		#ProcedureStatementNode
		jal populate
		#ProcedureStatementNode
		jal r
li $v0, 10
syscall
	#SubProgramDeclarationsNode
		#SubProgramNode
		populate:
			#DeclarationsNode
			move $t0, $sp	#saves stack pointer for later useage
			lw $sp, ($sp)	#move stack pointer to the head of the stack			addi $sp, $sp, -28	#move stack pointer for function start
			addi $s0, $sp, -4
			sw $s0, ($sp)	#set up stack pointer for new function
			sw $ra, 4($sp)	#save return address
			sw $t0, 8($sp)	#save old stack pointer
			
			#search for one level up function
			li $t2, 0	#load current level
			lw $t1, 16($t0)	#load previous level
			beq $t1, $t2, _u
			_:
			lw $t0, 12($t0)	#go one level up
			lw $t1, 16($t0)	#get level of function
			bne $t1, $t2, _
			_u:
			sw $t0, 12($sp)	#save one level up pointer
			li $t0, 1	#load function level to t0
			sw $t0, 16($sp)	#save scope level of this function
			#SubProgramHeadNode
			lw $t0, 4($sp)
			lw $t0, ($t0)
			#CompoundStatementNode
				#VariableAssignmentStatementNode
					#LiteralNode
					lw $t0, ($sp)	#put stack head pointer in t0
					li $t1, 0	#The actual literal value
					sw $t1, ($t0)	#put value on stack
				#putting ptr to iter's function call in V0
				move $v0, $sp
				addi $v0, $v0, 24	#offset to variable
				lw $t0, ($sp)	#put stack head in t0
				lw $t1, ($t0)	#pop last value on stack into t1
				sw $t1, ($v0)	#store value where it goes
				#VariableAssignmentStatementNode
					#ConsoleReadNode
					lw $t0, ($sp)
					li $v0, 6
					syscall
					swc1 $f0, ($t0)
				#putting ptr to fibs's function call in V0
				move $v0, $sp
				lw $v0, 12($v0)	#0
				addi $v0, $v0, 24	#offset to variable
				lw $t0, ($sp)	#put stack head in t0
				lw $t1, ($t0)	#pop last value on stack into t1
				sw $t1, ($v0)	#store value where it goes
				#VariableAssignmentStatementNode
					#ConsoleReadNode
					lw $t0, ($sp)
					li $v0, 6
					syscall
					swc1 $f0, ($t0)
				#putting ptr to fibs's function call in V0
				move $v0, $sp
				lw $v0, 12($v0)	#0
				addi $v0, $v0, 24	#offset to variable
				lw $t0, ($sp)	#put stack head in t0
				lw $t1, ($t0)	#pop last value on stack into t1
				sw $t1, ($v0)	#store value where it goes
				#WhileLoopNode
				WhileCondition:
					#BinaryOperationNode
						#AccessVariableNode
						#putting ptr to iter's function call in V0
						move $v0, $sp
						addi $v0, $v0, 24	#offset to variable
						lw $t0, ($v0)
						lw $t1, ($sp)
						sw $t0, ($t1)
					lw $t0, ($sp)
					addi $t0, $t0, -4
					sw $t0, ($sp)
						#LiteralNode
						lw $t0, ($sp)	#put stack head pointer in t0
						li $t1, 100	#The actual literal value
						sw $t1, ($t0)	#put value on stack
					lw $t0, ($sp)
					addi $t0, $t0, 4
					sw $t0, ($sp)
					lw $t1, ($t0)	#put first operand in t1
					lw $t2, -4($t0)	#put second operand in t1
					sle $t1, $t1, $t2
					sw $t1, ($t0)	#save computation
				lw $t0, ($sp)
				lw $t1, ($sp)
				bnez $t1, EndOfWhileLoop
					#VariableAssignmentStatementNode
						#FunctionExpressionNode
							#ArrayVarNode
								#BinaryOperationNode
									#AccessVariableNode
									#putting ptr to iter's function call in V0
									move $v0, $sp
									addi $v0, $v0, 24	#offset to variable
									lw $t0, ($v0)
									lw $t1, ($sp)
									sw $t0, ($t1)
								lw $t0, ($sp)
								addi $t0, $t0, -4
								sw $t0, ($sp)
									#LiteralNode
									lw $t0, ($sp)	#put stack head pointer in t0
									li $t1, 1	#The actual literal value
									sw $t1, ($t0)	#put value on stack
								lw $t0, ($sp)
								addi $t0, $t0, 4
								sw $t0, ($sp)
								lw $t1, ($t0)	#put first operand in t1
								lw $t2, -4($t0)	#put second operand in t1
								sub $t1, $t1, $t2
								sw $t1, ($t0)	#save computation
								#putting ptr to fibs's function call in V0
								move $v0, $sp
								lw $v0, 12($v0)	#0
							lw $t0, ($sp)	#load stack head pointer
							lw $t1, ($t0)	#put index in t0
							subi $t1, $t1, -2	#treat as 0 based indexing
							bltz $t1, ARRAY_OUT_OF_BOUNDS_ERROR
							bge $t1, 103, ARRAY_OUT_OF_BOUNDS_ERROR
							sll $t1, $t1, 2	#covert to bytes
							add $t1, $t1, $v0	#offset in array
							lw $t1, ($t1)
							sw $t1, ($t0)
						lw $t0, ($sp)
						addi $t0, $t0, -4
						sw $t0, ($sp)
							#ArrayVarNode
								#BinaryOperationNode
									#AccessVariableNode
									#putting ptr to iter's function call in V0
									move $v0, $sp
									addi $v0, $v0, 24	#offset to variable
									lw $t0, ($v0)
									lw $t1, ($sp)
									sw $t0, ($t1)
								lw $t0, ($sp)
								addi $t0, $t0, -4
								sw $t0, ($sp)
									#LiteralNode
									lw $t0, ($sp)	#put stack head pointer in t0
									li $t1, 2	#The actual literal value
									sw $t1, ($t0)	#put value on stack
								lw $t0, ($sp)
								addi $t0, $t0, 4
								sw $t0, ($sp)
								lw $t1, ($t0)	#put first operand in t1
								lw $t2, -4($t0)	#put second operand in t1
								sub $t1, $t1, $t2
								sw $t1, ($t0)	#save computation
								#putting ptr to fibs's function call in V0
								move $v0, $sp
								lw $v0, 12($v0)	#0
							lw $t0, ($sp)	#load stack head pointer
							lw $t1, ($t0)	#put index in t0
							subi $t1, $t1, -2	#treat as 0 based indexing
							bltz $t1, ARRAY_OUT_OF_BOUNDS_ERROR
							bge $t1, 103, ARRAY_OUT_OF_BOUNDS_ERROR
							sll $t1, $t1, 2	#covert to bytes
							add $t1, $t1, $v0	#offset in array
							lw $t1, ($t1)
							sw $t1, ($t0)
						lw $t0, ($sp)
						addi $t0, $t0, -4
						sw $t0, ($sp)
						jal populate_sum
						lw $t0, ($sp)
						lw $t1, ($t0)
						addi $t0, $t0, 8
						sw $t0, ($sp)
						lw $t1, ($t0)
					#putting ptr to fibs's function call in V0
					move $v0, $sp
					lw $v0, 12($v0)	#0
					addi $v0, $v0, 24	#offset to variable
					lw $t0, ($sp)	#put stack head in t0
					lw $t1, ($t0)	#pop last value on stack into t1
					sw $t1, ($v0)	#store value where it goes
				j WhileCondition
				EndOfWhileLoop:
		lw $t0, 4($sp)	#put return address in t0
		lw $t1, 20($sp)	#put return value in t1
		lw $sp, 8($sp)	#put stack pointer back up
		lw $t2, ($sp)	#put t2 to stack head ptr in calling function
		sw $t1, ($t2)	#put return value on stack
		jr $t0
			#SubProgramDeclarationsNode
				#SubProgramNode
				populate_sum:
					#DeclarationsNode
					move $t0, $sp	#saves stack pointer for later useage
					lw $sp, ($sp)	#move stack pointer to the head of the stack					addi $sp, $sp, -32	#move stack pointer for function start
					addi $s0, $sp, -4
					sw $s0, ($sp)	#set up stack pointer for new function
					sw $ra, 4($sp)	#save return address
					sw $t0, 8($sp)	#save old stack pointer
					
					#search for one level up function
					li $t2, 1	#load current level
					lw $t1, 16($t0)	#load previous level
					beq $t1, $t2, _s
					_t:
					lw $t0, 12($t0)	#go one level up
					lw $t1, 16($t0)	#get level of function
					bne $t1, $t2, _t
					_s:
					sw $t0, 12($sp)	#save one level up pointer
					li $t0, 2	#load function level to t0
					sw $t0, 16($sp)	#save scope level of this function
					#SubProgramHeadNode
					lw $t0, 4($sp)
					lw $t0, ($t0)
					lw $t1, 4($t0)	#get loaded value from stack
					sw $t1, 24($sp)	#save word in correct spot (hopefully?)
					lw $t1, 8($t0)	#get loaded value from stack
					sw $t1, 28($sp)	#save word in correct spot (hopefully?)
					#CompoundStatementNode
						#FunctionAssignmentStatementNode
						#putting ptr to sum's function call in V0
						move $v0, $sp
						lw $t0, ($sp)	#put stack head in t0
						lw $t1, ($t0)	#pop last value on stack into t1
						sw $t1, 20($v0)	#store value in return slot
				lw $t0, 4($sp)	#put return address in t0
				lw $t1, 20($sp)	#put return value in t1
				lw $sp, 8($sp)	#put stack pointer back up
				lw $t2, ($sp)	#put t2 to stack head ptr in calling function
				sw $t1, ($t2)	#put return value on stack
				jr $t0
					#SubProgramDeclarationsNode
		#SubProgramNode
		r:
			#DeclarationsNode
			move $t0, $sp	#saves stack pointer for later useage
			lw $sp, ($sp)	#move stack pointer to the head of the stack			addi $sp, $sp, -28	#move stack pointer for function start
			addi $s0, $sp, -4
			sw $s0, ($sp)	#set up stack pointer for new function
			sw $ra, 4($sp)	#save return address
			sw $t0, 8($sp)	#save old stack pointer
			
			#search for one level up function
			li $t2, 0	#load current level
			lw $t1, 16($t0)	#load previous level
			beq $t1, $t2, _p
			_r:
			lw $t0, 12($t0)	#go one level up
			lw $t1, 16($t0)	#get level of function
			bne $t1, $t2, _r
			_p:
			sw $t0, 12($sp)	#save one level up pointer
			li $t0, 1	#load function level to t0
			sw $t0, 16($sp)	#save scope level of this function
			#SubProgramHeadNode
			lw $t0, 4($sp)
			lw $t0, ($t0)
			#CompoundStatementNode
				#VariableAssignmentStatementNode
					#LiteralNode
					lw $t0, ($sp)	#put stack head pointer in t0
					li $t1, -2	#The actual literal value
					sw $t1, ($t0)	#put value on stack
				#putting ptr to iter's function call in V0
				move $v0, $sp
				addi $v0, $v0, 24	#offset to variable
				lw $t0, ($sp)	#put stack head in t0
				lw $t1, ($t0)	#pop last value on stack into t1
				sw $t1, ($v0)	#store value where it goes
				#WhileLoopNode
				WhileConditionu:
					#BinaryOperationNode
						#AccessVariableNode
						#putting ptr to iter's function call in V0
						move $v0, $sp
						addi $v0, $v0, 24	#offset to variable
						lw $t0, ($v0)
						lw $t1, ($sp)
						sw $t0, ($t1)
					lw $t0, ($sp)
					addi $t0, $t0, -4
					sw $t0, ($sp)
						#LiteralNode
						lw $t0, ($sp)	#put stack head pointer in t0
						li $t1, 100	#The actual literal value
						sw $t1, ($t0)	#put value on stack
					lw $t0, ($sp)
					addi $t0, $t0, 4
					sw $t0, ($sp)
					lw $t1, ($t0)	#put first operand in t1
					lw $t2, -4($t0)	#put second operand in t1
					sle $t1, $t1, $t2
					sw $t1, ($t0)	#save computation
				lw $t0, ($sp)
				lw $t1, ($sp)
				bnez $t1, EndOfWhileLoopu
					#ProcedureStatementNode
					jal r_r1
				j WhileConditionu
				EndOfWhileLoopu:
		lw $t0, 4($sp)	#put return address in t0
		lw $t1, 20($sp)	#put return value in t1
		lw $sp, 8($sp)	#put stack pointer back up
		lw $t2, ($sp)	#put t2 to stack head ptr in calling function
		sw $t1, ($t2)	#put return value on stack
		jr $t0
			#SubProgramDeclarationsNode
				#SubProgramNode
				r_r1:
					#DeclarationsNode
					move $t0, $sp	#saves stack pointer for later useage
					lw $sp, ($sp)	#move stack pointer to the head of the stack					addi $sp, $sp, -24	#move stack pointer for function start
					addi $s0, $sp, -4
					sw $s0, ($sp)	#set up stack pointer for new function
					sw $ra, 4($sp)	#save return address
					sw $t0, 8($sp)	#save old stack pointer
					
					#search for one level up function
					li $t2, 1	#load current level
					lw $t1, 16($t0)	#load previous level
					beq $t1, $t2, _n
					_o:
					lw $t0, 12($t0)	#go one level up
					lw $t1, 16($t0)	#get level of function
					bne $t1, $t2, _o
					_n:
					sw $t0, 12($sp)	#save one level up pointer
					li $t0, 2	#load function level to t0
					sw $t0, 16($sp)	#save scope level of this function
					#SubProgramHeadNode
					lw $t0, 4($sp)
					lw $t0, ($t0)
					#CompoundStatementNode
						#ConsoleWriteNode
							#ArrayVarNode
								#AccessVariableNode
								#putting ptr to iter's function call in V0
								move $v0, $sp
								lw $v0, 12($v0)	#1
								addi $v0, $v0, 24	#offset to variable
								lw $t0, ($v0)
								lw $t1, ($sp)
								sw $t0, ($t1)
								#putting ptr to fibs's function call in V0
								move $v0, $sp
								lw $v0, 12($v0)	#0
								lw $v0, 12($v0)	#1
							lw $t0, ($sp)	#load stack head pointer
							lw $t1, ($t0)	#put index in t0
							subi $t1, $t1, -2	#treat as 0 based indexing
							bltz $t1, ARRAY_OUT_OF_BOUNDS_ERROR
							bge $t1, 103, ARRAY_OUT_OF_BOUNDS_ERROR
							sll $t1, $t1, 2	#covert to bytes
							add $t1, $t1, $v0	#offset in array
							lw $t1, ($t1)
							sw $t1, ($t0)
						lw $t0, ($sp)
						lwc1 $f12, ($t0)
						li $v0, 2
						syscall
						#VariableAssignmentStatementNode
							#BinaryOperationNode
								#AccessVariableNode
								#putting ptr to iter's function call in V0
								move $v0, $sp
								lw $v0, 12($v0)	#1
								addi $v0, $v0, 24	#offset to variable
								lw $t0, ($v0)
								lw $t1, ($sp)
								sw $t0, ($t1)
							lw $t0, ($sp)
							addi $t0, $t0, -4
							sw $t0, ($sp)
								#LiteralNode
								lw $t0, ($sp)	#put stack head pointer in t0
								li $t1, 1	#The actual literal value
								sw $t1, ($t0)	#put value on stack
							lw $t0, ($sp)
							addi $t0, $t0, 4
							sw $t0, ($sp)
							lw $t1, ($t0)	#put first operand in t1
							lw $t2, -4($t0)	#put second operand in t1
							add $t1, $t1, $t2
							sw $t1, ($t0)	#save computation
						#putting ptr to iter's function call in V0
						move $v0, $sp
						lw $v0, 12($v0)	#1
						addi $v0, $v0, 24	#offset to variable
						lw $t0, ($sp)	#put stack head in t0
						lw $t1, ($t0)	#pop last value on stack into t1
						sw $t1, ($v0)	#store value where it goes
				lw $t0, 4($sp)	#put return address in t0
				lw $t1, 20($sp)	#put return value in t1
				lw $sp, 8($sp)	#put stack pointer back up
				lw $t2, ($sp)	#put t2 to stack head ptr in calling function
				sw $t1, ($t2)	#put return value on stack
				jr $t0
					#SubProgramDeclarationsNode
#ERRORs
ARRAY_OUT_OF_BOUNDS_ERROR:
la $a0, ARRAY_OUT_OF_BOUNDS_ERROR_MESSAGE
li $v0, 4
syscall
li $v0, 17
li $a0, 1
syscall

