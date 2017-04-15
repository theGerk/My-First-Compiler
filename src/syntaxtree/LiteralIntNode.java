package syntaxtree;

import scanner.TokenType;

/**
 * Created by Benji on 4/15/2017.
 */
public class LiteralIntNode extends ValueNode {
	private final int value;

	public LiteralIntNode(int input) {
		super(TokenType.INTEGER);
		value = input;
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
}
