package com.example.grouppurchase_backend.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.grouppurchase_backend.Dao.CommodityDao;
import com.example.grouppurchase_backend.Dao.OrderDao;
import com.example.grouppurchase_backend.Entity.Commodity;
import com.example.grouppurchase_backend.Entity.Order;
import com.example.grouppurchase_backend.Service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    CommodityDao commodityDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public int addCommodity(String commodity_name, String group_id, String image_url, String des, String price, String inventory, String miao) {
        return commodityDao.addCommodity(commodity_name, Integer.parseInt(group_id), image_url, des, Integer.parseInt(price), Integer.parseInt(inventory), Boolean.parseBoolean(miao));
    }

    @Override
    public void updateCommodity(Integer id, Integer type, String inner) {
        commodityDao.reviseCommodity(id, type, inner);
    }

    @Override
    public void deleteCommodity(Integer g_id, Integer c_id) {
        List<Order> orders = orderDao.getAllCommodityOrders(c_id);
        for (int i = 0; i < orders.size(); i++) {
            int or = orders.get(i).getOrder_id();
            orderDao.deleteOrder(or);
        }
        commodityDao.deleteCommodity(c_id);
    }

    @Override
    public String getCommodityByCommodity_id(int commodity_id) {
        Commodity target = commodityDao.getCommodityByCommodity_id(commodity_id);
        ArrayList<String> commodityInfo = new ArrayList<>();
        commodityInfo.add(target.getCommodity_name());
        commodityInfo.add(target.getImage_url());
        commodityInfo.add(target.getDes());
        commodityInfo.add(String.valueOf(target.getPrice()));
        commodityInfo.add(String.valueOf(target.getInventory()));
        commodityInfo.add(String.valueOf(target.isMiao()));
        return JSON.toJSONString(commodityInfo, SerializerFeature.BrowserCompatible);
    }

    @Override
    public String getCommodityPic() {
        List<Commodity> list = commodityDao.getAllCommodities();
        ArrayList<String> con = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Commodity c = list.get(i);
            con.add(String.valueOf(c.getCommodity_id()));
            con.add(c.getImage_url());
        }
        return JSON.toJSONString(con, SerializerFeature.BrowserCompatible);
    }

}