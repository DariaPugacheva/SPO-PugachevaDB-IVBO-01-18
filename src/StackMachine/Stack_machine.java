package StackMachine;

import Lexer.Tokens;
import java.util.*;

public class Stack_machine {
    private List<Tokens> RPNConvertedExpressions = new ArrayList<>();

    private Stack<Tokens> VarStack = new Stack<>();
    private Stack<Tokens> OperatorStack = new Stack<>();
    private HashMap<String, Integer> VarTable = new HashMap<>();

    private boolean isSucsessful = true;
    private int i = 0;
    private boolean IFistrue = false;
    private boolean Whfound = false;
    private boolean CycleStarted = false;
    private int goal = 0;
    private String symbol;
    private int cyclesize = 0;

    public Stack_machine(List<Tokens> RPNConvertedExpressions)
    {
        this.RPNConvertedExpressions = RPNConvertedExpressions;
        if (RPNConvertedExpressions.isEmpty())
        {
            isSucsessful = false;
            EndOfConversion();
        } else {
            PrepareTheExpressions();
            if (EndOfConversion())
                System.out.println(VarTable);
            else System.out.println("Incorrect condition in cycle! Please find the problem");
        }
    }

    private void PrepareTheExpressions()
    {
        while (i < RPNConvertedExpressions.size())
        {
            int tmpR;
            int tmpL;
            if (match().getType().equals(Lexer.Tokenstype.VAR) || match().getType().equals(Lexer.Tokenstype.DIGIT))
            {
                VarStack.push(match());
                i++;
            }
            else if (match().getType().equals(Lexer.Tokenstype.ASSIGN_OP))
            {
                OperatorStack.push(match());
                i++;
                int digit = Integer.parseInt(VarStack.pop().getLexem());
                String varname = VarStack.pop().getLexem();
                VarTable.put(varname, digit);
                OperatorStack.pop();
            }
            else if (match().getType().equals(Lexer.Tokenstype.DIFF_OP) || match().getType().equals(Lexer.Tokenstype.EQ_OP))
            {
                OperatorStack.push(match());
                if (VarTable.containsKey(VarStack.peek().getLexem()))
                {
                    tmpR = VarTable.get(VarStack.peek().getLexem());
                } else tmpR = Integer.parseInt(VarStack.peek().getLexem());
                VarStack.pop();
                if (VarTable.containsKey(VarStack.peek().getLexem()))
                {
                    tmpL = VarTable.get(VarStack.peek().getLexem());
                } else tmpL = Integer.parseInt(VarStack.peek().getLexem());
                VarStack.pop();
                if (Whfound)
                {
                    goal = tmpR;
                    symbol = OperatorStack.pop().getLexem();
                    CycleStarted = true;
                    i = GetCycleSize();
                    i++;
                }
                else {
                    if ((OperatorStack.peek().getLexem().equals(">") && tmpL > tmpR) || (OperatorStack.peek().getLexem().equals("<") && tmpL < tmpR)
                            || (OperatorStack.peek().getLexem().equals("~") && tmpL == tmpR)) {
                        OperatorStack.pop();
                        IFistrue = true;
                        i++;
                    } else {
                        OperatorStack.pop();
                        while ((!match().getType().equals(Lexer.Tokenstype.FRB))) {
                            i++;
                        }
                        i++;
                    }
                }
            }
            else if (match().getType().equals(Lexer.Tokenstype.ARITHMETIC_OP))
            {
                OperatorStack.push(match());
                if (VarTable.containsKey(VarStack.peek().getLexem()))
                {
                    tmpR = VarTable.get(VarStack.peek().getLexem());
                } else tmpR = Integer.parseInt(VarStack.peek().getLexem());
                VarStack.pop();
                if (VarTable.containsKey(VarStack.peek().getLexem()))
                {
                    tmpL = VarTable.get(VarStack.peek().getLexem());
                } else tmpL = Integer.parseInt(VarStack.peek().getLexem());

                switch (match().getLexem()) {
                    case "*":
                        tmpL *= tmpR;
                        VarStack.peek().setType(Lexer.Tokenstype.DIGIT);
                        VarStack.peek().setLexem(Integer.toString(tmpL));
                        i++;
                        OperatorStack.pop();
                        break;
                    case "+":
                        tmpL += tmpR;
                        VarStack.peek().setType(Lexer.Tokenstype.DIGIT);
                        VarStack.peek().setLexem(Integer.toString(tmpL));
                        i++;
                        OperatorStack.pop();
                        break;
                    case "-":
                        tmpL -= tmpR;
                        VarStack.peek().setType(Lexer.Tokenstype.DIGIT);
                        VarStack.peek().setLexem(Integer.toString(tmpL));
                        i++;
                        OperatorStack.pop();
                        break;
                    case "/":
                        tmpL /= tmpR;
                        VarStack.peek().setType(Lexer.Tokenstype.DIGIT);
                        VarStack.peek().setLexem(Integer.toString(tmpL));
                        i++;
                        OperatorStack.pop();
                        break;
                }
            }
            else if (match().getType().equals(Lexer.Tokenstype.DD_WHILE))
            {
                Whfound = true;
                i++;
            }
            else if (match().getType().equals(Lexer.Tokenstype.DD_ELSE) && IFistrue)
            {
                while (!match().getType().equals(Lexer.Tokenstype.FRB))
                {
                    i++;
                }
                i++;
            } else if (CycleStarted && (VarTable.get("z") < goal) && (symbol.equals("<")))
            {
                i = i - cyclesize;
                i++;
            } else if (CycleStarted && (VarTable.get("z") > goal) && (symbol.equals(">")))
            {
                i = i - cyclesize;
                i++;
            } else if (CycleStarted && (VarTable.get("z") == goal) && (symbol.equals("~")))
            {
                i = i - cyclesize;
                i++;
            }
            else i++;

            if ((CycleStarted && (VarTable.get("z") == goal) && (symbol.equals("<"))) || (CycleStarted && (VarTable.get("z") == goal) && (symbol.equals(">"))))
            {
                isSucsessful = false;
                EndOfConversion();
                i = RPNConvertedExpressions.size();
            }
        }
    }

    private int GetCycleSize()
    {
        while(!match().getType().equals(Lexer.Tokenstype.FRB))
        {
            cyclesize++;
            i++;
        }
        i = i - cyclesize;
        return i;
    }

    public boolean EndOfConversion()
    {
        return isSucsessful;
    }

    private Tokens match()
    {
        return RPNConvertedExpressions.get(i); // Возвращает следующий токен
    }
}
