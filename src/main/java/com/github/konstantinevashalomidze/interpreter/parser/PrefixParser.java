package com.github.konstantinevashalomidze.interpreter.parser;

import com.github.konstantinevashalomidze.interpreter.ast.expression.*;
import com.github.konstantinevashalomidze.interpreter.ast.expression.Identifier;
import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.types.*;
import com.github.konstantinevashalomidze.interpreter.token.types.Integer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class PrefixParser {

    private final Parser parser;

    public PrefixParser(Parser parser) {
        this.parser = parser;
    }


    public Expression parse() {
        if (parser.getCurrentToken() instanceof com.github.konstantinevashalomidze.interpreter.token.types.Identifier) {
            return new Identifier(parser.getCurrentToken());
        } else if (parser.getCurrentToken() instanceof Integer) {
            return new IntegerLiteral(parser.getCurrentToken());
        } else if (parser.getCurrentToken() instanceof Bang) {
            return parseBangOrMinus();
        } else if (parser.getCurrentToken() instanceof MinusPrefix) {
            return parseBangOrMinus();
        } else if (parser.getCurrentToken() instanceof True) {
            return new BooleanLiteral(parser.getCurrentToken());
        } else if (parser.getCurrentToken() instanceof False) {
            return new BooleanLiteral(parser.getCurrentToken());
        } else if (parser.getCurrentToken() instanceof Lp) {
            return parseLp();
        } else if (parser.getCurrentToken() instanceof If) {
            return parseIf();
        } else if (parser.getCurrentToken() instanceof Function) {
            return parseFunction();
        }
        return null;
    }


    private PrefixExpression parseBangOrMinus() {
        // prefix expression has form <operator><operand>
        PrefixExpression prefixExpression = new PrefixExpression(parser.getCurrentToken()); // !<operand>
        parser.readAndMoveOnNextToken(); // move on operand
        prefixExpression.setRight(parser.getExpressionParser().parseExpression(Precedence.PREFIX.getNumber())); // parse operand -> !parsedOperand
        return prefixExpression;
    }

    private Expression parseLp() {
        parser.readAndMoveOnNextToken(); // {<operand>
        Expression expression = parser.getExpressionParser().parseExpression(Precedence.LOWEST.getNumber()); // parses <operand>
        if (!parser.expectNextToken(Rp.INSTANCE))
            return null;
        return expression; // returns <operand>
    }

    private IfExpression parseIf() {
        // if (<condition>) { <consequence> } else { <alternative> }
        IfExpression ifExpression = new IfExpression(parser.getCurrentToken());
        if (!parser.expectNextToken(Lp.INSTANCE)) // if (
            return null;

        parser.readAndMoveOnNextToken();
        ifExpression.setCondition(parser.getExpressionParser().parseExpression(Precedence.LOWEST.getNumber())); // if (<condition>

        if (!parser.expectNextToken(Rp.INSTANCE)) // if (<condition>)
            return null;

        if (!parser.expectNextToken(Lb.INSTANCE)) // if (<condition>) {
            return null;

        ifExpression.setConsequence(parser.getStatementParser().parseBlockStatement()); // if (<condition>) { <consequence> }

        // evaluate else block if present at all
        if (parser.getNextToken() == Else.INSTANCE) {
            parser.readAndMoveOnNextToken(); // if (<condition>) { <consequence> } else

            if (!parser.expectNextToken(Lb.INSTANCE)) // if (<condition>) { <consequence> } else {
                return null;

            ifExpression.setAlternative(parser.getStatementParser().parseBlockStatement());
        }

        return ifExpression;


    }


    private Expression parseFunction() {
        // fn(<arguments>) { <statements> };
        FunctionLiteral functionLiteral = new FunctionLiteral(parser.getNextToken()); // fn

        if (!parser.expectNextToken(Lp.INSTANCE)) // fn(
            return null;

        List<Identifier> identifiers = new ArrayList<>();
        if (parser.getNextToken() == Rp.INSTANCE)
            parser.readAndMoveOnNextToken(); // fn()
        else {
            // fn (<arguments>
            parser.readAndMoveOnNextToken();
            Identifier identifier = new Identifier(parser.getCurrentToken());
            identifiers.add(identifier);

            while (parser.getNextToken() == Comma.INSTANCE) {
                parser.readAndMoveOnNextToken(); // moves on comma
                parser.readAndMoveOnNextToken(); // moves on argument identifier
                identifier = new Identifier(parser.getCurrentToken());
                identifiers.add(identifier);
            }

            if (!parser.expectNextToken(Rp.INSTANCE)) // fn (<arguments>)
                return null;
        }

        functionLiteral.setParameters(identifiers);

        if (!parser.expectNextToken(Lb.INSTANCE)) // fn (<arguments>) {
            return null;

        functionLiteral.setBody(parser.getStatementParser().parseBlockStatement());

        return functionLiteral;
    }



}
