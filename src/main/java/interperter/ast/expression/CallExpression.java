package interperter.ast.expression;


import interperter.token.Token;

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
    public String literal() {
        return token.literal();
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
