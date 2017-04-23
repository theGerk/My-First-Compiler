package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Represents a variable in the syntax tree.
 *
 * @author Erik Steinmetz
 */
public class AccessVariableNode extends IdentifierNodeBase {

	/**
	 * Creates a LiteralNode with the given attribute.
	 *
	 * @param id The attribute for this value node.
	 * @param containingScope scope of function the statement is in
	 * @throws java.lang.Exception if there is an error
	 */
	public AccessVariableNode(String id, Scope containingScope) throws Exception {
		super(id, validateID(id, containingScope));
	}

	/**
	 * Used to bypass error checking for children to call
	 *
	 * @param id ID of variable
	 * @param type type of variable
	 */
	protected AccessVariableNode(String id, TokenType type) {
		super(id, type);
	}

	private static TokenType validateID(String id, Scope scope) throws Exception {
		if (scope.getKind(id) == Scope.IdentifierKind.VAR) {
			return scope.getType(id);
		} else {
			throw new Exception("Expected " + id + " to be a VAR");
		}
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Name: " + this.name + "\n";
		return answer;
	}

	/**
	 * Writes component into assembly with tabbing for readability and sets up
	 * symbol table where needed.
	 *
	 * @param symbolTable the current scope
	 * @param indent tabs
	 *
	 * @return Mips assembly
	 */
	@Override
	protected String toMips(Scope symbolTable, String indent) {
		StringBuilder build = new StringBuilder(indent).append("#AccessVariableNode\n");

		//get ptr to variable's scope in v0:
		build.append(IPublicName.getVarPtrInV0(symbolTable, getName(), indent));

		//put value on stack
		build.append(indent).append("lw $t0, ($v0)\n");
		build.append(indent).append("lw $t1, ($sp)\n");
		build.append(indent).append("sw $t0, ($t1)\n");

		return build.toString();
	}
}
