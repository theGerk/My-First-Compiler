package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

import java.util.ArrayList;

/**
 * Function as an expression (returns something)
 */
public class FunctionExpressionNode extends IdentifierNodeBase {

	/**
	 * Constructor for function as an expression
	 *
	 * @param id function name
	 * @param params arguments being passed in
	 * @param containingScope scope containing expression
	 * @throws Exception
	 */
	public FunctionExpressionNode(String id, ArrayList<ExpressionNode> params, Scope containingScope) throws Exception {
		super(id, validateID(id, containingScope));
		if (containingScope.getKind(id) != Scope.IdentifierKind.FUNC) {
			throw new Exception(id + " is not a function");
		}
		ArrayList<TokenType> argTypes = containingScope.getArgsTypes(id);
		if (params.size() != argTypes.size()) {
			throw new Exception("number of pramaters do not match");
		}
		for (int i = 0; i < params.size(); i++) {
			if (params.get(i).getType() != argTypes.get(i)) {
				throw new Exception("incorrect argument at index: " + i);
			}
		}
		parametersList = params;
	}

	/**
	 * bypasses error checking for children
	 *
	 * @param id name of function being called
	 * @param params argumetns to function
	 * @param type return type of function
	 */
	protected FunctionExpressionNode(String id, ArrayList<ExpressionNode> params, TokenType type) {
		super(id, type);
		parametersList = params;
	}

	protected final ArrayList<ExpressionNode> parametersList;

	private static TokenType validateID(String id, Scope scope) throws Exception {
		if (scope.getKind(id) == Scope.IdentifierKind.FUNC) {
			if (scope.getType(id) != null) {
				return scope.getType(id);
			} else {
				throw new Exception(id + " does not return");
			}
		} else {
			throw new Exception(id + " is not a FUNCTION");
		}
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
		StringBuilder output = new StringBuilder(indentation(level)).append("Function ").append(this.name).append('\n');
		for (ExpressionNode expressionNode : parametersList) {
			output.append(expressionNode.indentedToString(level + 1));
		}
		return output.toString();
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#FunctionExpressionNode\n");

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
			build.append(indent).append("lw $t1, ($t0)\n");	//retrive return value from function
			build.append(indent).append("addi $t0, $t0, ").append(parametersList.size() * 4).append("\n");
			build.append(indent).append("sw $t0, ($sp)\n");
			build.append(indent).append("lw $t1, ($t0)\n");	//store return value on stack
		}

		return build.toString();
	}
}
