package com.mirea.briskovaekaterinaborisovna.employeedb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee",
        indices = {@Index(value = {"name"}, unique = true)})
public class Employee {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "salary")
    public int salary;

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }
}