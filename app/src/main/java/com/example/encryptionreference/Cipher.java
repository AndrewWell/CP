package com.example.encryptionreference;

import java.util.Locale;

public class Cipher {

    final static int RUS = 32;
    final static int ENG = 26;

    public String mirror(String str) {
        String reverse = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reverse += str.charAt(i);
        }
        return reverse;
    }

    public String encryptVigener(String openText, String key) {
        String encrypt = "";
        openText = openText.toLowerCase(Locale.ROOT);
        int keyLen = key.length(), letters;

        for (int i = 0, len = openText.length(); i < len; i++) {
            if (openText.charAt(i) >= 'a' && openText.charAt(i) <= 'z') {
                encrypt += (char) (((openText.charAt(i) + key.charAt(i % keyLen) - 2 * 'a') % ENG) + 'a');
            } else if (openText.charAt(i) >= 'а' && openText.charAt(i) <= 'я') {
                encrypt += (char) (((openText.charAt(i) + key.charAt(i % keyLen) - 2 * 'а') % RUS) + 'а');
            } else encrypt += openText.charAt(i);
        }
        return encrypt;
    }

    public String decryptVigener(String closeText, String key) {
        String decrypt = "";
        closeText = closeText.toLowerCase(Locale.ROOT);
        int keyLen = key.length(), letters;

        for (int i = 0, len = closeText.length(); i < len; i++) {
            if (closeText.charAt(i) >= 'a' && closeText.charAt(i) <= 'z') {
                decrypt += (char) (((closeText.charAt(i) - key.charAt(i % keyLen) + ENG) % ENG) + 97);
            } else if (closeText.charAt(i) >= 'а' && closeText.charAt(i) <= 'я') {
                decrypt += (char) (((closeText.charAt(i) - key.charAt(i % keyLen) + RUS) % RUS) + 1072);
            } else decrypt += closeText.charAt(i);
        }
        return decrypt;
    }

    public String encryptCaesar(String plaintText, int shearStep) {
        String encrypt = "",
                plaintText_toLowerCase = plaintText.toLowerCase();
        int plainLength = plaintText_toLowerCase.length();
        for (int i = 0; i < plainLength; i++) {

            if (plaintText_toLowerCase.charAt(i) >= 'a' && plaintText_toLowerCase.charAt(i) <= 'z') {
                encrypt += (char) (((plaintText_toLowerCase.charAt(i) - 97 + shearStep) % ENG) + 97);
            } else if (plaintText_toLowerCase.charAt(i) >= 'а' && plaintText_toLowerCase.charAt(i) <= 'я') {
                encrypt += (char) (((plaintText_toLowerCase.charAt(i) - 1072 + shearStep) % RUS) + 1072);
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


