package syntaxtree;

import scanner.TokenType;

/**
 * Created by Benji on 4/15/2017.
 */
public class RealLiteralNode extends LiteralNode {

	private final float value;

	public RealLiteralNode(float value) {
		super(TokenType.REAL);
		this.value = value;
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
	 * gets value of node
	 * @return node's value
	 */
	public float getValue() {
		return value;
	}
}
