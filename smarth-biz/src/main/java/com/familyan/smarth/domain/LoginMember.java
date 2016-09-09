package com.familyan.smarth.domain;



import com.lotus.core.web.cookyjar.Writable;
import com.lotus.core.web.cookyjar.util.WritableUtil;
import com.lotus.core.web.security.SecurityException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shaowenchao on 16/9/9.
 */
public class LoginMember implements Writable,Serializable {

    Long id;//id
    String name;//名称
    Long nextCheckTime;//下一次检查时间，用于账号失效检查
    Set<Long> features;//用户属性

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNextCheckTime() {
        return nextCheckTime;
    }

    public void setNextCheckTime(Long nextCheckTime) {
        this.nextCheckTime = nextCheckTime;
    }

    public void setFeatures(Set<Long> features) {
        this.features = features;
    }

    public Set<Long> getFeatures() {
        return features;
    }

    public boolean containsFeature(Long feature){
        if(features != null){
            return features.contains(feature);
        }
        return false;
    }

    public boolean containsFeature(Integer feature){
        if(features != null){
            return features.contains(feature.longValue());
        }
        return false;
    }

    @Override
    public String write() {
        if(id == null || name == null || nextCheckTime == null
                || features == null)
            throw new IllegalArgumentException("properties not set");
        return WritableUtil.write(
                id + "",
                name,
                nextCheckTime + "",
                join(features)
        );
    }

    private String join(Set<Long> features) {
        StringBuilder builder = new StringBuilder();
        if(features != null && features.size() > 0){
            int index = features.size();
            for(Long id : features){
                index --;
                builder.append(id);
                if(index > 0){
                    builder.append("^");
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void read(String value) {
        String[] values = WritableUtil.read(value);
        if(values == null || values.length != 4)
            throw new com.lotus.core.web.security.SecurityException(SecurityException.SecurityExceptionEnum.NOT_LOGIN,"Session value error,");
        id = Long.parseLong(values[0]);
        name =  values[1];
        nextCheckTime = Long.parseLong(values[2]);
        features = split(values[3]);
    }

    private Set<Long> split(String value) {
        features = new HashSet<>();
        if(value != null && value.length() > 0){
            String[] ids = value.split("\\^");
            for(String id : ids){
                features.add(Long.parseLong(id));
            }
        }
        return features;
    }

    public static void main(String[] args) {
        LoginMember member = new LoginMember();
        member.setId(10L);
        //String value = member.write();
        //member.read(value);

        member.setName("aamsm");
        ///value = member.write();
        //member.read(value);

        member.setNextCheckTime(System.currentTimeMillis());
        //value = member.write();
        //member.read(value);

        Set<Long> features = new HashSet<>();
        member.setFeatures(features);
        String value = member.write();
        member.read(value);

        features.add(1L);
        member.setFeatures(features);

        value = member.write();
        member.read(value);

        features.add(2L);
        value = member.write();
        member.read(value);
    }
}

