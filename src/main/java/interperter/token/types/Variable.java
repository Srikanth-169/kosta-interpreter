package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

import static org.kosta.interperter.token.Precedence.LOWEST;

public class Variable
    implements Token
{

    @Override
    public Precedence precedence() {
        return LOWEST;
    }


    @Override
    public String literal() {
        return "var";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
