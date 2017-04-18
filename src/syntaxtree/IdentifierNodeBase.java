package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Created by Benji on 4/14/2017. Anything that has an identifier extends this
 *
 * @author Benji
 */
public abstract class IdentifierNodeBase extends ExpressionNode implements IPublicName {

	/**
	 * The name of the variable associated with this node.
	 */
	protected final String name;

	protected IdentifierNodeBase(String id, TokenType tokenType) {
		super(tokenType);
		name = id;
	}

	/**
	 * Returns the name of the variable of this node.
	 *
	 * @return The name of this VariableNode.
	 */
	@Override
	public String getName() {
		return (this.name);
	}

	@Override
	public LiteralNode fold() {
		return null;
	}
}
