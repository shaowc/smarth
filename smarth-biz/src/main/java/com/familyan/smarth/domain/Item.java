package com.familyan.smarth.domain;

/**
 * Created by ibator.
 */
public class Item {

    /**
     * 
     *
     */
    private Integer id;

    /**
     * 体检包ID
     *
     */
    private Integer packetId;

    /**
     * 体检项目名称
     *
     */
    private String name;

    /**
     * 创建时间
     *
     */
    private java.util.Date gmtCreate;

    /**
     * 修改时间
     *
     * 默认值：CURRENT_TIMESTAMP
     */
    private java.util.Date gmtModify;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPacketId() {
        return packetId;
    }

    public void setPacketId(Integer packetId) {
        this.packetId = packetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(java.util.Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public java.util.Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(java.util.Date gmtModify) {
        this.gmtModify = gmtModify;
    }

}

