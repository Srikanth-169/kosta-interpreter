package parser;

import org.example.ast.expression.*;
import org.example.ast.expression.BooleanLiteral;
import org.example.ast.node.Program;
import org.example.ast.statement.BlockStatement;
import org.example.ast.statement.ExpressionStatement;
import org.example.ast.statement.ReturnStatement;
import org.example.ast.statement.VarStatement;
import org.example.lexer.Lexer;
import org.example.parser.Parser;
import org.example.token.TokenType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest
{
    private Lexer lexer;
    private Parser  parser;

    @AfterEach
    void destroyParser() {
        lexer = null;
        parser = null;
    }


    @Test
    void testParseProgram_NotNull() {
        lexer = new Lexer("var x = 5;");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertNotNull(program, "Program should not be null");
        assertTrue(parser.getErrors().isEmpty(), "Parser should not have errors: " + String.join(", ", parser.getErrors()));
    }

    @Test
    void testParseProgram_Empty()
    {
        lexer = new Lexer("");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertNotNull(program, "Program should not be null");
        assertTrue(program.getStatements().isEmpty(), "Program shouldn't have statements");
        assertTrue(parser.getErrors().isEmpty(), "Parser should not have errors: " + String.join(", ", parser.getErrors()));



    }


    @Test
    void testVarStatement() {
        lexer = new Lexer("var x = 5;");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertEquals(1, program.getStatements().size(), "Program should contain 1 statement");
        assertInstanceOf(VarStatement.class, program.getStatements().getFirst(), "Statement should be a var statement");

        VarStatement varStatement = (VarStatement) program.getStatements().getFirst();
        assertNotNull(varStatement.getValue(), "Variable value shouldn't be null");
        assertNotNull(varStatement.getName(), "Variable name shouldn't be null");
        assertNotNull(varStatement.getToken(), "Variable token shouldn't be null");

        assertEquals("x", varStatement.getName().getValue(), "Variable name should be x");
        assertInstanceOf(IntegerLiteral.class, varStatement.getValue(), "Value should be IntegerLiteral");
        assertEquals(5, ((IntegerLiteral) varStatement.getValue()).getValue(), "Value should be 5");
        assertEquals(TokenType.VARIABLE, varStatement.getToken().getTokenType(), "Token type should be VARIABLE");
        assertEquals("var", varStatement.getToken().getLiteral(), "Token literal should be 'var'");
    }

    @Test
    void testReturnStatement() {
        lexer = new Lexer("return 10;");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertEquals(1, program.getStatements().size(), "Program should contain 1 statement");
        assertInstanceOf(ReturnStatement.class, program.getStatements().getFirst(), "Statement should be a return statement");

        ReturnStatement returnStatement = (ReturnStatement) program.getStatements().getFirst();
        assertNotNull(returnStatement.getValue(), "Return value shouldn't be null");
        assertNotNull(returnStatement.getToken(), "Return token shouldn't be null");

        assertInstanceOf(IntegerLiteral.class, returnStatement.getValue(), "Value should be IntegerLiteral");
        assertEquals(10, ((IntegerLiteral) returnStatement.getValue()).getValue(), "Return value should be 10");
        assertEquals(TokenType.RETURN, returnStatement.getToken().getTokenType(), "Token type should be RETURN");
        assertEquals("return", returnStatement.getToken().getLiteral(), "Token literal should be 'return'");


    }

    @Test
    void testIdentifierExpression() {
        lexer = new Lexer("foobar;");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertNotNull(program.getStatements(), "Program should have statements");
        assertEquals(1, program.getStatements().size(), "Program should have one statement");
        assertInstanceOf(ExpressionStatement.class, program.getStatements().getFirst(), "Statement should be an ExpressionStatement");

        ExpressionStatement expressionStatement = (ExpressionStatement) program.getStatements().getFirst();
        assertNotNull(expressionStatement.getExpression(), "ExpressionStatement should have Expression");
        assertInstanceOf(Identifier.class, expressionStatement.getExpression(), "Expression should be an Identifier");

        Identifier identifier = (Identifier) expressionStatement.getExpression();
        assertNotNull(identifier.getValue(), "Identifier should have value");
        assertEquals("foobar", identifier.getValue(), "Identifier value should be foobar");
    }

    @Test
    void testIntegerLiteralExpression()
    {
        lexer = new Lexer("5;");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertNotNull(program.getStatements(), "Program should have statements");
        assertEquals(1, program.getStatements().size(), "Program should have one statement");
        assertInstanceOf(ExpressionStatement.class, program.getStatements().getFirst(), "Statement should be an ExpressionStatement");

        ExpressionStatement expressionStatement = (ExpressionStatement) program.getStatements().getFirst();
        assertNotNull(expressionStatement.getExpression(), "ExpressionStatement should have Expression");
        assertInstanceOf(IntegerLiteral.class, expressionStatement.getExpression(), "Expression should be an IntegerLiteral");

        IntegerLiteral literal = (IntegerLiteral) expressionStatement.getExpression();
        assertEquals(5, literal.getValue(), "IntegerLiteral value should be foobar");
    }

    @Test
    void testBooleanExpression() {
        String[] inputs = { "true", "false" };
        boolean[] expected = { true, false };

        for (int i = 0; i < inputs.length; i++)
        {
            lexer = new Lexer(inputs[i]);
            parser = new Parser(lexer);
            Program program = parser.parseProgram();

            assertInstanceOf(ExpressionStatement.class, program.getStatements().getFirst());
            ExpressionStatement expressionStatement = (ExpressionStatement) program.getStatements().getFirst();
            assertInstanceOf(BooleanLiteral.class, expressionStatement.getExpression());
            BooleanLiteral booleanLiteralExpression = (BooleanLiteral) expressionStatement.getExpression();
            assertEquals(expected[i], booleanLiteralExpression.getValue(), "Boolean value should be " + expected[i]);


        }

    }


    @Test
    void testPrefixExpression() {
        String[] inputs = { "!5;", "-15;" };
        String[] operators = { "!", "-" };
        int[] values = { 5, 15 };

        for (int i = 0; i < inputs.length; i++) {
            lexer = new Lexer(inputs[i]);
            parser = new Parser(lexer);
            Program program = parser.parseProgram();

            assertInstanceOf(ExpressionStatement.class, program.getStatements().getFirst());
            ExpressionStatement stmt = (ExpressionStatement) program.getStatements().getFirst();
            assertInstanceOf(PrefixExpression.class, stmt.getExpression(), "Expression should be PrefixExpression");
            PrefixExpression prefixExpr = (PrefixExpression) stmt.getExpression();

            assertEquals(operators[i], prefixExpr.getOperator(), "Operator should be " + operators[i]);
            assertInstanceOf(IntegerLiteral.class, prefixExpr.getRight(), "Right expression should be IntegerLiteral");
            assertEquals(values[i], ((IntegerLiteral) prefixExpr.getRight()).getValue(),
                    "Integer value should be " + values[i]);
        }

    }


    @ParameterizedTest
    @MethodSource("provideInfixExpressions")
    void testInfixExpressions(String input, Object leftValue, String operator, Object rightValue)
    {
        lexer = new Lexer(input);
        parser = new Parser(lexer);
        Program program = parser.parseProgram();


        assertTrue(parser.getErrors().isEmpty(),
                "Parser should not have errors: " + String.join(", ", parser.getErrors()));
        assertInstanceOf(ExpressionStatement.class, program.getStatements().getFirst(), "Statement should be ExpressionStatement");
        ExpressionStatement stmt = (ExpressionStatement) program.getStatements().getFirst();
        assertInstanceOf(InfixExpression.class, stmt.getExpression(), "ExpressionStatement should be InfixExpression");
        InfixExpression infixExpr = (InfixExpression) stmt.getExpression();

        // Test left value
        if (leftValue instanceof Integer) {
            assertInstanceOf(IntegerLiteral.class, infixExpr.getLeft(), "Left should be IntegerLiteral");
            assertEquals(leftValue, ((IntegerLiteral) infixExpr.getLeft()).getValue());
        } else if (leftValue instanceof BooleanLiteral) {
            assertInstanceOf(BooleanLiteral.class, infixExpr.getLeft(), "Left should be Boolean");
            assertEquals(leftValue, ((BooleanLiteral) infixExpr.getLeft()).getValue());
        }


        assertEquals(operator, infixExpr.getOperator());

        // test right value
        if (rightValue instanceof Integer) {
            assertInstanceOf(IntegerLiteral.class, infixExpr.getRight(), "Right should be IntegerLiteral");
            assertEquals(rightValue, ((IntegerLiteral) infixExpr.getRight()).getValue());
        } else if (rightValue instanceof BooleanLiteral) {
            assertInstanceOf(BooleanLiteral.class, infixExpr.getRight(), "Right should be Boolean");
            assertEquals(rightValue, ((BooleanLiteral) infixExpr.getRight()).getValue());
        }

    }

    static Stream<Arguments> provideInfixExpressions() {
        return Stream.of(
                Arguments.of("5 + 5;", 5, "+", 5),
                Arguments.of("5 - 5;", 5, "-", 5),
                Arguments.of("5 * 5;", 5, "*", 5),
                Arguments.of("5 / 5;", 5, "/", 5),
                Arguments.of("5 > 5;", 5, ">", 5),
                Arguments.of("5 < 5;", 5, "<", 5),
                Arguments.of("5 == 5;", 5, "==", 5),
                Arguments.of("5 != 5;", 5, "!=", 5),
                Arguments.of("true == true;", true, "==", true),
                Arguments.of("false != true;", false, "!=", true)
        );
    }



    @ParameterizedTest
    @MethodSource("provideOperatorPrecedenceTests")
    public void testOperatorPrecedence(String input, String expected) {
        lexer = new Lexer(input);
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertTrue(parser.getErrors().isEmpty(),
                "Parser should not have errors: " + String.join(", ", parser.getErrors()));

        // Test that the program, when converted to string, matches expected structure
        // This requires implementing toString() in AST nodes or a custom string representation
        assertEquals(expected, program.toString());
    }

    static Stream<Arguments> provideOperatorPrecedenceTests() {
        return Stream.of(
                Arguments.of("-a * b", "((-a) * b)"),
                Arguments.of("!-a", "(!(-a))"),
                Arguments.of("a + b + c", "((a + b) + c)"),
                Arguments.of("a + b - c", "((a + b) - c)"),
                Arguments.of("a * b * c", "((a * b) * c)"),
                Arguments.of("a * b / c", "((a * b) / c)"),
                Arguments.of("a + b / c", "(a + (b / c))"),
                Arguments.of("a + b * c + d / e - f", "(((a + (b * c)) + (d / e)) - f)"),
                Arguments.of("3 + 4; -5 * 5", "(3 + 4) ((-5) * 5)"),
                Arguments.of("5 > 4 == 3 < 4", "((5 > 4) == (3 < 4))"),
                Arguments.of("5 < 4 != 3 > 4", "((5 < 4) != (3 > 4))"),
                Arguments.of("3 + 4 * 5 == 3 * 1 + 4 * 5", "((3 + (4 * 5)) == ((3 * 1) + (4 * 5)))")
        );
    }



    @Test
    public void testParenthesizedExpressions() {
        String[] inputs = {
                "(5 + 5) * 2;",
                "2 / (5 + 5);",
                "-(5 + 5);",
                "!(true == true);"
        };
        String[] expected = {
                "((5 + 5) * 2)",
                "(2 / (5 + 5))",
                "(-(5 + 5))",
                "(!(true == true))"
        };

        for (int i = 0; i < inputs.length; i++) {
            lexer = new Lexer(inputs[i]);
            parser = new Parser(lexer);
            Program program = parser.parseProgram();

            // Check string representation matches expected
            assertEquals(expected[i], program.toString());
        }
    }



    @Test
    public void testIfExpression() {
        lexer = new Lexer("if (x < y) { x }");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertTrue(parser.getErrors().isEmpty(),
                "Parser should not have errors: " + String.join(", ", parser.getErrors()));

        ExpressionStatement stmt = (ExpressionStatement) program.getStatements().getFirst();
        IfExpression ifExpr = (IfExpression) stmt.getExpression();

        // condition
        InfixExpression condition = (InfixExpression) ifExpr.getCondition();
        assertEquals("<", condition.getOperator());
        assertEquals("x", ((Identifier) condition.getLeft()).getValue());
        assertEquals("y", ((Identifier) condition.getRight()).getValue());

        // consequence
        BlockStatement consequence = ifExpr.getConsequence();
        assertEquals(1, consequence.getStatements().size());
        ExpressionStatement consequenceStmt = (ExpressionStatement) consequence.getStatements().getFirst();
        assertEquals("x", ((Identifier) consequenceStmt.getExpression()).getValue());

        // alternative is null for this case
        assertNull(ifExpr.getAlternative());
    }


    @Test
    public void testIfElseExpression() {
        lexer = new Lexer("if (x < y) { x } else { y }");
        // TODO: test when functionality will be ready

    }



    @Test
    public void testFunctionLiteral() {
        lexer = new Lexer("fn(x, y) { x + y; }");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertTrue(parser.getErrors().isEmpty(),
                "Parser should not have errors: " + String.join(", ", parser.getErrors()));

        ExpressionStatement stmt = (ExpressionStatement) program.getStatements().getFirst();
        FunctionLiteral funcLiteral = (FunctionLiteral) stmt.getExpression();

        // parameters
        List<Identifier> params = funcLiteral.getParameters();
        assertEquals(2, params.size());
        assertEquals("x", params.get(0).getValue());
        assertEquals("y", params.get(1).getValue());

        // body
        BlockStatement body = funcLiteral.getBody();
        assertEquals(1, body.getStatements().size());
        ExpressionStatement bodyStmt = (ExpressionStatement) body.getStatements().getFirst();
        InfixExpression bodyExpr = (InfixExpression) bodyStmt.getExpression();
        assertEquals("+", bodyExpr.getOperator());
        assertEquals("x", ((Identifier) bodyExpr.getLeft()).getValue());
        assertEquals("y", ((Identifier) bodyExpr.getRight()).getValue());
    }

    @Test
    public void testFunctionLiteralWithoutParameters() {
        lexer = new Lexer("fn() { return 5; }");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertTrue(parser.getErrors().isEmpty(), "Parser should not have errors: " + String.join(", ", parser.getErrors()));

        ExpressionStatement stmt = (ExpressionStatement) program.getStatements().getFirst();
        FunctionLiteral funcLiteral = (FunctionLiteral) stmt.getExpression();

        // parameters
        List<Identifier> params = funcLiteral.getParameters();
        assertNotNull(params, "Parameters should not be null");
        assertEquals(0, params.size(), "Parameters should have size 0");

        // body
        BlockStatement body = funcLiteral.getBody();
        assertEquals(1, body.getStatements().size());
        ReturnStatement bodyStmt = (ReturnStatement) body.getStatements().getFirst();
        IntegerLiteral value = (IntegerLiteral) bodyStmt.getValue();
        assertEquals(5, value.getValue());

    }


    @Test
    public void testCallExpression() {
        lexer = new Lexer("add(1, 2 * 3, 4 + 5);");
        parser = new Parser(lexer);
        Program program = parser.parseProgram();

        assertTrue(parser.getErrors().isEmpty(),
                "Parser should not have errors: " + String.join(", ", parser.getErrors()));

        ExpressionStatement stmt = (ExpressionStatement) program.getStatements().getFirst();
        CallExpression callExpr = (CallExpression) stmt.getExpression();

        // Test function
        assertEquals("add", ((Identifier) callExpr.getFunction()).getValue());

        // Test arguments
        List<Expression> args = callExpr.getArguments();
        assertEquals(3, args.size());
        assertEquals(1, ((IntegerLiteral) args.get(0)).getValue());

        // expressions in arguments are parsed correctly
        InfixExpression arg1 = (InfixExpression) args.get(1);
        assertEquals("*", arg1.getOperator());
        assertEquals(2, ((IntegerLiteral) arg1.getLeft()).getValue());
        assertEquals(3, ((IntegerLiteral) arg1.getRight()).getValue());

        InfixExpression arg2 = (InfixExpression) args.get(2);
        assertEquals("+", arg2.getOperator());
        assertEquals(4, ((IntegerLiteral) arg2.getLeft()).getValue());
        assertEquals(5, ((IntegerLiteral) arg2.getRight()).getValue());
    }


    @Test
    public void testParsingErrors() {

    }

}
