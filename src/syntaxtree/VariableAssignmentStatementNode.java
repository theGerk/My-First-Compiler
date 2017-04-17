package syntaxtree;

import symboltable.Scope;

/**
 * Represents a single assignment statement.
 *
 * @author Erik Steinmetz
 */
public class VariableAssignmentStatementNode extends AssignmentStatementNodeBase {

	protected final String variable;

	/**
	 * getter for a identifier string
	 *
	 * @return the identifier or name for a node
	 */
	@Override
	public String getName() {
		return variable;
	}

	public VariableAssignmentStatementNode(VariableNode var, ExpressionNode expr) throws Exception {
		super(expr);
		if (var.getType() != expr.getType()) {
			throw new Exception(var.getName() + " can not be assigned a: " + expr.getType() + ", expects: " + var.getType());
		}
		variable = var.getName();
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Assignment\n";
		answer += this.indentation(level + 1) + this.variable;
		answer += this.assign.indentedToString(level + 1);
		return answer;
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		return indent + "#VariableAssignmentStatementNode\n"
				+ IPublicName.getVarPtrInV0(symbolTable, getName(), indent)
				//load from stack into where v0 is pointing
				+ indent + "lw $t0, ($sp)\t#put stack head in t0\n"
				+ indent + "lw $t1, ($t0)\t#pop last value on stack into t1\n"
				+ indent + "sw $t1, ($v0)\t#store value where it goes\n";
	}
}
