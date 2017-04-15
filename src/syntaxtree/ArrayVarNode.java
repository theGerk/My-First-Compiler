package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Created by Benji on 4/12/2017.
 */
public class ArrayVarNode extends VariableNode {    //TODO implement code gen function

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		return indentation(level) + name + "Array at\n" + index.indentedToString(level + 1);
	}
	
	/**
	 * Constructor, validates types and kinds
	 * @param id identifier name
	 * @param expr expression for index
	 * @param containingScope scope which id exists in
	 * @throws Exception if the identifier is for an not an array
	 */
	public ArrayVarNode(String id, ExpressionNode expr, Scope containingScope) throws Exception {
		super(id, validateID(id, containingScope));
		if (expr.getType() != TokenType.INTEGER) {
			throw new Exception("Need integer type for array index");
		}
		index = expr;
	}
	
	/**
	 * Used so as to not do any checking
	 * @param id the identifier name
	 * @param expr the expression to be resolved to determine index
	 * @param type the type the node would return
	 */
	protected ArrayVarNode(String id, ExpressionNode expr, TokenType type){
		super(id, type);
		index = expr;
	}
	
	private static TokenType validateID(String id, Scope scope) throws Exception {
		if(scope.getKind(id)== Scope.IdentifierKind.ARR)
			return scope.getType(id);
		else
			throw new Exception("Expected " + id + " to be an ARRAY");
	}

	private final ExpressionNode index;
}
