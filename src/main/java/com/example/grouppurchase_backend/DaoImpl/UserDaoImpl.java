package com.example.grouppurchase_backend.DaoImpl;

import com.example.grouppurchase_backend.Dao.UserDao;
import com.example.grouppurchase_backend.Entity.User;
import com.example.grouppurchase_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User check(String user_name) {
        return userRepository.findByUser_name(user_name);
    }

    @Override
    public int create(String user_name, String password, String email, String image_url) {
        User one = userRepository.findByUser_name(user_name);
        if (one != null) {
            System.out.println("Meet a same user.");
            return 0;
        }
        User u = new User();
        u.setUser_name(user_name);
        u.setPassword(password);    //存储加密后的密码
        u.setEmail(email);
        u.setImage_url(image_url);
        u.setBalance(0);
        userRepository.save(u);
        return u.getUser_id();
    }

    @Override
    public boolean add(int user_id, int balance) {
        User one = userRepository.findByUser_id(user_id);
        if (one == null) {
            System.out.println("Unable to find the user");
            return false;
        }
        int old = one.getBalance();
        old += balance;
        one.setBalance(old);
        userRepository.save(one);
        return true;
    }

    @Override
    public User findByUser_id(int user_id) {
        return userRepository.findByUser_id(user_id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void allEncode() {
//        Base64.Encoder encoder=Base64.getEncoder();
//        List<User> all=getAllUsers();
//        for(int i=0;i<all.size();i++) {
//            User u=all.get(i);
//            String pwd=encoder.encodeToString(u.getPassword().getBytes());
//            u.setPassword(pwd);
//            userRepository.save(u);
//        }
    }
}