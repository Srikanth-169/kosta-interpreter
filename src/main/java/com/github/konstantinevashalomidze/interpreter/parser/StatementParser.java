package com.github.konstantinevashalomidze.interpreter.parser;

import com.github.konstantinevashalomidze.interpreter.ast.expression.Identifier;
import com.github.konstantinevashalomidze.interpreter.ast.statement.*;
import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.types.*;

import java.lang.reflect.InvocationTargetException;

public class StatementParser {
    private final Parser parser;

    public StatementParser(Parser parser) {
        this.parser = parser;
    }

    public Statement parse() {
        if (parser.getCurrentToken() == Return.INSTANCE) return parseReturnStatement();
        else if (parser.getCurrentToken() == Variable.INSTANCE) return parseVarStatement();
        else return parseExpressionStatement();
    }

    private ReturnStatement parseReturnStatement() {
        // return <expression | value>;
        ReturnStatement returnStatement = new ReturnStatement(parser.getCurrentToken()); // return
        parser.readAndMoveOnNextToken();

        returnStatement.setValue(parser.getExpressionParser().parseExpression(Precedence.LOWEST.getNumber())); // return <expression | value>

        if (parser.getNextToken() == Semicolon.INSTANCE) // return <expression | value>;
            parser.readAndMoveOnNextToken();

        return returnStatement;
    }


    public BlockStatement parseBlockStatement() {
        // { <statements> }
        BlockStatement blockStatement = new BlockStatement(parser.getCurrentToken()); // {
        parser.readAndMoveOnNextToken(); // { <first statement>

        // while current token is not '}' and there are still tokens left to read
        while (!(parser.getCurrentToken() == Rb.INSTANCE) && !(parser.getCurrentToken() == Eof.INSTANCE)) {
            Statement statement = parser.getStatementParser().parse();
            if (statement != null)
                blockStatement.getStatements().add(statement);

            parser.readAndMoveOnNextToken();
        }
        return blockStatement;
    }


    private VarStatement parseVarStatement() {
        // var <identifier> = <expression | value>;
        VarStatement varStatement = new VarStatement(parser.getCurrentToken()); // var

        // var <identifier>
        if (!(parser.getNextToken() instanceof com.github.konstantinevashalomidze.interpreter.token.types.Identifier)) {
            return null;
        }
        parser.readAndMoveOnNextToken();

        varStatement.setName(new Identifier(parser.getCurrentToken()));

        // var <identifier> =
        if (!parser.expectNextToken(Assign.INSTANCE))
            return null;

        parser.readAndMoveOnNextToken();
        varStatement.setValue(parser.getExpressionParser().parseExpression(Precedence.LOWEST.getNumber())); // var <identifier> = <expression | value>

        // var <identifier> = <expression | value>;
        if (parser.getNextToken() == Semicolon.INSTANCE)
            parser.readAndMoveOnNextToken();
        return varStatement;
    }

    private ExpressionStatement parseExpressionStatement() {
        ExpressionStatement expressionStatement = new ExpressionStatement(parser.getCurrentToken(), parser.getExpressionParser().parseExpression(Precedence.LOWEST.getNumber()));

        if (parser.getNextToken() == Semicolon.INSTANCE)
            parser.readAndMoveOnNextToken();
        return expressionStatement;

    }
}
