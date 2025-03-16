package interperter.token.types;


import interperter.token.Precedence;
import interperter.token.Token;

import static interperter.token.Precedence.LOWEST;


public class Rp implements Token {

    @Override
    public Precedence precedence() {
        return LOWEST;
    }


    @Override
    public String literal() {
        return ")";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
