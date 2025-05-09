package com.mirea.briskovaekaterinaborisovna.lesson6;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.briskovaekaterinaborisovna.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String PREF_NAME = "pref_mirea";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Загружаем сохраненные данные
        binding.editTextGroup.setText(preferences.getString("group", ""));
        binding.editTextNumber.setText(preferences.getString("number", ""));
        binding.editTextFilm.setText(preferences.getString("movie", ""));

        binding.buttonSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("group", binding.editTextGroup.getText().toString());
            editor.putString("number", binding.editTextNumber.getText().toString());
            editor.putString("movie", binding.editTextFilm.getText().toString());
            editor.apply();
        });
    }
}