package ast;

import org.example.ast.expression.Identifier;
import org.example.ast.node.Program;
import org.example.ast.statement.VarStatement;
import org.example.token.Token;
import org.example.token.TokenType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AstTest
{


    @Test
    public void TestToString() {
        Program program = new Program();
        program.getStatements()
                .add(new VarStatement(
                        new Token(TokenType.VARIABLE, "var"),
                        new Identifier(new Token(TokenType.IDENTIFIER, "myVar"), "myVar"),
                        new Identifier(new Token(TokenType.IDENTIFIER, "anotherVar"), "anotherVar")

                ));

        assertEquals(
                program.toString(),
                "var myVar = anotherVar;",
                String.format("expected=var myVar = anotherVar; got=%s", program)
        );

    }


}
