package com.example.grouppurchase_backend.Dao;

import com.example.grouppurchase_backend.Entity.User;

import java.util.List;

public interface UserDao {
    User check(String user_name);

    int create(String user_name, String password, String email, String image_url);

    boolean add(int user_id, int balance);

    User findByUser_id(int user_id);

    List<User> getAllUsers();


    boolean updateUserPassword(User user);
    void allEncode();
}
