package com.github.konstantinevashalomidze.interpreter.ast.expression;


import com.github.konstantinevashalomidze.interpreter.ast.statement.BlockStatement;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import java.util.List;


public class FunctionLiteral
        implements Expression {

    private Token token;
    private List<Identifier> parameters;

    private BlockStatement body;


    public FunctionLiteral(Token token) {
        this.token = token;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionLiteral\n");
        sb.append("  |- Parameters:\n");
        for (Identifier param : parameters) {
            sb.append("     |- ").append(param.toString().replace("\n", "\n     |  ")).append("\n");
        }
        sb.append("  |- Body: ").append(body.toString().replace("\n", "\n  |  ")).append("\n");
        return sb.toString();
    }

    public List<Identifier> getParameters() {
        return parameters;
    }

    public void setParameters(List<Identifier> parameters) {
        this.parameters = parameters;
    }

    public BlockStatement getBody() {
        return body;
    }

    public void setBody(BlockStatement body) {
        this.body = body;
    }
}
