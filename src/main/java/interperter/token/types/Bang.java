package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

public class Bang implements Token {

    @Override
    public Precedence precedence() {
        return Precedence.PREFIX;
    }

    @Override
    public String literal() {
        return "!";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
