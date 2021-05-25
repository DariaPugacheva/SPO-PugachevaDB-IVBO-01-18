package Parser;

import Lexer.Tokens;
import Lexer.Tokenstype;
import Parsing_exceptions.Parsing_exception;
import java.util.List;

public class Parser {
    private StringBuilder LangStr = new StringBuilder();
    private List<Tokens> tokens;
    private int i = 0;
    private int s = 0;
    private boolean isSuccsesiful = false;

    public Parser(List<Tokens> tokens) throws Parsing_exception
    {
        this.tokens = tokens;
        if (tokens.isEmpty())
        {
            throw new Parsing_exception("The List is Empty!");
        } else {
            lang();
        }
    }

    private void lang() throws Parsing_exception
    {
        if (LangStr.length() > 0) System.out.println(LangStr);
        LangStr.delete(0, LangStr.length());
        if (i == tokens.size() )
        {
            isSuccsesiful = true;
            EndOfSearching();
        } else {
            try {
                a_expr();
            } catch (Parsing_exception err) {
                funk_expr();
            }
        }
    }

    private void funk_expr() throws Parsing_exception
    {
        try {
            cond_expr();
        } catch (Parsing_exception err) {
            try {
                cyc_expr();
            } catch (Parsing_exception err1)
            {
                try {
                    print_expr();
                } catch (Parsing_exception err2)
                {
                    try {
                        List_expr();
                    } catch (Parsing_exception err3)
                    {
                        AddRemove_expr();
                    }
                }
            }
        }
    }

    private void print_expr()  throws Parsing_exception
    {
        DD_PRINT();
        LB();
        VAR();
        RB();
        if (match().getType().equals(Tokenstype.D_EOS))
        {
            i++;
            lang();
        }
    }

