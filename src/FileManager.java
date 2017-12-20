import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by rodrigofranzoi on 07/06/17.
 */

public class FileManager {

    ArrayList<Tweet> initialTweets;
    PrintWriter writer;


    public FileManager(){
        this.initialTweets = loadTweetsFrom("files/pt.csv");
    }

    public ArrayList<String> loadLinesFrom(String txtFile) {

        ArrayList<String> array = new ArrayList<String>();

        File file = new File(txtFile);
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        scanner.useDelimiter("\n");
        while(scanner.hasNext()){
            array.add(scanner.next());
        }
        scanner.close();
        return array;
    }

    public ArrayList<Tweet> loadTweetsFrom(String csvFile) {

        ArrayList<Tweet> array = new ArrayList<Tweet>();

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] content = line.split(cvsSplitBy);
                Tweet tweet = new Tweet(content[0], content[1]);
                array.add(tweet);
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return array;
    }

    public void addToFile(Tweet t) {

        String s = t.content + "," + t.value;

        if (writer == null) {
            try {
                writer = new PrintWriter("files/tweets.csv", "UTF-8");
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }
        writer.println(s);
    }

    public void addToExit(ArrayList<Tweet> array, String path) {

        try {
            PrintWriter w = new PrintWriter(path, "UTF-8");

            for(int i = 0; i < array.size(); i++) {
                w.println(array.get(i).getCSVLine());
            }

            w.close();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public void closeFile(){
        writer.close();
    }

}
