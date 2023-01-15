package com.example.encryptionreference;

public class Cipher {

    final static int RUS = 32;
    final static int ENG = 26;

    public String mirror(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public String encryptVigener(String text, String key, int bias) {
        String encrypt = "";
        int keyLen = key.length(), letters;
        if (bias == 97) letters = ENG;
        else letters = RUS;
        for (int i = 0, len = text.length(); i < len; i++) {
            encrypt += (char) (((text.charAt(i) + key.charAt(i % keyLen) - 2 * bias) % letters) + bias);
        }
        return encrypt;
    }

    public String decryptVigener(String cipher, String key, int bias) {
        String decrypt = "";
        int keyLen = key.length(), letters;
        if (bias == 97) letters = ENG;
        else letters = RUS;
        for (int i = 0, len = cipher.length(); i < len; i++) {
            decrypt += (char) (((cipher.charAt(i) - key.charAt(i % keyLen) + letters) % letters) + bias);
        }
        return decrypt;
    }

    public String encryptCaesar(String plaintText, int shearStep) {
        String encrypt = "",
                plaintText_toLowerCase = plaintText.toLowerCase();
        int plainLength = plaintText_toLowerCase.length();
        for (int i = 0; i < plainLength; i++) {

            if (plaintText_toLowerCase.charAt(i) >= 'a' && plaintText_toLowerCase.charAt(i) <= 'z') {
                encrypt += (char) (((plaintText_toLowerCase.charAt(i) - 'a' + shearStep) % ENG) + 'a');
            } else if (plaintText_toLowerCase.charAt(i) >= 'а' && plaintText_toLowerCase.charAt(i) <= 'я') {
                encrypt += (char) (((plaintText_toLowerCase.charAt(i) - 'а' + shearStep) % RUS) + 'а');
            } else encrypt += plaintText_toLowerCase.charAt(i);
        }
        return encrypt;
    }

    public String decryptCaesar(String closedText, int shearStep) {
        String decrypt = "",
                closedText_toLowerCase = closedText.toLowerCase();
        int closedLength = closedText_toLowerCase.length();
        for (int i = 0; i < closedLength; i++) {

            if (closedText_toLowerCase.charAt(i) >= 'a' && closedText_toLowerCase.charAt(i) <= 'z') {
                decrypt += (char) ('z' - (('z' - closedText.charAt(i) + shearStep) % ENG));
            } else if (closedText_toLowerCase.charAt(i) >= 'а' && closedText_toLowerCase.charAt(i) <= 'я') {
                decrypt += (char) ('я' - (('я' - closedText_toLowerCase.charAt(i) + shearStep) % RUS));
            } else decrypt += closedText_toLowerCase.charAt(i);
        }
        return decrypt;
    }

    public String atbash(String plaintText) {
        String encrypt = "",
                plaintText_toLowerCase = plaintText.toLowerCase();
        int plainLength = plaintText_toLowerCase.length();
        for (int i = 0; i < plainLength; i++) {
            if (plaintText_toLowerCase.charAt(i) >= 'a' && plaintText_toLowerCase.charAt(i) <= 'z') {
                encrypt += getCodeFromSerialNumberAlph(ENG - getOrdinalElemAlph(plaintText_toLowerCase.charAt(i)) - 1, false);
            } else if (plaintText_toLowerCase.charAt(i) >= 'а' && plaintText_toLowerCase.charAt(i) <= 'я') {
                encrypt += getCodeFromSerialNumberAlph(RUS - getOrdinalElemAlph(plaintText_toLowerCase.charAt(i)) - 1, true);
            } else encrypt += plaintText_toLowerCase.charAt(i);
        }
        return encrypt;
    }

    private int getOrdinalElemAlph(char c) {//получение номера буквы в алфавитном порядке
        if (c >= 'a' && c <= 'z') return c - 96;
        return c - 1071;
    }

    private char getCodeFromSerialNumberAlph(int i, boolean rusAlbh) {//получить аски код в зависимости от номера буквы в алфавите
        if (rusAlbh) return (char) (i + 1071);
        return (char) (i + 96);
    }
}


