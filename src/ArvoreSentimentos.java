/**
 * Created by rodrigofranzoi on 08/06/17.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ArvoreSentimentos {

    public static void main(String[] args) throws IOException {



        TrieTree trieTree =  new TrieTree();
        Scanner scan = new Scanner(System.in);
        String s, l;

        int i;

        while (true) {
            System.out.printf("Escolha a funcionalidade:\n\n1 - Adicionar arquivo CSV ao dicionário\n2 - Ler arquivo txt e retornar os sentimentos em um outro arquivo\n3 - Retornar palavras com o sufixo\n4 - Retornar palavras com o score de sentimento em um intervalo\n\n5 -Sair\n\n");
            i = scan.nextInt();
            switch (i) {
                case 1:
                    System.out.printf("Digite o nome do arquivo para ler, exemplo: \"files/entrada.csv\" \n");
                    s = scan.next();
                    trieTree.addTweetsFrom(s);

                    break;
                case 2:

                    System.out.printf("Digite o nome do arquivo de leitura TXT, exemplo: \"files/entrada.txt\" \n");
                    s = scan.next();
                    System.out.printf("Digite o nome do arquivo de saída CSV, exemplo: \"files/polaridades.csv\" \n");
                    l = scan.next();
                    trieTree.getPolarityOf(s, l);

                    break;
                case 3:

                    System.out.printf("Digite o nome de um sufixo, exemplo: \"amo\"");
                    s = scan.next();

                    ArrayList<Token> t = trieTree.getTokensWithPrefix(s);

                    for (int j = 0; j<t.size() ; j++) {
                        System.out.printf(t.get(j).toString());
                    }

                    break;
                case 4:
                    System.out.printf("Digite o um intervalo mínimo, exemplo: \"0.3\" - ");
                    s = scan.next();
                    System.out.printf("Digite o um intervalo máximo, exemplo: \"1.5\" - ");
                    l = scan.next();

                    ArrayList<Token> tokens = trieTree.getTokensWithRange(Float.valueOf(s), Float.valueOf(l));

                    for (int j = 0; j<tokens.size() ; j++) {
                        System.out.printf(tokens.get(j).toString());
                    }

                case 5:
                    return;
                default:
                    return;
            }
        }

    }

}