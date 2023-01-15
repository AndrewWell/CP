package com.example.encryptionreference.ui.theory;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.encryptionreference.MyClass;
import com.example.encryptionreference.R;
import com.example.encryptionreference.databinding.FragmentTheoryBinding;

import java.util.Locale;


public class TheoryFragment extends Fragment {

    private FragmentTheoryBinding binding;
    private String spinner_method;
    private ArrayAdapter<?> adapter;
    private MyClass myClass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        TheoryViewModel theoryViewModel =
                new ViewModelProvider(this).get(TheoryViewModel.class);
        myClass = new MyClass();
        binding = FragmentTheoryBinding.inflate(inflater, container, false);

        createIntro();

        adapter = ArrayAdapter.createFromResource(getContext(), R.array.methods_for_theory,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerMethod.setAdapter(adapter);


        binding.spinnerMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_method = getResources().getStringArray(R.array.methods_for_crypto)[i];

                if (spinner_method.equals(getResources().getString(R.string.parsing_AES))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_AES);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_DES))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_DES);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_RC4))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_RC4);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_TripleDes))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_3DES);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_RSA))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_RSA);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Atbash))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Atbash);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Vigener))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Vigener);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Xor))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_XOR);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Gost_28147_89))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Gost_28147_89);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Polybius))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Polybius);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Morse))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Morse);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Moon))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Moon);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Hill))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Hill);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Caesar))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Caesar);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Playfair))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Playfair);
                } else if (spinner_method.equals(getResources().getString(R.string.parsing_Shannon_Fano))) {
                    setTextFromArrayString(binding.textView4, R.array.theory_Shannon_Fano);
                } else binding.textView4.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Spanned getHtml(String str) {
        return Html.fromHtml("<p>" + str + "<\\p>");
    }

    private void setTextFromArrayString(@NonNull TextView textView, int numArrayString) {
        textView.setText("");
        for (String str : getResources().getStringArray(numArrayString)) {
            textView.append(getHtml(str));
        }
    }

    private void createIntro() {//TODO добавить текст для подсказок Intro
        myClass.addIntro(getActivity(), binding.spinnerMethod, getResources().getString(R.string.intro_theory_spinner), getResources().getString(R.string.nextShowIntro));
        myClass.showIntro(getActivity(), "isShowTheory");
    }
}