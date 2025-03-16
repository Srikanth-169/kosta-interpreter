package interperter.token;

import org.kosta.interperter.token.types.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TokenManager
{
    private static TokenManager tokenManagerInstance;

    private final Map<String, Token> tokenTypeTokenMap;

    private TokenManager() {
        tokenTypeTokenMap = new HashMap<>();
        tokenTypeTokenMap.put(Assign.class.getSimpleName(), new Assign());
        tokenTypeTokenMap.put(Asterisk.class.getSimpleName(), new Asterisk());
        tokenTypeTokenMap.put(Bang.class.getSimpleName(), new Bang());
        tokenTypeTokenMap.put(Comma.class.getSimpleName(), new Comma());
        tokenTypeTokenMap.put(Definition.class.getSimpleName(), new Definition());
        tokenTypeTokenMap.put(Else.class.getSimpleName(), new Else().setLiteral("else"));
        tokenTypeTokenMap.put(Eof.class.getSimpleName(), new Eof());
        tokenTypeTokenMap.put(Eq.class.getSimpleName(), new Eq());
        tokenTypeTokenMap.put(False.class.getSimpleName(), new False());
        tokenTypeTokenMap.put(Function.class.getSimpleName(), new Function());
        tokenTypeTokenMap.put(Gt.class.getSimpleName(), new Gt());
        tokenTypeTokenMap.put(Identifier.class.getSimpleName(), new Identifier()); // has no use
        tokenTypeTokenMap.put(If.class.getSimpleName(), new If());
        tokenTypeTokenMap.put(Illegal.class.getSimpleName(), new Illegal());
        tokenTypeTokenMap.put(Integer.class.getSimpleName(), new Integer());
        tokenTypeTokenMap.put(Lb.class.getSimpleName(), new Lb());
        tokenTypeTokenMap.put(Lp.class.getSimpleName(), new Lp());
        tokenTypeTokenMap.put(Lt.class.getSimpleName(), new Lt());
        tokenTypeTokenMap.put(MinusPrefix.class.getSimpleName(), new MinusPrefix());
        tokenTypeTokenMap.put(MinusInfix.class.getSimpleName(), new MinusInfix());
        tokenTypeTokenMap.put(NotEq.class.getSimpleName(), new NotEq());
        tokenTypeTokenMap.put(Plus.class.getSimpleName(), new Plus());
        tokenTypeTokenMap.put(Rb.class.getSimpleName(), new Rb());
        tokenTypeTokenMap.put(Return.class.getSimpleName(), new Return());
        tokenTypeTokenMap.put(Rp.class.getSimpleName(), new Rp());
        tokenTypeTokenMap.put(Semicolon.class.getSimpleName(), new Semicolon());
        tokenTypeTokenMap.put(Slash.class.getSimpleName(), new Slash());
        tokenTypeTokenMap.put(True.class.getSimpleName(), new True());
        tokenTypeTokenMap.put(And.class.getSimpleName(), new And());
        tokenTypeTokenMap.put(Or.class.getSimpleName(), new Or());
        tokenTypeTokenMap.put(Variable.class.getSimpleName(), new Variable());
    }

    public Token getToken(String token)
    {
        return tokenTypeTokenMap.get(token);
    }


    public Optional<Token> getTokenWithLiteral(String literal) {
        return tokenTypeTokenMap.values().stream().filter(val -> val.literal() != null && val.literal().equals(literal)).findFirst();
    }

    public static TokenManager getTokenManagerInstance() {
        if (tokenManagerInstance == null)
            tokenManagerInstance = new TokenManager();
        return tokenManagerInstance;
    }
}
