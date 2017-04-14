package syntaxtree;

import scanner.TokenType;

/**
 * Created by Benji on 4/12/2017.
 */
public class ArrayVarNode extends VariableNode {

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		return indentation(level) + "Array at\n" + index.indentedToString(level + 1);
	}

	public ArrayVarNode(String name, ExpressionNode expr, TokenType type) throws Exception {
		super(name, type);
		if (expr.getType() != TokenType.INTEGER) {
			throw new Exception("Need integer type for array index");
		}
		index = expr;
	}

	private final ExpressionNode index;
}
