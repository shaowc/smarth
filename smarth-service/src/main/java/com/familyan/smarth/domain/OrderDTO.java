package com.familyan.smarth.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaowenchao on 16/9/9.
 */
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = -7724131053105173973L;

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
    private Integer packetId;

    private Integer price;

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

    private String city;

    private String address;

    private Integer status;

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

    private java.util.Date gmtPrepayIdValid;

    private java.util.Date gmtCreate;

    /**
     *
     *
     * 默认值：CURRENT_TIMESTAMP
     */
    private java.util.Date gmtModify;

    private List<Integer> statuses;


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

    public Integer getPacketId() {
        return packetId;
    }

    public void setPacketId(Integer packetId) {
        this.packetId = packetId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<Integer> getStatuses() {
        return statuses;
    }

    public void addStatus(Integer status) {
        if(statuses == null) {
            statuses = new ArrayList<>();
        }

        statuses.add(status);
    }
}
