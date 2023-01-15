package com.example.encryptionreference;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.encryptionreference.ui.settings.SettingsFragment;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MyClass {
    private List<Intro> introList = new ArrayList<>();
    private int count = 0, i = 0;

    public String getData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public void closeKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void copyBuf(Activity activity, String text, View view) {
        ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("copyBuffer", text));
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            Toast.makeText(activity, R.string.copyBuf, Toast.LENGTH_SHORT).show();
            closeKeyboard(activity, view);
        }
    }

    public void saveImage(Activity activity, ImageView imageView) {
        Uri images;
        ContentResolver contentResolver = activity.getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        Uri uri = contentResolver.insert(images, contentValues);
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            OutputStream outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Objects.requireNonNull(outputStream);
            Toast.makeText(activity, R.string.toast_savedImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(activity, R.string.toast_notSavedImage, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private SharedPreferences openShared(Activity activity) {
        return activity.getSharedPreferences("MyShared", Context.MODE_PRIVATE);
    }

    public void setBooleanShared(Activity activity, String nameBoolean, boolean isBoolean) {
        openShared(activity).edit().putBoolean(nameBoolean, isBoolean).commit();
    }

    public boolean isBooleanShared(Activity activity, String nameBoolean) {
        if (openShared(activity).getBoolean(nameBoolean, true))
            return true;
        return false;
    }

    public String getDefLang(Activity activity){
        return openShared(activity).getString("language","");
    }
    public void setDefLang(Activity activity, String lan){
        openShared(activity).edit().putString("language",lan).commit();
    }

    private void showIntro(Intro intro) {
        new MaterialTapTargetPrompt.Builder(intro.getActivity())
                .setTarget((View) intro.getObject())
                .setPrimaryText(intro.getText())
                .setSecondaryText(intro.getSecondaryText())
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                                || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {
                            if (clickIntro(intro.getActivity()) != null) {
                                showIntro(clickIntro(intro.getActivity()));
                                i++;
                            }
                        }
                    }
                })
                .show();
    }

    public boolean showIntro(Activity activity, String nameBoolean) {
        if (isBooleanShared(activity, nameBoolean)) {
            clickIntro(activity);
            setBooleanShared(activity, nameBoolean, false);
            return true;
        }
        return false;
    }


    public void addIntro(Activity activity, Object object, String text, String secondaryText) {
        introList.add(count, new Intro(object, text, secondaryText, activity));
        count++;
    }

    private Intro clickIntro(Activity activity) {
        if (i == 0) {
            showIntro(introList.get(i));
            i++;
        } else {
            if (i < count)
                return introList.get(i);
            i = 0;
        }
        return null;
    }

    public void shareText(Activity activity, String text){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,text);
        activity.startActivity(Intent.createChooser(i,"Share"));
    }

    public static void updateApp(Activity activity){
       Intent intent = new Intent(activity, SplashScreenActivity.class);
       activity.startActivity(intent);
       activity.finishAffinity();
    }
}
