package syntaxtree;

import scanner.TokenType;

/**
 * Created by Benji on 4/15/2017.
 */
public class RealLiteralNode extends LiteralNode {

	private final float value;

	/**
	 * constuctor for a floating point node
	 *
	 * @param input value of node
	 */
	public RealLiteralNode(float input) {
		super(TokenType.REAL);
		this.value = input;
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
	 *
	 * @return node's value
	 */
	public float getValue() {
		return value;
	}

	@Override
	int getRawInt() {
		return Float.floatToRawIntBits(value);
	}
}
