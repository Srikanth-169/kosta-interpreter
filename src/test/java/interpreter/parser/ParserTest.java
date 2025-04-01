package interpreter.parser;

import com.github.konstantinevashalomidze.interpreter.ast.expression.*;
import com.github.konstantinevashalomidze.interpreter.ast.node.Program;
import com.github.konstantinevashalomidze.interpreter.ast.statement.ExpressionStatement;
import com.github.konstantinevashalomidze.interpreter.ast.statement.ReturnStatement;
import com.github.konstantinevashalomidze.interpreter.ast.statement.Statement;
import com.github.konstantinevashalomidze.interpreter.ast.statement.VarStatement;
import com.github.konstantinevashalomidze.interpreter.lexer.Lexer;
import com.github.konstantinevashalomidze.interpreter.parser.Parser;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Parser implementation
 */
public class ParserTest {

    private Parser createParser(String input) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        List<String> errors = parser.errors();

        if (!errors.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Parser has errors:\n");
            for (String error : errors) {
                errorMsg.append("\t").append(error).append("\n");
            }
            fail(errorMsg.toString());
        }

        return parser;
    }

    @Test
    public void testVarStatements() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = """
                var x = 5;
                var y = 10;
                var foobar = 838383;
                """;

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(3, program.getStatements().size(), "Program should have 3 statements");

        String[] expectedIdentifiers = {"x", "y", "foobar"};
        int[] expectedValues = {5, 10, 838383};

        for (int i = 0; i < expectedIdentifiers.length; i++) {
            Statement stmt = program.getStatements().get(i);
            assertInstanceOf(VarStatement.class, stmt, "Statement is not a VarStatement");

            VarStatement varStmt = (VarStatement) stmt;
            testVarStatement(varStmt, expectedIdentifiers[i]);

            Expression value = varStmt.getValue();
            testIntegerLiteral(value, expectedValues[i]);
        }
    }

    @Test
    public void testReturnStatements() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = """
                return 5;
                return 10;
                return 993322;
                """;

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(3, program.getStatements().size(), "Program should have 3 statements");

        int[] expectedValues = {5, 10, 993322};

        for (int i = 0; i < expectedValues.length; i++) {
            Statement stmt = program.getStatements().get(i);
            assertInstanceOf(ReturnStatement.class, stmt, "Statement is not a ReturnStatement");

            ReturnStatement returnStmt = (ReturnStatement) stmt;
            assertEquals("return", returnStmt.literal(), "returnStmt.tokenLiteral() not 'return'");

            testIntegerLiteral(returnStmt.getValue(), expectedValues[i]);
        }
    }

    @Test
    public void testIdentifierExpression() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = "foobar;";

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(1, program.getStatements().size(), "Program should have 1 statement");

        Statement stmt = program.getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, stmt, "Statement is not an ExpressionStatement");

        ExpressionStatement exprStmt = (ExpressionStatement) stmt;
        testIdentifier(exprStmt.getExpression(), "foobar");
    }

    @Test
    public void testIntegerLiteralExpression() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = "5;";

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(1, program.getStatements().size(), "Program should have 1 statement");

        Statement stmt = program.getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, stmt, "Statement is not an ExpressionStatement");

        ExpressionStatement exprStmt = (ExpressionStatement) stmt;
        testIntegerLiteral(exprStmt.getExpression(), 5);
    }

    @Test
    public void testPrefixExpressions() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        class PrefixTest {
            final String input;
            final String operator;
            final int integerValue;

            PrefixTest(String input, String operator, int integerValue) {
                this.input = input;
                this.operator = operator;
                this.integerValue = integerValue;
            }
        }

        PrefixTest[] prefixTests = {
                new PrefixTest("!5;", "!", 5),
                new PrefixTest("-15;", "-", 15)
        };

        for (PrefixTest tt : prefixTests) {
            Parser parser = createParser(tt.input);
            Program program = parser.parseProgram();

            assertNotNull(program, "parseProgram() returned null");
            assertEquals(1, program.getStatements().size(), "Program should have 1 statement");

            Statement stmt = program.getStatements().getFirst();
            assertInstanceOf(ExpressionStatement.class, stmt, "Statement is not an ExpressionStatement");

            ExpressionStatement exprStmt = (ExpressionStatement) stmt;
            Expression expr = exprStmt.getExpression();
            assertInstanceOf(PrefixExpression.class, expr, "Expression is not a PrefixExpression");

            PrefixExpression prefixExpr = (PrefixExpression) expr;
            assertEquals(tt.operator, prefixExpr.getOperator(), "prefixExpr.getOperator() is not " + tt.operator);

            testIntegerLiteral(prefixExpr.getRight(), tt.integerValue);
        }
    }

    @Test
    public void testInfixExpressions() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        class InfixTest {
            final String input;
            final int leftValue;
            final String operator;
            final int rightValue;

            InfixTest(String input, int leftValue, String operator, int rightValue) {
                this.input = input;
                this.leftValue = leftValue;
                this.operator = operator;
                this.rightValue = rightValue;
            }
        }

        InfixTest[] infixTests = {
                new InfixTest("5 + 5;", 5, "+", 5),
                new InfixTest("5 - 5;", 5, "-", 5),
                new InfixTest("5 * 5;", 5, "*", 5),
                new InfixTest("5 / 5;", 5, "/", 5),
                new InfixTest("5 > 5;", 5, ">", 5),
                new InfixTest("5 < 5;", 5, "<", 5),
                new InfixTest("5 == 5;", 5, "==", 5),
                new InfixTest("5 != 5;", 5, "!=", 5),
                new InfixTest("5 & 5;", 5, "&", 5),
                new InfixTest("5 | 5;", 5, "|", 5),
        };

        for (InfixTest tt : infixTests) {
            Parser parser = createParser(tt.input);
            Program program = parser.parseProgram();

            assertNotNull(program, "parseProgram() returned null");
            assertEquals(1, program.getStatements().size(), "Program should have 1 statement");

            Statement stmt = program.getStatements().getFirst();
            assertInstanceOf(ExpressionStatement.class, stmt, "Statement is not an ExpressionStatement");

            ExpressionStatement exprStmt = (ExpressionStatement) stmt;
            testInfixExpression(exprStmt.getExpression(), tt.leftValue, tt.operator, tt.rightValue);
        }
    }


    @Test
    public void testBooleanExpression() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = """
                true;
                false;
                """;

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(2, program.getStatements().size(), "Program should have 2 statements");

        Statement stmt1 = program.getStatements().get(0);
        Statement stmt2 = program.getStatements().get(1);

        assertInstanceOf(ExpressionStatement.class, stmt1, "Statement is not an ExpressionStatement");
        assertInstanceOf(ExpressionStatement.class, stmt2, "Statement is not an ExpressionStatement");

        ExpressionStatement exprStmt1 = (ExpressionStatement) stmt1;
        ExpressionStatement exprStmt2 = (ExpressionStatement) stmt2;

        testBooleanLiteral(exprStmt1.getExpression(), true);
        testBooleanLiteral(exprStmt2.getExpression(), false);
    }

    @Test
    public void testIfExpression() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = "if (x < y) { x }";

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(1, program.getStatements().size(), "Program should have 1 statement");

        Statement stmt = program.getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, stmt, "Statement is not an ExpressionStatement");

        ExpressionStatement exprStmt = (ExpressionStatement) stmt;
        Expression expr = exprStmt.getExpression();
        assertInstanceOf(IfExpression.class, expr, "Expression is not an IfExpression");

        IfExpression ifExpr = (IfExpression) expr;
        testInfixExpression(ifExpr.getCondition(), "x", "<", "y");

        assertEquals(1, ifExpr.getConsequence().getStatements().size(), "Consequence should have 1 statement");

        Statement consequence = ifExpr.getConsequence().getStatements().get(0);
        assertInstanceOf(ExpressionStatement.class, consequence, "Consequence is not an ExpressionStatement");

        ExpressionStatement consequenceExpr = (ExpressionStatement) consequence;
        testIdentifier(consequenceExpr.getExpression(), "x");

        assertNull(ifExpr.getAlternative(), "Alternative should be null");
    }

    @Test
    public void testIfElseExpression() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = "if (x < y) { x } else { y }";

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(1, program.getStatements().size(), "Program should have 1 statement");

        Statement stmt = program.getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, stmt, "Statement is not an ExpressionStatement");

        ExpressionStatement exprStmt = (ExpressionStatement) stmt;
        Expression expr = exprStmt.getExpression();
        assertInstanceOf(IfExpression.class, expr, "Expression is not an IfExpression");

        IfExpression ifExpr = (IfExpression) expr;
        testInfixExpression(ifExpr.getCondition(), "x", "<", "y");

        assertEquals(1, ifExpr.getConsequence().getStatements().size(), "Consequence should have 1 statement");

        Statement consequence = ifExpr.getConsequence().getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, consequence, "Consequence is not an ExpressionStatement");

        ExpressionStatement consequenceExpr = (ExpressionStatement) consequence;
        testIdentifier(consequenceExpr.getExpression(), "x");

        assertNotNull(ifExpr.getAlternative(), "Alternative should not be null");
        assertEquals(1, ifExpr.getAlternative().getStatements().size(), "Alternative should have 1 statement");

        Statement alternative = ifExpr.getAlternative().getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, alternative, "Alternative is not an ExpressionStatement");

        ExpressionStatement alternativeExpr = (ExpressionStatement) alternative;
        testIdentifier(alternativeExpr.getExpression(), "y");
    }

    @Test
    public void testFunctionLiteral() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = "fn(x, y) { x + y; }";

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(1, program.getStatements().size(), "Program should have 1 statement");

        Statement stmt = program.getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, stmt, "Statement is not an ExpressionStatement");

        ExpressionStatement exprStmt = (ExpressionStatement) stmt;
        Expression expr = exprStmt.getExpression();
        assertInstanceOf(FunctionLiteral.class, expr, "Expression is not a FunctionLiteral");

        FunctionLiteral function = (FunctionLiteral) expr;

        assertEquals(2, function.getParameters().size(), "Function should have 2 parameters");
        testIdentifier(function.getParameters().get(0), "x");
        testIdentifier(function.getParameters().get(1), "y");

        assertEquals(1, function.getBody().getStatements().size(), "Function body should have 1 statement");
        Statement bodyStmt = function.getBody().getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, bodyStmt, "Body statement is not an ExpressionStatement");

        ExpressionStatement bodyExprStmt = (ExpressionStatement) bodyStmt;
        testInfixExpression(bodyExprStmt.getExpression(), "x", "+", "y");
    }

    @Test
    public void testFunctionParameterParsing() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        class ParameterTest {
            final String input;
            final String[] expectedParams;

            ParameterTest(String input, String[] expectedParams) {
                this.input = input;
                this.expectedParams = expectedParams;
            }
        }

        ParameterTest[] tests = {
                new ParameterTest("fn() {};", new String[]{}),
                new ParameterTest("fn(x) {};", new String[]{"x"}),
                new ParameterTest("fn(x, y, z) {};", new String[]{"x", "y", "z"})
        };

        for (ParameterTest tt : tests) {
            Parser parser = createParser(tt.input);
            Program program = parser.parseProgram();

            Statement stmt = program.getStatements().getFirst();
            ExpressionStatement exprStmt = (ExpressionStatement) stmt;
            FunctionLiteral function = (FunctionLiteral) exprStmt.getExpression();

            assertEquals(tt.expectedParams.length, function.getParameters().size(),
                    "Function parameters length doesn't match. Expected " + tt.expectedParams.length);

            for (int i = 0; i < tt.expectedParams.length; i++) {
                testIdentifier(function.getParameters().get(i), tt.expectedParams[i]);
            }
        }
    }

    @Test
    public void testCallExpression() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = "add(1, 2 * 3, 4 + 5);";

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(1, program.getStatements().size(), "Program should have 1 statement");

        Statement stmt = program.getStatements().getFirst();
        assertInstanceOf(ExpressionStatement.class, stmt, "Statement is not an ExpressionStatement");

        ExpressionStatement exprStmt = (ExpressionStatement) stmt;
        Expression expr = exprStmt.getExpression();
        assertInstanceOf(CallExpression.class, expr, "Expression is not a CallExpression");

        CallExpression call = (CallExpression) expr;
        testIdentifier(call.getFunction(), "add");

        assertEquals(3, call.getArguments().size(), "Call should have 3 arguments");
        testIntegerLiteral(call.getArguments().get(0), 1);
        testInfixExpression(call.getArguments().get(1), 2, "*", 3);
        testInfixExpression(call.getArguments().get(2), 4, "+", 5);
    }

    @Test
    public void testComplexProgram() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String input = """
                var a = 5;
                var b = 10;
                var result = fn(x, y) {
                    var c = x + y;
                    return c;
                };
                var output = result(a, b);
                if (output > 10) {
                    return true;
                } else {
                    return false;
                }
                """;

        Parser parser = createParser(input);
        Program program = parser.parseProgram();

        assertNotNull(program, "parseProgram() returned null");
        assertEquals(5, program.getStatements().size(), "Program should have 5 statements");

    }

    // helpers
    private void testVarStatement(VarStatement stmt, String name) {
        assertEquals("var", stmt.literal(), "stmt.tokenLiteral() not 'var'");
        assertEquals(name, stmt.getName().getValue(), "stmt.getName().getValue() not '" + name + "'");
        assertEquals(name, stmt.getName().literal(), "stmt.getName() not '" + name + "'");
    }

    private void testIntegerLiteral(Expression expression, int value) {
        assertInstanceOf(IntegerLiteral.class, expression, "Expression is not an IntegerLiteral");

        IntegerLiteral intLiteral = (IntegerLiteral) expression;
        assertEquals(value, intLiteral.getValue(), "intLiteral.getValue() not " + value);
        assertEquals(String.valueOf(value), intLiteral.literal(), "intLiteral.tokenLiteral() not '" + value + "'");
    }

    private void testIdentifier(Expression expression, String value) {
        assertInstanceOf(Identifier.class, expression, "Expression is not an Identifier");

        Identifier identifier = (Identifier) expression;
        assertEquals(value, identifier.getValue(), "identifier.getValue() not '" + value + "'");
        assertEquals(value, identifier.literal(), "identifier.tokenLiteral() not '" + value + "'");
    }

    private void testBooleanLiteral(Expression expression, boolean value) {
        assertInstanceOf(BooleanLiteral.class, expression, "Expression is not a BooleanLiteral");

        BooleanLiteral boolLiteral = (BooleanLiteral) expression;
        assertEquals(value, boolLiteral.getValue(), "boolLiteral.getValue() not " + value);
        assertEquals(String.valueOf(value), boolLiteral.literal(), "boolLiteral.tokenLiteral() not '" + value + "'");
    }

    private void testInfixExpression(Expression expression, Object left, String operator, Object right) {
        assertInstanceOf(InfixExpression.class, expression, "Expression is not an InfixExpression");

        InfixExpression infixExpr = (InfixExpression) expression;

        if (left instanceof Integer) {
            testIntegerLiteral(infixExpr.getLeft(), (Integer) left);
        } else if (left instanceof String) {
            testIdentifier(infixExpr.getLeft(), (String) left);
        }

        assertEquals(operator, infixExpr.getOperator(), "infixExpr.getOperator() not '" + operator + "'");

        if (right instanceof Integer) {
            testIntegerLiteral(infixExpr.getRight(), (Integer) right);
        } else if (right instanceof String) {
            testIdentifier(infixExpr.getRight(), (String) right);
        }
    }
}