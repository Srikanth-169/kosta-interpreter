package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

import static org.kosta.interperter.token.Precedence.SUM;

public class Plus implements Token {

    @Override
    public Precedence precedence() {
        return SUM;  // Assuming standard operator precedence
    }

    @Override
    public String literal() {
        return "+";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
