package com.example.grouppurchase_backend.Repository;

import com.example.grouppurchase_backend.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,Integer> {
    @Query(nativeQuery = true,value="select * from `group` where `group`.group_id = :group_id")
    Group getGroupByGroup_id(@Param("group_id")Integer group_id);

    @Query(nativeQuery = true,value="select * from `group` where `group`.head_id = :head_id")
    List<Group> getGroupByHead_id(@Param("head_id")Integer head_id);

    @Modifying
    @Query(value = "update Group set des = ?2,post = ?3,begin_time = ?4,end_time = ?5 where group_name = ?1")
    void modifyGroupByGroup_id(int group_id, String des, String post, String begin_time, String end_time);

    @Query(value = "from Group where group_name like concat('%',:keyword,'%')")
    List<Group> getGroupsByGroup_name(@Param("keyword") String keyword);

}

