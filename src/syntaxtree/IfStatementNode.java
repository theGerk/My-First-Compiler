
package syntaxtree;

import scanner.TokenType;

/**
 * Represents an if statement in Pascal.
 * An if statement includes a boolean expression, and two statements.
 * @author Erik Steinmetz
 */
public class IfStatementNode extends StatementNode {
    private final ExpressionNode test;
    private final StatementNode thenStatement;
    private final StatementNode elseStatement;
	
	public IfStatementNode(ExpressionNode condition, StatementNode onTrue, StatementNode onFalse) throws Exception {
		if(condition.getType() != TokenType.INTEGER)
			throw new Exception("Conditionals must resolve to an integer");
		test = condition;
		thenStatement = onTrue;
		elseStatement = onFalse;
	}
	
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Assignment\n";
        answer += this.test.indentedToString( level + 1);
        answer += this.thenStatement.indentedToString( level + 1);
        answer += this.elseStatement.indentedToString( level + 1);
        return answer;
    }

}
