#ProgramNode
.data
ARRAY_OUT_OF_BOUNDS_ERROR_MESSAGE: .asciiz "You may not access an index in an array out of it's range.\nPrepare to die."
.text
main:
	#DeclarationsNode
	move $t0, $sp	#saves stack pointer for later useage
	addi $sp, $sp, -68	#move stack pointer for function start
	addi $s0, $sp, -4
	sw $s0, ($sp)	#set up stack pointer for new function
	sw $ra, 4($sp)	#save return address
	sw $t0, 8($sp)	#save old stack pointer
	li $t0, 0	#load function level to t0
	sw $t0, 16($sp)	#save scope level of this function
	#CompoundStatementNode
		#VariableAssignmentStatementNode
			#LiteralNode
			lw $t0, ($sp)	#put stack head pointer in t0
			li $t1, 5	#The actual literal value
			sw $t1, ($t0)	#put value on stack
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#AssignVariableNode			#putting ptr to fee's function call in V0
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
			#BinaryOperationNode
				#LiteralNode
				lw $t0, ($sp)	#put stack head pointer in t0
				li $t1, 4	#The actual literal value
				sw $t1, ($t0)	#put value on stack
			lw $t0, ($sp)
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#FunctionExpressionNode
					#BinaryOperationNode
						#AccessVariableNode
						#putting ptr to fee's function call in V0
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
						li $t1, 1065353216	#The actual literal value
						sw $t1, ($t0)	#put value on stack
					lw $t0, ($sp)
					addi $t0, $t0, 4
					sw $t0, ($sp)
					lwc1 $f0, ($t0)	#put first operand into f0
					lwc1 $f2, -4($t0)	#put second operand into f2
					cvt.s.w $f0, $f0	#cast to float
					mul.s $f0, $f0, $f2
					swc1 $f0, ($t0)	#put return value on stack
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
					#BinaryOperationNode
						#AccessVariableNode
						#putting ptr to fi's function call in V0
						move $v0, $sp
						addi $v0, $v0, 28	#offset to variable
						lw $t0, ($v0)
						lw $t1, ($sp)
						sw $t0, ($t1)
					lw $t0, ($sp)
					addi $t0, $t0, -4
					sw $t0, ($sp)
						#LiteralNode
						lw $t0, ($sp)	#put stack head pointer in t0
						li $t1, 1159684096	#The actual literal value
						sw $t1, ($t0)	#put value on stack
					lw $t0, ($sp)
					addi $t0, $t0, 4
					sw $t0, ($sp)
					lwc1 $f0, ($t0)	#put first operand into f0
					lwc1 $f2, -4($t0)	#put second operand into f2
					cvt.s.w $f0, $f0	#cast to float
					sub.s $f0, $f0, $f2
					swc1 $f0, ($t0)	#put return value on stack
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
					#ArrayVarNode
						#LiteralNode
						lw $t0, ($sp)	#put stack head pointer in t0
						li $t1, 9	#The actual literal value
						sw $t1, ($t0)	#put value on stack
						#putting ptr to arr's function call in V0
						move $v0, $sp
						addi $v0, $v0, 40	#offset to variable
					lw $t0, ($sp)	#load stack head pointer
					lw $t1, ($t0)	#put index in t0
					subi $t1, $t1, 3	#treat as 0 based indexing
					bltz $t1, ARRAY_OUT_OF_BOUNDS_ERROR
					bge $t1, 7, ARRAY_OUT_OF_BOUNDS_ERROR
					sll $t1, $t1, 2	#covert to bytes
					add $t1, $t1, $v0	#offset in array
					lw $t1, ($t1)
					sw $t1, ($t0)
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
					#LiteralNode
					lw $t0, ($sp)	#put stack head pointer in t0
					li $t1, 1051206667	#The actual literal value
					sw $t1, ($t0)	#put value on stack
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
				jal test
				lw $t0, ($sp)	#get ptr to return value from function (stack head)
				lw $t1, ($t0)	#get return value from function
				addi $t0, $t0, 16	#pop stack head back to where it shoudl be
				sw $t0, ($sp)	#put stackhead back to it's rightful value
				sw $t1, ($t0)	#put return value from function there
			lw $t0, ($sp)
			addi $t0, $t0, 4
			sw $t0, ($sp)
			lwc1 $f0, ($t0)	#put first operand into f0
			lwc1 $f2, -4($t0)	#put second operand into f2
			cvt.s.w $f0, $f0	#cast to float
			sub.s $f0, $f0, $f2
			swc1 $f0, ($t0)	#put return value on stack
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#AssignArrayVarNode
				#LiteralNode
				lw $t0, ($sp)	#put stack head pointer in t0
				li $t1, 3	#The actual literal value
				sw $t1, ($t0)	#put value on stack
			#putting ptr to arr's function call in V0
			move $v0, $sp
			addi $v0, $v0, 40	#offset to variable
			lw $t0, ($sp)	#load stack head pointer
			lw $t1, ($t0)	#put index in t0
			subi $t1, $t1, 3	#treat as 0 based indexing
			bltz $t1, ARRAY_OUT_OF_BOUNDS_ERROR
			bge $t1, 7, ARRAY_OUT_OF_BOUNDS_ERROR
			sll $t1, $t1, 2	#covert to bytes
			add $t1, $t1, $v0	#offset in array
			sw $t1, ($t0)	#save ptr
		lw $t0, ($sp)	#has stack head
		lw $t1, ($t0)	#has ptr to assign to
		lw $t2, 4($t0)	#has value to assign
		sw $t2, ($t1)	#make assignment
		addi $t0, $t0, 4
		sw $t0, ($sp)	#pop off stack values
		#VariableAssignmentStatementNode
			#LiteralNode
			lw $t0, ($sp)	#put stack head pointer in t0
			li $t1, 1093224366	#The actual literal value
			sw $t1, ($t0)	#put value on stack
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#AssignArrayVarNode
				#BinaryOperationNode
					#AccessVariableNode
					#putting ptr to fee's function call in V0
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
					li $t1, 9	#The actual literal value
					sw $t1, ($t0)	#put value on stack
				lw $t0, ($sp)
				addi $t0, $t0, 4
				sw $t0, ($sp)
				lw $t1, ($t0)	#put first operand in t1
				lw $t2, -4($t0)	#put second operand in t1
				sub $t1, $t1, $t2
				sw $t1, ($t0)	#save computation
			#putting ptr to arr's function call in V0
			move $v0, $sp
			addi $v0, $v0, 40	#offset to variable
			lw $t0, ($sp)	#load stack head pointer
			lw $t1, ($t0)	#put index in t0
			subi $t1, $t1, 3	#treat as 0 based indexing
			bltz $t1, ARRAY_OUT_OF_BOUNDS_ERROR
			bge $t1, 7, ARRAY_OUT_OF_BOUNDS_ERROR
			sll $t1, $t1, 2	#covert to bytes
			add $t1, $t1, $v0	#offset in array
			sw $t1, ($t0)	#save ptr
		lw $t0, ($sp)	#has stack head
		lw $t1, ($t0)	#has ptr to assign to
		lw $t2, 4($t0)	#has value to assign
		sw $t2, ($t1)	#make assignment
		addi $t0, $t0, 4
		sw $t0, ($sp)	#pop off stack values
		#VariableAssignmentStatementNode
			#LiteralNode
			lw $t0, ($sp)	#put stack head pointer in t0
			li $t1, 1084227584	#The actual literal value
			sw $t1, ($t0)	#put value on stack
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#AssignArrayVarNode
				#LiteralNode
				lw $t0, ($sp)	#put stack head pointer in t0
				li $t1, 9	#The actual literal value
				sw $t1, ($t0)	#put value on stack
			#putting ptr to arr's function call in V0
			move $v0, $sp
			addi $v0, $v0, 40	#offset to variable
			lw $t0, ($sp)	#load stack head pointer
			lw $t1, ($t0)	#put index in t0
			subi $t1, $t1, 3	#treat as 0 based indexing
			bltz $t1, ARRAY_OUT_OF_BOUNDS_ERROR
			bge $t1, 7, ARRAY_OUT_OF_BOUNDS_ERROR
			sll $t1, $t1, 2	#covert to bytes
			add $t1, $t1, $v0	#offset in array
			sw $t1, ($t0)	#save ptr
		lw $t0, ($sp)	#has stack head
		lw $t1, ($t0)	#has ptr to assign to
		lw $t2, 4($t0)	#has value to assign
		sw $t2, ($t1)	#make assignment
		addi $t0, $t0, 4
		sw $t0, ($sp)	#pop off stack values
		#ProcedureStatementNode
			#BinaryOperationNode
				#AccessVariableNode
				#putting ptr to fee's function call in V0
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
				li $t1, 1065353216	#The actual literal value
				sw $t1, ($t0)	#put value on stack
			lw $t0, ($sp)
			addi $t0, $t0, 4
			sw $t0, ($sp)
			lwc1 $f0, ($t0)	#put first operand into f0
			lwc1 $f2, -4($t0)	#put second operand into f2
			cvt.s.w $f0, $f0	#cast to float
			mul.s $f0, $f0, $f2
			swc1 $f0, ($t0)	#put return value on stack
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#BinaryOperationNode
				#AccessVariableNode
				#putting ptr to fi's function call in V0
				move $v0, $sp
				addi $v0, $v0, 28	#offset to variable
				lw $t0, ($v0)
				lw $t1, ($sp)
				sw $t0, ($t1)
			lw $t0, ($sp)
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#LiteralNode
				lw $t0, ($sp)	#put stack head pointer in t0
				li $t1, 1036831949	#The actual literal value
				sw $t1, ($t0)	#put value on stack
			lw $t0, ($sp)
			addi $t0, $t0, 4
			sw $t0, ($sp)
			lwc1 $f0, ($t0)	#put first operand into f0
			lwc1 $f2, -4($t0)	#put second operand into f2
			cvt.s.w $f0, $f0	#cast to float
			mul.s $f0, $f0, $f2
			swc1 $f0, ($t0)	#put return value on stack
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#ArrayVarNode
				#LiteralNode
				lw $t0, ($sp)	#put stack head pointer in t0
				li $t1, 9	#The actual literal value
				sw $t1, ($t0)	#put value on stack
				#putting ptr to arr's function call in V0
				move $v0, $sp
				addi $v0, $v0, 40	#offset to variable
			lw $t0, ($sp)	#load stack head pointer
			lw $t1, ($t0)	#put index in t0
			subi $t1, $t1, 3	#treat as 0 based indexing
			bltz $t1, ARRAY_OUT_OF_BOUNDS_ERROR
			bge $t1, 7, ARRAY_OUT_OF_BOUNDS_ERROR
			sll $t1, $t1, 2	#covert to bytes
			add $t1, $t1, $v0	#offset in array
			lw $t1, ($t1)
			sw $t1, ($t0)
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
		jal proc
		lw $t0, ($sp)
		addi $t0, $t0, 12
		sw $t0, ($sp)
		#VariableAssignmentStatementNode
			#LiteralNode
			lw $t0, ($sp)	#put stack head pointer in t0
			li $t1, 5	#The actual literal value
			sw $t1, ($t0)	#put value on stack
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#AssignVariableNode			#putting ptr to fi's function call in V0
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
		#VariableAssignmentStatementNode
			#BinaryOperationNode
				#BinaryOperationNode
					#LiteralNode
					lw $t0, ($sp)	#put stack head pointer in t0
					li $t1, 3	#The actual literal value
					sw $t1, ($t0)	#put value on stack
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
					#AccessVariableNode
					#putting ptr to fee's function call in V0
					move $v0, $sp
					addi $v0, $v0, 24	#offset to variable
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
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#BinaryOperationNode
					#AccessVariableNode
					#putting ptr to fi's function call in V0
					move $v0, $sp
					addi $v0, $v0, 28	#offset to variable
					lw $t0, ($v0)
					lw $t1, ($sp)
					sw $t0, ($t1)
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
					#LiteralNode
					lw $t0, ($sp)	#put stack head pointer in t0
					li $t1, 23	#The actual literal value
					sw $t1, ($t0)	#put value on stack
				lw $t0, ($sp)
				addi $t0, $t0, 4
				sw $t0, ($sp)
				lw $t1, ($t0)	#put first operand in t1
				lw $t2, -4($t0)	#put second operand in t1
				add $t1, $t1, $t2
				sw $t1, ($t0)	#save computation
			lw $t0, ($sp)
			addi $t0, $t0, 4
			sw $t0, ($sp)
			lw $t1, ($t0)	#put first operand in t1
			lw $t2, -4($t0)	#put second operand in t1
			add $t1, $t1, $t2
			sw $t1, ($t0)	#save computation
		lw $t0, ($sp)
		addi $t0, $t0, -4
		sw $t0, ($sp)
			#AssignVariableNode			#putting ptr to fo's function call in V0
			move $v0, $sp
			addi $v0, $v0, 32	#offset to variable
			lw $t0, ($sp)
			sw $v0, ($t0)	#save ptr to stack
		lw $t0, ($sp)	#has stack head
		lw $t1, ($t0)	#has ptr to assign to
		lw $t2, 4($t0)	#has value to assign
		sw $t2, ($t1)	#make assignment
		addi $t0, $t0, 4
		sw $t0, ($sp)	#pop off stack values
		#IfStatementNode
			#BinaryOperationNode
				#AccessVariableNode
				#putting ptr to fo's function call in V0
				move $v0, $sp
				addi $v0, $v0, 32	#offset to variable
				lw $t0, ($v0)
				lw $t1, ($sp)
				sw $t0, ($t1)
			lw $t0, ($sp)
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#LiteralNode
				lw $t0, ($sp)	#put stack head pointer in t0
				li $t1, 13	#The actual literal value
				sw $t1, ($t0)	#put value on stack
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
				lw $t0, ($sp)	#put stack head pointer in t0
				li $t1, 13	#The actual literal value
				sw $t1, ($t0)	#put value on stack
			lw $t0, ($sp)
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#AssignVariableNode				#putting ptr to fo's function call in V0
				move $v0, $sp
				addi $v0, $v0, 32	#offset to variable
				lw $t0, ($sp)
				sw $v0, ($t0)	#save ptr to stack
			lw $t0, ($sp)	#has stack head
			lw $t1, ($t0)	#has ptr to assign to
			lw $t2, 4($t0)	#has value to assign
			sw $t2, ($t1)	#make assignment
			addi $t0, $t0, 4
			sw $t0, ($sp)	#pop off stack values
		j SkipFalseStatement
		GoToFalseStatement:
			#VariableAssignmentStatementNode
				#LiteralNode
				lw $t0, ($sp)	#put stack head pointer in t0
				li $t1, 26	#The actual literal value
				sw $t1, ($t0)	#put value on stack
			lw $t0, ($sp)
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#AssignVariableNode				#putting ptr to fo's function call in V0
				move $v0, $sp
				addi $v0, $v0, 32	#offset to variable
				lw $t0, ($sp)
				sw $v0, ($t0)	#save ptr to stack
			lw $t0, ($sp)	#has stack head
			lw $t1, ($t0)	#has ptr to assign to
			lw $t2, 4($t0)	#has value to assign
			sw $t2, ($t1)	#make assignment
			addi $t0, $t0, 4
			sw $t0, ($sp)	#pop off stack values
		SkipFalseStatement:
		#WhileLoopNode
		WhileCondition:
			#BinaryOperationNode
				#AccessVariableNode
				#putting ptr to fo's function call in V0
				move $v0, $sp
				addi $v0, $v0, 32	#offset to variable
				lw $t0, ($v0)
				lw $t1, ($sp)
				sw $t0, ($t1)
			lw $t0, ($sp)
			addi $t0, $t0, -4
			sw $t0, ($sp)
				#LiteralNode
				lw $t0, ($sp)	#put stack head pointer in t0
				li $t1, 0	#The actual literal value
				sw $t1, ($t0)	#put value on stack
			lw $t0, ($sp)
			addi $t0, $t0, 4
			sw $t0, ($sp)
			lw $t1, ($t0)	#put first operand in t1
			lw $t2, -4($t0)	#put second operand in t1
			sgt $t1, $t1, $t2
			sw $t1, ($t0)	#save computation
		lw $t0, ($sp)	#get stack head ptr
		lw $t1, ($t0)	#get result off stack
		beqz $t1, EndOfWhileLoop	#skip if evaluates to 0
			#CompoundStatementNode
				#VariableAssignmentStatementNode
					#BinaryOperationNode
						#AccessVariableNode
						#putting ptr to fo's function call in V0
						move $v0, $sp
						addi $v0, $v0, 32	#offset to variable
						lw $t0, ($v0)
						lw $t1, ($sp)
						sw $t0, ($t1)
					lw $t0, ($sp)
					addi $t0, $t0, -4
					sw $t0, ($sp)
						#LiteralNode
						lw $t0, ($sp)	#put stack head pointer in t0
						li $t1, 5	#The actual literal value
						sw $t1, ($t0)	#put value on stack
					lw $t0, ($sp)
					addi $t0, $t0, 4
					sw $t0, ($sp)
					lw $t1, ($t0)	#put first operand in t1
					lw $t2, -4($t0)	#put second operand in t1
					add $t1, $t1, $t2
					sw $t1, ($t0)	#save computation
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
					#AssignVariableNode					#putting ptr to fo's function call in V0
					move $v0, $sp
					addi $v0, $v0, 32	#offset to variable
					lw $t0, ($sp)
					sw $v0, ($t0)	#save ptr to stack
				lw $t0, ($sp)	#has stack head
				lw $t1, ($t0)	#has ptr to assign to
				lw $t2, 4($t0)	#has value to assign
				sw $t2, ($t1)	#make assignment
				addi $t0, $t0, 4
				sw $t0, ($sp)	#pop off stack values
		j WhileCondition	#jump to evaluate condition again
		EndOfWhileLoop:
