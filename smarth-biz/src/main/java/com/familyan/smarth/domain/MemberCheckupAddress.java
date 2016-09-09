package com.familyan.smarth.domain;

/**
 * Created by ibator.
 */
public class MemberCheckupAddress {

    /**
     * 
     *
     */
    private Integer id;

    /**
     * 
     *
     */
    private Long memberId;

    /**
     * 
     *
     */
    private Integer provinceId;

    /**
     * 
     *
     */
    private Integer cityId;

    /**
     * 
     *
     */
    private Integer countyId;

    /**
     * 
     *
     */
    private String address;

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
    private java.util.Date gmtModify;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

