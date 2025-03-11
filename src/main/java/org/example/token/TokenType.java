package org.example.token;


/**
 * ILLEGAL, EOF, IDENTIFIER, INTEGER, ASSIGN, PLUS, MINUS, BANG, ASTERISK, SLASH, LT, GT, EQ, NOT_EQ, COMMA,
 * SEMICOLON, LP, RP, LB, RB, FUNCTION, VARIABLE, TRUE, FALSE, IF, ELSE, RETURN
 *
 * @author Konstantine Vashalomidze
 */
public enum TokenType
{
    ILLEGAL("ILLEGAL"), // for indicating illegal token
    EOF("EOF"), // for telling parser to stop


    // identifiers + literals
    IDENTIFIER("IDENTIFIER"),
    INTEGER("INTEGER"),

    // operators
    ASSIGN("="),
    PLUS("+"),
    MINUS("-"),
    BANG("!"),
    ASTERISK("*"),
    SLASH("/"),
    LT("<"),
    GT(">"),
    EQ("=="),
    NOT_EQ("!="),

    // delimiters
    COMMA(","),
    SEMICOLON(";"),

    LP("("),
    RP(")"),
    LB("{"),
    RB("}"),

    // keywords
    FUNCTION("FUNCTION"),
    VARIABLE("VARIABLE"),
    TRUE("TRUE"),
    FALSE("FALSE"),
    IF("IF"),
    ELSE("ELSE"),
    RETURN("RETURN"),
    ;

    private final String val;

    TokenType(String val) {
        this.val = val;
    }

    public String getValue() {
        return val;
    }

    public static TokenType lookupIdentifier(String keyword)
    {
        return switch (keyword) {
            case "fn" -> FUNCTION;
            case "var" -> VARIABLE;
            case "true" -> TRUE;
            case "false" -> FALSE;
            case "if" -> IF;
            case "else" -> ELSE;
            case "return" -> RETURN;
            default -> IDENTIFIER;
        };
    }

}













