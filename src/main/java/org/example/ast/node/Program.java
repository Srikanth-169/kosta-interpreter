package org.example.ast.node;

import org.example.ast.node.Node;
import org.example.ast.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class Program
    implements Node
{
    private List<Statement> statements;
    public Program() {
        statements = new ArrayList<>();
    }

    @Override
    public String tokenLiteral()
    {
        if (!statements.isEmpty())
            return statements.get(0).tokenLiteral();
        else return "";
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (Statement statement : this.statements) {
            sb.append(statement.toString());
            sb.append(" ");
        }

        return sb.toString();
    }


}
