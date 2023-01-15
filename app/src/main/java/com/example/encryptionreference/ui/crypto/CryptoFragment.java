package com.example.encryptionreference.ui.crypto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.encryptionreference.Cipher;
import com.example.encryptionreference.MyClass;
import com.example.encryptionreference.R;
import com.example.encryptionreference.databinding.FragmentCryptoBinding;

public class CryptoFragment extends Fragment {

    private FragmentCryptoBinding binding;
    private MyClass myClass;
    private String spinner_method;
    private ArrayAdapter<?> adapter;
    private Cipher cipher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CryptoViewModel cryptoViewModel =
                new ViewModelProvider(this).get(CryptoViewModel.class);

        binding = FragmentCryptoBinding.inflate(inflater, container, false);
        myClass = new MyClass();
        cipher = new Cipher();
        View root = binding.getRoot();
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.methods_for_crypto,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerMethods.setAdapter(adapter);

        createIntro();

        binding.imageButtonSaveBuffer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClass.copyBuf(getActivity(), binding.multiACTVPublicText.getText().toString(), view);
            }
        });
        binding.imageButtonSaveBuffer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClass.copyBuf(getActivity(), binding.multiACTVPrivateText.getText().toString(), view);
            }
        });
        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.multiACTVKey.setText("");
                binding.multiACTVPublicText.setText("");
                binding.multiACTVPrivateText.setText("");
                Toast.makeText(getActivity(), R.string.clearForm, Toast.LENGTH_SHORT).show();
                myClass.closeKeyboard(getActivity(), view);
            }
        });
        binding.chipDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textViewHintEncrypted.setText(getResources().getString(R.string.crypto_titleDecrypt));
                binding.textViewHintDecrypt.setText(getResources().getString(R.string.crypto_titleEncrypt));
                binding.multiACTVPublicText.setHint(getResources().getString(R.string.crypto_helpEnterCloseText));
                myClass.setBooleanShared(getActivity(), "isEncrypt", false);
            }
        });
        binding.chipEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textViewHintEncrypted.setText(getResources().getString(R.string.crypto_titleEncrypt));
                binding.textViewHintDecrypt.setText(getResources().getString(R.string.crypto_titleDecrypt));
                binding.multiACTVPublicText.setHint(getResources().getString(R.string.crypto_helpEnterOpenText));
                myClass.setBooleanShared(getActivity(), "isEncrypt", true);
            }
        });

        binding.spinnerMethods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_method = getResources().getStringArray(R.array.methods_for_crypto)[i];

                if (spinner_method.equals(getResources().getString(R.string.parsing_Mirror))) {
                    binding.multiACTVKey.setEnabled(false);
                    if (myClass.isBooleanShared(getActivity(), "isEncrypt"))
                        binding.gifImage.setBackgroundResource(R.drawable.anim_coding_mirror);
                    else binding.gifImage.setBackgroundResource(R.drawable.anim_decoding_mirror);

                } else {
                    binding.gifImage.setBackgroundResource(R.drawable.anim_ss);
                    binding.multiACTVKey.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.imageButtonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClass.closeKeyboard(getActivity(), view);
                //TODO сделать проверку на то какой метод используется и проверку на то что происходит (шифрование или дешифрование)
                if (myClass.isBooleanShared(getActivity(), "isEncrypt")) {
                    if (spinner_method.equals(getResources().getString(R.string.parsing_Mirror)))
                        binding.multiACTVPrivateText.setText(cipher.mirror(binding.multiACTVPublicText.getText().toString()));
                }
            }
        });
        binding.imageButtonShare1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClass.shareText(getActivity(), binding.multiACTVPublicText.getText().toString());
            }
        });
        binding.imageButtonShare2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClass.shareText(getActivity(), binding.multiACTVPrivateText.getText().toString());
            }
        });
        binding.imageButtonSaveQRCode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO при нажатии переход в другой фрагмент
            }
        });
        return root;
    }

    private void createIntro() {//TODO добавить текст для подсказок Intro
        myClass.addIntro(getActivity(), binding.chip, getResources().getString(R.string.intro_crypto_enc_dec), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.spinnerMethods, getResources().getString(R.string.intro_crypto_method), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.buttonClear, getResources().getString(R.string.clearForm), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonConvert, getResources().getString(R.string.intro_crypto_convert), getResources().getString(R.string.nextShowIntro));

        myClass.addIntro(getActivity(), binding.imageButtonSaveBuffer1, getResources().getString(R.string.intro_crypto_copyBuf), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonSaveQRCode1, getResources().getString(R.string.intro_crypto_qrCode), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonShare1, getResources().getString(R.string.intro_crypto_share), getResources().getString(R.string.nextShowIntro));

        myClass.addIntro(getActivity(), binding.imageButtonSaveBuffer2, getResources().getString(R.string.intro_crypto_copyBuf), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonSaveQRCode2, getResources().getString(R.string.intro_crypto_qrCode), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonShare2, getResources().getString(R.string.intro_crypto_share), getResources().getString(R.string.nextShowIntro));

        myClass.showIntro(getActivity(), "isShowCrypto");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}