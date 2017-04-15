package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Represents a variable in the syntax tree.
 *
 * @author Erik Steinmetz
 */
public class VariableNode extends IdentifierNodeBase {

	/**
	 * Creates a ValueNode with the given attribute.
	 *
	 * @param id The attribute for this value node.
	 */
	public VariableNode(String id, Scope containingScope) throws Exception {
		super(id,validateID(id, containingScope));
	}
	
	protected VariableNode(String id, TokenType type){
		super(id,type);
	}
	
	private static TokenType validateID(String id, Scope scope) throws Exception {
		if(scope.getKind(id) == Scope.IdentifierKind.VAR)
			return scope.getType(id);
		else
			throw new Exception("Expected " + id + " to be a VAR");
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
	public boolean isFoldable() {
		return false;
	}
}
