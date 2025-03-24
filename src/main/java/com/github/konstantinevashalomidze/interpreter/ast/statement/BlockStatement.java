package com.github.konstantinevashalomidze.interpreter.ast.statement;


import com.github.konstantinevashalomidze.interpreter.token.Token;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement
    implements Statement
{

    private Token token;
    private List<Statement> statements;

    public BlockStatement(Token token) {
        this.token = token;
        statements = new ArrayList<>();
    }

    @Override
    public String literal() {
        return token.literal();
    }

    @Override
    public void statementNode() {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BlockStatement (").append(token.literal()).append(")\n"); // Root node with token
        for (Statement statement : statements) {
            // Indent each statement to represent it as a child of the BlockStatement node
            sb.append("  |- ").append(statement.toString().replace("\n", "\n  |  ")).append("\n");
        }
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
}
