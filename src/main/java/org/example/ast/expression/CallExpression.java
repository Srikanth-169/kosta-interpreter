package org.example.ast.expression;

import org.example.token.Token;

import java.util.ArrayList;
import java.util.List;

public class CallExpression
    implements Expression
{

    private Token token;
    private Expression function;
    private List<Expression> arguments;

    public CallExpression(Token token, Expression function) {
        this.token = token;
        this.function = function;
        arguments = new ArrayList<>();
    }


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
        List<String> args = new ArrayList<>();

        for (Expression argument : arguments)
        {
            args.add(argument.toString());
        }

        sb.append(function.toString()).append("(").append(String.join(", ", args)).append(")");
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Expression getFunction() {
        return function;
    }

    public void setFunction(Expression function) {
        this.function = function;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }
}
