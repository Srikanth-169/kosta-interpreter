package interperter.ast.expression;


import interperter.token.Token;

public class InfixExpression implements Expression {


    private Token token;
    private Expression right;
    private Expression left;

    public InfixExpression(Token currentToken) {
        this.token = currentToken;
    }

    public InfixExpression(Token currentToken, Expression left) {
        this.token = currentToken;
        this.left = left;
    }




    @Override
    public void expressionNode() {

    }

    @Override
    public String literal() {
        return token.literal();
    }

    public String getOperator() {
        return token.literal();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }


    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }
}
