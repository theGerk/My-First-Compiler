package syntaxtree;

import scanner.TokenType;

/**
 * Created by Benji on 4/15/2017.
 */
public class IntLiteralNode extends LiteralNode {

	private final int value;

	/**
	 * sets a literal with an integer value
	 *
	 * @param input value of literal
	 */
	public IntLiteralNode(int input) {
		super(TokenType.INTEGER);
		value = input;
	}

	/**
	 * sets a literal with a boolean value
	 *
	 * @param input value of literal
	 */
	public IntLiteralNode(boolean input) {
		super(TokenType.INTEGER);
		value = input ? 1 : 0;
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
		return indentation(level) + TokenType.INTEGER + " : " + value + '\n';
	}

	/**
	 * gets value associated with node
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * gets raw integer value of the literal
	 *
	 * @return value
	 */
	@Override
	int getRawInt() {
		return value;
	}
}
