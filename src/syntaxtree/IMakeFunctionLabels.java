package syntaxtree;

import symboltable.Scope;

/**
 * Created by Benji on 4/16/2017.
 */
interface IMakeFunctionLabels {
	/**
	 * Adds labels for each function into the symbol table
	 *
	 * @param labelPrefix the desired prefix for the label
	 * @param symbolTable the symbol table to be used
	 */
	void makeLabels(String labelPrefix, Scope symbolTable);
}
