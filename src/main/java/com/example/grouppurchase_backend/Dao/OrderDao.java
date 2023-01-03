package com.example.grouppurchase_backend.Dao;

import com.example.grouppurchase_backend.Entity.Order;

import java.util.List;

public interface OrderDao {
    String createOrder(int group_id, int commodity_id, int user_id,  int commodity_amount, String pay_time);

    void deleteOrder(int order_id);
    Order getOne(int order_id);
    List<Order> getAllGroupOrders(int group_id);

    List<Order> getAllUserOrders(int user_id);

    List<Order> getAllCommodityOrders(int commodity_id);

    boolean drawbackOrder(int order_id);
}