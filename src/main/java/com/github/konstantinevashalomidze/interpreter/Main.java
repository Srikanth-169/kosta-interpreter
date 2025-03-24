package com.github.konstantinevashalomidze.interpreter;


import com.github.konstantinevashalomidze.interpreter.repl.Repl;

/**
 * This application is written by following the design of the book  'Writing and interpreter in go by Thorsten Ball'.
 * @author Konstantine Vashalomidze
 */
public class Main
{
    public static void main(String[] args)
    {
        String user = System.getProperty("user.name");
        if (user == null)
            user = "Guest";

        System.out.printf("Hello %s! This is Kosta's programming language\n", user);
        System.out.println("Feel free to type in commands\n");
        // Read, Eval, Print, Loop
        Repl.start(System.in, System.out);
    }
}