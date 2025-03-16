package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

import static org.kosta.interperter.token.Precedence.PRODUCT;

public class Slash implements Token {

    @Override
    public Precedence precedence() {
        return PRODUCT;  // Same precedence as multiplication
    }

    @Override
    public String literal() {
        return "/";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
