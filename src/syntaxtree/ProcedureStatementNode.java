/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import java.util.ArrayList;
import scanner.TokenType;
import symboltable.Scope;

/**
 *
 * @author Benji
 */
public class ProcedureStatementNode extends StatementNode implements IPublicName {

	private final String id;
	protected ArrayList<ExpressionNode> parametersList;

	/**
	 *
	 * @param name identifier
	 * @param arguments actual arguments being passed\
	 * @param containingScope current scope, will not be modified
	 *
	 * @throws Exception when expected types and arguments don't match up
	 */
	public ProcedureStatementNode(String name, ArrayList<ExpressionNode> arguments, Scope containingScope) throws Exception {
		if (containingScope.getKind(name) != Scope.IdentifierKind.FUNC) {
			throw new Exception("identifier is not a function");
		}
		id = name;
		ArrayList<TokenType> expectedTypes = containingScope.getArgsTypes(id);
		if (arguments.size() != expectedTypes.size()) {
			throw new Exception("incorrect number of arguments");
		}
		for (int i = 0; i < arguments.size(); i++) {
			if (arguments.get(i).getType() != expectedTypes.get(i)) {
				throw new Exception("incorrect argument " + i + "th argument");
			}
		}
		parametersList = arguments;
	}

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 *
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		StringBuilder output = new StringBuilder(indentation(level)).append(id).append('\n');
		for (ExpressionNode arg : parametersList) {
			arg.indentedToString(level + 1);
		}
		return output.toString();
	}

	/**
	 * getter for a identifier string
	 *
	 * @return the identifier or name for a node
	 */
	@Override
	public String getName() {
		return id;
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#ProcedureStatementNode\n");

		//for each expression push it's evaluation onto the stack
		for (int i = 0; i < parametersList.size(); i++) {
			build.append(parametersList.get(i).toMips(symbolTable, indent + '\t'));
			build.append(indent).append("lw $t0, ($sp)\n");
			build.append(indent).append("addi $t0, $t0, -4\n");
			build.append(indent).append("sw $t0, ($sp)\n");
		}

		//call function
		build.append(indent).append("jal ").append(symbolTable.getLabel(getName())).append("\n");

		//push stack back to begining of evaluation
		if (parametersList.size() > 0) {
			build.append(indent).append("lw $t0, ($sp)\n");
			build.append(indent).append("addi $t0, $t0, ").append(parametersList.size() * 4).append("\n");
			build.append(indent).append("sw $t0, ($sp)\n");
		}

		return build.toString();
	}
}
