package Lexer;

public class Tokens {
    private Tokenstype type;
    private String lexem;

    /*Класс токена и его характеристиками (тип : лексема) */

    public Tokens(Tokenstype type, String lexem)
    {
        this.type = type;
        this.lexem = lexem;
    }

    public String getLexem() {
        return lexem;
    }

    public void setLexem(String lexem) {
        this.lexem = lexem;
    }

    public Tokenstype getType() {
        return type;
    }

    public void setType(Tokenstype type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return type + " " + lexem;
    }
}
