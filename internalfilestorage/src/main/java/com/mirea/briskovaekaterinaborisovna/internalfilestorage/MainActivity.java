package com.mirea.briskovaekaterinaborisovna.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.mirea.briskovaekaterinaborisovna.internalfilestorage.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private final String fileName = "mirea.txt";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSave.setOnClickListener(v -> {
            String date = binding.editTextDate.getText().toString();
            String description = binding.editTextDescription.getText().toString();
            String result = date + " - " + description;

            try (FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)) {
                outputStream.write(result.getBytes());
                Toast.makeText(this, "Сохранено!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка записи", Toast.LENGTH_SHORT).show();
            }
        });

        // Чтение файла через 5 секунд
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                binding.textViewOutput.post(() -> binding.textViewOutput.setText(getTextFromFile()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String getTextFromFile() {
        try (FileInputStream fin = openFileInput(fileName)) {
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            return text;
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return "Ошибка чтения файла";
        }
    }
}