package com.github.konstantinevashalomidze.interpreter.parser;

import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interpreter.token.types.Semicolon;

public class ExpressionParser {

    private final Parser parser;

    public ExpressionParser(Parser parser) {
        this.parser = parser;
    }


    public Expression parseExpression(int precedence) {
        Expression leftExpression = parser.getPrefixParser().parse(); // like 5 for example in 5 * 9 or - in -5
        // check if we have prefix parsing function associated with current token
        if (leftExpression == null) return null;

        // continue parsing till next token suggests a higher precedence infix expression
        while (!(parser.getNextToken() instanceof Semicolon) && precedence < parser.getNextToken().precedence().getNumber()) {
            parser.readAndMoveOnNextToken();
            Expression infixParsed = parser.getInfixParser().parse(leftExpression);
            if (infixParsed == null)
                return leftExpression;
            leftExpression = infixParsed;
        }

        return leftExpression;

    }




}
