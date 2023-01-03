package com.example.grouppurchase_backend.DaoImpl;

import com.example.grouppurchase_backend.Dao.OrderDao;
import com.example.grouppurchase_backend.Entity.Commodity;
import com.example.grouppurchase_backend.Entity.Group;
import com.example.grouppurchase_backend.Entity.Order;
import com.example.grouppurchase_backend.Entity.User;
import com.example.grouppurchase_backend.Repository.CommodityRepository;
import com.example.grouppurchase_backend.Repository.GroupRepository;
import com.example.grouppurchase_backend.Repository.OrderRepository;
import com.example.grouppurchase_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    CommodityRepository commodityRepository;

    public int checkForTime(String begin, String end, String want) {
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

    @Override
    public String createOrder(int group_id, int commodity_id, int user_id, int commodity_amount, String pay_time) {
        Group curGroup = groupRepository.getGroupByGroup_id(group_id);
        Commodity curCommodity = commodityRepository.getCommodityByCommodity_id(commodity_id);
        User curUser = userRepository.findByUser_id(user_id);
        User headUser = userRepository.findByUser_id(curGroup.getHead());
        int userBalance = curUser.getBalance();
        int unit_price = curCommodity.getPrice();
        int money = unit_price * commodity_amount;
        if (userBalance < money)
            return "Money";
        int commodityInventory = curCommodity.getInventory();
        boolean commodityMiao = curCommodity.isMiao();
        if (commodityInventory < commodity_amount && !commodityMiao)
            return "Inventory";
        String start = curGroup.getBegin_time();
        String end = curGroup.getEnd_time();
        int checkTimeRes = checkForTime(start, end, pay_time);
        if (checkTimeRes != 0)
            return "Time";
        curUser.setBalance(userBalance - money);           //钱的流动
        headUser.setBalance(headUser.getBalance() + money);
        if (!commodityMiao) curCommodity.setInventory(commodityInventory - commodity_amount);//非秒杀情况下，库存减少
        if (!Objects.equals(headUser, curUser)) {
            String follower = curGroup.getFollower();
            String[] follow = follower.split(",");
            boolean meet = false;
            for (String s : follow) {
                if (Objects.equals(s, String.valueOf(user_id))) {
                    meet = true;
                    break;
                }
            }
            if (!meet) {
                follower = follower + user_id + ",";
                curGroup.setFollower(follower);
            }
        }
        Order ord = new Order();
        ord.setGroup_id(group_id);
        ord.setCommodity_id(commodity_id);
        ord.setUser_id(user_id);
        ord.setPay_time(pay_time);
        ord.setCommodity_amount(commodity_amount);
        String post = groupRepository.getGroupByGroup_id(group_id).getPost();
        ord.setPost(post);
        ord.setMoney(money);
        ord.setIspaid(1);
        orderRepository.save(ord);
        return "success";
    }

    @Override
    public void deleteOrder(int order_id) {
        Order order = getOne(order_id);
        int user_id = order.getUser_id();
        int money = order.getMoney();
        int group_id = order.getGroup_id();
        Group group = groupRepository.getGroupByGroup_id(group_id);
        int head_id = group.getHead();
        User head = userRepository.findByUser_id(head_id);
        User user = userRepository.findByUser_id(user_id);
        int old_user = user.getBalance();
        old_user += money;
        user.setBalance(old_user);
        int old_head = head.getBalance();
        old_head -= money;
        head.setBalance(old_head);
        userRepository.save(user);
        userRepository.save(head);
        orderRepository.delete(getOne(order_id));
    }

    @Override
    public Order getOne(int order_id) {
        return orderRepository.getOrderByOrder_id(order_id);
    }

    @Override
    public List<Order> getAllGroupOrders(int group_id) {
        return orderRepository.getOrderByGroup_id(group_id);
    }

    @Override
    public List<Order> getAllUserOrders(int user_id) {
        return orderRepository.getOrderByUser_id(user_id);
    }

    @Override
    public List<Order> getAllCommodityOrders(int c_id) {
        return orderRepository.getOrderByCommodity_id(c_id);
    }

    @Override
    public boolean drawbackOrder(int order_id) {
        Order curOrder = getOne(order_id);
        if (curOrder == null) return false;

        Commodity curCom = commodityRepository.getCommodityByCommodity_id(curOrder.getCommodity_id());
        int orderAmount = curOrder.getCommodity_amount();
        curCom.setInventory(curCom.getInventory() + orderAmount);
        deleteOrder(order_id);
        return true;
    }
}