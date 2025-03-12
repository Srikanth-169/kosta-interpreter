package org.example;

import org.example.ast.node.Program;
import org.example.lexer.Lexer;
import org.example.parser.Parser;

public class Main2 {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("if (5 * 5 + 10 > 34) { 99 } else { 100 }");
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        System.out.println(program.getStatements());

    }
}
