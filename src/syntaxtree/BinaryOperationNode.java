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
	private TokenType operation;

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

	// Getters
	public ExpressionNode getLeft() {
		return (this.left);
	}

	public ExpressionNode getRight() {
		return (this.right);
	}

	public TokenType getOperation() {
		return (this.operation);
	}

	// Setters
	public void setOperation(TokenType op) {
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

		//do computation
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
