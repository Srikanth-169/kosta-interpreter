package org.example;

import org.example.lexer.Lexer;
import org.example.parser.Parser;

public class Main2 {

    public static void main(String[] args) {
        Lexer lexer = new Lexer("3 < 5 == true;");
        Parser parser = new Parser(lexer);

        System.out.println(parser.parseProgram());
    }
}
