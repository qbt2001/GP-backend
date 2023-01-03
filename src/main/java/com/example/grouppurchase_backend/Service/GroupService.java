package com.example.grouppurchase_backend.Service;

import com.example.grouppurchase_backend.Entity.Group;
import org.quartz.SchedulerException;

import java.text.ParseException;
import java.util.List;

public interface GroupService {

    String getGroupByGroup_id(Integer group_id);

    int addGroup(String group_name, String des, String post, String begin_time, String end_time, String head_id)
            throws ParseException, SchedulerException;

    void deleteGroup(Integer group_id) throws SchedulerException, ParseException;

    String finishGroup(Integer group_id, String want_end) throws SchedulerException, ParseException;

    String getAllGroups();

    String getGroupsByHead_id(Integer head_id);

    String getGroupsByFollower_id(Integer follower_id);

    String changeToString(List<Group> all);

    String getGroupsByHead_name(String KeyWord);

    String getGroupsByGroup_name(String KeyWord);

    boolean updateGroup(Integer id, Integer type, String inner) throws SchedulerException, ParseException;

    boolean checkHead(Integer id, Integer user_id);

    String createLinkByGroup_id(Integer group_id);

    String getHeadName(Integer id);

}