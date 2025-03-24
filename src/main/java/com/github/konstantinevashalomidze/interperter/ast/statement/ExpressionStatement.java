package com.github.konstantinevashalomidze.interperter.ast.statement;


import com.github.konstantinevashalomidze.interperter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interperter.token.Token;

public class ExpressionStatement
    implements Statement
{
    private Token token;
    private Expression expression;


    public ExpressionStatement(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

    public ExpressionStatement(Token token) {
        this.token = token;
    }

    public ExpressionStatement() {

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
        sb.append("ExpressionStatement");
        if (token != null) {
            sb.append(" (").append(token.literal()).append(")");
        }
        sb.append("\n");
        if (expression != null) {
            // Indent the expression to represent it as a child of the ExpressionStatement node
            sb.append("  |- ").append(expression.toString().replace("\n", "\n  |  ")).append("\n");
        }
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
