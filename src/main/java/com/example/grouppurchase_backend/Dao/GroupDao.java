package com.example.grouppurchase_backend.Dao;

import com.example.grouppurchase_backend.Entity.Group;

import java.text.ParseException;
import java.util.List;


public interface GroupDao {

    //Date parseTime(String time) throws ParseException;

    Group getGroupByGroup_id(Integer group_id);

    List<Group> getGroupsByHead_id(Integer head_id);

    List<Group> getGroupsByFollower_id(Integer follower_id);

    List<Group> getAllGroups();

    int addGroup(String group_name, String des, String post, String begin_time, String end_time, int head)throws ParseException;

    void deleteGroup(Integer group_id);

    List<Group> getGroupsByHead_name(String KeyWord);

    List<Group> getGroupsByGroup_name(String KeyWord);

    void updateGroup(Integer id, Integer type, String inner);

    int finishGroup(Integer id,String endtime);

    String createLinkByGroup_id(Integer group_id);

}