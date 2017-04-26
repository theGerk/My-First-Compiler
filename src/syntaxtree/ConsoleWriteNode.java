package syntaxtree;

import symboltable.Scope;

/**
 * Created by Benji on 4/13/2017.
 */
public class ConsoleWriteNode extends StatementNode {

	private final ExpressionNode expr;	//expression to write to console

	/**
	 * write line to console
	 *
	 * @param expression expression to be written
	 */
	public ConsoleWriteNode(ExpressionNode expression) {
		expr = expression;
	}

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 *
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		return indentation(level) + "write:\n" + expr.indentedToString(level + 1);
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#ConsoleWriteNode\n");
		build.append(expr.toMips(symbolTable, indent + '\t'));
		build.append(indent).append("lw $t0, ($sp)\n");
		switch (expr.getType()) {
			case INTEGER:
				build.append(indent).append("lw $a0, ($t0)\n");
				build.append(indent).append("li $v0, 1\n");
				build.append(indent).append("syscall\n");
				break;
			case REAL:
				build.append(indent).append("lwc1 $f12, ($t0)\n");
				build.append(indent).append("li $v0, 2\n");
				build.append(indent).append("syscall\n");
		}

		//append a new line
		build.append(indent).append("li $v0, 11\t#prepare syscall for print character\n");
		build.append(indent).append("li $a0, 10\t#put newline to be printed\n");
		build.append(indent).append("syscall\n");
		return build.toString();
	}
}
