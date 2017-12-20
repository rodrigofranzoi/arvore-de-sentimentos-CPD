/**
 * Created by rodrigofranzoi on 08/06/17.
 */
public class TrieNode {

    private int size;
    private char    c;
    public TrieNode[] array;
    public Token   token;

    TrieNode(int size, String key, Token token, int digit){
        this.size  = size;
        this.array = new TrieNode[size];
        this.c     = key.charAt(digit);
        add(key, token, digit);
    }

    public void add(String key, Token token, int digit) {

        if(key.length() == digit + 1) {
            if (this.token != null) {
                this.token.add(token);
            } else {
                this.token = token;
            }

        } else {
            if (array[(int) key.charAt(digit+1)] != null) {
                array[(int) key.charAt(digit+1)].add(key, token, digit + 1);
            } else {
                this.array[(int) key.charAt(digit+1)] = new TrieNode(this.size, key, token, digit + 1);
            }
        }
    }

    public Token get(String key, int digit) {

        if(key.length() == digit + 1) {
            return token;
        } else {
            if(array[(int) key.charAt(digit + 1)] == null){
                return null;
            } else {
                return array[(int) key.charAt(digit + 1)].get(key, digit + 1);
            }
        }
    }
}
