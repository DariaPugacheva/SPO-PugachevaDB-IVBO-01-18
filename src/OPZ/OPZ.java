package OPZ;

import Lexer.Tokens;
import Lexer.Tokenstype;
import Parsing_exceptions.Parsing_exception;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OPZ {
    private Stack<Tokens> OperatorStack = new Stack<>();
    private List<Tokens> tokens = new ArrayList<Tokens>();
    private List<Tokens> OPZTokens = new ArrayList<>();
    private int i = 0;

    public OPZ(List<Tokens> tokens) throws Parsing_exception
    {
        this.tokens = tokens;
        if (tokens.isEmpty())
        {
            throw new Parsing_exception("The List is Empty!");
        } else
        {
            while (i < tokens.size()) {
                opz();
            }
            ReturnList();
        }
    }

    public List<Tokens> ReturnList() {
        return OPZTokens;
    }

    private void opz()
    {
        while (i < tokens.size())
        {
            if (match().getType().equals(Tokenstype.VAR) || match().getType().equals(Tokenstype.DIGIT))
            {
                OPZTokens.add(match());
                i++;
            }

            else if (match().getType().equals(Tokenstype.ASSIGN_OP))
            {
                OperatorStack.push(match());
                i++;
            }

            else if (match().getType().equals(Tokenstype.LB))
            {
                OperatorStack.push(match());
                i++;
            }

            else if (match().getType().equals(Tokenstype.RB))
            {
                while (!OperatorStack.peek().getType().equals(Tokenstype.LB))
                {
                    OPZTokens.add(OperatorStack.pop());
                }
                OperatorStack.pop();
                i++;
                if (!OperatorStack.isEmpty() && PriorityCheck())
                {
                    while (!OperatorStack.peek().getType().equals(Tokenstype.LB)) {
                        if (OperatorStack.peek().getType().equals(Tokenstype.ASSIGN_OP)) break;
                        OPZTokens.add(OperatorStack.pop());
                    }
                }
            }

            else if (match().getType().equals(Tokenstype.ARITHMETIC_OP) || match().getType().equals(Tokenstype.EQ_OP) || match().getType().equals(Tokenstype.DIFF_OP))
            {
                if (PriorityCheck())
                {
                    while (!OperatorStack.peek().getType().equals(Tokenstype.LB)) {
                        if (OperatorStack.peek().getType().equals(Tokenstype.ASSIGN_OP)) break;
                        OPZTokens.add(OperatorStack.pop());
                    }
                } else if  ((OperatorStack.peek().getLexem().equals("*") && match().getLexem().equals("*")) || (OperatorStack.peek().getLexem().equals("*") && match().getLexem().equals("/")) ||
                        (OperatorStack.peek().getLexem().equals("/") && match().getLexem().equals("*")) || (OperatorStack.peek().getLexem().equals("/") && match().getLexem().equals("/")))
                {
                    OPZTokens.add(OperatorStack.pop());
                }
                OperatorStack.push(match());
                i++;
            } else if (match().getLexem().equals("{"))
            {
                i++;
                break;
            }
            else if (match().getType().equals(Tokenstype.D_EOS))
            {
                i++;
                break;
            } else if (match().getType().equals(Tokenstype.DD_IF) || match().getType().equals(Tokenstype.DD_ELSE)
                    || match().getType().equals(Tokenstype.DD_WHILE) || match().getType().equals(Tokenstype.DD_PRINT))
            {
                OPZTokens.add(match());
                i++;
            }
            else if (match().getType().equals(Tokenstype.FRB))
            {
                OPZTokens.add(match());
                i++;
            }
            else if (match().getType().equals(Tokenstype.DD_LLIST))
            {
                LinkedSkip();
            }
            else i++;

        }
        while (!OperatorStack.isEmpty())
            OPZTokens.add(OperatorStack.pop());
    }
    private void LinkedSkip()
    {
        while (true)
        {
            if (match().getType().equals(Tokenstype.VAR) || match().getType().equals(Tokenstype.DD_IF) || match().getType().equals(Tokenstype.DD_WHILE))
                break;
            else
                i++;
        }
    }
    private boolean PriorityCheck()
    {
        if ((OperatorStack.peek().getLexem().equals("-") && match().getLexem().equals("-")) || (OperatorStack.peek().getLexem().equals("-") && match().getLexem().equals("+"))
                || (OperatorStack.peek().getLexem().equals("+") && match().getLexem().equals("-")) || (OperatorStack.peek().getLexem().equals("+") && match().getLexem().equals("+")) ||
                (OperatorStack.peek().getLexem().equals("*") && match().getLexem().equals("+")) || (OperatorStack.peek().getLexem().equals("*") && match().getLexem().equals("-")) ||
                (OperatorStack.peek().getLexem().equals("/") && match().getLexem().equals("+")) || (OperatorStack.peek().getLexem().equals("/") && match().getLexem().equals("-")))
        {
            return true;
        }
        else return false;
    }


    private Tokens match()
    {
        return tokens.get(i); // Возвращает следующий токен
    }

}
