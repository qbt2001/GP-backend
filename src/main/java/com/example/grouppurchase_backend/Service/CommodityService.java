package com.example.grouppurchase_backend.Service;

import org.quartz.SchedulerException;

import java.text.ParseException;

public interface CommodityService {

    int addCommodity(String commodity_name, String group_id, String image_url, String des, String price, String inventory, String miao) throws SchedulerException, ParseException;

    void updateCommodity(Integer id, Integer type, String inner) throws SchedulerException, ParseException;

    void deleteCommodity(Integer g_id, Integer c_id) throws SchedulerException, ParseException;

    String getCommodityByCommodity_id(int commodity_id);

    String getCommodityPic();
}