package syntaxtree;

import scanner.TokenType;

import java.sql.Statement;

/**
 * Created by Benji on 4/13/2017.
 */
public class WhileLoopNode extends StatementNode {
	
	private final ExpressionNode condition;
	private final StatementNode instruction;
	
	public WhileLoopNode(ExpressionNode cond, StatementNode statment) throws Exception {
		if(cond.getType() != TokenType.INTEGER)
			throw new Exception("Need integer in condition");
		
		condition = cond;
		instruction = statment;
	}
	
	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		return null;
	}
}
