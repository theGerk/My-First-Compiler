package syntaxtree;

import symboltable.Scope;

/**
 * Represents a single assignment statement.
 *
 * @author Erik Steinmetz
 */
public class VariableAssignmentStatementNode extends AssignmentStatementNodeBase {

	protected final AssignVariableNode variable;

	/**
	 * getter for a identifier string
	 *
	 * @return the identifier or name for a node
	 */
	@Override
	public String getName() {
		return variable.getName();
	}

	/**
	 * constucts a variable assignment ndoe
	 *
	 * @param var variable being assigned to
	 * @param expr expression to evaluate to get value to assign to var
	 * @throws Exception
	 */
	public VariableAssignmentStatementNode(AssignVariableNode var, ExpressionNode expr) throws Exception {
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
		answer += variable.indentedToString(level + 1);
		answer += this.assign.indentedToString(level + 1);
		return answer;
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#VariableAssignmentStatementNode\n");

		//get expression evaluated and put on stack
		build.append(assign.toMips(symbolTable, indent + '\t'));

		//move stack to get ptr to variable
		build.append(indent).append("lw $t0, ($sp)\n");
		build.append(indent).append("addi $t0, $t0, -4\n");
		build.append(indent).append("sw $t0, ($sp)\n");

		//get ptr to put value on stack
		build.append(variable.toMips(symbolTable, indent + '\t'));

		//load from stack into where v0 is pointing
		build.append(indent).append("lw $t0, ($sp)\t#has stack head\n");
		build.append(indent).append("lw $t1, ($t0)\t#has ptr to assign to\n");
		build.append(indent).append("lw $t2, 4($t0)\t#has value to assign\n");
		build.append(indent).append("sw $t2, ($t1)\t#make assignment\n");

		//return stack to previous state
		build.append(indent).append("addi $t0, $t0, 4\n");
		build.append(indent).append("sw $t0, ($sp)\t#pop off stack values\n");

		return build.toString();
	}
}
