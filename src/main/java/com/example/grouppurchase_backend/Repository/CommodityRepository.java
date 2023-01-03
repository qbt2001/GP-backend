package com.example.grouppurchase_backend.Repository;

import com.example.grouppurchase_backend.Entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommodityRepository extends JpaRepository<Commodity, Integer> {
    @Query(nativeQuery = true,value = "select * from commodity where commodity.group_id = :group_id")
    List<Commodity> getCommoditiesByGroup_id(@Param("group_id") int group_id);
    @Query(nativeQuery = true,value = "select * from commodity where commodity.commodity_id = :commodity_id")
    Commodity getCommodityByCommodity_id(@Param("commodity_id") int commodity_id);
}