li $v0, 10
syscall
	#SubProgramDeclarationsNode
		#SubProgramNode
		test:
			#DeclarationsNode
			move $t0, $sp	#saves stack pointer for later useage
			lw $sp, ($sp)	#move stack pointer to the head of the stack
			addi $sp, $sp, -52	#move stack pointer for function start
			addi $s0, $sp, -4
			sw $s0, ($sp)	#set up stack pointer for new function
			sw $ra, 4($sp)	#save return address
			sw $t0, 8($sp)	#save old stack pointer
			
			#search for one level up function
			li $t2, 0	#load current level - 1 (what we are searching for)
			lw $t1, 16($t0)	#load previous function's level
			beq $t1, $t2, _t
			_:
			lw $t0, 12($t0)	#go one level up
			lw $t1, 16($t0)	#get level of function
			bne $t1, $t2, _
			_t:
			sw $t0, 12($sp)	#save one level up pointer
			li $t0, 1	#load function level to t0
			sw $t0, 16($sp)	#save scope level of this function
			#SubProgramHeadNode
			lw $t0, 8($sp)
			lw $t0, ($t0)
			lw $t1, 4($t0)	#get loaded value from stack
			sw $t1, 36($sp)	#save word in correct spot (hopefully?)
			lw $t1, 8($t0)	#get loaded value from stack
			sw $t1, 40($sp)	#save word in correct spot (hopefully?)
			lw $t1, 12($t0)	#get loaded value from stack
			sw $t1, 44($sp)	#save word in correct spot (hopefully?)
			lw $t1, 16($t0)	#get loaded value from stack
			sw $t1, 48($sp)	#save word in correct spot (hopefully?)
			#CompoundStatementNode
				#VariableAssignmentStatementNode
					#LiteralNode
					lw $t0, ($sp)	#put stack head pointer in t0
					li $t1, -6	#The actual literal value
					sw $t1, ($t0)	#put value on stack
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
					#AssignVariableNode					#putting ptr to greg's function call in V0
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
				#IfStatementNode
					#BinaryOperationNode
						#AccessVariableNode
						#putting ptr to greg's function call in V0
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
						li $t1, 10	#The actual literal value
						sw $t1, ($t0)	#put value on stack
					lw $t0, ($sp)
					addi $t0, $t0, 4
					sw $t0, ($sp)
					lw $t1, ($t0)	#put first operand in t1
					lw $t2, -4($t0)	#put second operand in t1
					sne $t1, $t1, $t2
					sw $t1, ($t0)	#save computation
				lw $t0, ($sp)
				lw $s0, ($t0)
				beqz $s0, GoToFalseStatementt
					#VariableAssignmentStatementNode
						#LiteralNode
						lw $t0, ($sp)	#put stack head pointer in t0
						li $t1, -4	#The actual literal value
						sw $t1, ($t0)	#put value on stack
					lw $t0, ($sp)
					addi $t0, $t0, -4
					sw $t0, ($sp)
						#AssignVariableNode						#putting ptr to w's function call in V0
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
				j SkipFalseStatementt
				GoToFalseStatementt:
					#VariableAssignmentStatementNode
						#LiteralNode
						lw $t0, ($sp)	#put stack head pointer in t0
						li $t1, 9	#The actual literal value
						sw $t1, ($t0)	#put value on stack
					lw $t0, ($sp)
					addi $t0, $t0, -4
					sw $t0, ($sp)
						#AssignVariableNode						#putting ptr to dorr's function call in V0
						move $v0, $sp
						addi $v0, $v0, 32	#offset to variable
						lw $t0, ($sp)
						sw $v0, ($t0)	#save ptr to stack
					lw $t0, ($sp)	#has stack head
					lw $t1, ($t0)	#has ptr to assign to
					lw $t2, 4($t0)	#has value to assign
					sw $t2, ($t1)	#make assignment
					addi $t0, $t0, 4
					sw $t0, ($sp)	#pop off stack values
				SkipFalseStatementt:
		lw $t0, 4($sp)	#put return address in t0
		lw $t1, 20($sp)	#put return value in t1
		lw $sp, 8($sp)	#put stack pointer back up
		lw $t2, ($sp)	#put t2 to stack head ptr in calling function
		sw $t1, ($t2)	#put return value on stack
		jr $t0
			#SubProgramDeclarationsNode
		#SubProgramNode
		proc:
			#DeclarationsNode
			move $t0, $sp	#saves stack pointer for later useage
			lw $sp, ($sp)	#move stack pointer to the head of the stack
			addi $sp, $sp, -52	#move stack pointer for function start
			addi $s0, $sp, -4
			sw $s0, ($sp)	#set up stack pointer for new function
			sw $ra, 4($sp)	#save return address
			sw $t0, 8($sp)	#save old stack pointer
			
			#search for one level up function
			li $t2, 0	#load current level - 1 (what we are searching for)
			lw $t1, 16($t0)	#load previous function's level
			beq $t1, $t2, _r
			_s:
			lw $t0, 12($t0)	#go one level up
			lw $t1, 16($t0)	#get level of function
			bne $t1, $t2, _s
			_r:
			sw $t0, 12($sp)	#save one level up pointer
			li $t0, 1	#load function level to t0
			sw $t0, 16($sp)	#save scope level of this function
			#SubProgramHeadNode
			lw $t0, 8($sp)
			lw $t0, ($t0)
			lw $t1, 4($t0)	#get loaded value from stack
			sw $t1, 40($sp)	#save word in correct spot (hopefully?)
			lw $t1, 8($t0)	#get loaded value from stack
			sw $t1, 44($sp)	#save word in correct spot (hopefully?)
			lw $t1, 12($t0)	#get loaded value from stack
			sw $t1, 48($sp)	#save word in correct spot (hopefully?)
			#CompoundStatementNode
				#VariableAssignmentStatementNode
					#FunctionExpressionNode
						#AccessVariableNode
						#putting ptr to three's function call in V0
						move $v0, $sp
						addi $v0, $v0, 32	#offset to variable
						lw $t0, ($v0)
						lw $t1, ($sp)
						sw $t0, ($t1)
					lw $t0, ($sp)
					addi $t0, $t0, -4
					sw $t0, ($sp)
						#AccessVariableNode
						#putting ptr to four's function call in V0
						move $v0, $sp
						addi $v0, $v0, 36	#offset to variable
						lw $t0, ($v0)
						lw $t1, ($sp)
						sw $t0, ($t1)
					lw $t0, ($sp)
					addi $t0, $t0, -4
					sw $t0, ($sp)
					jal proc_test2
					lw $t0, ($sp)	#get ptr to return value from function (stack head)
					lw $t1, ($t0)	#get return value from function
					addi $t0, $t0, 8	#pop stack head back to where it shoudl be
					sw $t0, ($sp)	#put stackhead back to it's rightful value
					sw $t1, ($t0)	#put return value from function there
				lw $t0, ($sp)
				addi $t0, $t0, -4
				sw $t0, ($sp)
					#AssignVariableNode					#putting ptr to one's function call in V0
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
		lw $t0, 4($sp)	#put return address in t0
		lw $t1, 20($sp)	#put return value in t1
		lw $sp, 8($sp)	#put stack pointer back up
		lw $t2, ($sp)	#put t2 to stack head ptr in calling function
		sw $t1, ($t2)	#put return value on stack
		jr $t0
			#SubProgramDeclarationsNode
				#SubProgramNode
				proc_test2:
					#DeclarationsNode
					move $t0, $sp	#saves stack pointer for later useage
					lw $sp, ($sp)	#move stack pointer to the head of the stack
					addi $sp, $sp, -44	#move stack pointer for function start
					addi $s0, $sp, -4
					sw $s0, ($sp)	#set up stack pointer for new function
					sw $ra, 4($sp)	#save return address
					sw $t0, 8($sp)	#save old stack pointer
					
					#search for one level up function
					li $t2, 1	#load current level - 1 (what we are searching for)
					lw $t1, 16($t0)	#load previous function's level
					beq $t1, $t2, _o
					_p:
					lw $t0, 12($t0)	#go one level up
					lw $t1, 16($t0)	#get level of function
					bne $t1, $t2, _p
					_o:
					sw $t0, 12($sp)	#save one level up pointer
					li $t0, 2	#load function level to t0
					sw $t0, 16($sp)	#save scope level of this function
					#SubProgramHeadNode
					lw $t0, 8($sp)
					lw $t0, ($t0)
					lw $t1, 4($t0)	#get loaded value from stack
					sw $t1, 36($sp)	#save word in correct spot (hopefully?)
					lw $t1, 8($t0)	#get loaded value from stack
					sw $t1, 40($sp)	#save word in correct spot (hopefully?)
					#CompoundStatementNode
						#VariableAssignmentStatementNode
							#LiteralNode
							lw $t0, ($sp)	#put stack head pointer in t0
							li $t1, -6	#The actual literal value
							sw $t1, ($t0)	#put value on stack
						lw $t0, ($sp)
						addi $t0, $t0, -4
						sw $t0, ($sp)
							#AssignVariableNode							#putting ptr to greg's function call in V0
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
						#IfStatementNode
							#BinaryOperationNode
								#AccessVariableNode
								#putting ptr to greg's function call in V0
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
								li $t1, 10	#The actual literal value
								sw $t1, ($t0)	#put value on stack
							lw $t0, ($sp)
							addi $t0, $t0, 4
							sw $t0, ($sp)
							lw $t1, ($t0)	#put first operand in t1
							lw $t2, -4($t0)	#put second operand in t1
							sne $t1, $t1, $t2
							sw $t1, ($t0)	#save computation
						lw $t0, ($sp)
						lw $s0, ($t0)
						beqz $s0, GoToFalseStatements
							#VariableAssignmentStatementNode
								#LiteralNode
								lw $t0, ($sp)	#put stack head pointer in t0
								li $t1, 16	#The actual literal value
								sw $t1, ($t0)	#put value on stack
							lw $t0, ($sp)
							addi $t0, $t0, -4
							sw $t0, ($sp)
								#AssignVariableNode								#putting ptr to w's function call in V0
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
						j SkipFalseStatements
						GoToFalseStatements:
							#VariableAssignmentStatementNode
								#LiteralNode
								lw $t0, ($sp)	#put stack head pointer in t0
								li $t1, 9	#The actual literal value
								sw $t1, ($t0)	#put value on stack
							lw $t0, ($sp)
							addi $t0, $t0, -4
							sw $t0, ($sp)
								#AssignVariableNode								#putting ptr to dorr's function call in V0
								move $v0, $sp
								addi $v0, $v0, 32	#offset to variable
								lw $t0, ($sp)
								sw $v0, ($t0)	#save ptr to stack
							lw $t0, ($sp)	#has stack head
							lw $t1, ($t0)	#has ptr to assign to
							lw $t2, 4($t0)	#has value to assign
							sw $t2, ($t1)	#make assignment
							addi $t0, $t0, 4
							sw $t0, ($sp)	#pop off stack values
						SkipFalseStatements:
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

