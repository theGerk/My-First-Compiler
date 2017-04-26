package syntaxtree;

import scanner.TokenType;

import symboltable.Scope;

/**
 * Created by Benji on 4/13/2017.
 */
public class WhileLoopNode extends StatementNode {

	private final ExpressionNode condition;
	private final StatementNode instruction;

	/**
	 * constuctor.. blah blah blah
	 *
	 * @param cond condition on which to loop, evaluates to boolean (integer)
	 * @param statment statement is done on looping
	 * @throws Exception
	 */
	public WhileLoopNode(ExpressionNode cond, StatementNode statment) throws Exception {
		if (cond.getType() != TokenType.INTEGER) {
			throw new Exception("Need integer in condition");
		}

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

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#WhileLoopNode\n");

		final String whileCondition = Scope.labelGenerator.getId("WhileCondition");
		final String endOfLoop = Scope.labelGenerator.getId("EndOfWhileLoop");

		build.append(indent).append(whileCondition).append(":\n");
		build.append(condition.toMips(symbolTable, indent + '\t'));

		//get result and branch on not equal zero
		build.append(indent).append("lw $t0, ($sp)\t#get stack head ptr\n");
		build.append(indent).append("lw $t1, ($t0)\t#get result off stack\n");
		build.append(indent).append("beqz $t1, ").append(endOfLoop).append("\t#skip if evaluates to 0\n");

		//do contents
		build.append(instruction.toMips(symbolTable, indent + '\t'));
		build.append(indent).append("j ").append(whileCondition).append("\t#jump to evaluate condition again\n");
		build.append(indent).append(endOfLoop).append(":\n");

		return build.toString();
	}
}
