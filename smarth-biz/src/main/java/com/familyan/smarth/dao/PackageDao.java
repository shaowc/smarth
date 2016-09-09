package com.familyan.smarth.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PackageDao {

    @Select("SELECT * FROM CK_PACKAGE WHERE id=#{id}")
    Package findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_PACKAGE WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' sepator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    Package findByIds(List<Integer> ids);

    @Insert("INSERT INTO CK_PACKAGE (id, name, price, gmt_create, gmt_modified ) " +
            "VALUES (#{id}, #{name}, #{price}, #{gmtCreate}, #{gmtModified} )")
    int insert(Package pkg);

    int update(Package pkg);


}