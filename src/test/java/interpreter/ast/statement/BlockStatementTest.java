package interpreter.ast.statement;

import com.github.konstantinevashalomidze.interpreter.ast.statement.BlockStatement;
import com.github.konstantinevashalomidze.interpreter.ast.statement.Statement;
import com.github.konstantinevashalomidze.interpreter.token.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlockStatementTest {
    private BlockStatement blockStatement;
    private Token mockToken;

    @BeforeEach
    public void setUp() {
        mockToken = mock(Token.class);
        when(mockToken.literal()).thenReturn("{");
        blockStatement = new BlockStatement(mockToken);
    }

    @Test
    public void testConstructorInitialization() {
        assertEquals(mockToken, blockStatement.getToken());
        assertTrue(blockStatement.getStatements().isEmpty());
    }


    @Test
    public void testLiteral() {
        assertEquals("{", blockStatement.literal());
    }

    @Test
    public void testStatementNode() {
        // This method is  not yet implemented in class
    }


    @Test
    public void testSetStatements() {
        List<Statement> newStatements = new ArrayList<>();
        Statement mockStatement = mock(Statement.class);
        newStatements.add(mockStatement);
        blockStatement.setStatements(newStatements);

        assertSame(newStatements, blockStatement.getStatements());
        assertEquals(1, blockStatement.getStatements().size());
    }

}
