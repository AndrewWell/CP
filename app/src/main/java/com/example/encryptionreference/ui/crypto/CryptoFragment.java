package com.example.encryptionreference.ui.crypto;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.encryptionreference.Cipher;
import com.example.encryptionreference.MyClass;
import com.example.encryptionreference.R;
import com.example.encryptionreference.databinding.FragmentCryptoBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

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
        cipher = new Cipher(getActivity(), binding.textViewHintCrypto);
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
                boolean isEncrypt = myClass.isBooleanShared(getActivity(), "isEncrypt");

                if (conformityCheck(spinner_method, R.string.parsing_Mirror)) {
                    binding.key.setVisibility(View.INVISIBLE);
                    if (isEncrypt)
                        binding.gifImage.setBackgroundResource(R.drawable.anim_coding_mirror);
                    else binding.gifImage.setBackgroundResource(R.drawable.anim_decoding_mirror);
                } else if (conformityCheck(spinner_method, R.string.parsing_Atbash)) {
                    binding.key.setVisibility(View.INVISIBLE);
                    if (isEncrypt)
                        binding.gifImage.setBackgroundResource(R.drawable.anim_coding_atbash);
                    else binding.gifImage.setBackgroundResource(R.drawable.anim_ss);
                } else if (conformityCheck(spinner_method, R.string.parsing_Vigener)) {
                    binding.key.setVisibility(View.VISIBLE);
                    if (isEncrypt)
                        binding.gifImage.setBackgroundResource(R.drawable.anim_coding_vigenere);
                    else binding.gifImage.setBackgroundResource(R.drawable.anim_ss);
                } else if (conformityCheck(spinner_method, R.string.parsing_Caesar)) {
                    binding.key.setVisibility(View.VISIBLE);
                    if (isEncrypt)
                        binding.gifImage.setBackgroundResource(R.drawable.anim_coding_caesar);
                    else binding.gifImage.setBackgroundResource(R.drawable.anim_decoding_caesar);
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
                String openText, closeText = "", key;
                openText = binding.multiACTVPublicText.getText().toString();
                key = binding.multiACTVKey.getText().toString();
                if (myClass.isBooleanShared(getActivity(), "isEncrypt")) {
                    if (conformityCheck(spinner_method, R.string.parsing_Mirror)) {
                        closeText = cipher.mirror(openText);
                    } else if (conformityCheck(spinner_method, R.string.parsing_Vigener)) {
                        closeText = cipher.encryptVigener(openText, key);
                    } else if (conformityCheck(spinner_method, R.string.parsing_Caesar)) {
                        if (isNumeric(key))
                            closeText = cipher.encryptCaesar(openText, Integer.valueOf(key));
                        else getResources().getString(R.string.incorrectKey);
                    } else if (conformityCheck(spinner_method, R.string.parsing_Atbash)) {
                        closeText = cipher.atbash(openText);
                    }
                } else {
                    if (conformityCheck(spinner_method, R.string.parsing_Mirror)) {
                        closeText = cipher.mirror(openText);
                    } else if (conformityCheck(spinner_method, R.string.parsing_Vigener)) {
                        closeText = cipher.decryptVigener(openText, key);
                    } else if (conformityCheck(spinner_method, R.string.parsing_Caesar)) {
                        if (isNumeric(key))
                            closeText = cipher.decryptCaesar(openText, Integer.valueOf(key));
                        else getResources().getString(R.string.incorrectKey);
                    } else if (conformityCheck(spinner_method, R.string.parsing_Atbash)) {
                        closeText = cipher.atbash(openText);
                    }
                }
                binding.multiACTVPrivateText.setText(closeText);
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
                String qr = binding.multiACTVPublicText.getText().toString();
                if (qr.length() < 1)
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_enterQR), Toast.LENGTH_SHORT).show();
                else
                    dialog(qr);
            }
        });
        binding.imageButtonSaveQRCode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qr = binding.multiACTVPrivateText.getText().toString();
                if (qr.length() < 1)
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_enterQR), Toast.LENGTH_SHORT).show();
                else
                    dialog(qr);
            }
        });
        return root;
    }

    private void createIntro() {
        myClass.addIntro(getActivity(), binding.chip, getResources().getString(R.string.intro_crypto_enc_dec), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.spinnerMethods, getResources().getString(R.string.intro_crypto_method), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.buttonClear, getResources().getString(R.string.intro_crypto_clearForm), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonConvert, getResources().getString(R.string.intro_crypto_convert), getResources().getString(R.string.nextShowIntro));

        myClass.addIntro(getActivity(), binding.imageButtonSaveBuffer1, getResources().getString(R.string.intro_crypto_copyBuf), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonSaveQRCode1, getResources().getString(R.string.intro_crypto_qrCode), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonShare1, getResources().getString(R.string.intro_crypto_share), getResources().getString(R.string.nextShowIntro));

        myClass.addIntro(getActivity(), binding.imageButtonSaveBuffer2, getResources().getString(R.string.intro_crypto_copyBuf), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonSaveQRCode2, getResources().getString(R.string.intro_crypto_qrCode), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonShare2, getResources().getString(R.string.intro_crypto_share), getResources().getString(R.string.nextShowIntro));

        myClass.showIntro(getActivity(), "isShowCrypto");
    }

    private boolean conformityCheck(String verification, int shibboleth) {
        if (verification.equals(getResources().getString(shibboleth))) return true;
        return false;
    }

    private boolean isNumeric(String text) {
        text = text.replace(" ", "");
        System.out.println(text);
        if (text.matches("[0-9]+")) return true;
        return false;
    }

    private void dialog(String textForQRCode) {
        myClass.closeKeyboard(getActivity(), getView());
        ImageView cancel, save, share, qrCode;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.qr_dialog, null);
        dialogBuilder.setView(dialogView);

        cancel = (ImageView) dialogView.findViewById(R.id.imageButtonCancel);
        save = (ImageView) dialogView.findViewById(R.id.imageButtonSave);
        share = (ImageView) dialogView.findViewById(R.id.imageButtonShare);
        qrCode = (ImageView) dialogView.findViewById(R.id.imageViewQR);

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap qrBitmap = null;
        try {
            qrBitmap = barcodeEncoder.encodeBitmap(textForQRCode,
                    BarcodeFormat.QR_CODE, 700, 700);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        qrCode.setImageBitmap(qrBitmap);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) qrCode.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    myClass.saveImage(getActivity(), qrCode);
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClass.shareImage(getActivity(), bitmap);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}