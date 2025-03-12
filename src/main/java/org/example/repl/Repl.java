package org.example.repl;

import org.example.ast.node.Program;
import org.example.evaluator.Evaluator;
import org.example.lexer.Lexer;
import org.example.object.Object;
import org.example.parser.Parser;
import org.example.token.Token;
import org.example.token.TokenType;

import java.io.*;
import java.util.List;

public class Repl {
    private static final String PROMPT = ">> ";

    private static final String KOSTA =
            """
                    ("`-''-/").___..--''"`-._\s
                     `6_ 6  )   `-.  (     ).`-.__.`)\s
                     (_Y_.)'  ._   )  `._ `. ``-..-'\s
                       _..`--'_..-_/  /--'_.'
                      ((((.-''  ((((.'  (((.-'\s
            """;

    /**
     * Starts readWholeStringStartingFromCurrentCharacter eval print loop. which continuously reads user input, evaluates it and prints the output.
     * @param input user provided input
     * @param output evaluation result
     */
    public static void start(InputStream input, OutputStream output) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
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
                Evaluator evaluator = new Evaluator();
                if (!parser.getErrors().isEmpty())
                {
                    printParseErrors(writer, parser.getErrors());
                    continue;
                }

                Object evaluated = evaluator.evaluate(program);
                if (evaluated != null)
                {
                    writer.write(evaluated.inspect());
                    writer.write("\n");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printParseErrors(BufferedWriter writer, List<String> errors) throws IOException {
        writer.write(KOSTA);
        writer.write("Parser tiger can't eat that code chunk!\n");
        writer.write(" parser errors:\n");
        for (String error : errors)
        {
            writer.write("\t" + error + "\n");
        }
    }
}