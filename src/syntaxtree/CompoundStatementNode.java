
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a compound statement in Pascal.
 * A compound statement is a block of zero or more
 * statements to be run sequentially.
 * @author ErikSteinmetz
 */
public class CompoundStatementNode extends StatementNode {
    
    private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();
    
    public void addStatement( StatementNode state) {
        this.statements.add( state);
    }
    
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Compound Statement\n";
        for( StatementNode state : statements) {
            answer += state.indentedToString( level + 1);
        }
        return answer;
    }
}
