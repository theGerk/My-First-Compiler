package syntaxtree;

/**
 * base class ofr all asignment statements Created by Benji on 4/15/2017.
 */
public abstract class AssignmentStatementNodeBase extends StatementNode implements IPublicName {	//probably should have put some code gen up here

	protected final ExpressionNode assign;

	protected AssignmentStatementNodeBase(ExpressionNode expression) {
		assign = expression;
	}
}
