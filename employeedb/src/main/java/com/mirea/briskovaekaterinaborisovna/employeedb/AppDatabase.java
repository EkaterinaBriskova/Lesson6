package com.mirea.briskovaekaterinaborisovna.employeedb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Employee.class}, version = 3)  // ← увеличь версию
public abstract class AppDatabase extends RoomDatabase {
    public abstract EmployeeDao employeeDao();
}