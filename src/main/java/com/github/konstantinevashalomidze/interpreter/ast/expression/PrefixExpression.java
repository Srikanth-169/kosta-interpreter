package com.github.konstantinevashalomidze.interpreter.ast.expression;


import com.github.konstantinevashalomidze.interpreter.token.Token;

public class PrefixExpression implements Expression {


    private Token token;
    private Expression right;

    public PrefixExpression(Token currentToken) {
        this.token = currentToken;
    }


    @Override
    public void expressionNode() {

    }

    @Override
    public String literal() {
        return token.literal();
    }


    public String getOperator() {
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
        sb.append("PrefixExpression (").append(token.literal()).append(")\n"); // Root node with operator
        if (right != null) {
            sb.append("  |- Right: ").append(right.toString().replace("\n", "\n  |  ")).append("\n");
        }
        return sb.toString();
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }
}
