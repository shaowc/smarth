package com.familyan.smarth.dao;

import com.familyan.smarth.domain.MemberPackage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberPackageDao {

    @Select("SELECT * FROM CK_MEMBER_PACKAGE WHERE id=#{id}")
    MemberPackage findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_MEMBER_PACKAGE WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' sepator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    MemberPackage findByIds(List<Integer> ids);

    @Insert("INSERT INTO CK_MEMBER_PACKAGE (id, package_id, member_id, status, gmt_create, gmt_modify ) " +
            "VALUES (#{id}, #{packageId}, #{memberId}, #{status}, #{gmtCreate}, #{gmtModify} )")
    int insert(MemberPackage memberPackage);

    int update(MemberPackage memberPackage);


}