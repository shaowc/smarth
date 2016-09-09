package com.familyan.smarth.domain;

/**
 * Created by ibator.
 */
public class Order {

    /**
     * 
     *
     */
    private Integer id;

    /**
     * 用户会员ID
     *
     */
    private Long memberId;

    /**
     * 快检手会员ID
     *
     */
    private Long checkerId;

    /**
     * 体检包ID
     *
     */
    private Integer packageId;

    /**
     * 购买时体检包内容
     *
     */
    private String packageContent;

    /**
     * 预约体检时间
     *
     */
    private java.util.Date checkupTime;

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
     * 商户系统内部的订单号,32个字符内、可包含字母
     *
     */
    private String outTradeNo;

    /**
     * 预支付交易会话标识，该值2小时内有效
     *
     */
    private String prepayId;

    /**
     * 
     *
     */
    private java.util.Date gmtPrepayIdValid;

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

    public Long getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Long checkerId) {
        this.checkerId = checkerId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageContent() {
        return packageContent;
    }

    public void setPackageContent(String packageContent) {
        this.packageContent = packageContent;
    }

    public java.util.Date getCheckupTime() {
        return checkupTime;
    }

    public void setCheckupTime(java.util.Date checkupTime) {
        this.checkupTime = checkupTime;
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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public java.util.Date getGmtPrepayIdValid() {
        return gmtPrepayIdValid;
    }

    public void setGmtPrepayIdValid(java.util.Date gmtPrepayIdValid) {
        this.gmtPrepayIdValid = gmtPrepayIdValid;
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

