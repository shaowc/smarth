package com.familyan.smarth.domain;

import java.io.Serializable;

/**
 * Created by shaowenchao on 16/9/11.
 */
public class CheckerDTO implements Serializable {
    private static final long serialVersionUID = 4321814825779345154L;

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
    private String identifyPic;

    /**
     * 身份证反面
     *
     */
    private String identifyPic2;

    /**
     * 资格证照片
     *
     */
    private String qualificationPic;

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

    public String getIdentifyPic() {
        return identifyPic;
    }

    public void setIdentifyPic(String identifyPic) {
        this.identifyPic = identifyPic;
    }

    public String getIdentifyPic2() {
        return identifyPic2;
    }

    public void setIdentifyPic2(String identifyPic2) {
        this.identifyPic2 = identifyPic2;
    }

    public String getQualificationPic() {
        return qualificationPic;
    }

    public void setQualificationPic(String qualificationPic) {
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
