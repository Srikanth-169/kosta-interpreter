package interperter.lexer;


import interperter.token.Token;
import interperter.token.TokenManager;
import interperter.token.types.*;
import interperter.token.types.Integer;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

/**
 *  Tokenizes the program into tokens. Get that tokens with readAndMoveOnNextToken().
 *
 * @author Konstantine Vashalomidze
 */
public class Lexer
{
    private String input;
    private char currentCharacter;
    private int nextCharacterIndex;
    private Token currentToken;

    private Token previousToken;


    public Lexer(String input) {
        this.input = input;
        moveOnNextCharacter();
    }

    /**
     * Advances currentCharacter to next character and related indexes as well.
     */
    private void moveOnNextCharacter() {
        if (nextCharacterIndex >= input.length())
            currentCharacter = '\0';
        else
            currentCharacter = input.charAt(nextCharacterIndex);
        nextCharacterIndex++;
    }

    /**
     * Directly read next character skipping all white spaces along the way.
     */
    private void skipWhiteSpace() {
        while (currentCharacter == ' ' || currentCharacter == '\t' || currentCharacter == '\n' || currentCharacter == '\r')
            moveOnNextCharacter();
    }


    /**
     * Check what is the next character in the input.
     * @return next character after current character in the input.
     */
    private char nextCharacter() {
        if (nextCharacterIndex >= input.length())
            return '\0';
        return input.charAt(nextCharacterIndex);
    }

    /**
     * Supply class and get corresponding currentToken.
     * @param classz class to extract simple name from.
     * @return currentToken in currentToken manager with provided class simple name.
     */
    private Token getToken(Class<?> classz)
    {
        return TokenManager.getTokenManagerInstance().getToken(classz.getSimpleName());
    }

    /**
     * reads consecutive characters satisfying provided function
     */
    private String readCurrentLiteral(Function<Character, Boolean> function) {
        StringBuilder literal = new StringBuilder();
        while (function.apply(currentCharacter))
        {
            literal.append(currentCharacter);
            moveOnNextCharacter();
        }
        return literal.toString();
    }






    /**
     * sets currentToken by reading set of characters together.
     */
    public Token readAndMoveOnNextToken()
            throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException
    {
        previousToken = currentToken;
        skipWhiteSpace(); // move to the next currentToken's first character index
        switch (currentCharacter) {
            case '=' -> {
                if (nextCharacter() == '=')
                {
                    currentToken = getToken(Eq.class);
                    moveOnNextCharacter();
                }
                else
                    currentToken = getToken(Assign.class);
            }
            case '*' -> currentToken = getToken(Asterisk.class);
            case '!' -> {
                if (nextCharacter() == '=')
                {
                    currentToken = getToken(NotEq.class);
                    moveOnNextCharacter();
                }
                else
                    currentToken = getToken(Bang.class);
            }
            case ',' -> currentToken = getToken(Comma.class);
            case '\0' -> currentToken = getToken(Eof.class);
            case '>' -> currentToken = getToken(Gt.class);
            case ')' -> currentToken = getToken(Rp.class);
            case '}' -> currentToken = getToken(Rb.class);
            case '<' -> currentToken = getToken(Lt.class);
            case '-' -> currentToken = isPrefix() ? getToken(MinusPrefix.class) : getToken(MinusInfix.class);
            case '+' -> currentToken = getToken(Plus.class);
            case '{' -> currentToken = getToken(Lb.class);
            case '(' -> currentToken = getToken(Lp.class);
            case ';' -> currentToken = getToken(Semicolon.class);
            case '/' -> currentToken = getToken(Slash.class);
            case '&' -> currentToken = getToken(And.class);
            case '|' -> currentToken = getToken(Or.class);
            default -> {
                if (Character.isLetter(currentCharacter))
                {
                    // this moves to the next currentToken starting index, so we directly return
                    String literal = readCurrentLiteral(Character::isLetter);
                    // if not found in manager that should be the identifier
                    if (TokenManager.getTokenManagerInstance().getTokenWithLiteral(literal).isEmpty())
                    {
                        currentToken = new Identifier().setLiteral(literal);
                        return currentToken;
                    }
                    // This creates new currentToken depending on the literal
                    // for example if literal is fn then new currentToken of Function will be created.
                    currentToken = TokenManager.getTokenManagerInstance().getTokenWithLiteral(literal).get().getClass().getDeclaredConstructor().newInstance().setLiteral(literal);
                    return currentToken;
                }
                else if (Character.isDigit(currentCharacter))
                {
                    // this moves to the next currentToken starting index, so we directly return
                    String literal = readCurrentLiteral(Character::isDigit);
                    currentToken = new Integer().setLiteral(literal);
                    return currentToken;
                }
                currentToken = getToken(Illegal.class);
            }
        }
        moveOnNextCharacter();

        return currentToken;
    }

    private boolean isPrefix() {
        // If there's no previous token (beginning of input or expression),
        // then it's definitely a prefix
        if (previousToken == null)
            return true;

        // Check if the previous token is an operator, opening bracket, or similar token
        // that would indicate this minus is a prefix
        return previousToken instanceof Assign ||
                previousToken instanceof Eq ||
                previousToken instanceof NotEq ||
                previousToken instanceof Bang ||
                previousToken instanceof Comma ||
                previousToken instanceof Rp || // Opening parenthesis
                previousToken instanceof Rb || // Opening brace
                previousToken instanceof Lt ||
                previousToken instanceof Gt ||
                previousToken instanceof Plus ||
                previousToken instanceof MinusInfix ||
                previousToken instanceof MinusPrefix ||
                previousToken instanceof Asterisk ||
                previousToken instanceof Slash ||
                previousToken instanceof And ||
                previousToken instanceof Or ||
                previousToken instanceof Semicolon;

        // Note: The non-prefix case would be after identifiers, integers,
        // or closing brackets/parentheses
    }





}
