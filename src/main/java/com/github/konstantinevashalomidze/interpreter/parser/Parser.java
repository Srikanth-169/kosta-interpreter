package com.github.konstantinevashalomidze.interpreter.parser;

import com.github.konstantinevashalomidze.interpreter.ast.expression.*;
import com.github.konstantinevashalomidze.interpreter.ast.expression.Identifier;
import com.github.konstantinevashalomidze.interpreter.ast.statement.*;
import com.github.konstantinevashalomidze.interpreter.token.types.*;
import com.github.konstantinevashalomidze.interpreter.ast.node.Program;
import com.github.konstantinevashalomidze.interpreter.lexer.Lexer;
import com.github.konstantinevashalomidze.interpreter.parser.infix.Infix;
import com.github.konstantinevashalomidze.interpreter.parser.prefix.Prefix;
import com.github.konstantinevashalomidze.interpreter.token.Precedence;
import com.github.konstantinevashalomidze.interpreter.token.Token;

import java.lang.Integer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Takes lexer and produced Abstract Syntax Tree via Pratt Parsing Algorithm.
 * @author Konstantine Vashalomidze
 */

public class Parser {
    private Lexer lexer;
    private List<String> errors;

    private Token currentToken;
    private Token nextToken;

    private Map<String, Prefix> prefixParsingFunctions;
    private Map<String, Infix> infixParsingFunctions;


    public Parser(Lexer lexer) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.lexer = lexer;
        errors = new ArrayList<>();
        prefixParsingFunctions = new HashMap<>();
        infixParsingFunctions = new HashMap<>();

        initialiseParsingFunctions();

