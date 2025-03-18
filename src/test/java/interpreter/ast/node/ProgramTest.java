package interpreter.ast.node;

import interperter.ast.node.Program;
import interperter.ast.statement.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProgramTest
{
    private Program program;

    @BeforeEach
    public void setup()
    {
        program = new Program();
    }

    @Test
    public void testNewProgramHasEmptyStatements()
    {
        assertTrue(program.getStatements().isEmpty());
    }

    @Test
    public void testLiteralWithEmptyStatements()
    {
        assertEquals("", program.literal());
    }


    @Test
    public void testLiteralWithStatements()
    {
        Statement mockStatement = mock(Statement.class);
        when(mockStatement.literal()).thenReturn("test literal");

        program.getStatements().add(mockStatement);

        // Verify literal returns the first statement's literal
        assertEquals("test literal", program.literal());
    }

    @Test
    public void testToStringWithNoStatements()
    {
        assertEquals("", program.toString());
    }


    @Test
    public void testToStringWithSingleStatement()
    {
        Statement mockStatement = mock(Statement.class);
        when(mockStatement.toString()).thenReturn("statement1");

        program.getStatements().add(mockStatement);

        // Verify toString works with a single statement
        assertEquals("statement1;", program.toString());
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

        program.getStatements().add(mockStatement1);
        program.getStatements().add(mockStatement2);
        program.getStatements().add(mockStatement3);


        // Verify toString concatenates statements with spaces
        assertEquals("statement1; statement2; statement3;", program.toString());



    }


}
