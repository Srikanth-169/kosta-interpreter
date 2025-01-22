package org.example.ast.expression;

import org.example.ast.expression.Expression;
import org.example.token.Token;

public class Identifier
    implements Expression
{
    private Token token;
    private String value;

    public Identifier(Token token, String value) {
        this.token = token;
        this.value = value;
    }

    public Identifier()
    {

    }

    @Override
    public void expressionNode() {

    }

    @Override
    public String tokenLiteral() {
        return this.token.getLiteral();
    }

    @Override
    public String toString() {
        return this.value;
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
