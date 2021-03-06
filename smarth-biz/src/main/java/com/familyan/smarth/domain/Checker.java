package com.familyan.smarth.domain;

/**
 * Created by ibator.
 */
public class Checker {

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
    private String name;

    /**
     * 手机号
     *
     */
    private String mobile;

    /**
     * 0 未知, 1 男 ，2 女
     *
     */
    private Integer gender;

    /**
     * 出生日期
     *
     */
    private String birthday;

    /**
     * 身份证照片
     *
     */
    private Integer identifyPic;

    /**
     * 身份证照片
     *
     */
    private Integer identifyPic1;

    /**
     * 身份证反面
     *
     */
    private Integer identifyPic2;

    /**
     * 资格证照片
     *
     */
    private Integer qualificationPic;

    /**
     * 介绍
     *
     */
    private String description;

    /**
     * 
     *
     */
    private String longitude;

    /**
     * 
     *
     */
    private String latitude;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getIdentifyPic() {
        return identifyPic;
    }

    public void setIdentifyPic(Integer identifyPic) {
        this.identifyPic = identifyPic;
    }

    public Integer getIdentifyPic1() {
        return identifyPic1;
    }

    public void setIdentifyPic1(Integer identifyPic1) {
        this.identifyPic1 = identifyPic1;
    }

    public Integer getIdentifyPic2() {
        return identifyPic2;
    }

    public void setIdentifyPic2(Integer identifyPic2) {
        this.identifyPic2 = identifyPic2;
    }

    public Integer getQualificationPic() {
        return qualificationPic;
    }

    public void setQualificationPic(Integer qualificationPic) {
        this.qualificationPic = qualificationPic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

