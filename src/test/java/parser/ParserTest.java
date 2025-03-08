package parser;

import org.example.ast.expression.*;
import org.example.ast.node.Program;
import org.example.ast.statement.ExpressionStatement;
import org.example.ast.statement.ReturnStatement;
import org.example.ast.statement.Statement;
import org.example.ast.statement.VarStatement;
import org.example.lexer.Lexer;
import org.example.parser.Parser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void testVarStatements() {
        String input = "var x = 5; var y = 10; var foobar = 838383;";

        Lexer l = new Lexer(input);
        Parser p = new Parser(l);

        Program program = p.parseProgram();
        checkParserErrors(p);

        assertNotNull(program, "ParseProgram() returned null");

        assertEquals(3, program.getStatements().size(),
                "program.Statements does not contain 3 statements");

        String[] expectedIdentifiers = {"x", "y", "foobar"};

        for (int i = 0; i < expectedIdentifiers.length; i++) {
            Statement stmt = program.getStatements().get(i);
            assertTrue(testVarStatement(stmt, expectedIdentifiers[i]));
        }
    }

    private boolean testVarStatement(Statement stmt, String name) {
        assertEquals("var", stmt.tokenLiteral(),
                "stmt.TokenLiteral does not match var");

        assertTrue(stmt instanceof VarStatement,
                "stmt is not instance of VarStatement. got=" + stmt.getClass());

        VarStatement varStmt = (VarStatement) stmt;

        assertEquals(name, varStmt.getName().getValue(),
                "varStmt.Name.Value does not match name");

        assertEquals(name, varStmt.getName().tokenLiteral(),
                "varStmt.Name.TokenLiteral does not match name");

        return true;
    }

    private void checkParserErrors(Parser p) {
        List<String> errors = p.getErrors();
        if (errors.isEmpty()) {
            return;
        }

        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append("parser has ").append(errors.size()).append(" errors\n");
        for (String msg : errors) {
            errorMsg.append("parser error: ").append(msg).append("\n");
        }
        fail(errorMsg.toString());
    }

    @Test
    void testReturnStatement() {
        String input = "return 5; return 10; return 993322;";

        Lexer l = new Lexer(input);
        Parser p = new Parser(l);

        Program program = p.parseProgram();
        checkParserErrors(p);

        assertEquals(3, program.getStatements().size(),
                "program.Statements does not contain 3 statements");

        for (Statement stmt : program.getStatements()) {
            assertInstanceOf(ReturnStatement.class, stmt, "stmt is not instance of ReturnStatement. got=" + stmt.getClass());

            assertEquals("return", stmt.tokenLiteral(),
                    "returnStmt.TokenLiteral does not match return");
        }
    }

    @Test
    void testIdentifierExpression() {
        String input = "foobar;";

        Lexer l = new Lexer(input);
        Parser p = new Parser(l);

        Program program = p.parseProgram();
        checkParserErrors(p);


        assertEquals(1, program.getStatements().size(),
                "program.Statements does not contain 1 statements");

        Statement statement = program.getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, statement, "program.getStatements().get(0) is not ExpressionStatement. got=" + statement.getClass());
        ExpressionStatement expressionStatement = (ExpressionStatement) statement;

        Expression expression = expressionStatement.getExpression();
        assertInstanceOf(Identifier.class, expression, "expression is not Identifier. got=" + expression.getClass());
        Identifier identifier = (Identifier) expression;

        assertEquals(identifier.getValue(), "foobar", "identifier.getValue() is not foobar. got=" + identifier.getValue());

        assertEquals(identifier.tokenLiteral(), "foobar", "identifier.tokenLiteral() is not foobar. got=" + identifier.tokenLiteral());

    }

    @Test
    void testIntegerLiteralExpression() {

        String input = "5;";

        Lexer l = new Lexer(input);
        Parser p = new Parser(l);

        Program program = p.parseProgram();
        checkParserErrors(p);


        assertEquals(1, program.getStatements().size(),
                "program.Statements does not contain 1 statements");

        Statement statement = program.getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, statement, "program.getStatements().get(0) is not ExpressionStatement. got=" + statement.getClass());
        ExpressionStatement expressionStatement = (ExpressionStatement) statement;

        Expression expression = expressionStatement.getExpression();
        assertInstanceOf(IntegerLiteral.class, expression, "expression is not IntegerLiteral. got=" + expression.getClass());
        IntegerLiteral literal = (IntegerLiteral) expression;

        assertEquals(literal.getValue(), 5, "literal.getValue() is not 5. got=" + literal.getValue());

        assertEquals(literal.tokenLiteral(), "5", "literal.tokenLiteral() is not '5'. got=" + literal.getValue());

    }

    @Test
    void testParsingPrefixExpressions() {
        Object[][] prefixTests = new Object[][] {
                new Object[] { "!5;", "!", 5 },
                new Object[] { "-15;", "-", 15 },
        };
        for (Object[] prefixTest : prefixTests) {
            Lexer l = new Lexer((String) prefixTest[0]);
            Parser p = new Parser(l);
            Program program = p.parseProgram();
            checkParserErrors(p);


            assertEquals(program.getStatements().size(), 1, String.format("program.getStatements does not contain %d statements. got=%d\n", 1, program.getStatements().size()));

            Statement statement = program.getStatements().getFirst();
            assertInstanceOf(ExpressionStatement.class, statement, "program.getStatements().get(0) is not ExpressionStatement. got=" + statement.getClass());
            ExpressionStatement expressionStatement = (ExpressionStatement) statement;

            assertInstanceOf(PrefixExpression.class, expressionStatement.getExpression(), String.format("expressionStatement is not PrefixExpression. got=" + expressionStatement.getExpression().getClass()));
            PrefixExpression prefixExpression = (PrefixExpression) expressionStatement.getExpression();

            assertEquals(prefixExpression.getOperator(), prefixTest[1], String.format("prefixExpression.getOperator is not %s, got=%s", prefixTest[1], prefixExpression.getOperator()));


            testIntegerLiteral(prefixExpression.getRight(), (int) prefixTest[2]);

        }
    }

    public void testIntegerLiteral(Expression prefixExpression, int value) {

        assertInstanceOf(IntegerLiteral.class, prefixExpression, "prefixExpression is not IntegerLiteral. got=" + prefixExpression.getClass());
        IntegerLiteral integerLiteral = (IntegerLiteral) prefixExpression;

        assertEquals(integerLiteral.getValue(), value, String.format("value is not %d. got=%d", value, integerLiteral.getValue()));

        assertEquals(integerLiteral.tokenLiteral(), String.valueOf(value), String.format("integerLiteral.tokenLiteral() is not %d. got=%s", value, integerLiteral.tokenLiteral()));
    }



    @Test
    public void testParsingInfixExpressions() {
        Object[][] tests = {
                { "5 + 5;", 5, "+", 5 },
                { "5 - 5;", 5, "-", 5 },
                { "5 * 5;", 5, "*", 5 },
                { "5 / 5;", 5, "/", 5 },
                { "5 > 5;", 5, ">", 5 },
                { "5 < 5;", 5, "<", 5 },
                { "5 == 5;", 5, "==", 5 },
                { "5 != 5;", 5, "!=", 5 },
        };

        for (Object[] test : tests) {
            Lexer lexer = new Lexer((String) test[0]);
            Parser parser = new Parser(lexer);

            Program program = parser.parseProgram();
            checkParserErrors(parser);

            assertEquals(program.getStatements().size(), 1, String.format("program.getStatements does not contain %d statements. got=%d\n", 1, program.getStatements().size()));

            Statement statement = program.getStatements().getFirst();
            assertInstanceOf(ExpressionStatement.class, statement, "program.getStatements().get(0) is not ExpressionStatement. got=" + statement.getClass());
            ExpressionStatement expressionStatement = (ExpressionStatement) statement;


            assertInstanceOf(InfixExpression.class, expressionStatement.getExpression(), String.format("expressionStatement is not InfixExpression. got=" + expressionStatement.getExpression().getClass()));
            InfixExpression infixExpression = (InfixExpression) expressionStatement.getExpression();


            assertEquals(infixExpression.getOperator(), test[2], String.format("infixExpression.getOperator is not %s, got=%s", test[2], infixExpression.getOperator()));


            testIntegerLiteral(infixExpression.getRight(), (int) test[3]);




        }



    }


    @Test
    void testOperatorPrecedenceParsing() {
        Object[][] tests = {
                { "-a * b", "((-a) * b) " },
                {  "!-a", "(!(-a)) " },
                {  "a + b + c", "((a + b) + c) " },
                {  "a + b - c", "((a + b) - c) " },
                { "a * b * c", "((a * b) * c) " },
                {  "a * b / c", "((a * b) / c) " },
                {  "a + b / c", "(a + (b / c)) " },
                {  "a + b * c + d / e- f", "(((a + (b * c)) + (d / e)) - f) " },
                {  "3 + 4;-5 * 5", "(3 + 4) ((-5) * 5) " },
                {  "5 > 4 == 3 < 4", "((5 > 4) == (3 < 4)) " },
                {  "5 < 4 != 3 > 4", "((5 < 4) != (3 > 4)) " },
                { "3 + 4 * 5 == 3 * 1 + 4 * 5", "((3 + (4 * 5)) == ((3 * 1) + (4 * 5))) " },
                { "3 + 4 * 5 == 3 * 1 + 4 * 5", "((3 + (4 * 5)) == ((3 * 1) + (4 * 5))) " }
        };

        for (Object[] test : tests)
        {
            Lexer lexer = new Lexer((String) test[0]);
            Parser parser = new Parser(lexer);

            Program program = parser.parseProgram();
            checkParserErrors(parser);



            assertEquals(program.toString(), test[1], String.format("expected=%s, got=%s", test[1], program));


        }


    }








}