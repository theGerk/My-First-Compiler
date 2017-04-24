package syntaxtree;

import symboltable.Scope;

import java.util.ArrayList;

/**
 * Represents a compound statement in Pascal. A compound statement is a block of
 * zero or more statements to be run sequentially.
 *
 * @author ErikSteinmetz
 */
public class CompoundStatementNode extends StatementNode {

	private final ArrayList<StatementNode> statements = new ArrayList<>();

	/**
	 * adds a statement to the statements list
	 *
	 * @param statment statement being added
	 */
	public void addStatement(StatementNode statment) {
		this.statements.add(statment);
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Compound Statement\n";
		for (StatementNode state : statements) {
			answer += state.indentedToString(level + 1);
		}
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
		StringBuilder output = new StringBuilder(indent).append("#CompoundStatementNode\n");
		for (StatementNode statement : statements) {
			output.append(statement.toMips(symbolTable, indent + '\t'));
		}
		return output.toString();
	}
}
