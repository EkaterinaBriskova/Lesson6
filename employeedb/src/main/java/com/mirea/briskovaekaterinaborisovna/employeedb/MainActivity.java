package com.mirea.briskovaekaterinaborisovna.employeedb;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView employeeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employeeTextView = findViewById(R.id.employeeTextView);

        // Получаем доступ к БД
        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();

        // Добавляем нового сотрудника (для примера)
        Employee newEmployee = new Employee("Человек-паук", 155000);
        employeeDao.insert(newEmployee);
        Log.d(TAG, "Добавлен сотрудник: " + newEmployee.name + " с зарплатой: " + newEmployee.salary);

        // Получаем всех сотрудников
        List<Employee> employees = employeeDao.getAll();

        // Формируем строку для отображения
        StringBuilder sb = new StringBuilder();
        for (Employee emp : employees) {
            sb.append("Имя: ").append(emp.name)
                    .append(", Зарплата: ").append(emp.salary)
                    .append("\n");
            Log.d(TAG, "Сотрудник: " + emp.name + ", Зарплата: " + emp.salary);
        }

        // Отображаем в TextView
        employeeTextView.setText(sb.toString());

        // Получение сотрудника по id
        Employee employeeById = employeeDao.getById(1);
        if (employeeById != null) {
            Log.d(TAG, "Получен сотрудник с ID 1: " + employeeById.name);
        } else {
            Log.d(TAG, "Сотрудник с ID 1 не найден");
        }

        // Обновляем сотрудника
        if (employeeById != null) {
            employeeById.salary = 300000;
            employeeDao.update(employeeById);
            Log.d(TAG, "Обновлён сотрудник: " + employeeById.name + ", Новая зарплата: " + employeeById.salary);
        }
    }
}