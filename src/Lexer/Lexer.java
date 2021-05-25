package Lexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Lexer {
    private StringBuilder Rawinput = new StringBuilder();
    private Tokenstype tokenstype;
    private String Lexem;
    private Tokens tokens;
    private boolean ERROR = false;
    private String errorMessage = "";
    private Set<Character> blankChars = new HashSet<Character>();
    private List<Tokens> token = new ArrayList<Tokens>();

    public Lexer(String filePath) throws Exception
    {
        try (Stream<String> st = Files.lines(Paths.get(filePath))) {
            st.forEach(Rawinput::append);
        } catch (IOException ex) {
            ERROR = true;
            errorMessage = "Could not read file: " + filePath;
            return;
        }

        blankChars.add('\r');
        blankChars.add('\n');
        blankChars.add((char) 8);
        blankChars.add((char) 9);
        blankChars.add((char) 11);
        blankChars.add((char) 12);
        blankChars.add((char) 32);

        moveAhead();
    }

    public void moveAhead() throws Exception
    {
        if (ERROR) {
            return;
        }
        if (Rawinput.length() == 0) {
            ERROR = true;
            return;
        }
        ignoreWhiteSpaces();

        if (findNextToken()) {
            return;
        }
        ERROR = true;
        if (Rawinput.length() > 0) {
            errorMessage = "Unexpected symbol: '" + Rawinput.charAt(0) + "'";
        }
    }

    private void ignoreWhiteSpaces() throws Exception
    {
        int charsToDelete = 0;
        while (blankChars.contains(Rawinput.charAt(charsToDelete))) {
            charsToDelete++;
        }
        if (charsToDelete > 0) {
            Rawinput.delete(0, charsToDelete);
        }
    }

    /*Лексический анализ - Создание токена и добавление его в список*/

    private boolean findNextToken()  throws Exception
    {
        for (Tokenstype t : Tokenstype.values()) {
            int end = t.endOfMatch(Rawinput.toString());

            if (end != -1) {
                tokenstype = t;
                Lexem = Rawinput.substring(0, end);
                Rawinput.delete(0, end);
                token.add(new Tokens(tokenstype, Lexem));
                return true;
            }
        }
        return false;
    }

    /*Служебные операции возвращающие некоторые компоненты*/

    public String errorMessage() {
        return errorMessage;
    }

    public boolean isERROR() {
        return ERROR;
    }

    public List<Tokens> GetTokenList() { return token;}
}
