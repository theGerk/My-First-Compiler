package syntaxtree;

import scanner.TokenType;

/**
 * Represents any operation in an expression.
 * @author Erik Steinmetz
 */
public class OperationNode extends ExpressionNode {
    
    /** The left operator of this operation. */
    private ExpressionNode left;
    
    /** The right operator of this operation. */
    private ExpressionNode right;
    
    /** The kind of operation. */
    private TokenType operation;
    
    /**
     * Creates an operation node given an operation token.
     * @param op The token representing this node's math operation.
     */
    public OperationNode ( TokenType op) {
        this.operation = op;
    }
    
    
    // Getters
    public ExpressionNode getLeft() { return( this.left);}
    public ExpressionNode getRight() { return( this.right);}
    public TokenType getOperation() { return( this.operation);}
    
    // Setters
    public void setLeft( ExpressionNode node) {
        // If we already have a left, remove it from our child list.
        this.left = node;
    }
    public void setRight( ExpressionNode node) { 
        // If we already have a right, remove it from our child list.
        this.right = node;
    }
    public void setOperation( TokenType op) { this.operation = op;}
    
    /**
     * Returns the operation token as a String.
     * @return The String version of the operation token.
     */
    @Override
    public String toString() {
        return operation.toString();
    }
    
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation(level);
        answer += "Operation: " + this.operation + "\n";
        answer += left.indentedToString(level + 1);
        answer += right.indentedToString(level + 1);
        return( answer);
    }

    @Override
    public boolean equals( Object o) {
        boolean answer = false;
        if( o instanceof OperationNode) {
            OperationNode other = (OperationNode)o;
            if( (this.operation == other.operation) &&
                    this.left.equals( other.left) &&
                    this.right.equals( other.right)) answer = true;
        }
        return answer;
    }
}
