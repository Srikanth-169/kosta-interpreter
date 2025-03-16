package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

import static org.kosta.interperter.token.Precedence.COMPARE;

public class Lt implements Token {
    @Override
    public Precedence precedence() {
        return COMPARE;  // Comparison operators
    }

    @Override
    public String literal() {
        return "<";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
