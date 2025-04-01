package interpreter.ast.node;

import com.github.konstantinevashalomidze.interpreter.ast.node.Program;
import com.github.konstantinevashalomidze.interpreter.ast.statement.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProgramTest {
    private Program program;

    @BeforeEach
    public void setup() {
        program = new Program();
    }

    @Test
    public void testNewProgramHasEmptyStatements() {
        assertTrue(program.getStatements().isEmpty());
    }

    @Test
    public void testLiteralWithEmptyStatements() {
        assertEquals("", program.literal());
    }


    @Test
    public void testLiteralWithStatements() {
        Statement mockStatement = mock(Statement.class);
        when(mockStatement.literal()).thenReturn("test literal");

        program.getStatements().add(mockStatement);

        // Verify literal returns the first statement's literal
        assertEquals("test literal", program.literal());
    }


}
