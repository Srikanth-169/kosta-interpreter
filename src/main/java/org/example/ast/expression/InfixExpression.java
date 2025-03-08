package org.example.ast.expression;

import org.example.ast.expression.Expression;
import org.example.ast.statement.ExpressionStatement;
import org.example.token.Token;

public class InfixExpression implements Expression {


    private Token token;
    private String operator;
    private Expression right;
    private Expression left;

    public InfixExpression(Token currentToken, String literal) {
        this.token = currentToken;
        operator = literal;
    }

    public InfixExpression(Token currentToken, String literal, Expression left) {
        this.token = currentToken;
        operator = literal;
        this.left = left;
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

        sb.append("(").append(left).append(" ").append(operator).append(" ").append(right.toString()).append(")");

        return sb.toString();
    }

    public String getOperator() {
        return operator;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }
}
