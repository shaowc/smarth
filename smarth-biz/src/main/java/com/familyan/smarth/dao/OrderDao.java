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
            "<foreach collection='list' open='(' close=')' index='index' item='item' sepator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    Order findByIds(List<Integer> ids);

    @Insert("INSERT INTO CK_ORDER (id, member_id, checker_id, packet_id, package_content, checkup_time, province_id, city_id, county_id, address, status, out_trade_no, prepay_id, gmt_prepay_id_valid, gmt_create, gmt_modify ) " +
            "VALUES (#{id}, #{memberId}, #{checkerId}, #{packetId}, #{packageContent}, #{checkupTime}, #{provinceId}, #{cityId}, #{countyId}, #{address}, #{status}, #{outTradeNo}, #{prepayId}, #{gmtPrepayIdValid}, now(), #{gmtModify} )")
    int insert(Order order);

    int update(Order order);

    int countByParams(@Param("params")OrderDTO order);

    List<Order> findByParams(@Param("params")OrderDTO order, @Param("start")Integer start, @Param("limit")Integer limit, @Param("orderBy")String orderBy);



}