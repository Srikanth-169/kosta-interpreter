package interperter.ast.expression;


import interperter.token.Token;

public class PrefixExpression implements Expression {


    private Token token;
    private Expression right;

    public PrefixExpression(Token currentToken) {
        this.token = currentToken;
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
}
