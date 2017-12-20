import java.util.ArrayList;

/**
 * Created by rodrigofranzoi on 24/06/17.
 */
public class Token {

    String name;
    float  score;
    int    count;

    Token(String name, float score, Tweet tweet) {
        this.name   = name;
        this.score  = score;
        this.count  = 1;
    }

    float getValue() {
        return score/count;
    }

    void add(Token token) {
        count++;
        this.score += token.score;
    }

    public String toString() {
        return "Conteúdo: " + name + " - Score acumulado: " + String.valueOf(score) + " - Frequencia: " + String.valueOf(count) + " - Valor Sentimento: " + String.valueOf(getValue()) + "\n";
    }
}
