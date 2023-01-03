package com.example.grouppurchase_backend.Service;

public interface OrderService {
    String createOrder(int commodity_id, int user_id,int commodity_amount,String pay_time);
    String GetOrdersByUser_id(int user_id);
    String GetOrdersByGroup_id(int group_id);
    String GetOrdersByCommodity_id(int commodity_id);
    String GetOrderAmountByUser_id(int user_id);
    String GetOrderTotalByUser_id(int user_id);
    String GetOrderAmountByGroup_id(int group_id);
    String GetOrderTotalByGroup_id(int group_id);
    boolean drawbackOrder(int order_id);
    boolean cancelOrder(int order_id);

    String GetFailedOrdersByUser_id(int user_id);
}

