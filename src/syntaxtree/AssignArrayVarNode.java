/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;
import symboltable.Scope.IdentifierKind;

/**
 *
 * @author bendy
 */
public class AssignArrayVarNode extends AssignVariableNode {

	private ExpressionNode index;

	/**
	 * Constructor, checks for errors
	 *
	 * @param variable
	 * @param index expression to evaluate to find index in array
	 * @param currentScope
	 * @throws Exception
	 */
	public AssignArrayVarNode(String variable, ExpressionNode index, Scope currentScope) throws Exception {
		super(variable, currentScope.getType(variable));
		if (currentScope.getKind(variable) != IdentifierKind.ARR) {
			throw new Exception(variable + " is meant to be an ARRAY");
		}
		if (TokenType.INTEGER != index.getType()) {
			throw new Exception("expression and array type mismatch");
		}
		this.index = index;
	}

	@Override
	public String toMips(Scope symbolTable, String indent) {
		StringBuilder output = new StringBuilder(indent).append("#AssignArrayVarNode\n");

		//evaluate index
		output.append(index.toMips(symbolTable, indent + '\t'));

		//get ptr to array's scope in v0
		output.append(IPublicName.getVarPtrInV0(symbolTable, getName(), indent + '\t'));

		//get 0 based index
		output.append(indent).append("lw $t0, ($sp)\t#load stack head pointer\n");	//load stack head pointer
		output.append(indent).append("lw $t1, ($t0)\t#put index in t0\n");	//load the index into t1
		output.append(indent).append("subi $t1, $t1, ").append(symbolTable.getStartIndex(getName())).append("\t#treat as 0 based indexing\n");

		//check for out of bounds
		output.append(indent).append("bltz $t1, ").append(Constant.ARRAY_OUT_OF_BOUNDS_LABEL).append("\n");
		output.append(indent).append("bge $t1, ").append(symbolTable.getArrayLength(getName())).append(", ").append(Constant.ARRAY_OUT_OF_BOUNDS_LABEL).append("\n");

		//put ptr on the stack
		output.append(indent).append("sll $t1, $t1, 2\t#covert to bytes\n");	//multiply by 4 by bitshifting
		output.append(indent).append("add $t1, $t1, $v0\t#offset in array\n");	//put ptr to actual value in t1 (t1 had byte offset from ptr, and v0 had array ptr)
		output.append(indent).append("sw $t1, ($t0)\t#save ptr\n");

		return output.toString();
	}

}
