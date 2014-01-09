import java.util.ArrayList;
import java.util.List;

public class SOUNDEXHashTable {
    private final SOUNDEX soundex; //SOUNDEX key generator

    private int idx; //Table size (index for the size sequence)
    private int[] sizes; //Size sequence
    private int elemQty; //Element quantity

    private String[] keyTable;
    private List[] elementTable;


    /**
     * Adds a value to the hash table. Creates a key for the value, puts it in the key table and then puts the value in
     * the element table, keeping the same index as the key in the key table.
     *
     * @param value
     */
    public void add(String value) {


        if (contains(value)) return; //If the element is already contained, nothing happens.
        if ((float) (elemQty / sizes[idx]) > 0.5) rehash(); //Check that the load factor is less than 50%, else rehash.


        String key = soundex.key(value);
        int pos = hash(key);

        //Collision treatment
        while (keyTable[pos] != null && !keyTable[pos].equals(key)) {
            pos = (pos + 1) % sizes[idx]; //Linear exploration
        }

        keyTable[pos] = key;
        elementTable[pos].add(value);
    }

    public List get(String value){
        int pos = find(value);
        if(pos == -1) return null;
        return elementTable[pos];
    }


    /**
     * Increases the size of the table and re-hashes all keys
     */
    public void rehash() {
        int currentSize = sizes[idx];
        if (sizes.length <= idx) { //Check that size < last size of the size sequence
            sizes = generatePrimeSeq(currentSize, currentSize * 3);
            idx = 0;
        }
        idx++;
        String[] auxKeyTable = new String[sizes[idx]];
        List[] auxElemTable = new List[sizes[idx]];


        for (int i = 0; i < currentSize; i++) {
            if (!keyTable[i].equals(null)) {
                int pos = hash(keyTable[i]);
                while (auxKeyTable[pos] != null) pos = (pos + 1) % sizes[idx];
                auxKeyTable[pos] = keyTable[i];
                auxElemTable[pos] = elementTable[i];
            }
        }

        keyTable = auxKeyTable;
        elementTable = auxElemTable;
    }



    /**
     * Takes a given value, gets its SOUNDEX key. Then assigns an integer to the first letter of the key and concatenates
     * it with the rest of the key's value. Then applies hash function module.
     *
     * @param value
     * @return
     */
    public int hash(String value) {
        String key = soundex.key(value);
        char first = key.charAt(0);
        key = key.substring(1);
        int num = assignInt(first) * 1000 + Integer.parseInt(key); //Unique integer for the SOUNDEX key
        return num % sizes[idx]; //Hash function module
    }

    /**
     * Function that assigns an integer to a letter. Returns the position in the alphabet for letter, the value of
     * numbers 0r -1 for special characters.
     *
     * @param character
     * @return
     */
    public int assignInt(char character) {
        switch (character) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'e':
                return 5;
            case 't':
                return 20;
            case 'a':
                return 1;
            case 'o':
                return 15;
            case 'i':
                return 9;
            case 'n':
                return 14;
            case 's':
                return 19;
            case 'h':
                return 8;
            case 'r':
                return 18;
            case 'd':
                return 4;
            case 'l':
                return 12;
            case 'c':
                return 3;
            case 'u':
                return 21;
            case 'm':
                return 13;
            case 'w':
                return 23;
            case 'f':
                return 6;
            case 'g':
                return 7;
            case 'y':
                return 25;
            case 'p':
                return 16;
            case 'b':
                return 2;
            case 'v':
                return 22;
            case 'k':
                return 11;
            case 'j':
                return 10;
            case 'x':
                return 24;
            case 'q':
                return 17;
            case 'z':
                return 26;
            default:
                return -1;
        }
    }

    /**
     * Generates an array of prime which will be used as the table size
     *
     * @param lo
     * @param hi
     * @return
     */
    public int[] generatePrimeSeq(int lo, int hi) {
        int[] dummy = new int[hi - lo];
        int pos = -1;
        for (int i = lo; i <= hi; i++) {
            boolean isPrime = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) dummy[++pos] = i;
        }
        int[] primeSeq = new int[pos + 1];
        for (int i = 0; i <= pos; i++) {
            primeSeq[i] = dummy[i];
        }
        return primeSeq;
    }

    /**
     * Constructor
     */
    public SOUNDEXHashTable() {
        soundex = new SOUNDEX();
        sizes = generatePrimeSeq(100, 300);
        idx = 0;
        elemQty = 0;
        keyTable = new String[sizes[idx]];

        //Initialize element table
        elementTable = new List[sizes[idx]];
        for (int i = 0; i < sizes[idx]; i++) {
            elementTable[i] = new ArrayList();
        }
    }

    /**
     * Constructor
     *
     * @param expectedSize
     */
    public SOUNDEXHashTable(int expectedSize) {
        if (expectedSize == 0) throw new IllegalArgumentException("Size must not be 0");
        soundex = new SOUNDEX();
        sizes = generatePrimeSeq(expectedSize, 3 * expectedSize);
        idx = 0;
        elemQty = 0;
        keyTable = new String[sizes[idx]];

        //Initialize element table
        elementTable = new List[sizes[idx]];
        for (int i = 0; i < sizes[idx]; i++) {
            elementTable[i] = new ArrayList();
        }
    }

    /**
     * Method that prints each value of the key table, ordered by position.
     */
    public void print() {
        for (int i = 0; i < sizes[idx]; i++) {
            System.out.println(keyTable[i]);
        }
    }

    /**
     * Size of the table
     *
     * @return
     */
    public int size() {
        return sizes[idx];
    }

    /**
     * Returns the index of the key for the given value in the key table. If key is not contained returns -1.
     *
     * @param value
     * @return
     */
    public int find(String value) {
        String key = soundex.key(value);
        int i = hash(key); //Position in the table
        int j = i;

        if (keyTable[i] == null) return -1;

        //If position is occupied by another key
        while (!keyTable[i].equals(key)) {
            i = (i + 1) % sizes[idx];
            if (i == j || keyTable[i] == null) return -1;
        }
        return i;
    }

    /**
     * Returns true if the value is contained in the element table, false otherwise.
     *
     * @param value
     * @return
     */
    public boolean contains(String value) {
        int i = find(value);
        if (i == -1) return false;
        return elementTable[i].contains(value);
    }

    /**
     * Removes a value from the element table's list. If such list becomes empty, removes the key from the key table. If
     * the value is not contained in the element table, nothing happens.
     *
     * @param value
     */
    public void remove(String value) {
        int i = find(value);
        if (i == -1) return;
        elementTable[i].remove(value);
        if (elementTable[i].isEmpty()) keyTable[i] = null;
    }




}
