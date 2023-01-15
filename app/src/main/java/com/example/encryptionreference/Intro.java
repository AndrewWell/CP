package com.example.encryptionreference;

import android.app.Activity;

public class Intro {
    private Object object;
    private String text, secondaryText;
    private Activity activity;

    public Intro(Object object, String text, String secondaryText, Activity activity) {
        this.object = object;
        this.text = text;
        this.secondaryText = secondaryText;
        this.activity = activity;
    }


    public Object getObject() {
        return object;
    }

    public String getText() {
        return text;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public Activity getActivity() {
        return activity;
    }
}
