package syntaxtree;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import scanner.TokenType;
import symboltable.Scope;

/**
 * Created by Benji on 4/14/2017.
 */
public class SubProgramHeadNode extends SyntaxTreeNode {

	private final String name;
	private final DeclarationsNode arguments;

	public SubProgramHeadNode(String name, DeclarationsNode arguments) {
		this.name = name;
		this.arguments = arguments;
	}

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		return indentation(level) + name + " -> " + returnType
				+ indentation(level) + "Arguments:\n"
				+ arguments.indentedToString(level + 1);
	}

}
