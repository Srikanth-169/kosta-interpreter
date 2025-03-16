package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

import static org.kosta.interperter.token.Precedence.LOWEST;

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
