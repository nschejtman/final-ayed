import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class ReadFromFile {

    public static void loadDictionary(SOUNDEXHashTable table) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./dictionary.txt"));
        String line = br.readLine();
        while (line != null) {
            table.add(line);
            line = br.readLine();
            System.out.println("loading...");
        }

        br.close();
    }

}
