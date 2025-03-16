package interperter.token.types;


import interperter.token.Precedence;
import interperter.token.Token;

import static interperter.token.Precedence.LOWEST;


public class If implements Token {


    @Override
    public Precedence precedence() {
        return LOWEST;
    }

    @Override
    public String literal() {
        return "if";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
