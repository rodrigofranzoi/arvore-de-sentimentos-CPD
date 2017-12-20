import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by rodrigofranzoi on 08/06/17.
 */
public class TrieTree {

    private static int SIZE = 128;
    private TrieNode[] array;
    private FileManager file;


    TrieTree() {

        this.file = new FileManager();
        this.array = new TrieNode[SIZE];

        for (int i= 0; i < file.initialTweets.size(); i++) {
            ArrayList<Token> t = file.initialTweets.get(i).getTokens();

            file.addToFile(file.initialTweets.get(i));

            for (int j = 0; j < t.size(); j++) {
                add(t.get(j).name, t.get(j), file.initialTweets.get(i));
            }
        }

        file.closeFile();
    }

    private void add(String key, Token token, Tweet tweet) {

        if( array[(int) key.charAt(0)] != null) {
            array[(int) key.charAt(0)].add(key, token, 0);

        } else {
            array[(int) key.charAt(0)] = new TrieNode(SIZE, key, token, 0);
        }
    }

    private void addTweet(String content) {

        Tweet t = getPolarity(content);

        for (int i = 0; i<t.getTokens().size(); i++) {
            add(t.getTokens().get(i).name, t.getTokens().get(i), t);
        }

        PrintWriter pw = null;

        try {
            File file = new File("files/tweets.csv");
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            String s = content + "," + t.value + "\n";
            pw.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private void addTweet(Tweet t) {

        for (int i = 0; i<t.getTokens().size(); i++) {
            add(t.getTokens().get(i).name, t.getTokens().get(i), t);
        }

        PrintWriter pw = null;

        try {
            File file = new File("files/tweets.csv");
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            String s = t.content + "," + t.value;
            pw.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public ArrayList<Token> getTokensWithRange(Float min, Float max){

        ArrayList<Token> t = new ArrayList<Token>();

        for (int i = 0; i < 128; i++) {
            if(array[i] != null){
                getTokensWithRange(min, max, array[i], t);
            }
        }
        return t;
    }

    private void getTokensWithRange(Float min, Float max,TrieNode node, ArrayList<Token> array){

        if(node.token != null) {
            if(node.token.getValue() >= min && node.token.getValue() <= max){
                array.add(node.token);
            }
        }

        for (int i = 1; i < 128; i++) {
            if(node.array[i] != null){
                getTokensWithRange(min, max, node.array[i], array);
            }
        }
    }

    public ArrayList<Token> getTokensWithPrefix(String prefix){

        ArrayList<Token> t = new ArrayList<Token>();

        TrieNode node = array[(int) prefix.charAt(0)];

        for (int i = 1; i < prefix.length(); i++) {
            if(node.array[(int) prefix.charAt(i)] != null) {
                node = node.array[(int) prefix.charAt(i)];
            } else {
                return t;
            }
        }

        getTokensWithPrefix(node,t);
        return t;
    }

    public void getTokensWithPrefix(TrieNode node, ArrayList<Token> array) {

        if(node.token != null) {
            array.add(node.token);
        }

        for (int i = 1; i < 128; i++) {
            if(node.array[i] != null){
                getTokensWithPrefix(node.array[i], array);
            }
        }
    }

    public void addTweetsFrom(String csvfile) {

        ArrayList<Tweet> ts = file.loadTweetsFrom(csvfile);

        for(int i = 0; i < ts.size(); i++) {
            addTweet(ts.get(i));
        }
    }

    public void getPolarityOf(String txtFile, String path) {

        ArrayList<String> contents = file.loadLinesFrom(txtFile);
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        for(int i = 0; i<contents.size(); i++) {
            tweets.add(getPolarity(contents.get(i)));
        }

        file.addToExit(tweets, path);
    }

    public Token getToken(String key) {

        String s = key.toLowerCase();

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


        if(array[(int) s.charAt(0)] != null) {
            return array[(int) s.charAt(0)].get(s, 0);
        }

        return null;
    }

    public Tweet getPolarity(String tweetContent) {

        float count = 0;

        String s = tweetContent.toLowerCase();

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

        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (token.length() > 2) {
                Token t = getToken(token);
                if (t !=  null) {
                    count += t.getValue();
                }
            }
        }

     return new Tweet(tweetContent, count);

    }

}
