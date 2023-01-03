package com.example.grouppurchase_backend.Service;

public interface UserService {
    String check(String user_name, String password);

    int create(String user_name, String password, String email, String image_url);

    boolean add(int user_id, int balance);

    String getSomeInfo(int user_id);

    String getUsername(int user_id);

    String getUserPic();

    boolean changePassword(int user_id,String originPassword, String newPassword);
}