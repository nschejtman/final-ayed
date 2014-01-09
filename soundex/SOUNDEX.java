public class SOUNDEX {
    /**
     * Returns the SOUNDEX key for a given string. Ignores special characters except '*', for which throws an
     * IllegalArgumentException. Considers only the first word of a phrase.
     *
     * @param value
     * @return
     */
    public String key(String value) {
        value = value.toLowerCase();
        int m = value.length();
        char[] chars = value.toCharArray();


        //Check that the string is codable
        for (int i = 0; i < m; i++) {
            if (chars[i] == '*') throw new IllegalArgumentException("Uncodable string"); // '*' escape character
            sdxIdx(chars[i]); // rest of the characters
            if (chars[i] == ' ') break;//only the first word
        }


        //SOUNDEX conditions
        for (int i = 0; i < m; i++) {
            if (chars[i] == ' ') break;//only the first word
            if (i + 1 < m && sdxIdx(chars[i]) == sdxIdx(chars[i + 1]))
                chars[i + 1] = '*'; //If two consecutive characters have the same SOUNDEX code, code only the first
            if (i + 2 < m && chars[i + 1] != ' ') {
                if (sdxIdx(chars[i]) == sdxIdx(chars[i + 2]) && sdxIdx(chars[i + 1]) == -1)
                    chars[i] = '*'; //If two characters separated by a vowel (code = -1) have the same SOUNDEX code, code the last
                if (sdxIdx(chars[i]) == sdxIdx(chars[i + 2]) && sdxIdx(chars[i + 1]) == -2)
                    chars[i + 2] = '*'; //If two characters separated by 'h' or 'w' have the same SOUNDEX code, code the first
            }
        }

        //SOUNDEX coding
        int dummy = 0; //Position of the first character
        while (chars[dummy] == '*' && dummy < m) dummy++;
        String key = String.valueOf(chars[dummy]);

        for (int i = dummy + 1; i < m; i++) {
            if (chars[i] == ' ') break;//only the first word
            int idx = sdxIdx(chars[i]);
            if (idx > 0) key = key + String.valueOf(idx);
        }

        //Check the size of the string
        while (key.length() < 4) key = key + 0;
        key = key.substring(0, 4);

        return key;
    }

    /**
     * Returns the SOUNDEX code for a given character
     *
     * @param character
     * @return
     */
    private int sdxIdx(char character) {
        //switch ordered by english language character frequency
        switch (character) {
            case '*':
                return -3;
            case 'e':
                return -1;
            case 't':
                return 3;
            case 'a':
                return -1;
            case 'o':
                return -1;
            case 'i':
                return -1;
            case 'n':
                return 5;
            case 's':
                return 2;
            case 'h':
                return -2;
            case 'r':
                return 6;
            case 'd':
                return 3;
            case 'l':
                return 4;
            case 'c':
                return 2;
            case 'u':
                return -1;
            case 'm':
                return 5;
            case 'w':
                return -2;
            case 'f':
                return 1;
            case 'g':
                return 2;
            case 'y':
                return -4;
            case 'p':
                return 1;
            case 'b':
                return 1;
            case 'v':
                return 1;
            case 'k':
                return 2;
            case 'j':
                return 2;
            case 'x':
                return 2;
            case 'q':
                return 2;
            case 'z':
                return 2;
            default:
                return -4;
        }
    }
}
