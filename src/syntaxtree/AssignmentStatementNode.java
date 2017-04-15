package syntaxtree;

/**
 * Represents a single assignment statement.
 *
 * @author Erik Steinmetz
 */
public class AssignmentStatementNode extends StatementNode {

	private final VariableNode variable;
	private final ExpressionNode expression;

	public VariableNode getVariable() {
		return variable;
	}

	public ExpressionNode getExpression() {
		return expression;
	}

	public AssignmentStatementNode(VariableNode var, ExpressionNode expr) throws Exception {
		if (var.getType() != expr.getType()) {
			throw new Exception(var.getName() + " can not be assigned a "  + expr.getType() + " expects " + var.getType());
		}
		variable = var;
		expression = expr;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Assignment\n";
		answer += this.variable.indentedToString(level + 1);
		answer += this.expression.indentedToString(level + 1);
		return answer;
	}
}
