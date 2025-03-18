package interpreter;

import interperter.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest
{
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private final String originalUsername = System.getProperty("user.name");


    @BeforeEach
    public void setUpStreams()
    {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams()
    {
        System.setOut(originalOut);
        System.setIn(originalIn);
        if (originalUsername != null)
            System.setProperty("user.name", originalUsername);
        else
            System.clearProperty("user.name");
    }



    @Test
    public void testMainWithValidUsername()
    {
        String testUsername = "TestUser";
        System.setProperty("user.name", testUsername);

        // Terminate Repl after execution
        ByteArrayInputStream inputStream = new ByteArrayInputStream("exit\n".getBytes());
        System.setIn(inputStream);

        // Act
        Main.main(new String[] {});

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Hello " + testUsername));
        assertTrue(output.contains("This is Kosta's programming language"));
    }


    @Test
    public void testMainWithNullUsername()
    {
        System.clearProperty("user.name");

        // Terminate Repl after execution
        ByteArrayInputStream inputStream = new ByteArrayInputStream("exit\n".getBytes());
        System.setIn(inputStream);

        // Act
        Main.main(new String[] {});

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Hello Guest"));



    }




}
