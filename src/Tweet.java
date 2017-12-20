import java.text.Normalizer;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by rodrigofranzoi on 24/06/17.
 */
public class Tweet {

    public String content;
    public float value;

    Tweet(String content, String value) {
        this.content = content;
        this.value   = Float.valueOf(value);
    }

    Tweet(String content, float value) {
        this.content = content;
        this.value   = value;
    }

    ArrayList<Token> getTokens(){

        String s = content.toLowerCase();

        s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        s = s.replace(".", "");
        s = s.replace("!", "");
        s = s.replace("?", "");
        s = s.replace(",", "");
        s = s.replace("(", "");
        s = s.replace(")", "");
        s = s.replace("\"", "");
        s = s.replace("'", "");
        s = s.replace(":", "");
        s = s.replace(";", "");

        StringTokenizer tokenizer = new StringTokenizer(s, " ");
        ArrayList<Token> tokenArray = new ArrayList<Token>();
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (token.length() > 2) {
                tokenArray.add(new Token(token, value, this));
            }
        }
        return tokenArray;
    }

    public String getPolarity(){
        if (value > 0.1) {
            return "Positivo";
        } else if (value < -0.1) {
            return "Negativo";
        } else {
            return "Neutro";
        }
    }

    public String toString() {
        return "Conteudo: " + content + " - Valor: " + String.valueOf(value) + " - Polaridade: " + getPolarity() + "\n";
    }

    public String getCSVLine() {
        return content + "," + String.valueOf(value);
    }

}
