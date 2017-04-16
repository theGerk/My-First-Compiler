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
	protected ArrayList<ExpressionNode> args;

	/**
	 *
	 * @param name identifier
	 * @param arguments actual arguments being passed\
	 * @param containingScope current scope, will not be modified
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
		args = arguments;
	}

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		StringBuilder output = new StringBuilder(indentation(level)).append(id).append('\n');
		for (ExpressionNode arg : args) {
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
}
