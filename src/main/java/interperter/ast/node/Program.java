package interperter.ast.node;


import org.kosta.interperter.ast.statement.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Program is the root node in AST, it consists of statements represented as array list.
 *
 * @author Konstantine Vashalomdize
 */
public class Program
    implements Node
{
    private List<Statement> statements;
    public Program() {
        statements = new ArrayList<>();
    }

    @Override
    public String literal()
    {
        if (!statements.isEmpty())
            return statements.getFirst().literal();
        else return "";
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        int count = 0;
        for (Statement statement : statements) {
            if (count != statements.size() - 1)
                sb.append(statement.toString()).append(" ");
            else
                sb.append(statement.toString());
            count++;
        }

        return sb.toString();
    }


}
