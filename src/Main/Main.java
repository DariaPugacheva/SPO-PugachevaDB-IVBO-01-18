package Main;

import Lexer.Lexer;
import Linked_list.Linked_list;
import Parser.Parser;
import OPZ.OPZ;
import StackMachine.Stack_machine;

public class Main {
    public static void main(String[] args) throws Exception
    {
        /*Вызов Лексического анализатора*/

        Lexer lexer = new Lexer("C:/SPO PugachevaDB IVBO-01-18/src/Example.txt"); // Call the Lexer

        System.out.println("-----------------");
        System.out.println("Lexical Analysis");
        System.out.println("-----------------");
        while (!lexer.isERROR()) {
            lexer.moveAhead();
        }
        System.out.println("List of the tokens: " + lexer.GetTokenList());
        System.out.println("Number of tokens is: " + lexer.GetTokenList().size());
        System.out.println("The Lexical analysis is done!");

        /*Вызов Синтаксического анализатора*/

        System.out.println("-----------------");
        System.out.println("Syntax Analysis");
        System.out.println("-----------------");

        Parser parser = new Parser(lexer.GetTokenList());

        System.out.println("End of file is reached.");
        System.out.println("Syntax analysis is done!");


        /*Вызов Преобразователя в ОПЗ*/

        System.out.println("-----------------");
        System.out.println("Conversion to OPZ");
        System.out.println("-----------------");

        if (parser.EndOfSearching())
        {
            OPZ poliz = new OPZ(lexer.GetTokenList());
            System.out.println("List of OPZ: ");
            System.out.println(poliz.ReturnList());
            System.out.println("EOF is reached.");
            System.out.println("Total number of tokens is: " + poliz.ReturnList().size());
            System.out.println("OPZ is done!");

            System.out.println("-----------------");
            System.out.println("Stack machine is preparing your expressions...");
            System.out.println("-----------------");

            Stack_machine stackmach = new Stack_machine(poliz.ReturnList());

        } else System.out.println("Parser is Unsuccsessful!");

        System.out.println("-----------------");
        System.out.println("Creating Linked List");
        System.out.println("-----------------");

        Linked_list list1 = new Linked_list(lexer.GetTokenList());





    }
}
