package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

import static org.kosta.interperter.token.Precedence.PREFIX;

public class MinusPrefix implements Token {

    @Override
    public Precedence precedence() {
        return PREFIX;  // Same precedence as plus
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
