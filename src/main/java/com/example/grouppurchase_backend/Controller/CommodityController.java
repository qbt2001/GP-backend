package com.example.grouppurchase_backend.Controller;


import com.example.grouppurchase_backend.Service.CommodityService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@RestController
public class CommodityController {

    @Autowired
    CommodityService commodityService;

    @RequestMapping("/HandleCommodityPic")
    @ResponseBody
    public String getPicture() {     //将所需相片存到本地相册
        return commodityService.getCommodityPic();
    }

    @RequestMapping("/CreateCommodity")
    @ResponseBody
    public String CreateCommodity(@RequestBody Map map) throws SchedulerException, ParseException {
        String commodity_name = String.valueOf(map.get("commodity_name"));
        String des = String.valueOf(map.get("des"));
        String group_id = String.valueOf(map.get("group_id"));
        String price = String.valueOf(map.get("price"));
        String inventory = String.valueOf(map.get("inventory"));
        String image_url = String.valueOf(map.get("image_url"));
        String miao = String.valueOf(map.get("miao"));
        int cid=commodityService.addCommodity(commodity_name,group_id,image_url,des,price,inventory,miao);
        return String.valueOf(cid);
    }

    @RequestMapping("/UpdateCommodity")
    @ResponseBody
    public void UpdateCommodity(@RequestBody Map map) throws SchedulerException, ParseException {
        String tmp1 = String.valueOf(map.get("commodity_id"));
        int id = Integer.parseInt(tmp1);
        String tmp2 = String.valueOf(map.get("type"));
        int type = Integer.parseInt(tmp2);
        String inner = String.valueOf(map.get("inner"));
        commodityService.updateCommodity(id, type, inner);
    }

    @RequestMapping("/DeleteCommodity")
    @ResponseBody
    public void DeleteCommodity(@RequestBody Map map) throws SchedulerException, ParseException {
        String tmp1 = String.valueOf(map.get("group_id"));
        int g_id = Integer.parseInt(tmp1);
        String tmp2 = String.valueOf(map.get("commodity_id"));
        int c_id = Integer.parseInt(tmp2);
        commodityService.deleteCommodity(g_id, c_id);
    }

    @RequestMapping("/getCommodityInfoByCommodity_id")
    @ResponseBody
    public String getCommodityByCommodity_id(@RequestBody Map map){
        String tmp=String.valueOf(map.get("commodity_id"));
        int commodity_id=Integer.parseInt(tmp);
        return commodityService.getCommodityByCommodity_id(commodity_id);
    }
}
