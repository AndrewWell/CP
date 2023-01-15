package com.example.encryptionreference.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.encryptionreference.BuildConfig;
import com.example.encryptionreference.MyClass;
import com.example.encryptionreference.R;
import com.example.encryptionreference.database.log.MyDbManagerLog;
import com.example.encryptionreference.databinding.FragmentAboutBinding;


public class AboutFragment extends Fragment {
    private MyDbManagerLog dbManagerLog;
    private FragmentAboutBinding binding;
    private MyClass myClass;
    private int countClickVersion = 0,
            countClickIcon = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AboutViewModel aboutViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);
        dbManagerLog = new MyDbManagerLog(getContext());
        myClass = new MyClass();
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        binding.buttonVersion.setText(getResources().getString(R.string.about_app_version) + ": " + BuildConfig.VERSION_NAME);

        binding.buttonIntro.setText(get2LineEntry(R.string.about_intro, R.string.about_hintIntro));
        binding.buttonSendFeedback.setText(get2LineEntry(R.string.about_titleFeedback, R.string.about_hintFeedback));

        binding.buttonVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countClickVersion++;
            }
        });
        binding.buttonIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterReset();
                myClass.setBooleanShared(getActivity(), "isShowScanner", true);
                myClass.setBooleanShared(getActivity(), "isShowTheory", true);
                myClass.setBooleanShared(getActivity(), "isShowCrypto", true);
                myClass.setBooleanShared(getActivity(), "isShowSettings", true);
            }
        });
        binding.imageButtonIconApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countClickVersion == 5) {
                    countClickIcon++;
                    if (countClickIcon == 2) {
                        dbManagerLog.insertToDb(myClass.getData(), getResources().getString(R.string.about_opedDM));
                        Toast.makeText(getActivity(), R.string.about_opedDM, Toast.LENGTH_SHORT).show();
                        myClass.setBooleanShared(getActivity(), "isOpenDM", true);
                        counterReset();
                    }
                } else {
                    countClickVersion = 0;
                }
            }
        });

        binding.buttonSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterReset();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getResources().getString(R.string.about_feedback_mail) +
                        "?subject=Feedback from the app: " + getResources().getString(R.string.app_name))));
            }
        });
        return binding.getRoot();
    }

    private void counterReset() {
        countClickVersion = 0;
        countClickIcon = 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        dbManagerLog.closeDb();
    }

    @Override
    public void onResume() {
        super.onResume();
        dbManagerLog.openDb();
    }

    private Spanned get2LineEntry(int first, int second) {
        return Html.fromHtml("<big>" + getResources().getString(first) + "</big><br><small>" + getResources().getString(second) + "</small>");
    }

}