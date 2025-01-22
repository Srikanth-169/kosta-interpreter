package org.example.repl;

import org.example.lexer.Lexer;
import org.example.token.Token;
import org.example.token.TokenType;

import java.io.*;

public class Repl {
    private static final String PROMPT = ">> ";

    public static void start(InputStream input, OutputStream output) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        try {
            while (true) {
                writer.write(PROMPT);
                writer.flush();

                String line = reader.readLine();
                if (line == null) {
                    return;
                }

                Lexer lexer = new Lexer(line);
                Token token;

                while ((token = lexer.readToken()).getTokenType() != TokenType.EOF) {
                    System.out.printf("%s%n", token);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}