package com.familyan.smarth.dao;

import com.familyan.smarth.domain.MemberPacket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberPacketDao {

    @Select("SELECT * FROM CK_MEMBER_PACKET WHERE id=#{id}")
    MemberPacket findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_MEMBER_PACKET WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' sepator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    MemberPacket findByIds(List<Integer> ids);

    @Insert("INSERT INTO CK_MEMBER_PACKET (id, packet_id, member_id, status, gmt_create, gmt_modify ) " +
            "VALUES (#{id}, #{packetId}, #{memberId}, #{status}, #{gmtCreate}, #{gmtModify} )")
    int insert(MemberPacket memberPacket);

    int update(MemberPacket memberPacket);


}