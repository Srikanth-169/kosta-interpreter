package interperter.repl;



import interperter.ast.node.Program;
import interperter.evaluator.Environment;
import interperter.evaluator.Evaluator;
import interperter.evaluator.value.Value;
import interperter.lexer.Lexer;
import interperter.parser.Parser;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Repl {
    private static final String PROMPT = ">> ";

    /**
     * Starts readWholeStringStartingFromCurrentCharacter eval print loop. which continuously reads user input, evaluates it and prints the output.
     * @param input user provided input
     * @param output evaluation result
     */
    public static void start(InputStream input, OutputStream output) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        Environment environment = new Environment();
        try {
            while (true) {
                writer.write(PROMPT);
                writer.flush();

                String line = reader.readLine();
                if (line == null || line.equals("exit()")) {
                    return;
                }

                Lexer lexer = new Lexer(line);
                Parser parser = new Parser(lexer);

                Program program = parser.parseProgram();
                Evaluator evaluator = new Evaluator(environment);
                if (!parser.errors().isEmpty())
                {
                    printParseErrors(writer, parser.errors());
                    continue;
                }


                Value evaluated = evaluator.evaluate(program);
                if (evaluated != null)
                {
                    writer.write(evaluated.inspect());
                    writer.write("\n");
                    writer.flush();
                }
            }
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void printParseErrors(BufferedWriter writer, List<String> errors) throws IOException {
        writer.write("Parser errors:\n");
        for (String error : errors)
            writer.write("\t" + error + "\n");
        writer.flush();
    }
}