package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Represents an if statement in Pascal. An if statement includes a boolean
 * expression, and two statements.
 *
 * @author Erik Steinmetz
 */
public class IfStatementNode extends StatementNode {

	private final ExpressionNode test;
	private final StatementNode thenStatement;
	private final StatementNode elseStatement;

	/**
	 * constructor
	 *
	 * @param condition expression resulting in boolean (integer)
	 * @param onTrue what to do if condition is true
	 * @param onFalse what to do if condition is false
	 * @throws Exception
	 */
	public IfStatementNode(ExpressionNode condition, StatementNode onTrue, StatementNode onFalse) throws Exception {
		if (condition.getType() != TokenType.INTEGER) {
			throw new Exception("Conditionals must resolve to an integer");
		}
		test = condition;
		thenStatement = onTrue;
		elseStatement = onFalse;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "IF STATEMENT\n";
		answer += this.test.indentedToString(level + 1);
		answer += this.thenStatement.indentedToString(level + 1);
		answer += this.elseStatement.indentedToString(level + 1);
		return answer;
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#IfStatementNode\n");

		String jumpToFalse = Scope.labelGenerator.getId("GoToFalseStatement");
		String jumpToEnd = Scope.labelGenerator.getId("SkipFalseStatement");

		//evaluate expression
		build.append(test.toMips(symbolTable, indent + '\t'));

		//load value into s0
		build.append(indent).append("lw $t0, ($sp)\n");
		build.append(indent).append("lw $s0, ($t0)\n");

		//branch and insert statements
		build.append(indent).append("beqz $s0, ").append(jumpToFalse).append("\n");
		build.append(thenStatement.toMips(symbolTable, indent + '\t'));
		build.append(indent).append("j ").append(jumpToEnd).append("\n");
		build.append(indent).append(jumpToFalse).append(":\n");
		build.append(elseStatement.toMips(symbolTable, indent + '\t'));
		build.append(indent).append(jumpToEnd).append(":\n");

		return build.toString();
	}

}
