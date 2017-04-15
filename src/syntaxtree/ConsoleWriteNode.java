package syntaxtree;

/**
 * Created by Benji on 4/13/2017.
 */
public class ConsoleWriteNode extends StatementNode {
	private final ExpressionNode expr;	//expression to write to console
	
	public ConsoleWriteNode(ExpressionNode expression) {
		expr = expression;
	}
	
	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		return indentation(level) + "write:\n" + expr.indentedToString(level + 1);
	}
}
