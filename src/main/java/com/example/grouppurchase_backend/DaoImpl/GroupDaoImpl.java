package com.example.grouppurchase_backend.DaoImpl;

import com.example.grouppurchase_backend.Dao.GroupDao;
import com.example.grouppurchase_backend.Entity.Group;
import com.example.grouppurchase_backend.Entity.User;
import com.example.grouppurchase_backend.Repository.GroupRepository;
import com.example.grouppurchase_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class GroupDaoImpl implements GroupDao {
    @Autowired
    private GroupRepository GroupRepository;

    @Autowired
    private UserRepository UserRepository;


//    @Override
//    public Date parseTime(String time) throws ParseException {
//        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//        return fmt.parse(time);
//    }

    @Override
    public Group getGroupByGroup_id(Integer group_id) {
        return GroupRepository.getGroupByGroup_id(group_id);
    }

    @Override
    public List<Group> getGroupsByHead_id(Integer head_id) {
        return GroupRepository.getGroupByHead_id(head_id);
    }

    @Override
    public List<Group> getGroupsByFollower_id(Integer follower_id) {
        List<Group> all = getAllGroups();
        List<Group> wanted = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            String[] follow = all.get(i).getFollower().split(",");
            for (String s : follow) {
                if (!Objects.equals(s, "")) {
                    if (Integer.parseInt(s) == follower_id) {
                        wanted.add(all.get(i));
                        break;
                    }
                }
            }
        }
        return wanted;
    }

    @Override
    public List<Group> getAllGroups() {
        return GroupRepository.findAll();
    }

    @Override
    public int addGroup(String group_name, String des, String post, String begin_time, String end_time, int head){
        Group g = new Group();
        g.setGroup_name(group_name);
        g.setNumber(0);
        g.setDes(des);
        g.setPost(post);
        g.setBegin_time(begin_time);
        g.setEnd_time(end_time);
        g.setHead(head);
        g.setFollower("");
        GroupRepository.save(g);
        return g.getGroup_id();
    }

    @Override
    public void deleteGroup(Integer group_id) {
        GroupRepository.deleteById(group_id);
    }

    @Override
    public List<Group> getGroupsByHead_name(String KeyWord) {
        List<User> heads = UserRepository.getUsersByUser_name(KeyWord);
        List<Group> found = new ArrayList<>();
        for (int i = 0; i < heads.size(); i++) {
            Integer headid = heads.get(i).getUser_id();
            found.addAll(GroupRepository.getGroupByHead_id(headid));
        }
        return found;
    }

    @Override
    public List<Group> getGroupsByGroup_name(String KeyWord) {
        return GroupRepository.getGroupsByGroup_name(KeyWord);
    }

    @Override
    public void updateGroup(Integer id, Integer type, String inner) {
        Group group = getGroupByGroup_id(id);
        switch (type) {
            case 0:
                group.setGroup_name(inner);
                break;
            case 1:
                group.setDes(inner);
                break;
            case 2:
                group.setPost(inner);
                break;
            case 3:
                group.setBegin_time(inner);
                break;
            case 4:
                group.setEnd_time(inner);
                break;
            default:
                break;
        }
        GroupRepository.save(group);
    }

    @Override
    public int finishGroup(Integer id, String endtime) {
        Group group=GroupRepository.getGroupByGroup_id(id);
        group.setEnd_time(endtime);
        GroupRepository.save(group);
        return group.getGroup_id();
    }

    @Override
    public String createLinkByGroup_id(Integer group_id){
        Group g ;
        g = GroupRepository.getGroupByGroup_id(group_id);
        String id = String.valueOf(group_id);
        g.setLink("groupPurchase://GroupPurchaseInfo?id="+id);
        return g.getLink();
    }
}