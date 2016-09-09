package com.familyan.smarth.dao;

import com.familyan.smarth.domain.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemDao {

    @Select("SELECT * FROM CK_ITEM WHERE id=#{id}")
    Item findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_ITEM WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' sepator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    Item findByIds(List<Integer> ids);

    @Insert("INSERT INTO CK_ITEM (id, packet_id, name, gmt_create, gmt_modify ) " +
            "VALUES (#{id}, #{packetId}, #{name}, #{gmtCreate}, #{gmtModify} )")
    int insert(Item item);

    int update(Item item);


}