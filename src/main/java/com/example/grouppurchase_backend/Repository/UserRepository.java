package com.example.grouppurchase_backend.Repository;

import com.example.grouppurchase_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(nativeQuery = true, value = "select * from user where user.username = :username")
    User findByUser_name(@Param("username") String username);

    @Query(nativeQuery = true, value = "select * from user  where user.user_id = :user_id")
    User findByUser_id(@Param("user_id") int user_id);

    @Query(value = "from User where user_name like concat('%',:keyword,'%')")
    List<User> getUsersByUser_name(@Param("keyword") String keyword);
}