    private void DD_PRINT() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.DD_PRINT);
        LangStr.append(" DD_PRINT");
        i++;
    }

    /*...................................................................*/
    /*Все что относится к списку*/

    private void List_expr() throws Parsing_exception
    {
        while (!match().getType().equals(Tokenstype.D_EOS)) {
            DD_LLIST();
            DIFF_OP();
            D_TYPE();
            DIFF_OP();
            NAME();
            matchToken(match(), Tokenstype.ASSIGN_OP);
            i++;
            D_NEW();
            DD_LLIST();
            DIFF_OP();
            DIFF_OP();
            LB();
            RB();
        }
        i++;
        lang();
    }

    private void D_NEW() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.D_NEW);
        LangStr.append(" D_NEW");
        i++;
    }

    private void NAME() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.NAME);
        LangStr.append(" NAME");
        i++;
    }

    private void D_TYPE() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.D_TYPE);
        LangStr.append(" D_TYPE");
        i++;
    }

    private void DD_LLIST() throws Parsing_exception
    {
        matchToken(match(),Tokenstype.DD_LLIST);
        LangStr.append(" DD_LLIST");
        i++;
    }

    private void AddRemove_expr() throws Parsing_exception
    {
        while (!match().getType().equals(Tokenstype.D_EOS)) {
            NAME();
            D_DOT();
            try {
                DD_ADD();
            } catch (Parsing_exception err)
            {
                DD_REMOVE();
            }
            LB();
            value();
            RB();
        }
        i++;
        lang();
    }

    private void DD_ADD() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.DD_ADD);
        LangStr.append(" DD_ADD");
        i++;
    }

    private void DD_REMOVE() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.DD_REMOVE);
        LangStr.append(" DD_REMOVE");
        i++;
    }

    private void D_DOT() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.D_DOT);
        LangStr.append(" D_DOT");
        i++;
    }


    /*...................................................................*/
    /*Все что относится к циклу*/

    private void cyc_expr() throws Parsing_exception
    {
        try {
            FRB();
        } catch (Parsing_exception err) {
            DD_WHILE();
            LB();
            cond();
            RB();
            LangStr.append("\n");
            FLB();
            LangStr.append("\n");
            try {
                a_expr();
            } catch (Parsing_exception err1) {
                try {
                    value_expr();
                } catch (Parsing_exception err2)
                {
                    cond_expr();
                }
            }
        }
    }

    private void DD_WHILE() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.DD_WHILE);
        LangStr.append(" DD_WHILE");
        i++;
    }


    /*...................................................................*/
    /*Все что относится к условию*/

    private void cond_expr() throws Parsing_exception
    {
        try {
            FRB();
        } catch (Parsing_exception err) {

            try {
                DD_IF();
                LB();
                cond();
                RB();
            } catch (Parsing_exception err2)
            {
                DD_ELSE();
            }
            LangStr.append("\n");
            FLB();
            LangStr.append("\n");
            try {
                a_expr();
            } catch (Parsing_exception err1) {
                value_expr();
            }
        }
    }

    private void DD_ELSE() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.DD_ELSE);
        LangStr.append(" DD_ELSE");
        i++;
    }

    private void FRB() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.FRB);
        LangStr.append(" FRB");
        i++;
        lang();
    }


    private void FLB() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.FLB);
        LangStr.append(" FLB");
        i++;
    }

    private void RB() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.RB);
        LangStr.append(" RB");
        i++;
    }

    private void cond() throws Parsing_exception
    {
        value();
        try {
            DIFF_OP();
        } catch (Parsing_exception err)
        {
            EQ_OP();
        }
        value();

    }

    private void EQ_OP() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.EQ_OP);
        LangStr.append(" EQ_OP");
        i++;
    }

    private void DIFF_OP() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.DIFF_OP);
        LangStr.append(" DIFF_OP");
        i++;
    }

    private void LB() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.LB);
        LangStr.append(" LB");
        i++;
    }

    private void DD_IF() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.DD_IF);
        LangStr.append(" DD_IF");
        i++;
    }

    /*Арифметические операции*/

    private void a_expr() throws Parsing_exception
    {
        VAR();
        ASSIGN_OP();
        value_expr();
    }

    private void value_expr() throws Parsing_exception
    {
        while (true)
        {
            try {
                LB();
                s++;
            }catch (Parsing_exception err) {
                try{
                    value();
                } catch (Parsing_exception err1) {
                    if (match().getType() == Tokenstype.D_EOS && s == 0) {
                        i++;
                        lang();
                        break;
                    } else  if (match().getType() == Tokenstype.D_EOS && s != 0) {
                        System.out.println("There is incorrect number of brackets in expression! Please find the expression!");
                        isSuccsesiful = false;
                        EndOfSearching();
                        i++;
                        break;
                    } else if (match().getType() == Tokenstype.RB) {
                        LangStr.append(" RB");
                        s--;
                        i++;
                    } else OP();
                }
            }
        }
    }

    private void value() throws Parsing_exception
    {
        try {
            VAR();
        } catch (Exception e)
        {
            DIGIT();
        }
    }

    private void DIGIT() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.DIGIT);
        LangStr.append(" DIGIT");
        i++;
    }

    private void OP() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.ARITHMETIC_OP);
        LangStr.append(" ARITHMETIC_OP");
        i++;
    }

    private void ASSIGN_OP() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.ASSIGN_OP);
        LangStr.append(" ASSIGN_OP");
        i++;
    }

    private void VAR() throws Parsing_exception
    {
        matchToken(match(), Tokenstype.VAR);
        LangStr.append(" VAR");
        i++;
    }

    /*...................................................................*/
    /*Поиск токенов в созданном списке, и проверка соответствия типов*/

    private void matchToken(Tokens token, Tokenstype type) throws Parsing_exception
    {
        if (!token.getType().equals(type))
        {
            throw new Parsing_exception(type
                    + " expected but "
                    + token.getType().name() + ": " + token.getType()
                    + " found");
        }
    }

    private Tokens match()
    {
        return tokens.get(i); // Возвращает следующий токен
    }

    public boolean EndOfSearching()
    {
        return isSuccsesiful;
    }

}
