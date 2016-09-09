package com.familyan.smarth.utils;

import com.familyan.smarth.domain.Member;
import com.familyan.smarth.domain.MemberDTO;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * Created by xifeng on 2015/8/25.
 */
public class TransferUtil {

    public static Member transfer(MemberDTO dto){
        if(dto == null) return null;
        Member member = new Member();
        BeanUtils.copyProperties(dto,member);
        if(dto.getFeatures() != null && dto.getFeatures().size() > 0){
            member.setFeatures(Joiner.on(",").join(dto.getFeatures()));
        }
        return member;
    }

    public static MemberDTO transfer(Member data){
        if(data == null) return null;
        MemberDTO dto = new MemberDTO();
        BeanUtils.copyProperties(data,dto);
        if(StringUtils.isNotBlank(data.getFeatures())){
            String[] feathures = StringUtils.split(data.getFeatures(),",");
            for(String id : feathures){
                dto.addFeature(Long.parseLong(id));
            }
        }
        return dto;
    }

    public static class TransferFunction<F,T> implements Function<F,T> {
        private Class<T> clazz;
        public TransferFunction(Class<T> clazz){
            this.clazz = clazz;
        }
        @Override
        public T apply(F input) {
            T t ;
            try {
                t = clazz.newInstance();
                BeanUtils.copyProperties(input,t);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException("Class can not instantiation ",e);
            }
            return t;
        }
    }

    public static <T,D> D transfer(T from ,D to){
        if(from == null) return null;
        BeanUtils.copyProperties(from,to);
        return to;
    }

}
