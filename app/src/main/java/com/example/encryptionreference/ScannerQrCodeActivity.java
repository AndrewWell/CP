package com.example.encryptionreference;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerQrCodeActivity  extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView zBarScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zBarScannerView = new ZBarScannerView(this);
        setContentView(zBarScannerView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        zBarScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zBarScannerView.setResultHandler(this);
        zBarScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        setResult(RESULT_OK, new Intent().putExtra("result", result.getContents()));
        finish();
    }
}
