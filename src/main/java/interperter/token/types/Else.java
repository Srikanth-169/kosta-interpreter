package interperter.token.types;


import interperter.token.Precedence;
import interperter.token.Token;

import static interperter.token.Precedence.LOWEST;


public class Else implements Token {


    @Override
    public Precedence precedence() {
        return LOWEST;
    }

    @Override
    public String literal() {
        return "else";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
