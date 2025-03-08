package org.example.parser;

import org.example.ast.expression.*;
import org.example.ast.expression.Boolean;
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
 * @author Konstantine Vashalomidze
 */
public class Parser
{



    private Lexer lexer;
    private List<String> errors;
    private Token currentToken;
    private Token peekToken;
    private Map<TokenType, Prefix> prefixParseFns; // functions that parse prefix tokens like ! or - or integer
    private Map<TokenType, Infix> infixParseFns; // functios to parse infix tokens like == or + and so on..


    /**
     * Parser constructor sets up the parsing environment with dynamic parsing strategies.
     * Uses the Pratt parsing technique to handle prefix and infix expressions flexibly.
     *
     * Key design considerations:
     * - Supports extensible expression parsing
     * - Allows easy addition of new token types and parsing rules
     * - Implements precedence-based expression parsing
     *
     * @param lexer The lexer responsible for breaking input into tokens
     */
    public Parser(Lexer lexer) {
        this.lexer = lexer; // Initialize core parsing components
        this.errors = new ArrayList<>();

        /* Set up parsing strategy maps for different token types */
        this.prefixParseFns = new HashMap<>();
        this.infixParseFns = new HashMap<>();


        /* Dynamically register parsing functions for different token types
           This approach allows for easy extension of the parser's capabilities */
        initializeParsingStrategies();

        this.readToken(); // sets peek token
        this.readToken(); // sets current token to peek token and advances peek token

    }

