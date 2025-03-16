package interperter.ast.expression;


import org.kosta.interperter.ast.statement.BlockStatement;
import org.kosta.interperter.token.Token;

import java.util.ArrayList;
import java.util.List;


public class FunctionLiteral
    implements Expression
{

    private Token token;
    private List<Identifier> parameters;

    private BlockStatement body;


    public FunctionLiteral(Token token) {
        this.token = token;
    }


    @Override
    public void expressionNode() {


    }

    @Override
    public String literal() {
        return token.literal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<String> params = new ArrayList<>();
        for (Identifier identifier : parameters)
        {
            params.add(identifier.toString());
        }

        sb.append(token.literal()).append("(").append(String.join(", ", params)).append(") ").append(body.toString());
        return sb.toString();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public List<Identifier> getParameters() {
        return parameters;
    }

    public void setParameters(List<Identifier> parameters) {
        this.parameters = parameters;
    }

    public BlockStatement getBody() {
        return body;
    }

    public void setBody(BlockStatement body) {
        this.body = body;
    }
}
