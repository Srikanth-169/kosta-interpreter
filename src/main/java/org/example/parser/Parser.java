package org.example.parser;

import org.example.ast.expression.Expression;
import org.example.ast.expression.Identifier;
import org.example.ast.expression.IntegerLiteral;
import org.example.ast.expression.PrefixExpression;
import org.example.ast.node.Program;
import org.example.ast.statement.ExpressionStatement;
import org.example.ast.statement.ReturnStatement;
import org.example.ast.statement.Statement;
import org.example.ast.statement.VarStatement;
import org.example.lexer.Lexer;
import org.example.token.Token;
import org.example.token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser
{
    private Lexer lexer;
    private List<String> errors;
    private Token currentToken;
    private Token peekToken;
    private Map<TokenType, Prefix> prefixParseFns;

    private Map<TokenType, Infix> infixParseFns;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.errors = new ArrayList<>();

        this.prefixParseFns = new HashMap<>();
        /* register prefix parsing functions */
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
        Prefix parsePrefixExpressionFn = () -> {
            PrefixExpression prefixExpression = new PrefixExpression(this.currentToken, this.currentToken.getLiteral());
            this.readToken();
            prefixExpression.setRight(this.parseExpression(Precedence.PREFIX));
            return prefixExpression;
        };
        this.registerPrefix(TokenType.BANG, parsePrefixExpressionFn);
        this.registerPrefix(TokenType.MINUS, parsePrefixExpressionFn);

        this.infixParseFns = new HashMap<>();

        this.readToken();
        this.readToken();

    }

    public Parser() {

    }


    public void readToken() {
        this.currentToken = this.peekToken;
        this.peekToken = this.lexer.readToken();
    }


    public Program parseProgram() {
        Program program = new Program();

        while (currentToken.getTokenType() != TokenType.EOF)
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
        // TODO: extract and evaluate expression
        while (!this.currentTokenIs(TokenType.SEMICOLON))
            this.readToken();
        return varStatement;
    }

    private ReturnStatement pareseReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement();
        returnStatement.setToken(this.currentToken);
        this.readToken();

        // TODO: extract and evaluate expression


        while (!this.currentTokenIs(TokenType.SEMICOLON))
            this.readToken();
        return returnStatement;
    }


    private ExpressionStatement parseExpressionStatement() {
        ExpressionStatement expressionStatement = new ExpressionStatement(this.currentToken, this.parseExpression(Precedence.LOWEST));

        if (this.peekTokenHas(TokenType.SEMICOLON)) {
            this.readToken();
        }

        return expressionStatement;


    }

    private Expression parseExpression(Precedence precedence) {
        Prefix prefix = this.prefixParseFns.get(this.currentToken.getTokenType());

        if (prefix == null)
            return null;

        Expression leftExpression = prefix.parseFn();

        return leftExpression;

    }



    private boolean expectPeek(TokenType tokenType) {
        if (this.peekTokenHas(tokenType)) {
            this.readToken();
            return true;
        } else {
            this.peekError(tokenType);
            return false;
        }
    }


    private void peekError(TokenType tokenType) {
        String message = String.format("expected next token to be %s, got %s instead", tokenType, this.peekToken.getTokenType());
        errors.add(message);
    }

    private boolean currentTokenIs(TokenType tokenType) {
        return this.currentToken.getTokenType() == tokenType;
    }

    private boolean peekTokenHas(TokenType tokenType) {
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
