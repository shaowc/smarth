package com.familyan.smarth.domain;

/**
 * Created by ibator.
 */
public class MemberPackage {

    /**
     * 
     *
     */
    private Integer id;

    /**
     * 
     *
     */
    private Integer packageId;

    /**
     * 检手会员ID
     *
     */
    private Integer memberId;

    /**
     * 状态，0：未支付，1：已支付，-1：已删除
     *
     * 默认值：0
     */
    private Integer status;

    /**
     * 
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

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

