package syntaxtree;

/**
 * Represents a single assignment statement.
 *
 * @author Erik Steinmetz
 */
public class VariableAssignmentStatementNode extends AssignmentStatementNodeBase {

	protected final VariableNode variable;

	/**
	 * getter for a identifier string
	 *
	 * @return the identifier or name for a node
	 */
	@Override
	public String getName() {
		return variable.getName();
	}

	public VariableAssignmentStatementNode(VariableNode var, ExpressionNode expr) throws Exception {
		super(expr);
		if (var.getType() != expr.getType()) {
			throw new Exception(var.getName() + " can not be assigned a: " + expr.getType() + ", expects: " + var.getType());
		}
		variable = var;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Assignment\n";
		answer += this.variable.indentedToString(level + 1);
		answer += this.assign.indentedToString(level + 1);
		return answer;
	}
}
