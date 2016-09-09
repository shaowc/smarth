package com.familyan.smarth.dao;

import com.familyan.smarth.domain.PacketDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PacketDao {

    @Select("SELECT * FROM CK_PACKET WHERE id=#{id}")
    PacketDO findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_PACKET WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' sepator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    PacketDO findByIds(List<Integer> ids);

    @Insert("INSERT INTO CK_PACKET (id, name, description, price, gmt_create, gmt_modified ) " +
            "VALUES (#{id}, #{name}, #{description},#{price}, #{gmtCreate}, #{gmtModified} )")
    int insert(PacketDO pkg);

    int update(PacketDO pkg);


}