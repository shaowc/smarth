package com.familyan.smarth.service.impl;

import com.familyan.smarth.constants.BindType;
import com.familyan.smarth.domain.Member;
import com.familyan.smarth.domain.MemberDTO;
import com.familyan.smarth.manager.MemberManager;
import com.familyan.smarth.service.MemberService;
import com.familyan.smarth.utils.TransferUtil;
import com.familyan.smarth.utils.UserNameUtil;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by shaowenchao on 16/9/8.
 */
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberManager manager;

    @Override
    public MemberDTO findById(Long id) {
        if(id == null || id < 1) return null;
        Member member = manager.findById(id);
        return TransferUtil.transfer(member);
    }

    @Override
    public MemberDTO findByBindType(BindType type, String loginId) {
        if(type == null || StringUtils.isBlank(loginId)) return null;
        Member member = manager.findByBindType(type,loginId);
        return TransferUtil.transfer(member);
    }

    @Override
    public Map<Long,MemberDTO> findByIds(List<Long> memberIds) {
        if(CollectionUtils.isEmpty(memberIds)){
            return Collections.emptyMap();
        }
        List<Member> list = manager.findByIds(memberIds);
        return new HashMap<>(FluentIterable.from(list).transform(new Function<Member, MemberDTO>() {
            @Override
            public MemberDTO apply(Member input) {
                return TransferUtil.transfer(input);
            }
        }).uniqueIndex(new Function<MemberDTO, Long>() {
            @Override
            public Long apply(MemberDTO input) {
                return input.getId();
            }
        }));
    }

    @Override
    public MemberDTO regMember(BindType type, MemberDTO member) {
        Member memberDO = TransferUtil.transfer(member);
        boolean registed = manager.regist(type,memberDO);
        if(registed)
            return TransferUtil.transfer(memberDO);
        return null;
    }

    @Override
    public boolean updateMember(MemberDTO update) {
        if(update.getId() == null || update.getId() < 1) return false;
        Member memberDO = new Member();
        BeanUtils.copyProperties(update,memberDO);
        memberDO.setPassword(null); // 不在这里更新密码
        if(memberDO.getUserName() != null){
            if(!UserNameUtil.validateUserName(memberDO.getUserName()))
                return false;
        }
        return manager.update(memberDO);
    }

    @Override
    public boolean addFeature(Long memberId, Long... features) {
        //先简单实现
        if(features == null || features.length == 0)
            return false;
        MemberDTO data = findById(memberId);
        if(data == null) return false;
        Set<Long> orgin = data.getFeatures();
        for(Long f : features){
            orgin.add(f);
        }
        Member memberDO = new Member();
        memberDO.setId(memberId);
        memberDO.setFeatures(Joiner.on(",").join(orgin));
        return manager.update(memberDO);
    }

    @Override
    public boolean removeFeature(Long memberId, Long... features) {
        if(features == null || features.length == 0)
            return false;
        MemberDTO data = findById(memberId);
        if(data == null) return false;
        Set<Long> orgin = data.getFeatures();
        for(Long f : features){
            orgin.remove(f);
        }
        Member memberDO = new Member();
        memberDO.setId(memberId);
        memberDO.setFeatures(Joiner.on(",").join(orgin));
        return manager.update(memberDO);
    }

    @Override
    public boolean login(BindType type, String loginId, String password) {
        return manager.login(type,loginId,password);
    }

    @Override
    public boolean resetPassword(Long memberId, String orginPassword, String newPassword) {
        if(memberId == null || memberId < 1 )
            return false;
        if(StringUtils.isBlank(orginPassword) || StringUtils.isBlank(newPassword))
            return false;
        return manager.resetPassword(memberId,orginPassword,newPassword);
    }

    @Override
    public boolean bind(Long memberId, BindType bindType, String id) {
        if(memberId == null || memberId < 1 || bindType == null)
            return false;
        if(StringUtils.isBlank(id))
            return false;
        return manager.bind(memberId,bindType,id);
    }

    @Override
    public boolean resetPasswordWithoutOrgin(Long memberId, String newPassword) {
        if(memberId == null || memberId < 1  )
            return false;
        if(StringUtils.isBlank(newPassword))
            return false;
        return manager.resetPasswordWithoutOrgin(memberId,newPassword);
    }

    @Override
    public MemberDTO loginNoPassword(BindType type, String loginId) {
        MemberDTO member = findByBindType(type,loginId);
        if(member != null){
            //success，保存登录历史记录
//            MemberLastLoginDO lastLoginDO = new MemberLastLoginDO();
//            BeanUtils.copyProperties(login,lastLoginDO);
//            lastLoginDO.setMemberId(member.getId());
//            lastLoginDO.setLastLogin(new Date());
//            manager.saveLostLogin(lastLoginDO);
        }
        return member;
    }
}
