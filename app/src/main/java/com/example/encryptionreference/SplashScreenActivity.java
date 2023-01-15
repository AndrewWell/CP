package com.example.encryptionreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


public class SplashScreenActivity extends AppCompatActivity {
    private MyClass myClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myClass = new MyClass();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        myClass.setBooleanShared(this,"isOpenDM",false);
        finish();
    }
}
