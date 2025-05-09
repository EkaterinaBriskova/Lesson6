package com.mirea.briskovaekaterinaborisovna.securesharedpreferences;

import android.content.SharedPreferences;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.mirea.briskovaekaterinaborisovna.securesharedpreferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences secureSharedPreferences;
    private ActivityMainBinding binding;  // Использование ViewBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            // Генерация мастер-ключа для шифрования
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // Создание EncryptedSharedPreferences
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    masterKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // Проверка наличия данных о поэте в SharedPreferences
            if (!secureSharedPreferences.contains("poet_name")) {
                secureSharedPreferences.edit()
                        .putString("poet_name", "Пушкин")
                        .apply();
            }

            // Чтение имени поэта
            String poetName = secureSharedPreferences.getString("poet_name", "");
            binding.poetNameTextView.setText(poetName);

            // Установка изображения поэта
            binding.poetImageView.setImageResource(R.drawable.pushkin);

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            binding.poetNameTextView.setText("Ошибка при инициализации безопасного хранилища");
        }
    }
}