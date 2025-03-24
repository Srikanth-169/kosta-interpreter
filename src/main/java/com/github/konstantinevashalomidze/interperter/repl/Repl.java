package com.github.konstantinevashalomidze.interperter.repl;



import com.github.konstantinevashalomidze.interperter.ast.node.Program;
import com.github.konstantinevashalomidze.interperter.evaluator.Environment;
import com.github.konstantinevashalomidze.interperter.evaluator.Evaluator;
import com.github.konstantinevashalomidze.interperter.evaluator.value.Value;
import com.github.konstantinevashalomidze.interperter.lexer.Lexer;
import com.github.konstantinevashalomidze.interperter.parser.Parser;

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
                if (line == null || line.startsWith("exit()")) {
                    return;
                }

                boolean userWantsTree = false;
                // if user wants to print AST
                if (line.startsWith("tree()"))
                {
                    line = line.replace("tree()", "");
                    userWantsTree = true;
                }

                Lexer lexer = new Lexer(line);
                Parser parser = new Parser(lexer);

                Program program = parser.parseProgram();
                if (userWantsTree) { // if user wants to print AST print it and return
                    writer.write(program.toString());
                    writer.flush();
                    return;
                }

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