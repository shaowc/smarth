package com.familyan.smarth.dao;

import com.familyan.smarth.domain.XtXzqh;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface XtXzqhDao {

    @Select("SELECT * FROM XT_XZQH WHERE code=#{code}")
    XtXzqh findByCode(int code);

    @Select("SELECT * FROM XT_XZQH WHERE level=#{level}")
    List<XtXzqh> findByLevel(int level);

    @Select("SELECT * FROM XT_XZQH WHERE display_level=#{displayLevel} AND display=1")
    List<XtXzqh> findByDisplayLevel(int displayLevel);

    @Select("SELECT * FROM XT_XZQH WHERE parent_code=#{parentCode}")
    List<XtXzqh> findByParentCode(int parentCode);

    @Select("SELECT * FROM XT_XZQH WHERE display_parent_code=#{displayParentCode} AND display=1")
    List<XtXzqh> findByDisplayParentCode(int displayParentCode);

    /**
     * 用户member表中手机城市数据
     * @return
     */
    @Select("SELECT code,name FROM XT_XZQH WHERE `name` LIKE CONCAT(#{name},'%') AND display_parent_code = #{displayParentCode}")
    List<XtXzqh> findCodeByName(Map<String, Object> map);

}