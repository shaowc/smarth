package com.familyan.smarth.dao;

import com.familyan.smarth.domain.Checker;
import com.familyan.smarth.domain.CheckerDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CheckerDao {

    @Select("SELECT * FROM CK_CHECKER WHERE id=#{id}")
    Checker findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_CHECKER WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<Checker> findByIds(List<Integer> ids);

    @Insert("INSERT INTO CK_CHECKER (id, member_id, name, mobile, gender, birthday, identify_pic, identify_pic1, identify_pic2, qualification_pic, description, longitude, latitude, gmt_create, gmt_modify ) " +
            "VALUES (#{id}, #{memberId}, #{name}, #{mobile}, #{gender}, #{birthday}, #{identifyPic}, #{identifyPic1}, #{identifyPic2}, #{qualificationPic}, #{description}, #{longitude}, #{latitude}, now(), #{gmtModify} )")
    int insert(Checker checker);

    int update(Checker checker);

    @Select("SELECT * FROM CK_CHECKER WHERE member_id=#{memberId}")
    Checker findByMemberId(Long memberId);

    @Select("<script>" +
            "SELECT * FROM CK_CHECKER WHERE member_id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<Checker> findByMemberIds(List<Long> memberIds);

    int countByParams(@Param("params")CheckerDTO checkerDTO);

    List<Checker> findByParams(@Param("params")CheckerDTO checkerDTO, @Param("start")Integer start, @Param("limit")Integer limit );

}