        readAndMoveOnNextToken(); // sets next token to first token in input
        readAndMoveOnNextToken(); // sets current token to first token in input and sets next token to second token
    }

    private void initialiseParsingFunctions() {
        /* start by initialising prefix parsing functions */
        prefixParsingFunctions.put(Identifier.class.getSimpleName(), () -> new Identifier(currentToken));
        prefixParsingFunctions.put(Integer.class.getSimpleName(), () -> new IntegerLiteral(currentToken));
        prefixParsingFunctions.put(Bang.class.getSimpleName(), this::parseBangOrMinus);
        prefixParsingFunctions.put(MinusPrefix.class.getSimpleName(), this::parseBangOrMinus);
        prefixParsingFunctions.put(True.class.getSimpleName(), () -> new BooleanLiteral(currentToken));
        prefixParsingFunctions.put(False.class.getSimpleName(), () -> new BooleanLiteral(currentToken));
        prefixParsingFunctions.put(Lp.class.getSimpleName(), this::parseLp);
        prefixParsingFunctions.put(If.class.getSimpleName(), this::parseIf);
        prefixParsingFunctions.put(Function.class.getSimpleName(), this::parseFunction);

        /* continue by initialising infix parsing functions */
        infixParsingFunctions.put(Plus.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(MinusInfix.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(Slash.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(Asterisk.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(Eq.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(NotEq.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(Lt.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(Gt.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(And.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(Or.class.getSimpleName(), this::parseInfixOperators);
        infixParsingFunctions.put(Lp.class.getSimpleName(), this::parseFunctionCall); // <identifier>(<arguments>);
    }

    private Expression parseFunctionCall(Expression left) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        CallExpression callExpression = new CallExpression(currentToken, left); // <identifier>(

        if (nextToken instanceof Rp)
        {
            readAndMoveOnNextToken(); // <identifier>()
            return callExpression;
        }

        readAndMoveOnNextToken(); // <identifier>(<arguments>
        callExpression.getArguments().add(parseExpression(0)); // parses argument

        while (nextToken instanceof Comma)
        {
            readAndMoveOnNextToken(); // move to comma
            readAndMoveOnNextToken(); // move to argument

            callExpression.getArguments().add(parseExpression(0));
        }

        if (!expectNextToken(Rp.class.getSimpleName())) // <identifier>(<arguments>)
            return null;

        return callExpression;
    }


    private Expression parseInfixOperators(Expression left) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // <expression | value> <operator> <expression | value>
        InfixExpression infixExpression = new InfixExpression(currentToken, left); // <expression | value> <operator>

        int currentPrecedence = currentToken.precedence().getNumber(); // remember before advancing
        readAndMoveOnNextToken();
        infixExpression.setRight(parseExpression(currentPrecedence)); // <expression | value> <operator> <expression | value>
        return infixExpression;



    }


    private Expression parseFunction() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // fn(<arguments>) { <statements> };
        FunctionLiteral functionLiteral = new FunctionLiteral(currentToken); // fn

        if (!expectNextToken(Lp.class.getSimpleName())) // fn(
            return null;

        List<Identifier> identifiers = new ArrayList<>();
        if (nextToken instanceof Rp)
            readAndMoveOnNextToken(); // fn()
        else
        {
            // fn (<arguments>
            readAndMoveOnNextToken();
            Identifier identifier = new Identifier(currentToken);
            identifiers.add(identifier);

            while (nextToken instanceof Comma)
            {
                readAndMoveOnNextToken(); // moves on comma
                readAndMoveOnNextToken(); // moves on argument identifier
                identifier = new Identifier(currentToken);
                identifiers.add(identifier);
            }

            if (!expectNextToken(Rp.class.getSimpleName())) // fn (<arguments>)
                return null;
        }

        functionLiteral.setParameters(identifiers);

        if (!expectNextToken(Lb.class.getSimpleName())) // fn (<arguments>) {
            return null;

        functionLiteral.setBody(parseBlockStatement());

        return functionLiteral;
    }

    private IfExpression parseIf() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // if (<condition>) { <consequence> } else { <alternative> }
        IfExpression ifExpression = new IfExpression(currentToken);
        if (!expectNextToken(Lp.class.getSimpleName())) // if (
            return null;

        readAndMoveOnNextToken();
        ifExpression.setCondition(parseExpression(0)); // if (<condition>

        if (!expectNextToken(Rp.class.getSimpleName())) // if (<condition>)
            return null;

        if (!expectNextToken(Lb.class.getSimpleName())) // if (<condition>) {
            return null;

        ifExpression.setConsequence(parseBlockStatement()); // if (<condition>) { <consequence> }

        // evaluate else block if present at all
        if (nextToken instanceof Else)
        {
            readAndMoveOnNextToken(); // if (<condition>) { <consequence> } else

            if (!expectNextToken(Lb.class.getSimpleName())) // if (<condition>) { <consequence> } else {
                return null;

            ifExpression.setAlternative(parseBlockStatement());
        }

        return ifExpression;



    }

    private BlockStatement parseBlockStatement() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // { <statements> }
        BlockStatement blockStatement = new BlockStatement(currentToken); // {
        readAndMoveOnNextToken(); // { <first statement>

        // while current token is not '}' and there are still tokens left to read
        while (!(currentToken instanceof Rb) && !(currentToken instanceof Eof))
        {
            Statement statement = parseStatement();
            if (statement != null)
                blockStatement.getStatements().add(statement);

            readAndMoveOnNextToken();
        }
        return blockStatement;
    }

    private Statement parseStatement() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return switch (currentToken.getClass().getSimpleName())
        {
            case "Return" -> parseReturnStatement();
            case "Variable" -> parseVarStatement();
            default -> parseExpressionStatement();
        };


    }

    private ExpressionStatement parseExpressionStatement() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ExpressionStatement expressionStatement = new ExpressionStatement(currentToken, parseExpression(Precedence.LOWEST.getNumber()));

        if (nextToken instanceof Semicolon)
            readAndMoveOnNextToken();

        return expressionStatement;

    }

    private VarStatement parseVarStatement() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // var <identifier> = <expression | value>;
        VarStatement varStatement = new VarStatement(currentToken); // var

        // var <identifier>
        if (!expectNextToken(Identifier.class.getSimpleName()))
            return null;

        varStatement.setName(new Identifier(currentToken));

        // var <identifier> =
        if (!expectNextToken(Assign.class.getSimpleName()))
            return null;

        readAndMoveOnNextToken();
        varStatement.setValue(parseExpression(0)); // var <identifier> = <expression | value>

        // var <identifier> = <expression | value>;
        if (nextToken instanceof Semicolon)
            readAndMoveOnNextToken();

        return varStatement;
    }

    private ReturnStatement parseReturnStatement() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // return <expression | value>;
        ReturnStatement returnStatement = new ReturnStatement(currentToken); // return
        readAndMoveOnNextToken();

        returnStatement.setValue(parseExpression(0)); // return <expression | value>

        if (nextToken instanceof Semicolon) // return <expression | value>;
            readAndMoveOnNextToken();

        return returnStatement;

    }

    private PrefixExpression parseBangOrMinus() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // prefix expression has form <operator><operand>
        PrefixExpression prefixExpression = new PrefixExpression(currentToken); // !<operand>
        readAndMoveOnNextToken(); // move on operand
        prefixExpression.setRight(parseExpression(Precedence.PREFIX.getNumber())); // parse operand -> !parsedOperand
        return prefixExpression;
    }

    private Expression parseLp() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        readAndMoveOnNextToken(); // {<operand>
        Expression expression = parseExpression(0); // parses <operand>
        if (!expectNextToken(Rp.class.getSimpleName()))
            return null;
        return expression; // returns <operand>
    }

    private boolean expectNextToken(String expectedName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String actualName = nextToken.getClass().getSimpleName();
        if (!actualName.equals(expectedName))
        {
            errors.add(String.format("Expected '%s', got '%s'", expectedName, actualName));
            return false;
        }
        readAndMoveOnNextToken();
        return true;
    }


    private Expression parseExpression(int precedence) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // check if we have prefix parsing function associated with current token
        Prefix prefixParsingFn = prefixParsingFunctions.get(currentToken.getClass().getSimpleName());
        if (prefixParsingFn == null)
            return null;


        Expression leftExpression = prefixParsingFn.parseFn(); // like 5 for example in 5 * 9 or - in -5


        // continue parsing till next token suggests a higher precedence infix expression
        while (!(nextToken instanceof Semicolon) && precedence < nextToken.precedence().getNumber())
        {
            Infix infixParsingFn = infixParsingFunctions.get(nextToken.getClass().getSimpleName());
            if (infixParsingFn == null)
                return leftExpression;
            readAndMoveOnNextToken();
            leftExpression = infixParsingFn.parseFn(leftExpression);
        }

        return leftExpression;

    }


    public void readAndMoveOnNextToken() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        currentToken = nextToken;
        nextToken = lexer.readAndMoveOnNextToken();
    }


    public Program parseProgram() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Program program = new Program();

        // current token is not end of the file token
        while (!(currentToken instanceof Eof))
        {
            Statement statement = parseStatement();
            if (statement != null)
                program.getStatements().add(statement);

            readAndMoveOnNextToken();
        }

        return program;
    }


    public List<String> errors() {
        return errors;
    }
}
