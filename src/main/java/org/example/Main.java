package org.example;

import org.example.repl.Repl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String user = System.getProperty("user.name");
        if (user == null)
            user = "Guest";

        System.out.printf("Hello %s! This is Kosta programming language\n", user);
        System.out.println("Feel free to type in commands\n");
        Repl.start(System.in, System.out);



    }
}