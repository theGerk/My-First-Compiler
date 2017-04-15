package syntaxtree;

import scanner.TokenType;

/**
 * Created by Benji on 4/15/2017.
 */
public abstract class AssignmentStatementNodeBase extends StatementNode {

	protected final ExpressionNode assign;

	protected AssignmentStatementNodeBase(ExpressionNode expression) {
		assign = expression;
	}
}
