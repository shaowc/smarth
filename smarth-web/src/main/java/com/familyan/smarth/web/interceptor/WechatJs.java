package com.familyan.smarth.web.interceptor;

/**
 * Created by shaowenchao on 16/6/25.
 */
public class WechatJs {

    private String appId;
    private long timestamp;
    private String nonceStr;
    private String signature;

    public String getAppId() {
        return appId;
    }

    public WechatJs setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public WechatJs setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public WechatJs setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public WechatJs setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    @Override
    public String toString() {
        return "WechatJs{" +
                "appId='" + appId + '\'' +
                ", timestamp=" + timestamp +
                ", nonceStr='" + nonceStr + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
