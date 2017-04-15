package syntaxtree;

import symboltable.Scope;

/**
 * Created by Benji on 4/14/2017.
 */
public class FunctionAssignmentStatementNode extends AssignmentStatementNodeBase {

	protected final String assignee;

	public FunctionAssignmentStatementNode(String id, ExpressionNode expression, Scope currentScope) throws Exception {
		super(expression);
		if(currentScope.getType(id) != expression.getType())
			throw new Exception(id + " must return something of type " + currentScope.getType(id) + " instead found " + expression.getType());
		assignee = id;
	}

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		return indentation(level) + assignee + " :=\n" + assign.indentedToString(level + 1);
	}
}
