import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AppSDX {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //For reading input

        SOUNDEXHashTable table = new SOUNDEXHashTable(6000);

        //Loads the dictionary
        try {
            ReadFromFile.loadDictionary(table);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Enter word: (type 0 to exit)");
        String word = sc.nextLine();
        while (!word.equals("0")) {
            List list = table.get(word);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                System.out.println(list.get(i));
            }
            System.out.println("Enter word: (type 0 to exit)");
            word = sc.nextLine();
        }


    }
}
