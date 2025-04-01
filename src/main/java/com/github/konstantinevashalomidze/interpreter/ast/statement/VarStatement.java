package com.github.konstantinevashalomidze.interpreter.ast.statement;


import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interpreter.ast.expression.Identifier;
import com.github.konstantinevashalomidze.interpreter.token.Token;

public class VarStatement
        implements Statement {
    Token token;
    Identifier name;
    Expression value;


    public VarStatement(Token token, Identifier name, Expression value) {
        this.token = token;
        this.name = name;
        this.value = value;
    }

    public VarStatement() {

    }

    public VarStatement(Token currentToken) {
        token = currentToken;
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
        sb.append("VarStatement");
        if (token != null) {
            sb.append(" (").append(token.literal()).append(")");
        }
        sb.append("\n");
        if (name != null) {
            sb.append("  |- Name: ").append(name.toString().replace("\n", "\n  |  ")).append("\n");
        }
        if (value != null) {
            sb.append("  |- Value: ").append(value.toString().replace("\n", "\n  |  ")).append("\n");
        }
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
