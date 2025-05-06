package com.github.konstantinevashalomidze.interpreter.parser;

import com.github.konstantinevashalomidze.interpreter.ast.node.Program;
import com.github.konstantinevashalomidze.interpreter.ast.statement.*;
import com.github.konstantinevashalomidze.interpreter.lexer.Lexer;
import com.github.konstantinevashalomidze.interpreter.token.Token;
import com.github.konstantinevashalomidze.interpreter.token.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Takes lexer and produced Abstract Syntax Tree via Pratt Parsing Algorithm.
 *
 * @author Konstantine Vashalomidze
 */

public class Parser {
    private final Lexer lexer;
    private final List<String> errors;

    private Token currentToken;
    private Token nextToken;

    private final StatementParser statementParser;
    private final PrefixParser prefixParser;
    private final InfixParser infixParser;
    private final ExpressionParser expressionParser;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        errors = new ArrayList<>();

        statementParser = new StatementParser(this);
        prefixParser = new PrefixParser(this);
        infixParser = new InfixParser(this);
        expressionParser = new ExpressionParser(this);

        readAndMoveOnNextToken(); // sets next token to first token in input
        readAndMoveOnNextToken(); // sets current token to first token in input and sets next token to second token
    }

    public boolean expectNextToken(Token expectedToken) {
        if (!(nextToken == expectedToken)) {
            return false;
        }
        readAndMoveOnNextToken();
        return true;
    }





    public void readAndMoveOnNextToken() {
        currentToken = nextToken;
        nextToken = lexer.readAndMoveOnNextToken();
    }


    public Program parseProgram() {
        Program program = new Program();
        // current token is not end of the file token
        while (!(currentToken == Eof.INSTANCE)) {
            Statement statement = statementParser.parse();
            if (statement != null)
                program.getStatements().add(statement);
            readAndMoveOnNextToken();
        }
        return program;
    }


    public List<String> errors() {
        return errors;
    }

    public Lexer getLexer() {
        return lexer;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public Token getNextToken() {
        return nextToken;
    }

    public StatementParser getStatementParser() {
        return statementParser;
    }

    public PrefixParser getPrefixParser() {
        return prefixParser;
    }

    public InfixParser getInfixParser() {
        return infixParser;
    }

    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }
}
