/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

/**
 *
 * @author Benji
 */
public class SubProgramNode extends SyntaxTreeNode {

	private String id;
	private SubProgramDeclarationsNode subProcs;
	private DeclarationsNode arguments;
	private CompoundStatementNode instructions;

	//TODO impliment this
	@Override
	public String indentedToString(int level) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
