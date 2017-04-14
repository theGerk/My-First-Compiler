package syntaxtree;

import scanner.TokenType;

import java.util.ArrayList;

/**
 * Created by Benji on 4/12/2017.
 */
public class FunctionExpressionNode extends VariableNode {

	public FunctionExpressionNode(String id, TokenType returnType, ArrayList<ExpressionNode> params, ArrayList<TokenType> argTypes) throws Exception {
		super(id, returnType);
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

	protected final ArrayList<ExpressionNode> parametersList;

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
