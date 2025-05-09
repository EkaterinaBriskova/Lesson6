package com.mirea.briskovaekaterinaborisovna.notebook;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.mirea.briskovaekaterinaborisovna.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSave.setOnClickListener(v -> saveToFile());
        binding.buttonLoad.setOnClickListener(v -> loadFromFile());
    }

    private void saveToFile() {
        String fileName = binding.editFileName.getText().toString();
        String quote = binding.editQuote.getText().toString();

        if (fileName.isEmpty() || quote.isEmpty()) {
            Toast.makeText(this, "Введите наименование файла, и цитату", Toast.LENGTH_SHORT).show();
            return;
        }

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName);

        try {
            path.mkdirs(); // создаем директорию, если её нет
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(quote.getBytes(StandardCharsets.UTF_8));
            stream.close();
            Log.i("Блокнот", "Файл успешно сохранён: " + file.getAbsolutePath());
            Toast.makeText(this, "Цитата сохранена", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Блокнот", "Ошибка записи файла: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        String fileName = binding.editFileName.getText().toString();

        if (fileName.isEmpty()) {
            Toast.makeText(this, "Введите имя файла", Toast.LENGTH_SHORT).show();
            return;
        }

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName);

        try {
            FileInputStream stream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
            reader.close();
            binding.editQuote.setText(text.toString());
            Log.i("Блокнот", "Файл успешно загружен: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e("Блокнот", "Ошибка чтения файла: " + e.getMessage());
        }
    }
}