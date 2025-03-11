package lexer;

import org.example.lexer.Lexer;
import org.example.token.Token;
import org.example.token.TokenType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.token.TokenType.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Konstantine Vashalomdize
 */
public class LexerTest
{

    private Lexer lexer;

    @BeforeEach
    void reconstructLexer() {
        String input = """
        var x = 5;
        var y = 10;
        var add = fn(x, y) {
          return x + y;
        };
        var result = add(x, y);
        
        if (x < y) {
          return true;
        } else {
          return false;
        }
        
        10 + 15 - 5 * 20 / 4;
        
        !true;
        !=false;
        ==5;
        =10;
        """;
        lexer = new Lexer(input);
    }

    @AfterEach
    void destroyLexer() {
        lexer = null;
    }




    @Test
    void testReadAndMoveOnNextToken() {
        Token[] expectedTokens = {
                new Token(TokenType.VARIABLE, "var"),
                new Token(TokenType.IDENTIFIER, "x"),
                new Token(TokenType.ASSIGN, "="),
                new Token(TokenType.INTEGER, "5"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.VARIABLE, "var"),
                new Token(TokenType.IDENTIFIER, "y"),
                new Token(TokenType.ASSIGN, "="),
                new Token(TokenType.INTEGER, "10"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.VARIABLE, "var"),
                new Token(TokenType.IDENTIFIER, "add"),
                new Token(TokenType.ASSIGN, "="),
                new Token(TokenType.FUNCTION, "fn"),
                new Token(TokenType.LP, "("),
                new Token(TokenType.IDENTIFIER, "x"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.IDENTIFIER, "y"),
                new Token(TokenType.RP, ")"),
                new Token(TokenType.LB, "{"),

                new Token(TokenType.RETURN, "return"),
                new Token(TokenType.IDENTIFIER, "x"),
                new Token(TokenType.PLUS, "+"),
                new Token(TokenType.IDENTIFIER, "y"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.RB, "}"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.VARIABLE, "var"),
                new Token(TokenType.IDENTIFIER, "result"),
                new Token(TokenType.ASSIGN, "="),
                new Token(TokenType.IDENTIFIER, "add"),
                new Token(TokenType.LP, "("),
                new Token(TokenType.IDENTIFIER, "x"),
                new Token(TokenType.COMMA, ","),
                new Token(TokenType.IDENTIFIER, "y"),
                new Token(TokenType.RP, ")"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.IF, "if"),
                new Token(TokenType.LP, "("),
                new Token(TokenType.IDENTIFIER, "x"),
                new Token(TokenType.LT, "<"),
                new Token(TokenType.IDENTIFIER, "y"),
                new Token(TokenType.RP, ")"),
                new Token(TokenType.LB, "{"),

                new Token(TokenType.RETURN, "return"),
                new Token(TokenType.TRUE, "true"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.RB, "}"),
                new Token(TokenType.ELSE, "else"),
                new Token(TokenType.LB, "{"),

                new Token(TokenType.RETURN, "return"),
                new Token(TokenType.FALSE, "false"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.RB, "}"),

                new Token(TokenType.INTEGER, "10"),
                new Token(TokenType.PLUS, "+"),
                new Token(TokenType.INTEGER, "15"),
                new Token(TokenType.MINUS, "-"),
                new Token(TokenType.INTEGER, "5"),
                new Token(TokenType.ASTERISK, "*"),
                new Token(TokenType.INTEGER, "20"),
                new Token(TokenType.SLASH, "/"),
                new Token(TokenType.INTEGER, "4"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.BANG, "!"),
                new Token(TokenType.TRUE, "true"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.NOT_EQ, "!="),
                new Token(TokenType.FALSE, "false"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.EQ, "=="),
                new Token(TokenType.INTEGER, "5"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.ASSIGN, "="),
                new Token(TokenType.INTEGER, "10"),
                new Token(TokenType.SEMICOLON, ";"),

                new Token(TokenType.EOF, "")
        };


        /* check if the tokenized input is expected */
        Token currentToken;
        int i = 0;
        while ((currentToken = lexer.readAndMoveOnNextToken()).getTokenType() != EOF)
        {
            assertEquals(currentToken.getTokenType(), expectedTokens[i].getTokenType());
            assertEquals(currentToken.getLiteral(), expectedTokens[i].getLiteral());
            i++;
        }
    }


    @Test
    void testConstructor() {
        // on empty input
        new Lexer("");
        new Lexer("asdkfjalskdfjasd faslkdfj asdlkfj;as dfkja;sdl fkajs; ldfkjasd ;l");
        new Lexer(null);
    }

}
