package org.example.token;

import org.example.parser.Precedence;

import static org.example.parser.Precedence.*;

/**
 * Is token that lexer uses to tokenize user's code.
 *
 * @author Konstantine Vashalomidze
 */
public class Token
{
    private TokenType tokenType;
    private String literal; // actual value of the token, meaning how is it written in initial code.

    public Token(TokenType tokenType, String literal) {
        this.tokenType = tokenType;
        this.literal = literal;
    }

    public Token() {

    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", literal='" + literal + '\'' +
                '}';
    }

    /**
     *  Determines priority of the expression operation, for example in this expression (a + b) / 3
     *  highest priority has LP
     * @return returns precedence of token
     */
    public Precedence getPrecedence() {
        return switch (tokenType) {
            // TokenType -> Precedence
            case EQ -> EQUEALS;
            case NOT_EQ -> EQUEALS;
            case LT -> LESSG_REATER;
            case GT -> LESSG_REATER;
            case PLUS -> SUM;
            case MINUS -> SUM;
            case SLASH -> PRODUCT;
            case ASTERISK -> PRODUCT;
            case LP -> CALL;
            default -> LOWEST;
        };
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getLiteral() {
        return literal;
    }
}
