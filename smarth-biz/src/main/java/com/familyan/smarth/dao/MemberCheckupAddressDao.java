package com.familyan.smarth.dao;

import com.familyan.smarth.domain.MemberCheckupAddress;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberCheckupAddressDao {

    @Select("SELECT * FROM MEMBER_CHECKUP_ADDRESS WHERE id=#{id}")
    MemberCheckupAddress findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM MEMBER_CHECKUP_ADDRESS WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    MemberCheckupAddress findByIds(List<Integer> ids);

    @Insert("INSERT INTO MEMBER_CHECKUP_ADDRESS (id, member_id, province_id, city_id, county_id, address, gmt_create, gmt_modify ) " +
            "VALUES (#{id}, #{memberId}, #{provinceId}, #{cityId}, #{countyId}, #{address}, #{gmtCreate}, #{gmtModify} )")
    int insert(MemberCheckupAddress memberCheckupAddress);

    int update(MemberCheckupAddress memberCheckupAddress);


}