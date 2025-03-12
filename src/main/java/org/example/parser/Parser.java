package org.example.parser;

import org.example.ast.expression.*;
import org.example.ast.expression.BooleanLiteral;
import org.example.ast.node.Program;
import org.example.ast.statement.*;
import org.example.lexer.Lexer;
import org.example.token.Token;
import org.example.token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Pratt Parsing Implementation: A powerful technique for parsing expressions
 * This approach allows for flexible and extensible parsing of complex expressions
 * by using prefix and infix parsing functions dynamically registered for different token types
 *
 * @author Konstantine Vashalomidze
 */
public class Parser
{



    private Lexer lexer;
    private List<String> errors;
    private Token currentToken;
    private Token peekToken;
    private Map<TokenType, Prefix> prefixParseFns; // functions that parse prefix tokens like ! or - or integer
    private Map<TokenType, Infix> infixParseFns; // functios to parse infix tokens like == or + and so on.


    /**
     * parser constructor sets up the parsing environment with dynamic parsing strategies.
     * uses the Pratt parsing technique to handle prefix and infix expressions
     *
     * @param lexer The lexer responsible for breaking input into tokens
     */
    public Parser(Lexer lexer) {
        this.lexer = lexer; // Initialize core parsing components
        this.errors = new ArrayList<>();

        this.prefixParseFns = new HashMap<>();
        this.infixParseFns = new HashMap<>();

        /* dynamically register parsing functions for different token types
           this approach allows for easy extension of the parser's capabilities */
        initializeParsingStrategies();

        this.readAndMoveOnNextToken(); // sets peek token
        this.readAndMoveOnNextToken(); // sets current token to peek token and advances peek token

    }

