package com.familyan.smarth.domain;

import java.io.Serializable;

/**
 * Created by xifeng on 2016/4/9.
 */
public class MemberWechatDTO implements Serializable {

    private static final long serialVersionUID = 6152919040983566697L;

    public static final int SUBSCRIBE = 1; //关注状态

    public static final int UN_SUBSCRIBE = 0; //取消关注状态
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 指定的app，如union
     */
    private String app;
    /**
     * 微信openId
     */
    private String openId;
    /**
     * 所属用户id
     */
    private Long memberId;
    /**
     * 是否关注
     */
    private Integer subscribe;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别，1男，2女，0未知
     */
    private Integer sex;
    /**
     * 市
     */
    private String city;
    /**
     * 省
     */
    private String province;
    /**
     * 国家
     */
    private String country;
    /**
     * 语言
     */
    private String language;
    /**
     * 头像
     */
    private String headImgUrl;
    /**
     * 关注事件
     */
    private java.util.Date subscribeTime;
    /**
     * 备注
     */
    private String remark;


    public void setId(Integer id){
        this.id = id;
    }

    public Integer  getId(){
        return id;
    }

    public void setApp(String app){
        this.app = app;
    }

    public String  getApp(){
        return app;
    }

    public void setOpenId(String openId){
        this.openId = openId;
    }

    public String  getOpenId(){
        return openId;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public Long  getMemberId(){
        return memberId;
    }

    public void setSubscribe(Integer subscribe){
        this.subscribe = subscribe;
    }

    public Integer  getSubscribe(){
        return subscribe;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String  getNickName(){
        return nickName;
    }

    public void setSex(Integer sex){
        this.sex = sex;
    }

    public Integer  getSex(){
        return sex;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String  getCity(){
        return city;
    }

    public void setProvince(String province){
        this.province = province;
    }

    public String  getProvince(){
        return province;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String  getCountry(){
        return country;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public String  getLanguage(){
        return language;
    }

    public void setHeadImgUrl(String headImgUrl){
        this.headImgUrl = headImgUrl;
    }

    public String  getHeadImgUrl(){
        return headImgUrl;
    }

    public void setSubscribeTime(java.util.Date subscribeTime){
        this.subscribeTime = subscribeTime;
    }

    public java.util.Date  getSubscribeTime(){
        return subscribeTime;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String  getRemark(){
        return remark;
    }
}
