package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Created by Benji on 4/15/2017.
 */
public class IntLiteralNode extends LiteralNode {

	private final int value;

	public IntLiteralNode(int input) {
		super(TokenType.INTEGER);
		value = input;
	}

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
		return indentation(level) + value;
	}

	/**
	 * gets value associated with node
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	@Override
	int getRawInt() {
		return value;
	}
}
