package com.example.encryptionreference.ui.qr_scanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.encryptionreference.MyClass;
import com.example.encryptionreference.R;
import com.example.encryptionreference.ScannerQrCodeActivity;
import com.example.encryptionreference.databinding.FragmentQrScannerBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRScannerFragment extends Fragment {

    private FragmentQrScannerBinding binding;
    private MyClass myClass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QRScannerViewModel qrScannerViewModel =
                new ViewModelProvider(this).get(QRScannerViewModel.class);

        binding = FragmentQrScannerBinding.inflate(inflater, container, false);
        myClass = new MyClass();
        View root = binding.getRoot();
        createIntro();
        binding.buttonScanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });

        binding.buttonCreateQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateQrCode(getTextFromEditText(binding.editTextTextMultiLine), 750);
            }
        });
        binding.imageButtonSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkWriteStoragePermission();
            }
        });
        binding.imageButtonSaveBuffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClass.copyBuf(getActivity(), binding.editTextTextMultiLine.getText().toString(), view);
            }
        });
        binding.imageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClass.shareText(getActivity(), binding.editTextTextMultiLine.getText().toString());
            }
        });
        return root;
    }


    private String getTextFromEditText(EditText editText) {
        String authorLine = "QR code generated in the program '" + getString(R.string.app_name) + "':\n";
        if (editText.getText().length() < 1)
            return authorLine + "empty line";
        return authorLine + editText.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            startActivityForResult(new Intent(getActivity().getApplicationContext(), ScannerQrCodeActivity.class), 1);
        }
    }

    private void checkWriteStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            myClass.saveImage(getActivity(), binding.imageViewQrCode);
        }
    }

    @Override
//TODO посмотреть аналог данного метода, т.к. он устарел(из-за этого при разрешении использования камеры данный метод не обрабатывается)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startActivityForResult(new Intent(getActivity().getApplicationContext(), ScannerQrCodeActivity.class), 1);
        } else if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)

                myClass.saveImage(getActivity(), binding.imageViewQrCode);
        }
    }

    private void generateQrCode(String text, int size) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = null;
        try {
            bitmap = barcodeEncoder.encodeBitmap(text,
                    BarcodeFormat.QR_CODE, size, size);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        binding.imageViewQrCode.setImageBitmap(bitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String str = data.getStringExtra("result");
        binding.editTextTextMultiLine.setText(str);
        generateQrCode(str, 750);
    }

    private void createIntro() {//TODO добавить текст для подсказок Intro
        myClass.addIntro(getActivity(), binding.buttonCreateQrCode, getResources().getString(R.string.intro_qrCode_create), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.buttonScanQrCode, getResources().getString(R.string.intro_qrCode_scan), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonSaveBuffer, getResources().getString(R.string.intro_crypto_copyBuf), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonSaveImage, getResources().getString(R.string.intro_qrCode_saveImage), getResources().getString(R.string.nextShowIntro));
        myClass.addIntro(getActivity(), binding.imageButtonShare, getResources().getString(R.string.intro_crypto_share), getResources().getString(R.string.nextShowIntro));
        myClass.showIntro(getActivity(), "isShowScanner");
    }
}