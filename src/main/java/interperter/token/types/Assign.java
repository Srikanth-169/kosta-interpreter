package interperter.token.types;


import interperter.token.Precedence;
import interperter.token.Token;

public class Assign
    implements Token
{


    @Override
    public Precedence precedence() {
        return Precedence.LOWEST;
    }

    @Override
    public String literal() {
        return "=";
    }

    @Override
    public Token setLiteral(String string) {
        return this;
    }
}



