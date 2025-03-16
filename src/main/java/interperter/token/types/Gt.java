package interperter.token.types;


import interperter.token.Precedence;
import interperter.token.Token;

import static interperter.token.Precedence.COMPARE;


public class Gt implements Token {

    @Override
    public Precedence precedence() {
        return COMPARE;  // Comparison operators
    }

    @Override
    public String literal() {
        return ">";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
