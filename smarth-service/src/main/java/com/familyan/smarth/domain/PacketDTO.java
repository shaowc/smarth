package com.familyan.smarth.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shaowenchao on 16/9/10.
 */
public class PacketDTO implements Serializable {
    private static final long serialVersionUID = 2770728160759659046L;
    /**
     *
     *
     */
    private Integer id;

    /**
     * 体检包名称
     *
     */
    private String name;

    private String description;

    private String content;

    /**
     * 体检包价格
     *
     * 默认值：0
     */
    private Integer price;

    /**
     *
     *
     */
    private java.util.Date gmtCreate;

    /**
     *
     *
     * 默认值：CURRENT_TIMESTAMP
     */
    private java.util.Date gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public java.util.Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(java.util.Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public java.util.Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(java.util.Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}
