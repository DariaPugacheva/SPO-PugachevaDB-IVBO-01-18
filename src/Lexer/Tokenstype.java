package Lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Tokenstype {
    ARITHMETIC_OP("[-|+|*|/]"),
    ASSIGN_OP("="),

    D_EOS(";"),
    D_DOT("\\."),
    D_TYPE("int"),
    D_NEW("new"),
    DD_IF("if"),
    DD_WHILE("while"),
    DD_ELSE("else"),
    DD_PRINT("print"),
    DD_LLIST("LinkedList"),

    DD_ADD("add"),
    DD_REMOVE("remove"),

    LB("\\("),
    RB("\\)"),
    FLB("\\{"),
    FRB("\\}"),

    DIFF_OP("[> | <]"),
    EQ_OP("~"),
    NAME("[а-я]+"),

    DIGIT("[0-9]+"),
    VAR("[a-z]+");


    private final Pattern pattern;

    Tokenstype(String regex) {
        pattern = Pattern.compile("^" + regex);
    }

    int endOfMatch(String s) {
        Matcher m = pattern.matcher(s);

        if (m.find()) {
            return m.end();
        }
        return -1;
    }
}
