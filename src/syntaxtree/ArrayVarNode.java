package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Created by Benji on 4/12/2017.
 */
public class ArrayVarNode extends VariableNode {

	/**
	 * Creates a String representation of this node and its children.
	 *
	 * @param level The tree level at which this node resides.
	 *
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		return indentation(level) + name + "Array at\n" + index.indentedToString(level + 1);
	}

	/**
	 * Constructor, validates types and kinds
	 *
	 * @param id identifier name
	 * @param expr expression for index
	 * @param containingScope scope which id exists in
	 *
	 * @throws Exception if the identifier is for an not an array
	 */
	public ArrayVarNode(String id, ExpressionNode expr, Scope containingScope) throws Exception {
		super(id, validateID(id, containingScope));
		if (expr.getType() != TokenType.INTEGER) {
			throw new Exception("Need integer type for array index");
		}
		index = expr;
	}

	/**
	 * Used so as to not do any checking
	 *
	 * @param id the identifier name
	 * @param expr the expression to be resolved to determine index
	 * @param type the type the node would return
	 */
	protected ArrayVarNode(String id, ExpressionNode expr, TokenType type) {
		super(id, type);
		index = expr;
	}

	private static TokenType validateID(String id, Scope scope) throws Exception {
		if (scope.getKind(id) == Scope.IdentifierKind.ARR) {
			return scope.getType(id);
		} else {
			throw new Exception("Expected " + id + " to be an ARRAY");
		}
	}

	private final ExpressionNode index;

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder output = new StringBuilder(indent).append("#ArrayVarNode\n");

		//evaluate index
		output.append(index.toMips(symbolTable, indent + '\t'));

		//get ptr to array's scope in v0
		output.append(IPublicName.getVarPtrInV0(symbolTable, name, indent + '\t'));

		//get 0 based index
		output.append(indent).append("lw $t0, ($sp)\t#load stack head pointer\n");	//load stack head pointer
		output.append(indent).append("lw $t1, ($t0)\t#put index in t0");	//load the index into t1
		output.append(indent).append("subi $t1, $t1, ").append(symbolTable.getStartIndex(getName())).append("\t#treat as 0 based indexing\n");

		//check for out of bounds
		output.append(indent).append("bltz $t1, ").append(Constant.ARRAY_OUT_OF_BOUNDS_LABEL).append("\n");
		output.append(indent).append("bge $t1, ").append(symbolTable.getLength(getName())).append(", ").append(Constant.ARRAY_OUT_OF_BOUNDS_LABEL).append("\n");

		//put value on the stack
		output.append(indent).append("sll $t1, $t1, 2\t#covert to bytes\n");	//multiply by 4 by bitshifting
		output.append(indent).append("add $t1, $t1, $v0\t#offset in array\n");	//put ptr to actual value in t1 (t1 had byte offset from ptr, and v0 had array ptr)
		output.append(indent).append("lw $t1, ($t1)");
		output.append(indent).append("sw $t1, ($t0)");

		return output.toString();
	}
}
