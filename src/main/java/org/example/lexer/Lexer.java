package org.example.lexer;

import org.example.token.Token;
import org.example.token.TokenType;

import java.util.List;
import java.util.function.Function;


/**
 *  Tokenizes the program into tokens. Get that tokens with readAndMoveOnNextToken().
 *
 * @author Konstantine Vashalomidze
 */
public class Lexer
{
    private String input;
    private int currentCharacterPosition; // current index of character in input
    private int nextCharacterPosition; // next valid character index in input
    private char currentCharacter; // current character being readWholeStringStartingFromCurrentCharacter from input

    public Lexer(String input)
    {
        this.input = input == null ? "" : input; // if null was provided
        moveOnNextCharacter();
    }

    /**
     * Reads next character from the input, updates 'currentCharacterPosition' to 'nextCharacterPosition' and increments readWholeStringStartingFromCurrentCharacter currentCharacterPosition.
     */
    private void moveOnNextCharacter()
    {
        if (nextCharacterPosition >= input.length()) // EOF input reached
            currentCharacter = '\0';
        else
            currentCharacter = input.charAt(nextCharacterPosition);
        currentCharacterPosition = nextCharacterPosition;
        nextCharacterPosition += 1;
    }

    /**
     * retrieve next token in input. Token is set of characters grouped together.
     * @return next token in input
     */
    public Token readAndMoveOnNextToken() {
        Token token;
        skipWhiteSpace();
        // detect token type by value value and return it
        switch (currentCharacter) {
            case '=' -> { // could be assignment or equals check.
                switch (readNextCharacter()) {
                    case '=' -> { // equals
                        moveOnNextCharacter();
                        token = new Token(TokenType.EQ, TokenType.EQ.getValue());
                    }
                    default -> token = new Token(TokenType.ASSIGN, TokenType.ASSIGN.getValue()); // assignment
                }
            }
            case '-' -> token = new Token(TokenType.MINUS, TokenType.MINUS.getValue());
            case '!' -> { // could be not or not equal
                switch (readNextCharacter()) {
                    case '=' -> { // not equal case
                        moveOnNextCharacter();
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
            // word to readWholeStringStartingFromCurrentCharacter is identifier
            // fn
            // var
            // true
            // false
            // if
            // else
            // return
            // or integer
            default -> {
                if (Character.isLetter(currentCharacter))
                {
                    String literal = readWholeStringStartingFromCurrentCharacter(Character::isLetter); // readWholeStringStartingFromCurrentCharacter whole word like var or false for example
                    TokenType tokenType = TokenType.lookupIdentifier(literal); // determine its TokenType
                    token = new Token(tokenType, literal);
                    return token; // we return since we don't want to readWholeStringStartingFromCurrentCharacter character, readWholeStringStartingFromCurrentCharacter() method does this itself
                }
                else if (Character.isDigit(currentCharacter))
                {
                    return new Token(TokenType.INTEGER, readWholeStringStartingFromCurrentCharacter(Character::isDigit)); // same here
                }
                else // undefined word
                    token = new Token(TokenType.ILLEGAL, String.valueOf(currentCharacter));
            }

        }

        moveOnNextCharacter(); // indirectly means finding next word in input since we use skip whitespace method most likely

        return token;
    }


    /**
     *  reads set of letters or digits grouped together in input for example if the input is
     * @param f predicate returning true if this character should be readWholeStringStartingFromCurrentCharacter false otherwise
     * @return word string that was readWholeStringStartingFromCurrentCharacter depending on predicate
     */
    private String readWholeStringStartingFromCurrentCharacter(Function<Character, Boolean> f) {
        int positionStart = currentCharacterPosition; // started reading
        while (f.apply(currentCharacter)) // while it's our desired character
            moveOnNextCharacter();
        return input.substring(positionStart, currentCharacterPosition);
    }

    /**
     * Retrieve next character in the input.
     * @return '\0' if ther is no char to readWholeStringStartingFromCurrentCharacter or next character to readWholeStringStartingFromCurrentCharacter.
     */
    public char readNextCharacter()
    {
        if (nextCharacterPosition >= input.length())
            return '\0';
        else
            return input.charAt(nextCharacterPosition);
    }

    /**
     * Skips white spaces by reading characters
     */
    private void skipWhiteSpace()
    {
        while (currentCharacter == ' ' || currentCharacter == '\t' || currentCharacter == '\n' || currentCharacter == '\r')
            moveOnNextCharacter();
    }

}
