package syntaxtree;

import symboltable.Scope;

/**
 * The base class for all nodes in a syntax tree.
 *
 * @author Erik Steinmetz
 */
public abstract class SyntaxTreeBase {

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	public abstract String indentedToString(int level);

	/**
	 * Creates an indentation String for the indentedToString.
	 *
	 * @param level The amount of indentation.
	 * @return A String displaying the given amount of indentation.
	 */
	protected String indentation(int level) {
		String answer = "";
		if (level > 0) {
			answer = "|-- ";
		}
		for (int indent = 1; indent < level; indent++) {
			answer += "--- ";
		}
		return (answer);
	}
	
	/**
	 * Writes component into assembly with tabbing for readability and sets up symbol table where needed.
	 * @param symbolTable the current scope
	 * @param indent tabs
	 * @return Mips assembly
	 */
	protected abstract String toMips(Scope symbolTable, String indent);

}
