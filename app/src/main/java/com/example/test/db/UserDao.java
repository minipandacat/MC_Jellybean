package com.example.test.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert
    void insertUser(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user WHERE email = :email")
    User findByEmail(String email);

    @Query("SELECT * FROM user WHERE password = :password")
    User getPassword(String password);

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    User getUser(String email, String password);

    @Insert
    void insert(User user);
}