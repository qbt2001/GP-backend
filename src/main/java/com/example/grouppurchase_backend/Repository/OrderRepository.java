package com.example.grouppurchase_backend.Repository;

import com.example.grouppurchase_backend.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query(nativeQuery = true,value="select * from grouppurchase.order where `order`.user_id =:user_id")
    List<Order> getOrderByUser_id(@Param("user_id")Integer user_id);
    @Query(nativeQuery = true,value="select * from grouppurchase.order where `order`.group_id=:group_id")
    List<Order> getOrderByGroup_id(@Param("group_id")Integer group_id);
    @Query(nativeQuery = true,value="select * from grouppurchase.order where `order`.order_id=:order_id")
    Order getOrderByOrder_id(@Param("order_id")Integer order_id);
    @Query(nativeQuery = true,value="select * from grouppurchase.order where `order`.commodity_id=:commodity_id")
    List<Order> getOrderByCommodity_id(@Param("commodity_id")Integer commodity_id);
}
