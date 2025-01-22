package org.example.token;

public class Token
{
    private TokenType tokenType;
    private String literal;

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

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getLiteral() {
        return literal;
    }
}
