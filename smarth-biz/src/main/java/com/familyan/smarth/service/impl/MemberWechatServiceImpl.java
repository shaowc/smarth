package com.familyan.smarth.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.familyan.smarth.domain.MemberWechatDO;
import com.familyan.smarth.domain.MemberWechatDTO;
import com.familyan.smarth.manager.MemberWechatManager;
import com.familyan.smarth.service.MemberWechatService;
import com.lotus.core.util.TransferUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xifeng on 2016/4/9.
 */
@Service
public class MemberWechatServiceImpl implements MemberWechatService {

    @Autowired
    MemberWechatManager manager;

    @Override
    public MemberWechatDTO findByMemberId(String app, Long memberId) {
        return TransferUtil.transfer(manager.findByMemberId(app, memberId), new MemberWechatDTO());
    }

    @Override
    public MemberWechatDTO findByOpenId(String app, String openId) {
        return TransferUtil.transfer(manager.findByOpenId(app, openId),new  MemberWechatDTO());
    }

    @Override
    public Map<String, MemberWechatDTO> findByOpenIds(String app, Collection<String> openIds) {
        Map<String, MemberWechatDO> doMap = manager.findByOpenIds(app, openIds);
        if (doMap.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, MemberWechatDTO>  dtoMap = new HashMap<>(doMap.size());
        for (String key : doMap.keySet()) {
            MemberWechatDO wechatDO = doMap.get(key);
            MemberWechatDTO wechatDTO = new MemberWechatDTO();
            BeanUtils.copyProperties(wechatDO, wechatDTO);
            dtoMap.put(key, wechatDTO);
        }
        return dtoMap;
    }

    @Override
    public MemberWechatDTO save(MemberWechatDTO dto) {
        MemberWechatDO data = TransferUtil.transfer(dto ,new MemberWechatDO());
        boolean success = manager.save(data);
        if(success){
            return TransferUtil.transfer(manager.findById(data.getId()), new MemberWechatDTO());
        }
        return null;
    }
}
