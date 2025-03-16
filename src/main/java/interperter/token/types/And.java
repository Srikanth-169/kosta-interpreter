package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

import static org.kosta.interperter.token.Precedence.AND;

public class And
    implements Token
{

    @Override
    public Precedence precedence() {
        return AND;
    }

    @Override
    public String literal() {
        return "&";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
