package syntaxtree;

import scanner.LookUp;
import scanner.TokenType;
import symboltable.Scope;

/**
 * Represents any operation in an expression.
 *
 * @author Erik Steinmetz
 */
public class BinaryOperationNode extends ExpressionNode {

	/**
	 * The left operator of this operation.
	 */
	private final ExpressionNode left;

	/**
	 * The right operator of this operation.
	 */
	private final ExpressionNode right;

	/**
	 * The kind of operation.
	 */
	private final TokenType operation;

	/**
	 * Creates an operation node given an operation token.
	 *
	 * @param op The token representing this node's math operation.
	 * @param left The left expression
	 * @param right The right expression
	 *
	 * @throws java.lang.Exception if inputs are invalid
	 */
	public BinaryOperationNode(ExpressionNode left, TokenType op, ExpressionNode right) throws Exception {
		super(outType(op, left.getType(), right.getType()));
		this.left = left;
		this.right = right;
		this.operation = op;
	}

	/**
	 * Returns the operation token as a String.
	 *
	 * @return The String version of the operation token.
	 */
	@Override
	public String toString() {
		return operation.toString();
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Operation: " + this.operation + "\n";
		answer += left.indentedToString(level + 1);
		answer += right.indentedToString(level + 1);
		return (answer);
	}

	/**
	 * throws if types aren't valid, otherwise returns the output type of the
	 * operation
	 *
	 * @param op operation
	 * @param left left expression return type
	 * @param right right expression return type
	 *
	 * @return operation output
	 */
	private static TokenType outType(TokenType op, TokenType left, TokenType right) throws Exception {
		Exception expt = new Exception(String.format(OUT_TYPE_EXCEPTION_FORMAT_STRING, left.toString(), LookUp.LOOKUP.teg(op), right.toString()));
		switch (op) {
			// integer operations (including bitwise)
			// int, int -> int
			case AND:
			case OR:
			case DIV:
			case MOD:
				if (left == TokenType.INTEGER && right == TokenType.INTEGER) {
					return TokenType.INTEGER;
				} else {
					throw expt;
				}

			// normal arithmatic
			// int, int -> int
			// int, real-> real
			// real,int -> real
			// real,real-> real
			case PLUS:
			case MINUS:
			case ASTERISK:
				return left == TokenType.REAL || right == TokenType.REAL ? TokenType.REAL : TokenType.INTEGER;

			// comparison operators
			// int, int -> int
			// int, real-> int
			// real,int -> int
			// real,real-> int
			case DIAMOND:
			case LESSTHAN:
			case GREATERTHAN:
			case LESSTHANEQUALS:
			case GREATERTHANEQUALS:
			case EQUALS:
				return TokenType.INTEGER;

			// floating point operations
			// int, int -> real
			// int, real-> real
			// real,int -> real
			// real,real-> real
			case FORWARDSLASH:
				return TokenType.REAL;

			default:
				throw new Exception(LookUp.LOOKUP.teg(op) + " is not a valid binary opperator.");
		}
	}

	private static final String OUT_TYPE_EXCEPTION_FORMAT_STRING = "%s %s %s is not valid";

