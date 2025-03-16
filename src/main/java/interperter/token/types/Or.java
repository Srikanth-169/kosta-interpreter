package interperter.token.types;


import interperter.token.Precedence;
import interperter.token.Token;

import static interperter.token.Precedence.OR;


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
