package com.github.konstantinevashalomidze.interpreter.ast.expression;


import com.github.konstantinevashalomidze.interpreter.ast.statement.BlockStatement;
import com.github.konstantinevashalomidze.interpreter.token.Token;

public class IfExpression
    implements Expression
{

    private Token token;
    private Expression condition;
    private BlockStatement consequence;
    private BlockStatement alternative;


    public IfExpression(Token token) {
        this.token = token;
    }

    @Override
    public void expressionNode() {

    }

    @Override
    public String literal() {
        return token.literal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IfExpression\n");
        sb.append("  |- Condition: ").append(condition.toString().replace("\n", "\n  |  ")).append("\n");
        sb.append("  |- Consequence: ").append(consequence.toString().replace("\n", "\n  |  ")).append("\n");
        if (alternative != null) {
            sb.append("  |- Alternative: ").append(alternative.toString().replace("\n", "\n  |  ")).append("\n");
        }
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public BlockStatement getConsequence() {
        return consequence;
    }

    public void setConsequence(BlockStatement consequence) {
        this.consequence = consequence;
    }

    public BlockStatement getAlternative() {
        return alternative;
    }

    public void setAlternative(BlockStatement alternative) {
        this.alternative = alternative;
    }
}
