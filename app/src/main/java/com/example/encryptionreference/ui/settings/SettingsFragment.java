package com.example.encryptionreference.ui.settings;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.encryptionreference.MyClass;
import com.example.encryptionreference.R;
import com.example.encryptionreference.database.log.MyDbManagerLog;
import com.example.encryptionreference.databinding.FragmentSettingsBinding;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private MyDbManagerLog dbManagerLog;
    private MyClass myClass;
    private String langSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel qrScannerViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        dbManagerLog = new MyDbManagerLog(getContext());
        myClass = new MyClass();

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.switchPermCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.switchPermCamera.isChecked())
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                //TODO else найти способ програмно отозвать permission
            }
        });
        binding.switchPermFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.switchPermFile.isChecked())
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                //TODO else найти способ програмно отозвать permission
            }
        });
        binding.buttonWtirePDFFromLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFileLog();
                binding.textViewLogs.setText(dbManagerLog.getAllLogFromString());
            }
        });
        binding.switchPermMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext().getApplicationContext(), getString(R.string.tempUnav), Toast.LENGTH_LONG).show();
                binding.switchPermMK.setChecked(false);
                binding.switchPermMK.setEnabled(false);
            }
        });
        View root = binding.getRoot();
        binding.switchForDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchForDev.isChecked())
                    binding.forDev.setVisibility(View.INVISIBLE);
                dbManagerLog.insertToDb(myClass.getData(), getResources().getString(R.string.about_closeDM));
                Toast.makeText(getActivity(), R.string.about_closeDM, Toast.LENGTH_SHORT).show();
                myClass.setBooleanShared(getActivity(), "isOpenDM", false);
            }
        });
        binding.radioButtonRu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClick("ru", binding.radioButtonRu);
            }
        });
        binding.radioButtonEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClick("en", binding.radioButtonEn);
            }
        });
        binding.radioButtonSys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClick("system", binding.radioButtonSys);
            }
        });
        return root;
    }

    private void createFileLog() {
        try {
            //todo найти куда сохраняется файл с логами
            FileOutputStream fileOutputStream = getActivity().openFileOutput("[LOG]: " + myClass.getData() + ".txt", Context.MODE_APPEND);
            fileOutputStream.write(dbManagerLog.getAllLogFromString().getBytes());
            fileOutputStream.close();
            Toast.makeText(getContext(), R.string.settings_uploaded, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            dbManagerLog.insertToDb(myClass.getData(), e.getStackTrace().toString());
            e.printStackTrace();
        } catch (IOException e) {
            dbManagerLog.insertToDb(myClass.getData(), e.getStackTrace().toString());
            e.printStackTrace();
        }
    }

    private void setClick(String language, RadioButton nextRadio) {
        myClass.setDefLang(getActivity(), language);
        binding.radioButtonSys.setChecked(false);
        binding.radioButtonEn.setChecked(false);
        binding.radioButtonRu.setChecked(false);
        nextRadio.setChecked(true);
        Toast.makeText(getContext().getApplicationContext(), getString(R.string.setLocaleChan), Toast.LENGTH_SHORT).show();
        myClass.updateApp(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbManagerLog.openDb();
        if (myClass.isBooleanShared(getActivity(), "isOpenDM")) {
            binding.forDev.setVisibility(View.VISIBLE);
            binding.switchForDev.setChecked(true);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            binding.switchPermCamera.setChecked(true);
        } else {
            binding.switchPermCamera.setChecked(false);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            binding.switchPermFile.setChecked(true);
        } else {
            binding.switchPermFile.setChecked(false);
        }
        String lan=myClass.getDefLang(getActivity());
        if (lan.equals("ru")) binding.radioButtonRu.setChecked(true);
        else if (lan.equals("en")) binding.radioButtonEn.setChecked(true);
        else if (lan.equals("system")) binding.radioButtonSys.setChecked(true);
    }

}