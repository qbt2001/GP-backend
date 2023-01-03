package com.example.grouppurchase_backend.Controller;

import com.example.grouppurchase_backend.Service.GroupService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;


@RestController
public class GroupController {
    @Autowired
    GroupService groupService;

    @RequestMapping("/getAllGroupInfo")
    @ResponseBody
    public String getAllGroups() {

        return groupService.getAllGroups();
    }

    @RequestMapping("/getOneGroupInfo")
    @ResponseBody
    public String getGroupByGroup_id(@RequestBody Map map) {
        Integer group_id = Integer.valueOf(String.valueOf(map.get("group_id")));
        return groupService.getGroupByGroup_id(group_id);
    }

    @RequestMapping("/CreateGroup")
    @ResponseBody
    public String CreateGroup(@RequestBody Map map) throws ParseException, SchedulerException {
        String group_name = String.valueOf(map.get("group_name"));
        String des = String.valueOf(map.get("des"));
        String post = String.valueOf(map.get("post"));
        String begin_time = String.valueOf(map.get("begin_time"));
        String end_time = String.valueOf(map.get("end_time"));
        String head_id = String.valueOf(map.get("head_id"));

        return String.valueOf(groupService.addGroup(group_name, des, post, begin_time, end_time, head_id));
    }

    @RequestMapping("/DeleteGroup")
    @ResponseBody
    public void DeleteGroup(@RequestBody Map map) throws SchedulerException, ParseException {
        String group_id = String.valueOf(map.get("group_id"));
        groupService.deleteGroup(Integer.valueOf(group_id));
    }

    @RequestMapping("/FinishGroup")
    @ResponseBody
    public String FinishGroup(@RequestBody Map map) throws SchedulerException, ParseException {
        String group_id = String.valueOf(map.get("group_id"));
        String endtime = String.valueOf(map.get("end_time"));
        return groupService.finishGroup(Integer.valueOf(group_id), endtime);
    }

    @RequestMapping("/GetLaunched")
    @ResponseBody
    public String getLaunched(@RequestBody Map map) {
        String tmp = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(tmp);
        return groupService.getGroupsByHead_id(user_id);
    }

    @RequestMapping("/GetJoined")
    @ResponseBody
    public String getJoined(@RequestBody Map map) {
        String tmp = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(tmp);
        return groupService.getGroupsByFollower_id(user_id);
    }

    @RequestMapping("/SearchGroup")
    @ResponseBody
    public String SearchGroup(@RequestBody Map map) {
        String ByHead = String.valueOf(map.get("ByHead"));
        String ByGName = String.valueOf(map.get("ByGName"));
        String KeyWord = String.valueOf(map.get("KeyWord"));

        if (Objects.equals(ByHead, "1")) {
            return groupService.getGroupsByHead_name(KeyWord);
        } else {
            return groupService.getGroupsByGroup_name(KeyWord);
        }
    }

    @RequestMapping("/UpdateGroup")
    @ResponseBody
    public String UpdateGroup(@RequestBody Map map) throws SchedulerException, ParseException {
        String tmp1 = String.valueOf(map.get("group_id"));
        int id = Integer.parseInt(tmp1);
        String tmp2 = String.valueOf(map.get("type"));
        int type = Integer.parseInt(tmp2);
        String inner = String.valueOf(map.get("inner"));
        if (groupService.updateGroup(id, type, inner))
            return "1";
        else
            return "0";
    }

    @RequestMapping("/CheckHead")
    @ResponseBody
    public String checkHead(@RequestBody Map map) {
        String tmp1 = String.valueOf(map.get("id"));
        int id = Integer.parseInt(tmp1);
        String tmp2 = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(tmp2);
        if (groupService.checkHead(id, user_id)) {
            return "1";
        } else {
            return "0";
        }
    }
    @RequestMapping("/GetLink")
    @ResponseBody
    public String CreateLink(@RequestBody Map map) {
        String tmp = String.valueOf(map.get("group_id"));
        int group_id = Integer.parseInt(tmp);
        System.out.println(groupService.createLinkByGroup_id(group_id));
        return groupService.createLinkByGroup_id(group_id);
    }

    @RequestMapping("/GetHeadName")
    @ResponseBody
    public String getHeadName(@RequestBody Map map) {
        String tmp1 = String.valueOf(map.get("group_id"));
        int id = Integer.parseInt(tmp1);
        return groupService.getHeadName(id);
    }

}

