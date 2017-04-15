package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Created by Benji on 4/15/2017.
 */
public class LiteralRealNode extends ValueNode {

	private final float value;

	public LiteralRealNode(float value) {
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
}
