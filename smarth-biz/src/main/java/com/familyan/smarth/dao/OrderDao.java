package com.familyan.smarth.dao;

import com.familyan.smarth.domain.Order;
import com.familyan.smarth.domain.OrderDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderDao {

    @Select("SELECT * FROM CK_ORDER WHERE id=#{id}")
    Order findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_ORDER WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    Order findByIds(List<Integer> ids);

    @Insert("INSERT INTO CK_ORDER (id, member_id, checker_id, packet_id, price, package_content, checkup_time, city, address, status, gmt_create, gmt_modify ) " +
            "VALUES (#{id}, #{memberId}, #{checkerId}, #{packetId}, #{price}, #{packageContent}, #{checkupTime}, #{city}, #{address}, #{status}, now(), #{gmtModify} )")
    int insert(Order order);

    int update(Order order);

    int countByParams(@Param("params")OrderDTO order);

    List<Order> findByParams(@Param("params")OrderDTO order, @Param("start")Integer start, @Param("limit")Integer limit, @Param("orderBy")String orderBy);

    @Select("SELECT * FROM CK_ORDER WHERE out_trade_no=#{outTradeNo}")
    Order findByOutTradeNo(String outTradeNo);



}