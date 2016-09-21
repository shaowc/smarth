package com.familyan.smarth.dao;

import com.familyan.smarth.domain.MemberChecker;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberCheckerDao {

    @Select("SELECT * FROM CK_MEMBER_CHECKER WHERE id=#{id}")
    MemberChecker findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_MEMBER_CHECKER WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<MemberChecker> findByIds(List<Integer> ids);

    @Select("SELECT * FROM CK_MEMBER_CHECKER WHERE member_id=#{memberId} AND status=1")
    List<MemberChecker> findByMemberId(Long memberId);

    @Select("SELECT * FROM CK_MEMBER_CHECKER WHERE member_id=#{memberId} AND checker_id=#{checkerId} AND status=1")
    MemberChecker findByMemberIdAndCheckerId(@Param("memberId")Long memberId, @Param("checkerId")Long checkerId);

    @Insert("INSERT INTO CK_MEMBER_CHECKER (id, member_id, checker_id, status, gmt_create, gmt_modify ) " +
            "VALUES (#{id}, #{memberId}, #{checkerId}, #{status}, now(), #{gmtModify} )")
    int insert(MemberChecker memberChecker);

    int update(MemberChecker memberChecker);


}