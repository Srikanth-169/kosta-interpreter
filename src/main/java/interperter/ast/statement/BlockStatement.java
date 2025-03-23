package interperter.ast.statement;


import interperter.token.Token;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement
    implements Statement
{

    private Token token;
    private List<Statement> statements;

    public BlockStatement(Token token) {
        this.token = token;
        statements = new ArrayList<>();
    }

    @Override
    public String literal() {
        return token.literal();
    }

    @Override
    public void statementNode() {

    }



    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
}
