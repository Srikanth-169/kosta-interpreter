package interperter.ast.expression;


import interperter.token.Token;

public class Identifier
    implements Expression
{
    private Token token;

    public Identifier(Token token) {
        this.token = token;
    }

    public Identifier() {

    }

    @Override
    public void expressionNode() {

    }

    @Override
    public String literal() {
        return token.literal();
    }

    @Override
    public String toString() {
        return "Identifier (" + token.literal() + ")";
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return token.literal();
    }

    public void setToken(Token token) {
        this.token = token;
    }

}
