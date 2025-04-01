package com.github.konstantinevashalomidze.interpreter.ast.statement;


import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interpreter.token.Token;

public class ReturnStatement
        implements Statement {
    Token token;
    Expression value;


    public ReturnStatement(Token token, Expression value) {
        this.token = token;
        this.value = value;
    }

    public ReturnStatement() {

    }

    public ReturnStatement(Token currentToken) {
        this.token = currentToken;
    }


    @Override
    public String literal() {
        return this.token.literal();
    }

    @Override
    public void statementNode() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ReturnStatement");
        if (token != null) {
            sb.append(" (").append(token.literal()).append(")");
        }
        sb.append("\n");
        if (value != null) {
            // Indent the value to represent it as a child of the ReturnStatement node
            sb.append("  |- ").append(value.toString().replace("\n", "\n  |  ")).append("\n");
        }
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
