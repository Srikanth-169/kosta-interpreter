package org.example.repl;

import org.example.ast.node.Program;
import org.example.lexer.Lexer;
import org.example.parser.Parser;
import org.example.token.Token;
import org.example.token.TokenType;

import java.io.*;

public class Repl {
    private static final String PROMPT = ">> ";

    /**
     * Starts read eval print loop. which continuously reads user input, evaluates it and prints the output.
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
                Token token;

                // Read next token while it's not end of the file
                while ((token = lexer.readToken()).getTokenType() != TokenType.EOF) {
                    System.out.printf("%s%n", token);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}