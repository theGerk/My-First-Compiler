package syntaxtree;

import symboltable.Scope;

/**
 * sets return statement for functions
 */
public class FunctionAssignmentStatementNode extends AssignmentStatementNodeBase {

	protected final String assignee;

	/**
	 * constructor
	 *
	 * @param id ID of function we are assigning to
	 * @param expression expression to assign to ID
	 * @param currentScope scope containing statement
	 * @throws Exception
	 */
	public FunctionAssignmentStatementNode(String id, ExpressionNode expression, Scope currentScope) throws Exception {
		super(expression);
		if (currentScope.getType(id) != expression.getType()) {
			throw new Exception(id + " must return something of type " + currentScope.getType(id) + " instead found " + expression.getType());
		}
		assignee = id;
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
		return indentation(level) + "ASSIGN RETURN\n" + indentation(level + 1) + getName() + '\n' + assign.indentedToString(level + 1);
	}

	/**
	 * getter for a identifier string
	 *
	 * @return the identifier or name for a node
	 */
	@Override
	public String getName() {
		return assignee;
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent + "#FunctionAssignmentStatementNode\n");

		//evaluate and put on stack
		build.append(assign.toMips(symbolTable, indent + '\t'));

		//get var ptr
		build.append(IPublicName.getVarPtrInV0(symbolTable, assignee, indent));

		//load from stack into where v0 is pointing
		build.append(indent).append("lw $t0, ($sp)\t#has stack head\n");
		build.append(indent).append("lw $t2, ($t0)\t#has value to assign\n");
		build.append(indent).append("sw $t2, ($v0)\t#make assignment\n");

		return build.toString();
	}
}
