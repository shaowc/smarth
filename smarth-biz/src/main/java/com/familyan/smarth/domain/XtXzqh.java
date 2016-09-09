package com.familyan.smarth.domain;

/**
 * Created by ibator.
 */
public class XtXzqh {

    /**
     * 行政区划代码
     *
     */
    private Integer code;

    /**
     * 行政区划名称
     *
     */
    private String name;

    /**
     * 国标行政区划等级
     *
     */
    private Integer level;

    /**
     * 国标上级行政区划
     *
     */
    private Integer parentCode;

    /**
     * 是否显示，0：否，1：是
     *
     */
    private Integer display;

    /**
     * 显示等级，过滤市辖区，省直辖县级行政区划
     *
     */
    private Integer displayLevel;

    /**
     * 显示查询的上级行政区划code
     *
     */
    private Integer displayParentCode;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getDisplayLevel() {
        return displayLevel;
    }

    public void setDisplayLevel(Integer displayLevel) {
        this.displayLevel = displayLevel;
    }

    public Integer getDisplayParentCode() {
        return displayParentCode;
    }

    public void setDisplayParentCode(Integer displayParentCode) {
        this.displayParentCode = displayParentCode;
    }

}

