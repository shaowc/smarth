package com.familyan.smarth.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shaowenchao on 16/9/8.
 */
public class MemberDTO implements Serializable {
    private static final long serialVersionUID = -2607918374231157354L;
    /**
     * 自增id，会员id
     *
     */
    private Long id;

    /**
     * 用户名
     *
     */
    private String userName;

    /**
     * 真实姓名
     *
     */
    private String realName;

    /**
     * 密码 md5
     *
     */
    private String password;

    /**
     * 头像链接
     *
     */
    private String avatar;

    /**
     * 0 未知, 1 男 ，2 女
     *
     */
    private Integer gender;

    /**
     * 生日 yyyy-MM-dd
     *
     */
    private String birthday;

    /**
     * 手机号码
     *
     */
    private String mobile;

    /**
     * 邮件地址
     *
     */
    private String email;

    /**
     * 绑定的微信id
     *
     */
    private String weixinId;

    /**
     * 省份id
     *
     */
    private Integer provinceId;

    /**
     * 城市id
     *
     */
    private Integer cityId;

    /**
     * 县区id
     *
     */
    private Integer countyId;

    /**
     * 经度
     *
     */
    private String longitude;

    /**
     * 纬度
     *
     */
    private String latitude;

    /**
     * 用户属性列表, 以,分隔，如检手，普通用户等
     *
     */
    private Set<Long> features = new HashSet<>();

    /**
     * 状态 1 正常, 2 禁用
     *
     * 默认值：1
     */
    private Integer status;

    /**
     * 注册时间
     *
     */
    private java.util.Date gmtCreate;

    /**
     * 最后修改时间
     *
     */
    private java.util.Date gmtModified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
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

    public Set<Long> getFeatures() {
        return features;
    }

    public void addFeature(Long feature){
        if(feature == null)
            throw new IllegalArgumentException("feature can not set null");
        features.add(feature);
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

    public java.util.Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(java.util.Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
