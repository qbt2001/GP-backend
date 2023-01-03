package com.example.grouppurchase_backend.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.grouppurchase_backend.Dao.GroupDao;
import com.example.grouppurchase_backend.Dao.OrderDao;
import com.example.grouppurchase_backend.Dao.UserDao;
import com.example.grouppurchase_backend.Entity.Group;
import com.example.grouppurchase_backend.Entity.Order;
import com.example.grouppurchase_backend.Entity.User;
import com.example.grouppurchase_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    GroupDao groupDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public boolean changePassword(int user_id,String originPassword,String newPassword) {
        User user = userDao.findByUser_id(user_id);
        if(user == null)
            return false;   //user为空
        Base64.Decoder decoder=Base64.getDecoder();
        byte[] tmp=decoder.decode(user.getPassword());
        String pwd=new String(tmp);
        if (!Objects.equals(pwd, originPassword))
            return false;    //密码错误

        Base64.Encoder encoder=Base64.getEncoder();
        String newPwd=encoder.encodeToString(newPassword.getBytes());  //对新密码加密
        user.setPassword(newPwd);

        return userDao.updateUserPassword(user);

    }

    @Override
    public String check(String user_name, String password) {
        User u = userDao.check(user_name);
        if (u == null)
            return "1";    //找不到用户
        Base64.Decoder decoder=Base64.getDecoder();
        byte[] tmp=decoder.decode(u.getPassword());    //解密
        String pwd=new String(tmp);
        if (!Objects.equals(pwd, password))
            return "2";    //密码错误
        String email = u.getEmail();
        System.out.println(email);
        List<Group> launch = groupDao.getGroupsByHead_id(u.getUser_id());
        List<Group> join = groupDao.getGroupsByFollower_id(u.getUser_id());
        List<Order> orders = orderDao.getAllUserOrders(u.getUser_id());
        int all = orders.size();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getIspaid() == 0) {
                orders.remove(order);
                i--;
            }
        }
        int now = orders.size();
        ArrayList<String> con = new ArrayList<>();
        con.add(String.valueOf(u.getUser_id()));    //用户id
        con.add(u.getUser_name());    //用户名
        con.add(u.getImage_url());    //用户头像
        con.add(String.valueOf(u.getBalance()));    //用户余额
        con.add(String.valueOf(launch.size()));    //发起数
        con.add(String.valueOf(join.size()));    //参与数
        con.add(String.valueOf(now));    //正常订单数
        con.add(String.valueOf(all - now));    //被取消的订单数
        return JSON.toJSONString(con, SerializerFeature.BrowserCompatible);
    }

    @Override
    public int create(String user_name, String password, String email, String image_url) {
        Base64.Encoder encoder=Base64.getEncoder();
        String pwd=encoder.encodeToString(password.getBytes());    //加密
        return userDao.create(user_name, pwd, email, image_url);
    }

    @Override
    public boolean add(int user_id, int balance) {
        return userDao.add(user_id, balance);
    }

    @Override
    public String getSomeInfo(int user_id) {
        User u = userDao.findByUser_id(user_id);
        List<Group> launch = groupDao.getGroupsByHead_id(user_id);
        List<Group> join = groupDao.getGroupsByFollower_id(user_id);
        List<Order> orders = orderDao.getAllUserOrders(user_id);
        int all = orders.size();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getIspaid() == 0) {
                orders.remove(order);
                i--;
            }
        }
        int now = orders.size();
        ArrayList<String> con = new ArrayList<>();
        con.add(String.valueOf(u.getBalance()));    //余额
        con.add(String.valueOf(launch.size()));    //发起数
        con.add(String.valueOf(join.size()));    //参与数
        con.add(String.valueOf(now));    //正常订单数
        con.add(String.valueOf(all - now));    //被取消的订单数
        return JSON.toJSONString(con, SerializerFeature.BrowserCompatible);
    }

    @Override
    public String getUsername(int user_id) {
        User u = userDao.findByUser_id(user_id);
        return String.valueOf(u.getUser_name());
    }

    @Override
    public String getUserPic() {
        List<User> list = userDao.getAllUsers();
        ArrayList<String> con = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            con.add(String.valueOf(u.getUser_id()));
            con.add(u.getImage_url());
        }
        return JSON.toJSONString(con, SerializerFeature.BrowserCompatible);
    }


}