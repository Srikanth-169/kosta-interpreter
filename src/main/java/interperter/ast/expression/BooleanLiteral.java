package interperter.ast.expression;


import interperter.token.Token;

public class BooleanLiteral
    implements Expression
{

    private Token token;

    public BooleanLiteral(Token token) {
        this.token = token;
    }

    @Override
    public void expressionNode() {

    }

    @Override
    public String literal() {
        return token.literal();
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "BooleanLiteral (" + token.literal() + ")";
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean getValue() {
        return token.literal().equals("true");
    }

}
