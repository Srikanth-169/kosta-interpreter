package com.github.konstantinevashalomidze.interpreter.parser;

import com.github.konstantinevashalomidze.interpreter.ast.expression.CallExpression;
import com.github.konstantinevashalomidze.interpreter.ast.expression.Expression;
import com.github.konstantinevashalomidze.interpreter.ast.expression.InfixExpression;
import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.types.*;

import java.lang.reflect.InvocationTargetException;

public class InfixParser {
    private final Parser parser;
    public InfixParser(Parser parser) {
        this.parser = parser;
    }

    public Expression parse(Expression left) {
        if (parser.getCurrentToken() instanceof Plus ||
                parser.getCurrentToken() instanceof MinusInfix ||
                parser.getCurrentToken() instanceof Slash ||
                parser.getCurrentToken() instanceof Asterisk ||
                parser.getCurrentToken() instanceof Eq ||
                parser.getCurrentToken() instanceof NotEq ||
                parser.getCurrentToken() instanceof Lt ||
                parser.getCurrentToken() instanceof Gt ||
                parser.getCurrentToken() instanceof And ||
                parser.getCurrentToken() instanceof Or) {

            return parseInfixOperators(left);

        } else if (parser.getCurrentToken() instanceof Lp) {

            return parseFunctionCall(left);

        }

        return null;
    }

    private Expression parseInfixOperators(Expression left) {
        // <expression | value> <operator> <expression | value>
        InfixExpression infixExpression = new InfixExpression(parser.getCurrentToken(), left); // <expression | value> <operator>

        int currentPrecedence = parser.getCurrentToken().precedence().getNumber(); // remember before advancing
        parser.readAndMoveOnNextToken();
        infixExpression.setRight(parser.getExpressionParser().parseExpression(currentPrecedence)); // <expression | value> <operator> <expression | value>
        return infixExpression;
    }

    private Expression parseFunctionCall(Expression left) {
        CallExpression callExpression = new CallExpression(parser.getCurrentToken(), left); // <identifier>(

        if (parser.getNextToken() == Rp.INSTANCE) {
            parser.readAndMoveOnNextToken(); // <identifier>()
            return callExpression;
        }

        parser.readAndMoveOnNextToken(); // <identifier>(<arguments>
        callExpression.getArguments().add(parser.getExpressionParser().parseExpression(Precedence.LOWEST.getNumber())); // parses argument

        while (parser.getNextToken() == Comma.INSTANCE) {
            parser.readAndMoveOnNextToken(); // move to comma
            parser.readAndMoveOnNextToken(); // move to argument

            callExpression.getArguments().add(parser.getExpressionParser().parseExpression(Precedence.LOWEST.getNumber()));
        }

        if (!parser.expectNextToken(Rp.INSTANCE)) // <identifier>(<arguments>)
            return null;

        return callExpression;
    }

}
