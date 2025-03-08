package org.example.ast.expression;

import org.example.ast.statement.BlockStatement;
import org.example.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionLiteral
    implements Expression
{

    private Token token;
    private List<Identifier> parameters;

    private BlockStatement body;



    @Override
    public void expressionNode() {


    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<String> params = new ArrayList<>();
        for (Identifier identifier : parameters)
        {
            params.add(identifier.toString());
        }

        sb.append(token.getLiteral()).append("(").append(String.join(", ", params)).append(") ").append(body.toString());
        return sb.toString();
    }
}