    /**
     * initializes parsing strategies for different token types.
     * this method demonstrates the extensibility of the Pratt parsing approach.
     */
    private void initializeParsingStrategies() {
        // prefix parsing registrations (handle tokens at the start of expressions)
        registerPrefix(TokenType.IDENTIFIER, () -> new Identifier(currentToken, currentToken.getLiteral()));
        registerPrefix(TokenType.INTEGER, () -> {
            IntegerLiteral literal = new IntegerLiteral(currentToken);
            try {
                int value = Integer.parseInt(currentToken.getLiteral());
                literal.setValue(value);
                return literal;
            } catch (NumberFormatException ex) {
                return null;
            }
        });
        // shared prefix expression handler for unary operators
        Prefix parsePrefixExpressionFn = () -> {
            // >!(
            PrefixExpression prefixExpression = new PrefixExpression(currentToken, currentToken.getLiteral());
            readAndMoveOnNextToken();
            // !>(
            prefixExpression.setRight(parseExpression(Precedence.PREFIX));
            // token is ! and right is (
            return prefixExpression;
        };
        registerPrefix(TokenType.BANG, parsePrefixExpressionFn);
        registerPrefix(TokenType.MINUS, parsePrefixExpressionFn);
        registerPrefix(TokenType.TRUE, () -> new BooleanLiteral(currentToken, currentTokenIs(TokenType.TRUE)));
        registerPrefix(TokenType.FALSE, () -> new BooleanLiteral(currentToken, !currentTokenIs(TokenType.FALSE)));
        registerPrefix(TokenType.LP, () -> {
           readAndMoveOnNextToken();

           Expression expression = parseExpression(Precedence.LOWEST);

           if (!expectPeek(TokenType.RP)) {
               return null;
           }

            return expression;
        });
        registerPrefix(TokenType.IF, () -> {
            IfExpression expression = new IfExpression(currentToken);

            if (!expectPeek(TokenType.LP)) {
                return null;
            }

            this.readAndMoveOnNextToken();
            expression.setCondition(parseExpression(Precedence.LOWEST));

            if (!expectPeek(TokenType.RP))
                return null;

            if (!expectPeek(TokenType.LB))
                return null;

            expression.setConsequence(parseBlockStatement());

            if (peekTokenIs(TokenType.ELSE))
            {
                readAndMoveOnNextToken();

                if (!expectPeek(TokenType.LB))
                    return null;

                expression.setAlternative(parseBlockStatement());
            }

            return expression;
        });


        registerPrefix(TokenType.FUNCTION, () -> {
            FunctionLiteral functionLiteral = new FunctionLiteral(currentToken);

            if (!expectPeek(TokenType.LP))
                return null;

            List<Identifier> identifiers = new ArrayList<>();

            /* can be extracted as fucntion */
            /* start */
            if (peekTokenIs(TokenType.RP)) {
                readAndMoveOnNextToken();
            }
            else
            {
                readAndMoveOnNextToken();

                Identifier identifier = new Identifier(currentToken, currentToken.getLiteral());
                identifiers.add(identifier);

                while (peekTokenIs(TokenType.COMMA)) {
                    readAndMoveOnNextToken();
                    readAndMoveOnNextToken();
                    identifier = new Identifier(currentToken, currentToken.getLiteral());
                    identifiers.add(identifier);
                }

                if (!expectPeek(TokenType.RP)) {
                    return null;
                }
            }
            /* end */

            functionLiteral.setParameters(identifiers);

            if (!expectPeek(TokenType.LB))
                return null;


            functionLiteral.setBody(parseBlockStatement());

            return functionLiteral;
        });


        Infix parseInfixExpressionFn = (left) -> { // left is already parsed expression
            /* we create infix expression on current token */
            InfixExpression infixExpression = new InfixExpression(currentToken, currentToken.getLiteral(), left);

            Precedence precedence = currentToken.getPrecedence(); // remember the infix operator precedence before advancing
            readAndMoveOnNextToken();
            infixExpression.setRight(parseExpression(precedence)); // parse right side, meaning remaining expression
            return infixExpression; // fully parsed expression
        };
        registerInfix(TokenType.PLUS, parseInfixExpressionFn);
        registerInfix(TokenType.MINUS, parseInfixExpressionFn);
        registerInfix(TokenType.SLASH, parseInfixExpressionFn);
        registerInfix(TokenType.ASTERISK, parseInfixExpressionFn);
        registerInfix(TokenType.EQ, parseInfixExpressionFn);
        registerInfix(TokenType.NOT_EQ, parseInfixExpressionFn);
        registerInfix(TokenType.LT, parseInfixExpressionFn);
        registerInfix(TokenType.GT, parseInfixExpressionFn);
        registerInfix(TokenType.LP, (Expression function) -> {
            CallExpression callExpression = new CallExpression(currentToken, function);

            if (peekTokenIs(TokenType.RP)) {
                readAndMoveOnNextToken();
                return callExpression;
            }

            readAndMoveOnNextToken();
            callExpression.getArguments().add(parseExpression(Precedence.LOWEST));

            while (peekTokenIs(TokenType.COMMA)) {
                readAndMoveOnNextToken();
                readAndMoveOnNextToken();

                callExpression.getArguments().add(parseExpression(Precedence.LOWEST));
            }


            if (!expectPeek(TokenType.RP))
                return null;

            return callExpression;

        });

    }



    public Parser() {

    }


    public void readAndMoveOnNextToken() {
        this.currentToken = this.peekToken;
        this.peekToken = lexer.readAndMoveOnNextToken();
    }

    /**
     * core parsing method that transforms a stream of tokens into an Abstract Syntax Tree (AST).
     *
     *
     * create a program container
     * iterate through tokens until EOF
     * parse each statement and add to program
     *
     * @return Fully parsed Program representing the entire input
     */
    public Program parseProgram() {
        Program program = new Program();

        while (currentToken.getTokenType() != TokenType.EOF) // continue parsing until end of file
        {
            // statement types are var, return, expression, block
            Statement statement = parseStatement(); // applies parsing strategy
            if (statement != null) {
                program.getStatements().add(statement);
            }
            // current token is semicolon so we need to advance current token to the next one
            readAndMoveOnNextToken();
        }

        return program;
    }

    private Statement parseStatement() {
        return switch (currentToken.getTokenType()) {
            case VARIABLE -> parseVarStatement();
            case RETURN -> pareseReturnStatement();
            default -> parseExpressionStatement();
        };
    }


