package com.familyan.smarth.dao;

import com.familyan.smarth.domain.LoginMember;
import com.familyan.smarth.domain.MemberLocation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MemberLocationDao {

    @Select("SELECT * FROM MEMBER_LOCATION WHERE member_id=#{memberId}")
    MemberLocation findByMemberId(Long memberId);

    @Select("<script>" +
            "SELECT * FROM MEMBER_LOCATION WHERE member_id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<MemberLocation> findByMemberIds(List<Long> memberIds);

    @Insert("INSERT INTO MEMBER_LOCATION (id, member_id, province, city, county, address, longitude, latitude, type, gmt_create, gmt_modify ) " +
            "VALUES (#{id}, #{memberId}, #{province}, #{city}, #{county}, #{address}, #{longitude}, #{latitude}, #{type}, now(), #{gmtModify} )")
    int insert(MemberLocation memberLocation);

    int update(MemberLocation memberLocation);

    @Update("UPDATE MEMBER_LOCATION SET type=#{type} WHERE member_id=#{memberId}")
    int updateType(@Param("memberId")Long memberId, @Param("type")Integer type);


}