package interpreter.ast.statement;

import interperter.ast.statement.BlockStatement;
import interperter.ast.statement.Statement;
import interperter.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlockStatementTest
{
    private BlockStatement blockStatement;
    private Token mockToken;

    @BeforeEach
    public void setUp()
    {
        mockToken = mock(Token.class);
        when(mockToken.literal()).thenReturn("{");
        blockStatement = new BlockStatement(mockToken);
    }

    @Test
    public void testConstructorInitialization()
    {
        assertEquals(mockToken, blockStatement.getToken());
        assertTrue(blockStatement.getStatements().isEmpty());
    }


    @Test
    public void testLiteral()
    {
        assertEquals("{", blockStatement.literal());
    }

    @Test
    public void testStatementNode()
    {
        // This method is  not yet implemented in class
    }

    @Test
    public void testToStringWithSingleStatement()
    {
        Statement mockStatement = mock(Statement.class);
        when(mockStatement.toString()).thenReturn("statement1");

        blockStatement.getStatements().add(mockStatement);

        assertEquals("{ statement1; }", blockStatement.toString());
    }

    @Test
    public void testToStringWithMultipleStatements()
    {
        Statement mockStatement1 = mock(Statement.class);
        Statement mockStatement2 = mock(Statement.class);
        Statement mockStatement3 = mock(Statement.class);

        when(mockStatement1.toString()).thenReturn("statement1");
        when(mockStatement2.toString()).thenReturn("statement2");
        when(mockStatement3.toString()).thenReturn("statement3");

        blockStatement.getStatements().add(mockStatement1);
        blockStatement.getStatements().add(mockStatement2);
        blockStatement.getStatements().add(mockStatement3);

        assertEquals("{ statement1; statement2; statement3; }", blockStatement.toString());
    }

    @Test
    public void testSetStatements()
    {
        List<Statement> newStatements = new ArrayList<>();
        Statement mockStatement = mock(Statement.class);
        newStatements.add(mockStatement);
        blockStatement.setStatements(newStatements);

        assertSame(newStatements, blockStatement.getStatements());
        assertEquals(1, blockStatement.getStatements().size());
    }

}
