package com.example.grouppurchase_backend.Dao;

import com.example.grouppurchase_backend.Entity.Commodity;

import java.util.List;
import java.util.Optional;

public interface CommodityDao {
    int addCommodity(String commodity_name,int group_id,String image_url,String des,int price,int inventory,boolean miao);
    void deleteCommodity(int commodity_id);
    /*
     *type 为修改的类型，content为新的内容
     * type = 0修改commodity_name,type = 1修改image_url,
     * type = 2修改des,type = 3修改price,
     * type = 4修改inventory，type = 5修改miao
     */
    void reviseCommodity(int commodity_id,int type,String content);
    Optional<Commodity> getOne(int commodity_id);
    List<Commodity> getCommoditiesByGroup_id(int group_id);
    Commodity getCommodityByCommodity_id(int commodity_id);
    List<Commodity> getAllCommodities();
}