    /**
     * Initializes parsing strategies for different token types.
     * This method demonstrates the extensibility of the Pratt parsing approach.
     *
     * Registrations include:
     * - Prefix parsers for identifiers, integers, negation, logical NOT
     * - Infix parsers for arithmetic and comparison operators
     */
    private void initializeParsingStrategies() {
        // Prefix parsing registrations (handle tokens at the start of expressions)
        this.registerPrefix(TokenType.IDENTIFIER, () -> new Identifier(this.currentToken, currentToken.getLiteral()));
        this.registerPrefix(TokenType.INTEGER, () -> {
            IntegerLiteral literal = new IntegerLiteral(this.currentToken);
            try {
                int value = Integer.parseInt(this.currentToken.getLiteral());
                literal.setValue(value);
                return literal;
            } catch (NumberFormatException ex) {
                this.errors.add(ex.getMessage());
                return null;
            }
        });
        // Shared prefix expression handler for unary operators
        Prefix parsePrefixExpressionFn = () -> {
            PrefixExpression prefixExpression = new PrefixExpression(this.currentToken, this.currentToken.getLiteral());
            this.readToken();
            prefixExpression.setRight(this.parseExpression(Precedence.PREFIX));
            return prefixExpression;
        };
        this.registerPrefix(TokenType.BANG, parsePrefixExpressionFn);
        this.registerPrefix(TokenType.MINUS, parsePrefixExpressionFn);
        this.registerPrefix(TokenType.TRUE, () -> new Boolean(this.currentToken, currentTokenIs(TokenType.TRUE)));
        this.registerPrefix(TokenType.FALSE, () -> new Boolean(this.currentToken, currentTokenIs(TokenType.FALSE)));
        this.registerPrefix(TokenType.LP, () -> {
           this.readToken();

           Expression expression = this.parseExpression(Precedence.LOWEST);

           if (!this.expectPeek(TokenType.RP)) {
               return null;
           }

            return expression;
        });
        this.registerPrefix(TokenType.IF, () -> {
            IfExpression expression = new IfExpression(this.currentToken);

            if (!this.expectPeek(TokenType.LP)) {
                return null;
            }

            this.readToken();
            expression.setCondition(this.parseExpression(Precedence.LOWEST));

            if (!this.expectPeek(TokenType.RP))
                return null;

            if (!this.expectPeek(TokenType.LB))
                return null;

            expression.setConsequence(this.parseBlockStatement());

            return expression;
        });


        this.registerPrefix(TokenType.FUNCTION, () -> {
            FunctionLiteral functionLiteral = new FunctionLiteral(this.currentToken);

            if (!this.expectPeek(TokenType.LP))
                return null;

            List<Identifier> identifiers = new ArrayList<>();

            /* can be extracted as fucntion */
            /* start */
            if (this.peekTokenIs(TokenType.RP)) {
                this.readToken();
            }
            else
            {
                this.readToken();

                Identifier identifier = new Identifier(this.currentToken, this.currentToken.getLiteral());
                identifiers.add(identifier);

                while (peekTokenIs(TokenType.COMMA)) {
                    this.readToken();
                    this.readToken();
                    identifier = new Identifier(this.currentToken, this.currentToken.getLiteral());
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


        // Infix parsing registrations (handle operators between expressions)
        Infix parseInfixExpressionFn = (left) -> {
            InfixExpression infixExpression = new InfixExpression(this.currentToken, this.currentToken.getLiteral(), left);

            Precedence precedence = this.curPrecedence();
            this.readToken();
            infixExpression.setRight(this.parseExpression(precedence));
            return infixExpression;
        };
        this.registerInfix(TokenType.PLUS, parseInfixExpressionFn);
        this.registerInfix(TokenType.MINUS, parseInfixExpressionFn);
        this.registerInfix(TokenType.SLASH, parseInfixExpressionFn);
        this.registerInfix(TokenType.ASTERISK, parseInfixExpressionFn);
        this.registerInfix(TokenType.EQ, parseInfixExpressionFn);
        this.registerInfix(TokenType.NOT_EQ, parseInfixExpressionFn);
        this.registerInfix(TokenType.LT, parseInfixExpressionFn);
        this.registerInfix(TokenType.GT, parseInfixExpressionFn);
        this.registerInfix(TokenType.LP, (Expression function) -> {
            CallExpression callExpression = new CallExpression(this.currentToken, function);

            if (peekTokenIs(TokenType.RP)) {
                this.readToken();
                return callExpression;
            }

            this.readToken();
            callExpression.getArguments().add(this.parseExpression(Precedence.LOWEST));

            while (this.peekTokenIs(TokenType.COMMA)) {
                this.readToken();
                this.readToken();

                callExpression.getArguments().add(this.parseExpression(Precedence.LOWEST));
            }


            if (!this.expectPeek(TokenType.RP))
                return null;

            return callExpression;

        });

    }



    public Parser() {

    }


    public void readToken() {
        this.currentToken = this.peekToken;
        this.peekToken = this.lexer.readToken();
    }

    /**
     * Core parsing method that transforms a stream of tokens into an Abstract Syntax Tree (AST).
     *
     * Parsing strategy:
     * 1. Create a program container
     * 2. Iterate through tokens until EOF
     * 3. Parse each statement and add to program
     *
     * @return Fully parsed Program representing the entire input
     */
    public Program parseProgram() {
        Program program = new Program();

        while (currentToken.getTokenType() != TokenType.EOF) // Continue parsing until end of file
        {
            Statement statement = this.parseStatement();
            if (statement != null) {
                program.getStatements().add(statement);
            }
            this.readToken();
        }

        return program;
    }

    private Statement parseStatement() {
        return switch (currentToken.getTokenType()) {
            case VARIABLE -> this.parseVarStatement();
            case RETURN -> this.pareseReturnStatement();
            default -> this.parseExpressionStatement();
        };
    }


    private VarStatement parseVarStatement() {
        VarStatement varStatement = new VarStatement();
        varStatement.setToken(this.currentToken);
        if (!this.expectPeek(TokenType.IDENTIFIER))
            return null;
        varStatement.setName(new Identifier(currentToken, currentToken.getLiteral()));
        if (!this.expectPeek(TokenType.ASSIGN))
            return null;
        this.readToken();
        varStatement.setValue(this.parseExpression(Precedence.LOWEST));

        if (this.peekTokenIs(TokenType.SEMICOLON)) {
            this.readToken();
        }
        return varStatement;
    }

    private ReturnStatement pareseReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement();
        returnStatement.setToken(this.currentToken);
        this.readToken();

        returnStatement.setValue(this.parseExpression(Precedence.LOWEST));

        if (this.peekTokenIs(TokenType.SEMICOLON))
            this.readToken();

        return returnStatement;
    }


    private ExpressionStatement parseExpressionStatement() {
        ExpressionStatement expressionStatement = new ExpressionStatement(this.currentToken, this.parseExpression(Precedence.LOWEST));

        if (this.peekTokenIs(TokenType.SEMICOLON)) {
            this.readToken();
        }

        return expressionStatement;
    }

    private BlockStatement parseBlockStatement() {
        BlockStatement blockStatement = new BlockStatement(this.currentToken);

        this.readToken();

        while (!this.currentTokenIs(TokenType.RB) && !this.currentTokenIs(TokenType.EOF))
        {
            Statement statement = this.parseStatement();
            if (statement != null) {
                blockStatement.getStatements().add(statement);
            }

            this.readToken();
        }

        return blockStatement;
    }

    /**
     * Expression parsing method implementing Pratt parsing algorithm.
     *
     * Advanced parsing technique that handles:
     * - Prefix expressions (like -5, !true)
     * - Infix expressions (like 5 + 3, a * b)
     * - Precedence-based parsing to resolve complex expressions
     *
     * @param precedence Current precedence level to control expression parsing depth
     * @return Parsed Expression with resolved precedence
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

            this.readToken();

            leftExpression = infix.parseFn(leftExpression);
        }

        return leftExpression;

    }


    private boolean expectPeek(TokenType tokenType) {
        if (this.peekTokenIs(tokenType)) {
            this.readToken();
            return true;
        } else {
            this.peekError(tokenType);
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

    private Precedence curPrecedence() {
        return this.currentToken.getPrecedence();
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
