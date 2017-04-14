package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Represents a variable in the syntax tree.
 *
 * @author Erik Steinmetz
 */
public class VariableNode extends ExpressionNode {

	/**
	 * The name of the variable associated with this node.
	 */
	String name;

	/**
	 * Creates a ValueNode with the given attribute.
	 *
	 * @param id The attribute for this value node.
	 */
	public VariableNode(String id, Scope containingScope) throws Exception {
		super(validateID(id, containingScope));
		this.name = id;
	}
	
	protected VariableNode(String id, TokenType type){
		super(type);
		name = id;
	}
	
	private static TokenType validateID(String id, Scope scope) throws Exception {
		if(scope.getKind(id) == Scope.IdentifierKind.VAR)
			return scope.getType(id);
		else
			throw new Exception("Expected " + id + " to be a VAR");
	}

	/**
	 * Returns the name of the variable of this node.
	 *
	 * @return The name of this VariableNode.
	 */
	public String getName() {
		return (this.name);
	}

	/**
	 * Returns the name of the variable as the description of this node.
	 *
	 * @return The attribute String of this node.
	 */
	@Override
	public String toString() {
		return (name);
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Name: " + this.name + "\n";
		return answer;
	}

	@Override
	public boolean equals(Object o) {
		boolean answer = false;
		if (o instanceof VariableNode) {
			VariableNode other = (VariableNode) o;
			if (this.name.equals(other.name)) {
				answer = true;
			}
		}
		return answer;
	}
	
	/**
	 * checks for if code folding is possible
	 *
	 * @return if the node can be folded
	 */
	@Override
	public boolean foldable() {
		return false;
	}
}