    /**
     * parses var statement having the form VARIABLE IDENTIFIER ASSIGN EXPRESSION SEMICOLON
     * @return VarStatement
     */
    private VarStatement parseVarStatement() {
        VarStatement varStatement = new VarStatement();
        varStatement.setToken(currentToken); // equip var token
        /* equip name of the variable */
        if (!expectPeek(TokenType.IDENTIFIER))
            return null;
        varStatement.setName(new Identifier(currentToken, currentToken.getLiteral()));
        if (!expectPeek(TokenType.ASSIGN))
            return null;
        /* we don't need to save assign in var statment since it's obvious and abstracted */
        readAndMoveOnNextToken();
        varStatement.setValue(parseExpression(Precedence.LOWEST)); // parse expression after assign operator and equip

        if (peekTokenIs(TokenType.SEMICOLON)) {
            readAndMoveOnNextToken();
        }
        return varStatement;
    }

    private ReturnStatement pareseReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement();
        returnStatement.setToken(currentToken); // set return as token in return statement
        readAndMoveOnNextToken();


        returnStatement.setValue(parseExpression(Precedence.LOWEST));

        if (peekTokenIs(TokenType.SEMICOLON))
            readAndMoveOnNextToken();

        return returnStatement;
    }


    private ExpressionStatement parseExpressionStatement() {
        ExpressionStatement expressionStatement = new ExpressionStatement(currentToken, parseExpression(Precedence.LOWEST));

        if (peekTokenIs(TokenType.SEMICOLON)) {
            readAndMoveOnNextToken();
        }

        return expressionStatement;
    }

    private BlockStatement parseBlockStatement() {
        BlockStatement blockStatement = new BlockStatement(currentToken);

        this.readAndMoveOnNextToken();

        while (!currentTokenIs(TokenType.RB) && !currentTokenIs(TokenType.EOF))
        {
            Statement statement = parseStatement();
            if (statement != null) {
                blockStatement.getStatements().add(statement);
            }

            this.readAndMoveOnNextToken();
        }

        return blockStatement;
    }

    /**
     * expression parsing method implementing Pratt parsing algorithm.
     * prefix expressions (like -5, !true)
     * infix expressions (like 5 + 3, a * b)
     * precedence-based parsing to resolve complex expressions
     *
     * @param precedence current precedence level to control expression parsing depth
     * @return parsed Expression with resolved precedence
     */
    private Expression parseExpression(Precedence precedence) {
        // Attempt to find a prefix parsing function for the current token
        Prefix prefix = this.prefixParseFns.get(this.currentToken.getTokenType());
        if (prefix == null) // No prefix parsing strategy found
            return null;

        Expression leftExpression = prefix.parseFn(); // Parse the initial (left) expression

        // Continue parsing while next token suggests a higher precedence infix expression
        while (!this.peekTokenIs(TokenType.SEMICOLON) && precedence.getValue() < this.peekPrecedence().getValue()) {
            Infix infix = this.infixParseFns.get(this.peekToken.getTokenType());
            if (infix == null)
                return leftExpression;

            this.readAndMoveOnNextToken();

            leftExpression = infix.parseFn(leftExpression);
        }

        return leftExpression;

    }


    private boolean expectPeek(TokenType tokenType) {
        if (peekTokenIs(tokenType)) {
            readAndMoveOnNextToken();
            return true;
        } else {
            peekError(tokenType);
            return false;
        }
    }

    /**
     *
     * This method captures and collects parsing errors without halting the entire process.
     *
     * @param tokenType Expected token type that was not found
     */
    private void peekError(TokenType tokenType) {
        String message = String.format("expected next token to be %s, got %s instead", tokenType, this.peekToken.getTokenType());
        errors.add(message);
    }


    private Precedence peekPrecedence() {
        return this.peekToken.getPrecedence();
    }



    private boolean currentTokenIs(TokenType tokenType) {
        return this.currentToken.getTokenType() == tokenType;
    }

    private boolean peekTokenIs(TokenType tokenType) {
        return this.peekToken.getTokenType() == tokenType;
    }

    private void registerPrefix(TokenType tokenType, Prefix prefixParseFn) {
        this.prefixParseFns.put(tokenType, prefixParseFn);
    }

    private void registerInfix(TokenType tokenType, Infix infixParseFn) {
        this.infixParseFns.put(tokenType, infixParseFn);
    }


    public Lexer getLexer() {
        return lexer;
    }

    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }

    public Token getPeekToken() {
        return peekToken;
    }

    public void setPeekToken(Token peekToken) {
        this.peekToken = peekToken;
    }
}
