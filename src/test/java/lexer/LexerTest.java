package lexer;

import org.example.lexer.Lexer;
import org.example.token.Token;
import org.example.token.TokenType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    @Test
    void testNextToken() {
        String input = "var five = 5; var ten = 10; var add = fn(x, y) { x + y; }; " +
                "var result = add(five, ten); !-/*5; 5 < 10 > 5; " +
                "if (5 < 10) { return true; } else { return false; }; " +
                "10 == 10; 10 != 9;";

        record TestCase(TokenType expectedType, String expectedLiteral) {}

        TestCase[] tests = {
                new TestCase(TokenType.VARIABLE, "var"),
                new TestCase(TokenType.IDENTIFIER, "five"),
                new TestCase(TokenType.ASSIGN, "="),
                new TestCase(TokenType.INTEGER, "5"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.VARIABLE, "var"),
                new TestCase(TokenType.IDENTIFIER, "ten"),
                new TestCase(TokenType.ASSIGN, "="),
                new TestCase(TokenType.INTEGER, "10"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.VARIABLE, "var"),
                new TestCase(TokenType.IDENTIFIER, "add"),
                new TestCase(TokenType.ASSIGN, "="),
                new TestCase(TokenType.FUNCTION, "fn"),
                new TestCase(TokenType.LP, "("),
                new TestCase(TokenType.IDENTIFIER, "x"),
                new TestCase(TokenType.COMMA, ","),
                new TestCase(TokenType.IDENTIFIER, "y"),
                new TestCase(TokenType.RP, ")"),
                new TestCase(TokenType.LB, "{"),
                new TestCase(TokenType.IDENTIFIER, "x"),
                new TestCase(TokenType.PLUS, "+"),
                new TestCase(TokenType.IDENTIFIER, "y"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.RB, "}"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.VARIABLE, "var"),
                new TestCase(TokenType.IDENTIFIER, "result"),
                new TestCase(TokenType.ASSIGN, "="),
                new TestCase(TokenType.IDENTIFIER, "add"),
                new TestCase(TokenType.LP, "("),
                new TestCase(TokenType.IDENTIFIER, "five"),
                new TestCase(TokenType.COMMA, ","),
                new TestCase(TokenType.IDENTIFIER, "ten"),
                new TestCase(TokenType.RP, ")"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.BANG, "!"),
                new TestCase(TokenType.MINUS, "-"),
                new TestCase(TokenType.SLASH, "/"),
                new TestCase(TokenType.ASTERISK, "*"),
                new TestCase(TokenType.INTEGER, "5"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.INTEGER, "5"),
                new TestCase(TokenType.LT, "<"),
                new TestCase(TokenType.INTEGER, "10"),
                new TestCase(TokenType.GT, ">"),
                new TestCase(TokenType.INTEGER, "5"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.IF, "if"),
                new TestCase(TokenType.LP, "("),
                new TestCase(TokenType.INTEGER, "5"),
                new TestCase(TokenType.LT, "<"),
                new TestCase(TokenType.INTEGER, "10"),
                new TestCase(TokenType.RP, ")"),
                new TestCase(TokenType.LB, "{"),
                new TestCase(TokenType.RETURN, "return"),
                new TestCase(TokenType.TRUE, "true"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.RB, "}"),
                new TestCase(TokenType.ELSE, "else"),
                new TestCase(TokenType.LB, "{"),
                new TestCase(TokenType.RETURN, "return"),
                new TestCase(TokenType.FALSE, "false"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.RB, "}"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.INTEGER, "10"),
                new TestCase(TokenType.EQ, "=="),
                new TestCase(TokenType.INTEGER, "10"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.INTEGER, "10"),
                new TestCase(TokenType.NOT_EQ, "!="),
                new TestCase(TokenType.INTEGER, "9"),
                new TestCase(TokenType.SEMICOLON, ";"),
                new TestCase(TokenType.EOF, "")
        };

        Lexer lexer = new Lexer(input);

        for (int i = 0; i < tests.length; i++) {
            Token token = lexer.readToken();

            assertEquals(
                    tests[i].expectedType,
                    token.getTokenType(),
                    String.format("tests[%d] - tokentype wrong. expected=%s, got=%s",
                            i, tests[i].expectedType, token.getTokenType())
            );

            assertEquals(
                    tests[i].expectedLiteral,
                    token.getLiteral(),
                    String.format("tests[%d] - literal wrong. expected=%s, got=%s",
                            i, tests[i].expectedLiteral, token.getLiteral())
            );
        }
    }
}