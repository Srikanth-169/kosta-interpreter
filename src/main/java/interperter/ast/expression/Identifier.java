package interperter.ast.expression;


import org.kosta.interperter.token.Token;

public class Identifier
    implements Expression
{
    private Token token;

    public Identifier(Token token) {
        this.token = token;
    }

    @Override
    public void expressionNode() {

    }

    @Override
    public String literal() {
        return this.token.literal();
    }

    @Override
    public String toString() {
        return token.literal();
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
