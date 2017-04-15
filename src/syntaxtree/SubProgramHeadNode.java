package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Created by Benji on 4/14/2017.
 */
public class SubProgramHeadNode extends SyntaxTreeNode {

	private final String name;
	private final DeclarationsNode arguments;
	private final TokenType returnType;

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		return indentation(level) +
	}

}
