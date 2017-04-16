package syntaxtree;

/**
 * Created by Benji on 4/15/2017.
 */
public abstract class AssignmentStatementNodeBase extends StatementNode implements IPublicName {	//TODO maybe make code gen

	protected final ExpressionNode assign;

	protected AssignmentStatementNodeBase(ExpressionNode expression) {
		assign = expression;
	}
}
