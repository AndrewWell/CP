package com.example.encryptionreference;

import android.app.Activity;
import android.widget.TextView;


import java.text.MessageFormat;
import java.util.Locale;

public class Cipher {

    final static int RUS = 32;
    final static int ENG = 26;
    String hint = "";
    Activity activity;
    TextView textView;

    public Cipher(Activity activity, TextView textView) {
        this.activity = activity;
        this.textView=textView;
    }

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
        hint =activity.getResources().getString(R.string.cipher_encVigener)+"\n";
        for (int i = 0, len = openText.length(); i < len; i++) {
            if (openText.charAt(i) >= 'a' && openText.charAt(i) <= 'z') {
                encrypt += (char) (((openText.charAt(i) + key.charAt(i % keyLen) - 2 * 'a') % ENG) + 'a');
                hint += MessageFormat.format("Y[{0}] = ({1}+{2})mod{3} = {4}\n", i, getOrdinalElemAlph(openText.charAt(i)), getOrdinalElemAlph(key.charAt(i % keyLen)), ENG, encrypt.charAt(i))+"\n";
            } else if (openText.charAt(i) >= 'а' && openText.charAt(i) <= 'я') {
                encrypt += (char) (((openText.charAt(i) + key.charAt(i % keyLen) - 2 * 'а') % RUS) + 'а');
                hint += MessageFormat.format("Y[{0}] = ({1}+{2})mod{3} = {4}\n", i, getOrdinalElemAlph(openText.charAt(i)), getOrdinalElemAlph(key.charAt(i % keyLen)), RUS, encrypt.charAt(i))+"\n";
            } else encrypt += openText.charAt(i);
        }if(textView!=null)textView.setText(hint);
        return encrypt;
    }

    public String decryptVigener(String closeText, String key) {
        String decrypt = "";
        closeText = closeText.toLowerCase(Locale.ROOT);
        int keyLen = key.length(), letters;
        hint =activity.getResources().getString(R.string.cipher_decVigener)+ "\n";
        for (int i = 0, len = closeText.length(); i < len; i++) {
            if (closeText.charAt(i) >= 'a' && closeText.charAt(i) <= 'z') {
                decrypt += (char) (((closeText.charAt(i) - key.charAt(i % keyLen) + ENG) % ENG) + 97);
                hint += MessageFormat.format("Y[{0}] = ({1}+{2})mod{3} = {4}\n", i, getOrdinalElemAlph(closeText.charAt(i)), getOrdinalElemAlph(key.charAt(i % keyLen)), ENG, decrypt.charAt(i))+"\n";
            } else if (closeText.charAt(i) >= 'а' && closeText.charAt(i) <= 'я') {
                decrypt += (char) (((closeText.charAt(i) - key.charAt(i % keyLen) + RUS) % RUS) + 1072);
                hint += MessageFormat.format("Y[{0}] = ({1}+{2})mod{3} = {4}\n", i, getOrdinalElemAlph(closeText.charAt(i)), getOrdinalElemAlph(key.charAt(i % keyLen)), RUS, decrypt.charAt(i))+"\n";
            } else decrypt += closeText.charAt(i);
        }if(textView!=null)textView.setText(hint);
        return decrypt;
    }

    public String encryptCaesar(String plaintText, int shearStep) {
        String encrypt = "",
                plaintText_toLowerCase = plaintText.toLowerCase();
        int plainLength = plaintText_toLowerCase.length();
        hint = activity.getResources().getString(R.string.cipher_encCaesar)+"\n";
        for (int i = 0; i < plainLength; i++) {

            if (plaintText_toLowerCase.charAt(i) >= 'a' && plaintText_toLowerCase.charAt(i) <= 'z') {
                encrypt += (char) (((plaintText_toLowerCase.charAt(i) - 97 + shearStep) % ENG) + 97);
                hint += MessageFormat.format("Y[{0}] = ({1}+{2})mod{3} = {4}\n", i, getOrdinalElemAlph(plaintText_toLowerCase.charAt(i)), shearStep, ENG, encrypt.charAt(i))+"\n";
            } else if (plaintText_toLowerCase.charAt(i) >= 'а' && plaintText_toLowerCase.charAt(i) <= 'я') {
                encrypt += (char) (((plaintText_toLowerCase.charAt(i) - 1072 + shearStep) % RUS) + 1072);
                hint += MessageFormat.format("Y[{0}] = ({1}+{2})mod{3} = {4}\n", i, getOrdinalElemAlph(plaintText_toLowerCase.charAt(i)), shearStep, RUS, encrypt.charAt(i))+"\n";
            } else encrypt += plaintText_toLowerCase.charAt(i);
        }if(textView!=null)textView.setText(hint);
        return encrypt;
    }

    public String decryptCaesar(String closedText, int shearStep) {
        String decrypt = "",
                closedText_toLowerCase = closedText.toLowerCase();
        int closedLength = closedText_toLowerCase.length();
        hint =activity.getResources().getString(R.string.cipher_decCaesar)+"\n";
        for (int i = 0; i < closedLength; i++) {

            if (closedText_toLowerCase.charAt(i) >= 'a' && closedText_toLowerCase.charAt(i) <= 'z') {
                decrypt += (char) ('z' - (('z' - closedText.charAt(i) + shearStep) % ENG));
                hint += MessageFormat.format("Y[{0}] = ({1}+{2})mod{3} = {4}\n", i, getOrdinalElemAlph(closedText_toLowerCase.charAt(i)), shearStep, ENG, decrypt.charAt(i))+"\n";
            } else if (closedText_toLowerCase.charAt(i) >= 'а' && closedText_toLowerCase.charAt(i) <= 'я') {
                decrypt += (char) ('я' - (('я' - closedText_toLowerCase.charAt(i) + shearStep) % RUS));
                hint += MessageFormat.format("Y[{0}] = ({1}+{2})mod{3} = {4}\n", i, getOrdinalElemAlph(closedText_toLowerCase.charAt(i)), shearStep, RUS, decrypt.charAt(i))+"\n";
            } else decrypt += closedText_toLowerCase.charAt(i);
        }if(textView!=null)textView.setText(hint);
        return decrypt;
    }

    public String atbash(String plaintText) {
        String encrypt = "",
                plaintText_toLowerCase = plaintText.toLowerCase();
        int plainLength = plaintText_toLowerCase.length();
        hint = activity.getResources().getString(R.string.cipher_atbash)+"\n";
        for (int i = 0; i < plainLength; i++) {
            if (plaintText_toLowerCase.charAt(i) >= 'a' && plaintText_toLowerCase.charAt(i) <= 'z') {
                encrypt += getCodeFromSerialNumberAlph(ENG - getOrdinalElemAlph(plaintText_toLowerCase.charAt(i)) - 1, false);
                hint += MessageFormat.format("Y[{0}] = {1}-{2}+1 = {3}", i, ENG, plaintText_toLowerCase.charAt(i), encrypt.charAt(i))+"\n";
            } else if (plaintText_toLowerCase.charAt(i) >= 'а' && plaintText_toLowerCase.charAt(i) <= 'я') {
                encrypt += getCodeFromSerialNumberAlph(RUS - getOrdinalElemAlph(plaintText_toLowerCase.charAt(i)) - 1, true);
                hint += MessageFormat.format("Y[{0}] = {1}-{2}+1 = {3}", i, RUS, plaintText_toLowerCase.charAt(i), encrypt.charAt(i))+"\n";
            } else encrypt += plaintText_toLowerCase.charAt(i);
        }if(textView!=null)textView.setText(hint);
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


