package syntaxtree;

import symboltable.Scope;

/**
 * Represents a single statement in Pascal. Quite useless really
 *
 * @author Erik Steinmetz
 */
public abstract class StatementNode extends SyntaxTreeBase {

	/**
	 * Statement nodes represent one line of code, as such each statement node
	 * will do something different, although upon their completion some things
	 * must remain the same.
	 * <ol>
	 * <li>Stack head pointer will be pointing to it's default value ($sp -
	 * 4)</li>
	 * <li>Function overhead values are not changed, stack head pointer may be
	 * used, but SHOULD be reset.</li>
	 * </ol>
	 *
	 * @param symbolTable the scope in which the statement resides.
	 * @param indent for formating
	 * @return MIPS
	 */
	@Override
	abstract protected String toMips(Scope symbolTable, String indent);

}
