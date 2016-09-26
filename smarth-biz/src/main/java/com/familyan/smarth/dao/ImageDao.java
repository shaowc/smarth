package com.familyan.smarth.dao;

import com.familyan.smarth.domain.ImageDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/25.
 */
public interface ImageDao {

    @Insert("INSERT INTO CK_IMAGE(id, img, gmt_create, gmt_modify) VALUES(#{id}, #{img}, now(), #{gmtModify})")
    void insert(ImageDO imageDO);

    @Select("SELECT * FROM CK_IMAGE WHERE id=#{id}")
    ImageDO findById(Integer id);

    @Select("<script>" +
            "SELECT * FROM CK_IMAGE WHERE id IN " +
            "<foreach collection='list' open='(' close=')' index='index' item='item' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<ImageDO> findByIds(List<Integer> ids);

}
