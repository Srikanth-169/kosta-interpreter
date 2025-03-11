package org.example.ast.statement;

import org.example.token.Token;

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
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public void statementNode() {

    }

    @Override
    public String toString() {
        if (statements.isEmpty())
            return "{ }";

        StringBuilder sb = new StringBuilder();
        int count = 0;
        sb.append("{ ");
        for (Statement statement : statements)
        {
            if (count != statements.size() - 1)
                sb.append(statement.toString()).append(" ");
            else
                sb.append(statement.toString());
            count++;
        }
        sb.append(" }");
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
