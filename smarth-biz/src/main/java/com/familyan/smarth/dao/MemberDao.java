package com.familyan.smarth.dao;

import com.familyan.smarth.domain.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface MemberDao {


    @Insert("INSERT INTO MEMBER (id, user_name, real_name, password, avatar, gender, birthday, mobile, email, weixin_id, province_id, city_id, county_id, longitude, latitude, features, status, gmt_create, gmt_modified ) " +
            "VALUES (#{id}, #{userName}, #{realName}, #{password}, #{avatar}, #{gender}, #{birthday}, #{mobile}, #{email}, #{weixinId}, #{provinceId}, #{cityId}, #{countyId}, #{longitude}, #{latitude}, #{features}, #{status}, #{gmtCreate}, #{gmtModified} )")
    int insert(Member member);

    @Select("SELECT * FROM MEMBER WHERE id = #{id}")
    Member findById(Long memberId);

    List<Member> findByIds(List<Long> ids);

    int update(Member member);

    Member findByProperty(Member query);

    @Delete("DELETE FROM MEMBER WHERE id = #{id}")
    void delete(Long id);

    /**
     * 根据名字模糊查询
     * @param userName
     * @return
     */
    List<Member> findByLikeName(String userName);

}