package interperter.token.types;


import interperter.token.Precedence;
import interperter.token.Token;

import static interperter.token.Precedence.LOWEST;


public class Integer
    implements Token
{
    private String literal;



    @Override
    public Precedence precedence() {
        return LOWEST;
    }


    @Override
    public String literal() {
        return literal;
    }

    @Override
    public Token setLiteral(String literal) {
        this.literal = literal;
        return this;
    }
}
