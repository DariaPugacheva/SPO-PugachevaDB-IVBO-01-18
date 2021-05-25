package Linked_list;

import Lexer.Tokens;
import Lexer.Tokenstype;
import Parsing_exceptions.Parsing_exception;

import java.util.LinkedList;
import java.util.List;

public class Linked_list {
    private List<Tokens> tokens;
    private List<Integer> LLIST;
    private int i = 0;
    private String typestr;
    private String NAMEstr;
    private int temp = 0;
    private int counter = 0;


    public Linked_list (List<Tokens> tokens) throws Parsing_exception
    {
        this.tokens = tokens;
        if (tokens.isEmpty())
        {
            throw new Parsing_exception("The List is Empty!");
        } else {
            ListCreationFunction();
            ADDingREMOVingFunction();
            System.out.println(LLIST);
        }
    }

    /*...................................................................*/
    /*Добавление/удаление*/

    private void ADDingREMOVingFunction() throws Parsing_exception
    {
        while (true)
        {
            if (match().getType().equals(Tokenstype.VAR) || match().getType().equals(Tokenstype.DD_IF) || match().getType().equals(Tokenstype.DD_WHILE))
                break;
            else
            {
                NAME();
                DOT();
                try {
                    ADD();
                } catch (Parsing_exception err)
                {
                    REMOVE();
                }
            }
        }
    }

    private void REMOVE() throws Parsing_exception
    {
        int index = 0;
        System.out.println(LLIST);
        i += 2;
        temp = Integer.parseInt(match().getLexem());
        index = LLIST.indexOf(temp);
        LLIST.remove(index);
        i += 3;
        if (i >= tokens.size())
        {
            End();
        }
    }

    private void ADD() throws Parsing_exception
    {
        if (match().getType().equals(Tokenstype.DD_REMOVE))
        {
            throw new Parsing_exception("Removing");
        } else {
            i += 2;
            temp = Integer.parseInt(match().getLexem());
            LLIST.add(temp);
            counter++;
            i += 3;
        }
        if (i >= tokens.size())
        {
            End();
        }
    }

    private void DOT() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.D_DOT);
        i++;
    }

    private void NAME() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.NAME);
        i++;
    }



    /*...................................................................*/
    /*Создание списка*/

    private void ListCreationFunction()
    {
        while (!match().getType().equals(Tokenstype.DD_LLIST))
        {
            i++;
        }
        LLIST = new LinkedList<>();
        i += 2;
        typestr = match().getLexem();
        i+= 2;
        NAMEstr = match().getLexem();
        while (!match().getType().equals(Tokenstype.NAME))
        {
            i++;
        }
        i += 9;
    }

    private void matchToken(Tokens token, Tokenstype type) throws Parsing_exception
    {
        if (!token.getType().equals(type))
        {
            throw new Parsing_exception(type
                    + " expected but "
                    + token.getType().name() + ": " + token.getType()
                    + " found");
        }
//        else {
//            System.out.println("' " + token.getLexem() + " '" + " - lexem's type is actually - " + type);
//        }
    }

    private Tokens match()
    {
        return tokens.get(i); // Возвращает следующий токен
    }

    public void End()
    {
        System.out.println("List successfully created!");
    }
}
