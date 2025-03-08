package org.example.lexer;

import org.example.token.Token;
import org.example.token.TokenType;

import java.util.function.Function;


/**
 *
 * @author Konstantine Vashalomidze
 */
public class Lexer
{
    private String input;
    private int position; // current index of character in input
    private int readPosition; // next valid character index in input
    private char ch; // current character being read from input

    public Lexer(String input)
    {
        this.input = input;
        this.readChar();
    }

    /**
     * Reads next character from the input, updates 'position' to 'readPosition' and increments read position.
     */
    public void readChar() {
        if (readPosition >= input.length()) // EOF input reached
            this.ch = '\0';
        else
            this.ch = this.input.charAt(this.readPosition);
        this.position = this.readPosition;
        this.readPosition += 1;
    }

    /**
     * retrieve next token in input. Token is set of characters grouped together.
     * @return next token in input
     */
    public Token readToken() {
        Token token;

        this.skipWhiteSpace();

        // detect token type by value value and return it
        switch (this.ch) {
            case '=' -> { // could be assignment or equals check.
                switch (this.peekChar()) {
                    case '=' -> { // equals
                        this.readChar();
                        token = new Token(TokenType.EQ, TokenType.EQ.getValue());
                    }
                    default -> token = new Token(TokenType.ASSIGN, TokenType.ASSIGN.getValue()); // assignment
                }
            }
            case '-' -> token = new Token(TokenType.MINUS, TokenType.MINUS.getValue());
            case '!' -> { // could be not or not equal
                switch (this.peekChar()) {
                    case '=' -> { // not equal case
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
            // word to read is identifier
            // fn
            // var
            // true
            // false
            // if
            // else
            // return
            // or integer
            default -> {
                if (this.isLetter(this.ch))
                {
                    String literal = this.read(this::isLetter); // read whole word like var or false for example
                    TokenType tokenType = TokenType.lookupIdentifier(literal); // determine its TokenType
                    token = new Token(tokenType, literal);
                    return token; // we return since we don't want to read character, read() method does this itself
                }
                else if (this.isDigit(this.ch))
                    return new Token(TokenType.INTEGER, this.read(this::isDigit)); // same here
                else // undefined word
                    token = new Token(TokenType.ILLEGAL, String.valueOf(this.ch));
            }

        }

        this.readChar(); // indirectly means finding next word in input since we use skip whitespace method most likely

        return token;
    }

    private boolean isLetter(char ch) {
        return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z' || ch == '_';
    }

    private boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9';
    }

    /**
     *  reads set of letters or digits grouped together in input for example if the input is
     * @param f predicate returning true if this character should be read false otherwise
     * @return word string that was read depending on predicate
     */
    private String read(Function<Character, Boolean> f) {
        int positionStart = this.position; // started reading
        while (f.apply(this.ch)) // while it's our desired character
            this.readChar();
        return this.input.substring(positionStart, this.position);
    }

    /**
     * Retrieve next character in the input to read.
     * @return '\0' if ther is no char to read or next character to read.
     */
    public char peekChar()
    {
        if (this.readPosition >= input.length())
            return '\0';
        else
            return this.input.charAt(this.readPosition);
    }

    /**
     * Skips white spaces by reading characters
     */
    private void skipWhiteSpace()
    {
        while (this.ch == ' ' || this.ch == '\t' || this.ch == '\n' || this.ch == '\r')
            this.readChar();
    }

}
