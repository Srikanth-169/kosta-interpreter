package interperter.evaluator.value;



import interperter.ast.expression.Identifier;
import interperter.ast.statement.BlockStatement;
import interperter.evaluator.Environment;

import java.util.List;
import java.util.stream.Collectors;

public class Function
    implements Value
{
    private List<Identifier> parameters;
    private BlockStatement body;

    private Environment environment;

    public Function(List<Identifier> parameters, BlockStatement body, Environment environment)
    {
        this.parameters = parameters;
        this.body = body;
        this.environment = environment;
    }

    @Override
    public String inspect() {
        StringBuilder sb = new StringBuilder();
        String params = parameters.stream().map(Identifier::getValue).collect(Collectors.joining(", "));
        sb.append("fn ").append("(").append(params).append(") {\n").append(body.toString()).append("\n}");
        return sb.toString();
    }

    public BlockStatement getBody() {
        return body;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public List<Identifier> getParameters() {
        return parameters;
    }
}
