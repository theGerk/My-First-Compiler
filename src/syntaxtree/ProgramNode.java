
package syntaxtree;

/**
 * Represents a Pascal Program
 * @author Erik Steinmetz
 */
public class ProgramNode extends SyntaxTreeNode {
    
    
    private String name;
    private DeclarationsNode variables;
    private SubProgramDeclarationsNode functions;
    private CompoundStatementNode main;
    
    public ProgramNode( String aName) {
        this.name = aName;
    }

    public DeclarationsNode getVariables() {
        return variables;
    }

    public void setVariables(DeclarationsNode variables) {
        this.variables = variables;
    }

    public SubProgramDeclarationsNode getFunctions() {
        return functions;
    }

    public void setFunctions(SubProgramDeclarationsNode functions) {
        this.functions = functions;
    }

    public CompoundStatementNode getMain() {
        return main;
    }

    public void setMain(CompoundStatementNode main) {
        this.main = main;
    }
    
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Program: " + name + "\n";
        answer += variables.indentedToString( level + 1);
        answer += functions.indentedToString( level + 1);
        answer += main.indentedToString( level + 1);
        return answer;
    }
}
