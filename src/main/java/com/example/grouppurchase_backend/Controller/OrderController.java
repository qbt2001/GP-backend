package com.example.grouppurchase_backend.Controller;

import com.example.grouppurchase_backend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Service
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/CreateOrder")
    @ResponseBody
    public String createOrder(@RequestBody Map map) {
        String Commodity=String.valueOf(map.get("commodity_id"));
        int commodity_id=Integer.parseInt(Commodity);
        String User=String.valueOf(map.get("user_id"));
        int user_id=Integer.parseInt(User);
        String pay_time=String.valueOf(map.get("pay_time"));
        String Amount=String.valueOf(map.get("commodity_amount"));
        int commodity_amount=Integer.parseInt(Amount);
        return  orderService.createOrder(commodity_id,user_id,
                commodity_amount,pay_time);
    }

    @RequestMapping("/getFailedOrder")
    @ResponseBody
    public String getFailedOrdersByUser_id(@RequestBody Map map) {
        String tmp=String.valueOf(map.get("user_id"));
        int user_id=Integer.parseInt(tmp);
        return orderService.GetFailedOrdersByUser_id(user_id);
    }

    @RequestMapping("/getUserOrder")
    @ResponseBody
    public String getOrdersByUser_id(@RequestBody Map map) {
        String tmp=String.valueOf(map.get("user_id"));
        int user_id=Integer.parseInt(tmp);
        return orderService.GetOrdersByUser_id(user_id);
    }
    @RequestMapping("/getGroupOrder")
    @ResponseBody
    public String getOrdersByGroup_id(@RequestBody Map map) {
        String tmp=String.valueOf(map.get("group_id"));
        int group_id=Integer.parseInt(tmp);
        return orderService.GetOrdersByGroup_id(group_id);
    }
    @RequestMapping("/getCommodityOrder")
    @ResponseBody
    public String getOrdersByCommodity_id(@RequestBody Map map) {
        String tmp=String.valueOf(map.get("commodity_id"));
        int commodity_id=Integer.parseInt(tmp);
        return orderService.GetOrdersByCommodity_id(commodity_id);
    }
    @RequestMapping("/getUserOrderTotal")
    @ResponseBody
    public String getOrderTotalByUser_id(@RequestBody Map map) {
        String tmp=String.valueOf(map.get("user_id"));
        int user_id=Integer.parseInt(tmp);
        return orderService.GetOrderTotalByUser_id(user_id);
    }
    @RequestMapping("/getGroupOrderTotal")
    @ResponseBody
    public String getOrderTotalByGroup_id(@RequestBody Map map){
        String tmp=String.valueOf(map.get("group_id"));
        int group_id= Integer.parseInt(tmp);
        return orderService.GetOrderTotalByGroup_id(group_id);
    }
    @RequestMapping("/getUserOrderAmount")
    @ResponseBody
    public String getOrderAmountByUser_id(@RequestBody Map map)
    {
        String tmp=String.valueOf(map.get("user_id"));
        int user_id=Integer.parseInt(tmp);
        return orderService.GetOrderAmountByUser_id(user_id);
    }
    @RequestMapping("/getGroupOrderAmount")
    @ResponseBody
    public String getOrderAmountByGroup_id(@RequestBody Map map)
    {
        String tmp=String.valueOf(map.get("group_id"));
        int group_id= Integer.parseInt(tmp);
        return orderService.GetOrderAmountByGroup_id(group_id);
    }
    @RequestMapping("/drawbackOrder") //取消订单 传进来body是oder_id
    @ResponseBody                        // 返回 ture 删除成功  false 未找到该编号订单
    public boolean drawbackOrder(@RequestBody Map map)
    {
        String tmp=String.valueOf(map.get("order_id"));
        int order_id=Integer.parseInt(tmp);
        return orderService.drawbackOrder(order_id);
    }
}


















