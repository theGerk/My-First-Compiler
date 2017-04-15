package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

import java.util.ArrayList;

/**
 * Created by Benji on 4/12/2017.
 */
public class FunctionExpressionNode extends ExpressionNode {

	public FunctionExpressionNode(String id, ArrayList<ExpressionNode> params, Scope containingScope) throws Exception {
		super(validateID(id, containingScope));
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
		name = id;
		parametersList = params;
	}

	protected FunctionExpressionNode(String id, ArrayList<ExpressionNode> params, TokenType type) {
		super(id, type);
		parametersList = params;
	}

	protected final String name;
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
}
