package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

import static org.kosta.interperter.token.Precedence.SUM;

public class MinusInfix implements Token {

    @Override
    public Precedence precedence() {
        return SUM;  // Same precedence as plus
    }

    @Override
    public String literal() {
        return "-";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
