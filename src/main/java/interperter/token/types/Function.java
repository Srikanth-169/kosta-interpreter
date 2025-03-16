package interperter.token.types;


import interperter.token.Precedence;
import interperter.token.Token;

import static interperter.token.Precedence.LOWEST;


public class Function implements Token {



    @Override
    public Precedence precedence() {
        return LOWEST;  // Keywords typically don't have operator precedence
    }

    @Override
    public String literal() {
        return "fn";
    }


    public Token setLiteral(String literal) {
        return this;
    }

}
