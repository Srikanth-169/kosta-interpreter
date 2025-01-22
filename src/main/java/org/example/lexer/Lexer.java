package org.example.lexer;

import org.example.token.Token;
import org.example.token.TokenType;

import java.util.function.Function;

public class Lexer
{
    private String input;
    private int position;
    private int readPosition;
    private char ch;

    public Lexer(String input)
    {
        this.input = input;
        this.readChar();
    }

    public void readChar() {
        if (readPosition >= input.length()) // EOF input reached
            this.ch = '\0';
        else
            this.ch = this.input.charAt(this.readPosition);
        this.position = this.readPosition;
        this.readPosition += 1;
    }

    public Token readToken() {
        Token token;

        this.skipWhiteSpace();
        switch (this.ch) {
            case '=' -> {
                switch (this.peekChar()) {
                    case '=' -> {
                        this.readChar();
                        token = new Token(TokenType.EQ, TokenType.EQ.getValue());
                    }
                    default -> token = new Token(TokenType.ASSIGN, TokenType.ASSIGN.getValue());
                }
            }
            case '-' -> token = new Token(TokenType.MINUS, TokenType.MINUS.getValue());
            case '!' -> {
                switch (this.peekChar()) {
                    case '=' -> {
                        this.readChar();
                        token = new Token(TokenType.NOT_EQ, TokenType.NOT_EQ.getValue());
                    }
                    default -> token = new Token(TokenType.BANG, TokenType.BANG.getValue());
                }
            }
            case '/' -> token = new Token(TokenType.SLASH, TokenType.SLASH.getValue());
            case '*' -> token = new Token(TokenType.ASTERISK, TokenType.ASTERISK.getValue());
            case '<' -> token = new Token(TokenType.LT, TokenType.LT.getValue());
            case '>' -> token = new Token(TokenType.GT, TokenType.GT.getValue());
            case ';' -> token = new Token(TokenType.SEMICOLON, TokenType.SEMICOLON.getValue());
            case '(' -> token = new Token(TokenType.LP, TokenType.LP.getValue());
            case ')' -> token = new Token(TokenType.RP, TokenType.RP.getValue());
            case '+' -> token = new Token(TokenType.PLUS, TokenType.PLUS.getValue());
            case ',' -> token = new Token(TokenType.COMMA, TokenType.COMMA.getValue());
            case '{' -> token = new Token(TokenType.LB, TokenType.LB.getValue());
            case '}' -> token = new Token(TokenType.RB, TokenType.RB.getValue());
            case '\0' -> token = new Token(TokenType.EOF, "");
            default -> {
                if (this.isLetter(this.ch))
                {
                    String literal = this.read(this::isLetter);
                    TokenType tokenType = TokenType.lookupIdentifier(literal);
                    token = new Token(tokenType, literal);
                    return token;
                }
                else if (this.isDigit(this.ch))
                    return new Token(TokenType.INTEGER, this.read(this::isDigit));
                else
                    token = new Token(TokenType.ILLEGAL, String.valueOf(this.ch));
            }

        }

        this.readChar();

        return token;
    }

    private boolean isLetter(char ch) {
        return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z' || ch == '_';
    }

    private boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9';
    }

    /* reads set of letters or digits grouped together in input */
    private String read(Function<Character, Boolean> f) {
        int positionStart = this.position;
        while (f.apply(this.ch))
            this.readChar();
        return this.input.substring(positionStart, this.position);
    }


    public char peekChar()
    {
        if (this.readPosition >= input.length())
        {
            return '\0';
        }
        else
        {
            return this.input.charAt(this.readPosition);
        }
    }

    private void skipWhiteSpace()
    {
        while (this.ch == ' ' || this.ch == '\t' || this.ch == '\n' || this.ch == '\r')
            this.readChar();
    }





}
