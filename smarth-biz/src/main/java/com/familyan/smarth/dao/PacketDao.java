package com.familyan.smarth.dao;

import com.familyan.smarth.domain.Packet;
import com.familyan.smarth.domain.PacketDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PacketDao {

    @Select("SELECT * FROM CK_PACKET WHERE id=#{id}")
    Packet findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_PACKET WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<Packet> findByIds(List<Integer> ids);

    @Insert("INSERT INTO CK_PACKET (id, name, description, content, price, gmt_create, gmt_modified ) " +
            "VALUES (#{id}, #{name}, #{description}, #{content}, #{price}, now(), #{gmtModified} )")
    int insert(Packet pkg);

    int update(Packet pkg);

    @Select("SELECT COUNT(1) FROM CK_PACKET ")
    int countByParams(PacketDTO packet);

    List<Packet> findByParams(@Param("params")PacketDTO packet, @Param("start")Integer start, @Param("limit")Integer limit, @Param("orderBy")String orderBy);


}