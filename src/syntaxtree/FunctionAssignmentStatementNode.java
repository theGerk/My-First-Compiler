package syntaxtree;

import symboltable.Scope;

/**
 * Created by Benji on 4/14/2017.
 */
public class FunctionAssignmentStatementNode extends AssignmentStatementNodeBase {

	protected final String assignee;

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
		return indentation(level) + assignee + " :=\n" + assign.indentedToString(level + 1);
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
		return indent + "#FunctionAssignmentStatementNode\n"
				+ IPublicName.getFuncPtrInV0(symbolTable.getParent(), getName(), indent)
				//load from stack into function return
				+ indent + "lw $t0, ($sp)\t#put stack head in t0\n"
				+ indent + "lw $t1, ($t0)\t#pop last value on stack into t1\n"
				+ indent + "sw $t1, 20($v0)\t#store value in return slot\n";
	}
}
