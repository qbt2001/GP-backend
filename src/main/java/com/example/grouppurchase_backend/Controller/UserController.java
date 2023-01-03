package com.example.grouppurchase_backend.Controller;

import com.example.grouppurchase_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Component
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/HandleUserPic")
    @ResponseBody
    public String getPicture() {     //将所需相片存到本地相册
        return userService.getUserPic();
    }

    @RequestMapping("/CheckUser")
    @ResponseBody
    public String check(@RequestBody Map map) {     //登录时检测用户名与密码
        String user_name = String.valueOf(map.get("user_name"));
        String password = String.valueOf(map.get("password"));
        return userService.check(user_name, password);
    }

    @RequestMapping("/NewUser")
    @ResponseBody
    public String create(@RequestBody Map map) {    //注册新用户
        String user_name = String.valueOf(map.get("user_name"));
        String password = String.valueOf(map.get("password"));
        String email = String.valueOf(map.get("email"));
        String image_url = String.valueOf(map.get("image_url"));
        System.out.println("111");
        int uid = userService.create(user_name, password, email, image_url);
        return String.valueOf(uid);
    }

    @RequestMapping("/AddBalance")
    @ResponseBody
    public String add(@RequestBody Map map) {    //充值
        String tmp1 = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(tmp1);
        String tmp2 = String.valueOf(map.get("money"));
        int balance = Integer.parseInt(tmp2);
        if (userService.add(user_id, balance))
            return "1";
        else
            return "0";
    }

    @RequestMapping("/ChangePassword")
    @ResponseBody
    public boolean changePassword(@RequestBody Map map)
    {
        String uid = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(uid);
        String originPassword = String.valueOf(map.get("originPassword"));
        String newPassword = String.valueOf(map.get("newPassword"));
        if(userService.changePassword(user_id,originPassword,newPassword))
            return true;
        else
            return false;

    }

    @RequestMapping("/GetSomeInfo")
    @ResponseBody
    public String getSomeInfo(@RequestBody Map map) {    //余额、发起数、参与数
        String tmp = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(tmp);
        return userService.getSomeInfo(user_id);
    }

    @RequestMapping("/GetUsername")
    @ResponseBody
    public String getUsername(@RequestBody Map map) {
        String tmp = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(tmp);
        return userService.getUsername(user_id);
    }
}
