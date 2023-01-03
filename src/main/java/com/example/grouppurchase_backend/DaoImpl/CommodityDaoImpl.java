package com.example.grouppurchase_backend.DaoImpl;

import com.example.grouppurchase_backend.Dao.CommodityDao;
import com.example.grouppurchase_backend.Entity.Commodity;
import com.example.grouppurchase_backend.Entity.Group;
import com.example.grouppurchase_backend.Repository.CommodityRepository;
import com.example.grouppurchase_backend.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommodityDaoImpl implements CommodityDao {
    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    GroupRepository groupRepository;

    @Override
    public int addCommodity(String commodity_name, int group_id, String image_url, String des, int price, int inventory, boolean miao) {
        Commodity c = new Commodity();
        Group g = groupRepository.getGroupByGroup_id(group_id);
        c.setMiao(miao);
        c.setCommodity_name(commodity_name);
        c.setInventory(inventory);
        c.setPrice(price);
        c.setImage_url(image_url);
        c.setDes(des);
        c.setGroup(g);
        commodityRepository.save(c);
        return c.getCommodity_id();
    }

    @Override
    public void deleteCommodity(int commodity_id) {
        commodityRepository.deleteById(commodity_id);
    }

    /*
     *type 为修改的类型，content为新的内容
     * type = 0修改image_url,type = 1修改commodity_name,
     * type = 2修改price,type = 3修改inventory,
     * type = 4修改des，type = 5修改miao
     */
    @Override
    public void reviseCommodity(int commodity_id, int type, String content) {
        if (!getOne(commodity_id).isPresent())
            return;
        Commodity target = getOne(commodity_id).get();
        switch (type) {
            case 0:
                target.setImage_url(content);
                break;
            case 1:
                target.setCommodity_name(content);
                break;
            case 2:
                target.setPrice(Integer.parseInt(content));
                break;
            case 3:
                target.setInventory(Integer.parseInt(content));
                break;
            case 4:
                target.setDes(content);
                break;
            case 5:
                target.setMiao(Boolean.parseBoolean(content));
                break;
            default:
        }
        commodityRepository.save(target);
    }

    @Override
    public Optional<Commodity> getOne(int commodity_id) {
        return commodityRepository.findById(commodity_id);
    }

    @Override
    public Commodity getCommodityByCommodity_id(int commodity_id) {
        return commodityRepository.getCommodityByCommodity_id(commodity_id);
    }

    @Override
    public List<Commodity> getAllCommodities() {
        return commodityRepository.findAll();
    }


    @Override
    public List<Commodity> getCommoditiesByGroup_id(int group_id) {
        return commodityRepository.getCommoditiesByGroup_id(group_id);
    }


}