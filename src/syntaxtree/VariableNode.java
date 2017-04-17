package syntaxtree;

import scanner.TokenType;
import symboltable.Scope;

/**
 * Represents a variable in the syntax tree.
 *
 * @author Erik Steinmetz
 */
public class VariableNode extends IdentifierNodeBase {

	/**
	 * Creates a LiteralNode with the given attribute.
	 *
	 * @param id The attribute for this value node.
	 */
	public VariableNode(String id, Scope containingScope) throws Exception {
		super(id, validateID(id, containingScope));
	}

	protected VariableNode(String id, TokenType type) {
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
		StringBuilder output = new StringBuilder(indent).append("#VariableNode\n");

		//get ptr to variable's scope in v0:
		output.append(IPublicName.getVarPtrInV0(symbolTable, getName(), indent + '\t'));

		//get ptr to value in t0
		output.append(indent).append("addi $t0, $v0, ").append(symbolTable.getMemoryOffset(getName())).append("\t#put ptr to variable in t0\n");

		//put value on stack
		output.append(indent).append("lw $t0, ($t0)\n");
		output.append(indent).append("lw $t1, ($sp)\n");
		output.append(indent).append("sw $t0, ($t1)\n");

		return output.toString();
	}
}