	/**
	 * returns a literal if folding is possible, otherwise returns null
	 *
	 * @return if the node can be folded
	 */
	@Override
	public LiteralNode fold() {
		LiteralNode leftInput = left.fold();
		LiteralNode RightInput = right.fold();

		if (leftInput == null || RightInput == null) {
			return null;
		} else {
			switch (leftInput.getType()) {
				case REAL: {
					RealLiteralNode leftVal = (RealLiteralNode) leftInput;
					switch (RightInput.getType()) {
						case REAL: {
							RealLiteralNode rightVal = (RealLiteralNode) RightInput;
							switch (operation) {
								case PLUS:
									return new RealLiteralNode(leftVal.getValue() + rightVal.getValue());
								case MINUS:
									return new RealLiteralNode(leftVal.getValue() - rightVal.getValue());
								case ASTERISK:
									return new RealLiteralNode(leftVal.getValue() * rightVal.getValue());
								case DIAMOND:
									return new IntLiteralNode(leftVal.getValue() != rightVal.getValue());
								case LESSTHAN:
									return new IntLiteralNode(leftVal.getValue() < rightVal.getValue());
								case GREATERTHAN:
									return new IntLiteralNode(leftVal.getValue() > rightVal.getValue());
								case LESSTHANEQUALS:
									return new IntLiteralNode(leftVal.getValue() <= rightVal.getValue());
								case GREATERTHANEQUALS:
									return new IntLiteralNode(leftVal.getValue() >= rightVal.getValue());
								case EQUALS:
									return new IntLiteralNode(leftVal.getValue() == rightVal.getValue());
								case FORWARDSLASH:
									return new RealLiteralNode(leftVal.getValue() / rightVal.getValue());
							}
						}
						case INTEGER: {
							IntLiteralNode rightVal = (IntLiteralNode) RightInput;
							switch (operation) {
								case PLUS:
									return new RealLiteralNode(leftVal.getValue() + rightVal.getValue());
								case MINUS:
									return new RealLiteralNode(leftVal.getValue() - rightVal.getValue());
								case ASTERISK:
									return new RealLiteralNode(leftVal.getValue() * rightVal.getValue());
								case DIAMOND:
									return new IntLiteralNode(leftVal.getValue() != rightVal.getValue());
								case LESSTHAN:
									return new IntLiteralNode(leftVal.getValue() < rightVal.getValue());
								case GREATERTHAN:
									return new IntLiteralNode(leftVal.getValue() > rightVal.getValue());
								case LESSTHANEQUALS:
									return new IntLiteralNode(leftVal.getValue() <= rightVal.getValue());
								case GREATERTHANEQUALS:
									return new IntLiteralNode(leftVal.getValue() >= rightVal.getValue());
								case EQUALS:
									return new IntLiteralNode(leftVal.getValue() == rightVal.getValue());
								case FORWARDSLASH:
									return new RealLiteralNode(leftVal.getValue() / rightVal.getValue());
							}
						}
					}
				}
				case INTEGER: {
					IntLiteralNode leftVal = (IntLiteralNode) leftInput;
					switch (RightInput.getType()) {
						case REAL: {
							RealLiteralNode rightVal = (RealLiteralNode) RightInput;
							switch (operation) {
								case PLUS:
									return new RealLiteralNode(leftVal.getValue() + rightVal.getValue());
								case MINUS:
									return new RealLiteralNode(leftVal.getValue() - rightVal.getValue());
								case ASTERISK:
									return new RealLiteralNode(leftVal.getValue() * rightVal.getValue());
								case DIAMOND:
									return new IntLiteralNode(leftVal.getValue() != rightVal.getValue());
								case LESSTHAN:
									return new IntLiteralNode(leftVal.getValue() < rightVal.getValue());
								case GREATERTHAN:
									return new IntLiteralNode(leftVal.getValue() > rightVal.getValue());
								case LESSTHANEQUALS:
									return new IntLiteralNode(leftVal.getValue() <= rightVal.getValue());
								case GREATERTHANEQUALS:
									return new IntLiteralNode(leftVal.getValue() >= rightVal.getValue());
								case EQUALS:
									return new IntLiteralNode(leftVal.getValue() == rightVal.getValue());
								case FORWARDSLASH:
									return new RealLiteralNode(leftVal.getValue() / rightVal.getValue());
							}
						}
						case INTEGER: {
							IntLiteralNode rightVal = (IntLiteralNode) RightInput;
							switch (operation) {
								case AND:
									return new IntLiteralNode(leftVal.getValue() & rightVal.getValue());
								case OR:
									return new IntLiteralNode(leftVal.getValue() | rightVal.getValue());
								case DIV:
									return new IntLiteralNode(leftVal.getValue() / rightVal.getValue());
								case MOD:
									return new IntLiteralNode(leftVal.getValue() % rightVal.getValue());
								case PLUS:
									return new IntLiteralNode(leftVal.getValue() + rightVal.getValue());
								case MINUS:
									return new IntLiteralNode(leftVal.getValue() - rightVal.getValue());
								case ASTERISK:
									return new IntLiteralNode(leftVal.getValue() * rightVal.getValue());
								case DIAMOND:
									return new IntLiteralNode(leftVal.getValue() != rightVal.getValue());
								case LESSTHAN:
									return new IntLiteralNode(leftVal.getValue() < rightVal.getValue());
								case GREATERTHAN:
									return new IntLiteralNode(leftVal.getValue() > rightVal.getValue());
								case LESSTHANEQUALS:
									return new IntLiteralNode(leftVal.getValue() <= rightVal.getValue());
								case GREATERTHANEQUALS:
									return new IntLiteralNode(leftVal.getValue() >= rightVal.getValue());
								case EQUALS:
									return new IntLiteralNode(leftVal.getValue() == rightVal.getValue());
								case FORWARDSLASH:
									return new RealLiteralNode((float) leftVal.getValue() / (float) rightVal.getValue());
							}
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	protected String toMips(Scope symbolTable, String indent) {
		//check for folding
		LiteralNode fold = fold();
		if (fold != null) {
			return fold.toMips(symbolTable, indent);
		}

		StringBuilder build = new StringBuilder(indent).append("#BinaryOperationNode\n");

		//evaluate left side
		build.append(left.toMips(symbolTable, indent + '\t'));

		//push stack head
		build.append(indent).append("lw $t0, ($sp)\n")
				.append(indent).append("addi $t0, $t0, -4\n")
				.append(indent).append("sw $t0, ($sp)\n");

		//evaluate right side
		build.append(right.toMips(symbolTable, indent + '\t'));

		//push back stack head	(alo puts stack head in t0)
		build.append(indent).append("lw $t0, ($sp)\n")
				.append(indent).append("addi $t0, $t0, 4\n")
				.append(indent).append("sw $t0, ($sp)\n");

		//do computation
		switch (left.getType()) {
			case REAL:
				build.append(indent).append("lwc1 $f2, -4($t0)\t#put second operand into f2\n") //put right into f2
						.append(indent).append("lwc1 $f0, ($t0)\t#put first operand into f0\n");	//put left into f0
				switch (right.getType()) {
					case REAL:
						appendFloatingPointComputationAndPush(build, indent);
						break;
					case INTEGER:
						build.append(indent).append("cvt.s.w $f2, $f2\t#cast to float\n");	//need to cast right side
						appendFloatingPointComputationAndPush(build, indent);
						break;
				}
				break;
			case INTEGER:
				switch (right.getType()) {
					case REAL:
						build.append(indent).append("lwc1 $f0, ($t0)\t#put first operand into f0\n")
								.append(indent).append("lwc1 $f2, -4($t0)\t#put second operand into f2\n")
								.append(indent).append("cvt.s.w $f0, $f0\t#cast to float\n");
						appendFloatingPointComputationAndPush(build, indent);
						break;
					case INTEGER:
						if (operation == TokenType.FORWARDSLASH) {	//only thing that uses floating point math over here
							build.append(indent).append("lwc1 $f0, ($t0)\t#put first operand into f0\n")
									.append(indent).append("lwc1 $f2, -4($t0)\t#put second operand into f1\n")
									.append(indent).append("cvt.s.w $f0, $f0\t#cast to float\n")
									.append(indent).append("cvt.s.w $f2, $f2\t#cast to float\n");
							appendFloatingPointComputationAndPush(build, indent);
						} else {
							build.append(indent).append("lw $t1, ($t0)\t#put first operand in t1\n")
									.append(indent).append("lw $t2, -4($t0)\t#put second operand in t1\n");
							appendIntegerComputationAndPush(build, indent);
						}
				}
		}
		return build.toString();
	}

	/**
	 * expects values to be loaded in f0 and f2 in order, saves value to stack
	 * at t0
	 *
	 * @param build the string builder being used, will be appended to
	 * @param indent for formating
	 *
	 * @return same object as build
	 */
	private StringBuilder appendFloatingPointComputationAndPush(StringBuilder build, String indent) {
		switch (operation) {
			case PLUS:
				build.append(indent).append("add.s $f0, $f0, $f2\n")
						.append(indent).append("swc1 $f0, ($t0)\t#put return value on stack\n");
				break;
			case MINUS:
				build.append(indent).append("sub.s $f0, $f0, $f2\n")
						.append(indent).append("swc1 $f0, ($t0)\t#put return value on stack\n");
				break;
			case ASTERISK:
				build.append(indent).append("mul.s $f0, $f0, $f2\n")
						.append(indent).append("swc1 $f0, ($t0)\t#put return value on stack\n");
				break;
			case DIAMOND:
				build.append(indent).append("c.eq.s $f0, $f2\t#compare not equal\n")
						.append(indent).append("li $t1, 1\n")
						.append(indent).append("movt $t1, $zero\n")
						.append(indent).append("sw $t1, ($t0)\n");
				break;
			case LESSTHAN:
				build.append(indent).append("c.lt.s $f0, $f2\t#compare less then\n")
						.append(indent).append("li $t1, 1\n")
						.append(indent).append("movf $t1, $zero\n")
						.append(indent).append("sw $t1, ($t0)\t#put return value on stack\n");
				break;
			case GREATERTHAN:
				build.append(indent).append("c.gt.s $f0, $f2\t#compare greater then\n")
						.append(indent).append("li $t1, 1\n")
						.append(indent).append("movf $t1, $zero\n")
						.append(indent).append("sw $t1, ($t0)\t#put return value on stack\n");
				break;
			case LESSTHANEQUALS:
				build.append(indent).append("c.ge.s $f0, $f2\t#compare less then or equal to\n")
						.append(indent).append("li $t1, 1\n")
						.append(indent).append("movt $t1, $zero\n")
						.append(indent).append("sw $t1, ($t0)\t#put return value on stack\n");
				break;
			case GREATERTHANEQUALS:
				build.append(indent).append("c.le.s $f0, $f2\t#compares greater then or equal to\n")
						.append(indent).append("li $t1, 1\n")
						.append(indent).append("movt $t1, $zero\n")
						.append(indent).append("sw $t1, ($t0)\t#put return value on stack\n");
				break;
			case EQUALS:
				build.append(indent).append("c.eq.s $f0, $f2\t#compare equals\n")
						.append(indent).append("li $t1, 1\n")
						.append(indent).append("movf $t1, $zero\n")
						.append(indent).append("sw $t1, ($t0)\t#put return value on stack\n");
				break;
			case FORWARDSLASH:
				build.append(indent).append("div.s $f0, $f0, $f2\n")
						.append(indent).append("swc1 $f0, ($t0)\t#put return value on stack\n");
				break;
		}
		return build;
	}

	/**
	 * expects values to be loaded in t1 and t2 in order, saves value to stack
	 * at t0
	 *
	 * @param build the string builder being used, will be appended to
	 * @param indent for formating
	 *
	 * @return same object as build
	 */
	private StringBuilder appendIntegerComputationAndPush(StringBuilder build, String indent) {
		//put output of opperation into $t1
		switch (operation) {
			case AND:
				build.append(indent).append("and $t1, $t1, $t2\n");
				break;
			case OR:
				build.append(indent).append("or $t1, $t1, $t2\n");
				break;
			case DIV:
				build.append(indent).append("div $t1, $t1, $t2\n");
				break;
			case MOD:
				build.append(indent).append("rem $t1, $t1, $t2\n");
				break;
			case PLUS:
				build.append(indent).append("add $t1, $t1, $t2\n");
				break;
			case MINUS:
				build.append(indent).append("sub $t1, $t1, $t2\n");
				break;
			case ASTERISK:
				build.append(indent).append("mul $t1, $t1, $t2\n");
				break;
			case DIAMOND:
				build.append(indent).append("sne $t1, $t1, $t2\n");
				break;
			case LESSTHAN:
				build.append(indent).append("slt $t1, $t1, $t2\n");
				break;
			case GREATERTHAN:
				build.append(indent).append("sgt $t1, $t1, $t2\n");
				break;
			case LESSTHANEQUALS:
				build.append(indent).append("sle $t1, $t1, $t2\n");
				break;
			case GREATERTHANEQUALS:
				build.append(indent).append("sgt $t1, $t1, $t2\n");
				break;
			case EQUALS:
				build.append(indent).append("seq $t1, $t1, $t2\n");
				break;
		}
		return build.append(indent).append("sw $t1, ($t0)\t#save computation\n");
	}
}
