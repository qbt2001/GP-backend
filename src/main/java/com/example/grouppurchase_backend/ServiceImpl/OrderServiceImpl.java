package com.example.grouppurchase_backend.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.grouppurchase_backend.Dao.CommodityDao;
import com.example.grouppurchase_backend.Dao.GroupDao;
import com.example.grouppurchase_backend.Dao.OrderDao;
import com.example.grouppurchase_backend.Dao.UserDao;
import com.example.grouppurchase_backend.Entity.Group;
import com.example.grouppurchase_backend.Entity.Order;
import com.example.grouppurchase_backend.Entity.User;
import com.example.grouppurchase_backend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    GroupDao groupDao;
    @Autowired
    UserDao userDao;
    @Autowired
    CommodityDao commodityDao;

    @Override
    public String createOrder(int commodity_id, int user_id, int commodity_amount, String pay_time) {
        int group_id = commodityDao.getCommodityByCommodity_id(commodity_id).getGroup().getGroup_id();
        String order = orderDao.createOrder(
                group_id, commodity_id, user_id, commodity_amount, pay_time
        );
        return order;
    }

    @Override
    public String GetOrdersByUser_id(int user_id) {
        List<Order> all = orderDao.getAllUserOrders(user_id);
        for (int i = 0; i < all.size(); i++) {
            Order order = all.get(i);
            if (order.getIspaid() == 0) {
                all.remove(order);
                i--;
            }
        }
        ArrayList<JSONArray> orderJson = new ArrayList<>();
        ArrayList<String> num = new ArrayList<>();
        num.add(String.valueOf(all.size()));
        orderJson.add((JSONArray) JSONArray.toJSON(num));
        for (int i = 0; i < all.size(); i++) {
            Order one = all.get(i);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(one.getOrder_id()));//0
            arrayList.add(
                    String.valueOf(
                            groupDao.getGroupByGroup_id(
                                    one.getGroup_id()
                            ).getGroup_name()
                    ));//返回团购名称 1
            arrayList.add(
                    String.valueOf(
                            userDao.findByUser_id(
                                    one.getUser_id()
                            ).getUser_name()
                    ));//返回用户名称 2
            arrayList.add(
                    String.valueOf(
                            commodityDao.getCommodityByCommodity_id(
                                    one.getCommodity_id()
                            ).getCommodity_name()
                    )
            );//返回商品名称 3
            arrayList.add(
                    String.valueOf(
                            one.getCommodity_id()
                    )
            );//返回商品id 4
            arrayList.add(String.valueOf(one.getCommodity_amount()));//5
            arrayList.add(String.valueOf(one.getMoney()));//6
            arrayList.add(String.valueOf(one.getPay_time()));//7
            arrayList.add(String.valueOf(one.getPost()));//8
            arrayList.add(String.valueOf(one.getIspaid()));//9
            arrayList.add(String.valueOf(one.getStatus()));
            orderJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        return JSON.toJSONString(orderJson, SerializerFeature.BrowserCompatible);
    }

    @Override
    public String GetOrdersByGroup_id(int group_id) {
        List<Order> all = orderDao.getAllGroupOrders(group_id);
        for (int i = 0; i < all.size(); i++) {
            Order order = all.get(i);
            if (order.getIspaid() == 0) {
                all.remove(order);
                i--;
            }
        }
        ArrayList<JSONArray> orderJson = new ArrayList<>();
        ArrayList<String> num = new ArrayList<>();
        num.add(String.valueOf(all.size()));
        orderJson.add((JSONArray) JSONArray.toJSON(num));
        for (int i = 0; i < all.size(); i++) {
            Order one = all.get(i);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(one.getOrder_id()));
            arrayList.add(
                    String.valueOf(
                            groupDao.getGroupByGroup_id(
                                    one.getGroup_id()
                            ).getGroup_name()
                    ));//返回团购名称
            arrayList.add(
                    String.valueOf(
                            userDao.findByUser_id(
                                    one.getUser_id()
                            ).getUser_name()
                    ));//返回用户名称
            arrayList.add(
                    String.valueOf(
                            commodityDao.getCommodityByCommodity_id(
                                    one.getCommodity_id()
                            ).getCommodity_name()
                    )
            );//返回商品名称
            arrayList.add(
                    String.valueOf(
                            one.getCommodity_id()
                    )
            );//返回商品id
            arrayList.add(String.valueOf(one.getCommodity_amount()));
            arrayList.add(String.valueOf(one.getMoney()));
            arrayList.add(String.valueOf(one.getPay_time()));
            arrayList.add(String.valueOf(one.getPost()));
            arrayList.add(String.valueOf(one.getIspaid()));
            arrayList.add(String.valueOf(one.getStatus()));
            orderJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        return JSON.toJSONString(orderJson, SerializerFeature.BrowserCompatible);
    }

    @Override
    public String GetOrdersByCommodity_id(int commodity_id) {
        List<Order> all = orderDao.getAllCommodityOrders(commodity_id);
        return "1";
    }

    @Override
    public String GetOrderAmountByUser_id(int user_id) {
        List<Order> all = orderDao.getAllUserOrders(user_id);
        for (int i = 0; i < all.size(); i++) {
            Order order = all.get(i);
            if (order.getIspaid() == 0) {
                all.remove(order);
                i--;
            }
        }
        return String.valueOf(all.size());
    }

    @Override
    public String GetOrderTotalByUser_id(int user_id) {
        List<Order> all = orderDao.getAllUserOrders(user_id);
        for (int i = 0; i < all.size(); i++) {
            Order order = all.get(i);
            if (order.getIspaid() == 0) {
                all.remove(order);
                i--;
            }
        }
        int TotalMoney = 0;
        for (int i = 0; i < all.size(); i++)
            TotalMoney += all.get(i).getMoney();
        return String.valueOf(TotalMoney);
    }

    @Override
    public String GetOrderAmountByGroup_id(int group_id) {
        List<Order> all = orderDao.getAllGroupOrders(group_id);
        for (int i = 0; i < all.size(); i++) {
            Order order = all.get(i);
            if (order.getIspaid() == 0) {
                all.remove(order);
                i--;
            }
        }
        return String.valueOf(all.size());
    }

    @Override
    public String GetOrderTotalByGroup_id(int group_id) {
        List<Order> all = orderDao.getAllGroupOrders(group_id);
        for (int i = 0; i < all.size(); i++) {
            Order order = all.get(i);
            if (order.getIspaid() == 0) {
                all.remove(order);
                i--;
            }
        }
        int TotalMoney = 0;
        for (int i = 0; i < all.size(); i++) {
            TotalMoney += all.get(i).getMoney();
        }
        return String.valueOf(TotalMoney);
    }

    @Override
    public boolean drawbackOrder(int order_id) {
        return orderDao.drawbackOrder(order_id);
    }

    @Override
    public boolean cancelOrder(int order_id) {
        return orderDao.cancelOrder(order_id);
    }

    @Override
    public String payOrder(int order_id)
    {
        Order order=orderDao.getOne(order_id);
        if (order.getIspaid()==1)
            return "isPaid";

        User user=userDao.findByUser_id(order.getUser_id());
        int money=order.getMoney();
        int userBalance=user.getBalance();
        if (userBalance<money)
            return "balance";

        Group group=groupDao.getGroupByGroup_id(order.getGroup_id());
        User head=userDao.findByUser_id(group.getHead());
        int headBalance=head.getBalance();
        user.setBalance(userBalance-money);
        head.setBalance(headBalance+money);
        order.setIspaid(1);
        return "success";
    }

    @Override
    public String GetFailedOrdersByUser_id(int user_id) {
        List<Order> all = orderDao.getAllUserOrders(user_id);
        for (int i = 0; i < all.size(); i++) {
            Order order = all.get(i);
            if (order.getIspaid() == 1) {
                all.remove(order);
                i--;
            }
        }
        ArrayList<JSONArray> orderJson = new ArrayList<>();
        ArrayList<String> num = new ArrayList<>();
        num.add(String.valueOf(all.size()));
        orderJson.add((JSONArray) JSONArray.toJSON(num));
        for (int i = 0; i < all.size(); i++) {
            Order one = all.get(i);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(one.getOrder_id()));//0
            arrayList.add(
                    String.valueOf(
                            groupDao.getGroupByGroup_id(
                                    one.getGroup_id()
                            ).getGroup_name()
                    ));//返回团购名称 1
            arrayList.add(
                    String.valueOf(
                            userDao.findByUser_id(
                                    one.getUser_id()
                            ).getUser_name()
                    ));//返回用户名称 2
            arrayList.add(
                    String.valueOf(
                            commodityDao.getCommodityByCommodity_id(
                                    one.getCommodity_id()
                            ).getCommodity_name()
                    )
            );//返回商品名称 3
            arrayList.add(
                    String.valueOf(
                            one.getCommodity_id()
                    )
            );//返回商品id 4
            arrayList.add(String.valueOf(one.getCommodity_amount()));//5
            arrayList.add(String.valueOf(one.getMoney()));//6
            arrayList.add(String.valueOf(one.getPay_time()));//7
            arrayList.add(String.valueOf(one.getPost()));//8
            arrayList.add(String.valueOf(one.getIspaid()));//9
            arrayList.add(String.valueOf(one.getStatus()));
            orderJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        return JSON.toJSONString(orderJson, SerializerFeature.BrowserCompatible);
    }
}