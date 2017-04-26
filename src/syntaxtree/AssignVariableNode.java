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
class AssignVariableNode extends SyntaxTreeBase implements IPublicName {

	String var;
	TokenType expectedType;

	/**
	 * constuctor
	 *
	 * @param variable variable name
	 * @param currentScope statement's scope
	 * @throws Exception
	 */
	public AssignVariableNode(String variable, Scope currentScope) throws Exception {
		var = variable;
		expectedType = currentScope.getType(variable);

		if (currentScope.getKind(variable) != IdentifierKind.VAR) {
			throw new Exception(variable + " is supposed to be of type VAR");
		}
	}

	/**
	 * for children to call
	 *
	 * @param variable variable's name
	 * @param expected type of variable
	 */
	protected AssignVariableNode(String variable, TokenType expected) {
		var = variable;
		expectedType = expected;
	}

	@Override
	public String indentedToString(int level) {
		return this.indentation(level) + "Assign Variable Node";
	}

	/**
	 * Should put a pointer to the variable on the stack
	 *
	 * @param symbolTable
	 * @param indent
	 * @return
	 */
	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#AssignVariableNode");

		//get var ptr
		build.append(IPublicName.getVarPtrInV0(symbolTable, var, indent));

		//put on stack
		build.append(indent).append("lw $t0, ($sp)\n");
		build.append(indent).append("sw $v0, ($t0)\t#save ptr to stack\n");

		return build.toString();
	}

	@Override
	public String getName() {
		return var;
	}

	TokenType getType() {
		return expectedType;
	}

}
