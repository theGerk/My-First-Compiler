package syntaxtree;

/**
 * Created by Benji on 4/15/2017.
 */
public abstract class AssignmentStatementNodeBase extends StatementNode implements IPublicName {

	protected final ExpressionNode assign;

	protected AssignmentStatementNodeBase(ExpressionNode expression) {
		assign = expression;
	}
}
