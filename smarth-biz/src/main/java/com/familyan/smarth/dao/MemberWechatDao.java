/**
 * Auto generated
*/
package com.familyan.smarth.dao;

import com.familyan.smarth.domain.MemberWechatDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.Collection;
import java.util.List;

public interface MemberWechatDao {
    /**
     * 按id 查询
     * @param id
    */
    @Select("SELECT * FROM MEMBER_WECHAT WHERE id = #{id}")
    MemberWechatDO findByPrimaryKey(Integer id);

    @Select("<script>" +
            "SELECT * FROM MEMBER_WECHAT WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' separator=','>" +
            "     #{item}" +
            "</foreach>" +
            "</script>")
    List<MemberWechatDO> findByPrimaryKeys(List<Integer> ids);

    @Select("SELECT * FROM MEMBER_WECHAT limit #{start},#{limit}")
    List<MemberWechatDO> findByPage(@Param("start") int start, @Param("limit") int limit);

    @Select("SELECT count(1) FROM MEMBER_WECHAT")
    Long countAll();

    /**
     * 写入数据，不同的表可以有不同的要求增减字段
     */
    @Insert("INSERT INTO MEMBER_WECHAT (`app`, `open_id`, `member_id`, `subscribe`, `nick_name`, `sex`, `city`, `province`, `country`, `language`, `head_img_url`, `subscribe_time`, `remark` )"+
            " VALUES "+
            " ( #{app}, #{openId}, #{memberId}, #{subscribe}, #{nickName}, #{sex}, #{city}, #{province}, #{country}, #{language}, #{headImgUrl}, #{subscribeTime}, #{remark} ) ")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false,statement = "SELECT LAST_INSERT_ID()",resultType = Integer.class)
    void insert(MemberWechatDO obj);

    @Select("SELECT * FROM MEMBER_WECHAT WHERE `app` = #{app} and `member_id`=#{memberId}")
    MemberWechatDO findByMemberId(@Param("app") String app, @Param("memberId") Long memberId);

    @Select("SELECT * FROM MEMBER_WECHAT WHERE `app` = #{app} and `open_id`=#{openId}")
    MemberWechatDO findByOpenId(@Param("app") String app, @Param("openId") String openId);

    List<MemberWechatDO> findByOpenIds(@Param("app") String app, @Param("openIds") Collection<String> openIds);

    int updateById(MemberWechatDO obj);
}