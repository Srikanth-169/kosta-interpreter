package interperter.token.types;

import org.kosta.interperter.token.Precedence;
import org.kosta.interperter.token.Token;

import static org.kosta.interperter.token.Precedence.OR;

public class Or
    implements Token
{

    @Override
    public Precedence precedence() {
        return OR;
    }


    @Override
    public String literal() {
        return "|";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}
