package com.example.grouppurchase_backend.ServiceImpl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.grouppurchase_backend.Dao.CommodityDao;
import com.example.grouppurchase_backend.Dao.GroupDao;
import com.example.grouppurchase_backend.Dao.OrderDao;
import com.example.grouppurchase_backend.Dao.UserDao;
import com.example.grouppurchase_backend.Entity.Commodity;
import com.example.grouppurchase_backend.Entity.Group;
import com.example.grouppurchase_backend.Entity.Order;
import com.example.grouppurchase_backend.Entity.User;
import com.example.grouppurchase_backend.Service.GroupService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {


    @Autowired
    GroupDao groupDao;

    @Autowired
    CommodityDao commodityDao;

    @Autowired
    UserDao userDao;

    @Autowired
    OrderDao orderDao;

    public boolean timeIsNotEqual(String begin, String end) {
        String[] b = begin.split("-");
        String[] bb = b[2].split(" ");
        String[] bbb = bb[1].split(":");
        String be = b[0] + b[1] + bb[0] + bbb[0] + bbb[1];
        String[] e = end.split("-");
        String[] ee = e[2].split(" ");
        String[] eee = ee[1].split(":");
        String en = e[0] + e[1] + ee[0] + eee[0] + eee[1];
        return (be.compareTo(en) > 0);
    }

    public int timeCanFinish(String begin, String end, String want) {
        String[] b = begin.split("-");
        String[] bb = b[2].split(" ");
        String[] bbb = bb[1].split(":");
        String be = b[0] + b[1] + bb[0] + bbb[0] + bbb[1];
        String[] e = end.split("-");
        String[] ee = e[2].split(" ");
        String[] eee = ee[1].split(":");
        String en = e[0] + e[1] + ee[0] + eee[0] + eee[1];
        String[] w = want.split("-");
        String[] ww = w[2].split(" ");
        String[] www = ww[1].split(":");
        String wa = w[0] + w[1] + ww[0] + www[0] + www[1];
        if (be.compareTo(wa) <= 0 && en.compareTo(wa) >= 0) {
            return 0;
        } else if (be.compareTo(wa) > 0) {
            return 1;    //团购尚未开始
        } else {
            return 2;    //团购已经结束
        }
    }

    public String getTime()
    {
        Calendar cal=Calendar.getInstance();
        int y=cal.get(Calendar.YEAR);
        int m=cal.get(Calendar.MONTH)+1;
        int d=cal.get(Calendar.DATE);
        int h=cal.get(Calendar.HOUR_OF_DAY);
        int mi=cal.get(Calendar.MINUTE);
        int s=cal.get(Calendar.SECOND);
        String time=y+"-"+
                (m<10?"0"+m:""+m)+"-"+
                (d<10?"0"+d:""+d)+" "+
                (h<10?"0"+h:""+h)+":"+
                (mi<10?"0"+mi:""+mi)+":"+
                (s<10?"0"+s:""+s);
        return time;
    }

    public List<Group> checkGroupTime(List<Group> all,boolean t)
    {
        List<Group> result=new ArrayList<>();
        String time=getTime();
        for (int i=0;i<all.size();i++)
        {
            Group group=all.get(i);
            int state=timeCanFinish(group.getBegin_time(),group.getEnd_time(),time);
            group.setState(state);
            if (state!=2||t)
                result.add(group);
        }
        return result;
    }

    @Override
    public String getAllGroups() {
        List<Group> all = groupDao.getAllGroups();
        return changeToString(checkGroupTime(all,false));
    }

    @Override
    public String changeToString(List<Group> all) {
        ArrayList<JSONArray> groupJson = new ArrayList<>();
        ArrayList<String> num = new ArrayList<>();
        num.add(String.valueOf(all.size()));
        groupJson.add((JSONArray) JSONArray.toJSON(num));
        for (int i = 0; i < all.size(); i++) {
            Group one = all.get(i);
            List<Commodity> commodities = commodityDao.getCommoditiesByGroup_id(one.getGroup_id());
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(String.valueOf(commodities.size()));//商品的种类 0
            arrayList.add(String.valueOf(one.getGroup_id()));//1
            arrayList.add(one.getGroup_name());//2
            arrayList.add(one.getDes());//3
            arrayList.add(one.getPost());//4
            arrayList.add(String.valueOf(one.getNumber()));//5
            arrayList.add(String.valueOf(one.getHead()));//6
            arrayList.add(one.getBegin_time());//7
            arrayList.add(one.getEnd_time());//8
            arrayList.add(one.getLink());//9

            arrayList.add(String.valueOf(one.getState()));

            for (int j = 0; j < commodities.size(); ++j) {
                Commodity co = commodities.get(j);
                arrayList.add(String.valueOf(co.getCommodity_id()));//10
                arrayList.add(co.getCommodity_name());//11
                arrayList.add(co.getDes());//12
                arrayList.add(co.getImage_url());//13
                arrayList.add(String.valueOf(co.getInventory()));//14
                arrayList.add(String.valueOf(co.getPrice()));//15
                String str;
                if (co.isMiao())
                    str = "1";
                else str = "0";
                arrayList.add(str);//16
            }
            groupJson.add((JSONArray) JSONArray.toJSON(arrayList));
        }
        return JSON.toJSONString(groupJson, SerializerFeature.BrowserCompatible);
    }

    @Override
    public String getGroupsByHead_name(String KeyWord) {
        List<Group> found = groupDao.getGroupsByHead_name(KeyWord);
        return changeToString(checkGroupTime(found,true));
    }

    @Override
    public String getGroupsByGroup_name(String KeyWord) {
        List<Group> found = groupDao.getGroupsByGroup_name(KeyWord);
        return changeToString(checkGroupTime(found,true));
    }

    @Override
    public boolean updateGroup(Integer id, Integer type, String inner) throws SchedulerException, ParseException {
        Group group = groupDao.getGroupByGroup_id(id);
        if (type == 3) {
            String end = group.getEnd_time();
            if (timeIsNotEqual(inner, end)) {
                return false;
            }
        } else if (type == 4) {
            String begin = group.getBegin_time();
            if (timeIsNotEqual(begin, inner)) {
                return false;
            }
        }
        groupDao.updateGroup(id, type, inner);
        if (type == 4) {    //若修改结束时间，应对秒杀商品的结算进行调整
//            miaoService.deleteMiaoJob(id);
//            miaoService.addMiaoJob(id);
        }
        return true;
    }

    @Override
    public boolean checkHead(Integer id, Integer user_id) {
        Group group = groupDao.getGroupByGroup_id(id);
        int head = group.getHead();
        return user_id == head;
    }

    @Override
    public String getHeadName(Integer id) {
        Group group = groupDao.getGroupByGroup_id(id);
        int head = group.getHead();
        User user = userDao.findByUser_id(head);
        return user.getUser_name();
    }

    @Override
    public String getGroupByGroup_id(Integer group_id) {

        Group group = groupDao.getGroupByGroup_id(group_id);
        ArrayList<String> arrayList = new ArrayList<>();
        List<Commodity> commodities = commodityDao.getCommoditiesByGroup_id(group.getGroup_id());
        arrayList.add(String.valueOf(commodities.size()));//商品的种类 0
        arrayList.add(String.valueOf(group.getGroup_id()));//1
        arrayList.add(group.getGroup_name());//2
        arrayList.add(group.getDes());//3
        arrayList.add(group.getPost());//4
        arrayList.add(String.valueOf(group.getNumber()));//5
        arrayList.add(String.valueOf(group.getHead()));//6
        arrayList.add(group.getBegin_time());//7
        arrayList.add(group.getEnd_time());//8
        arrayList.add(group.getLink());//9

        String time=getTime();
        group.setState(timeCanFinish(group.getBegin_time(),group.getEnd_time(),time));
        arrayList.add(String.valueOf(group.getState()));

        for (int j = 0; j < commodities.size(); ++j) {
            Commodity co = commodities.get(j);
            arrayList.add(String.valueOf(co.getCommodity_id()));//10
            arrayList.add(co.getCommodity_name());//11
            arrayList.add(co.getDes());//12
            arrayList.add(co.getImage_url());//13
            arrayList.add(String.valueOf(co.getInventory()));//14
            arrayList.add(String.valueOf(co.getPrice()));//15
            String str;
            if (co.isMiao())
                str = "1";
            else str = "0";
            arrayList.add(str);//16
        }
        return JSON.toJSONString(arrayList, SerializerFeature.BrowserCompatible);
    }

    @Override
    public String getGroupsByHead_id(Integer head_id) {
        List<Group> all = groupDao.getGroupsByHead_id(head_id);
        return changeToString(checkGroupTime(all,true));
    }

    @Override
    public String getGroupsByFollower_id(Integer follower_id) {
        List<Group> all = groupDao.getGroupsByFollower_id(follower_id);
        return changeToString(checkGroupTime(all,true));
    }

    @Override
    public int addGroup(String group_name, String des, String post, String begin_time, String end_time, String head_id)
            throws ParseException, SchedulerException {
        int gid = (groupDao.addGroup(group_name, des, post, begin_time, end_time, Integer.parseInt(head_id)));
//        miaoService.addMiaoJob(gid);
        return gid;
    }

    @Override
    public void deleteGroup(Integer group_id) throws SchedulerException, ParseException {
        List<Order> orders = orderDao.getAllGroupOrders(group_id);
        for (int i = 0; i < orders.size(); i++) {
            int or = orders.get(i).getOrder_id();
            orderDao.deleteOrder(or);
        }
        List<Commodity> commodities = commodityDao.getCommoditiesByGroup_id(group_id);
        for (int i = 0; i < commodities.size(); i++) {
            Commodity co = commodities.get(i);
            int cid = co.getCommodity_id();
            commodityDao.deleteCommodity(cid);
        }
//        miaoService.deleteMiaoJob(group_id);
        groupDao.deleteGroup(group_id);
    }

    @Override
    public String finishGroup(Integer group_id, String want_end) throws SchedulerException, ParseException {
        Group group = groupDao.getGroupByGroup_id(group_id);
        String begintime = group.getBegin_time();
        String endtime = group.getEnd_time();
        int judge = timeCanFinish(begintime, endtime, want_end);
        if (judge == 0) {
            int gid = groupDao.finishGroup(group_id, want_end);
            if (gid != 0) {
//                miaoService.deleteMiaoJob(gid);
//                miaoService.addMiaoJob(gid);
            }
        }
        return String.valueOf(judge);
    }
   @Override
    public String createLinkByGroup_id(Integer group_id){
        return groupDao.createLinkByGroup_id(group_id);
   }
}


