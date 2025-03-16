package interperter.token.types;


import interperter.token.Precedence;
import interperter.token.Token;

import static interperter.token.Precedence.AND;


public class And
    implements Token
{

    @Override
    public Precedence precedence() {
        return AND;
    }

    @Override
    public String literal() {
        return "&";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
