package com.familyan.smarth.manager.impl;

import com.familyan.smarth.dao.MemberWechatDao;
import com.familyan.smarth.domain.MemberWechatDO;
import com.familyan.smarth.manager.MemberWechatManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xifeng on 2016/4/9.
 */
@Service
public class MemberWechatManagerImpl implements MemberWechatManager {
    @Autowired
    MemberWechatDao wechatDao;

    @Override
    public MemberWechatDO findByMemberId(String app, Long memberId) {
        return wechatDao.findByMemberId(app,memberId);
    }

    @Override
    public MemberWechatDO findByOpenId(String app, String openId) {
        return wechatDao.findByOpenId(app,openId);
    }

    @Override
    public Map<String, MemberWechatDO> findByOpenIds(String app, Collection<String> openIds) {
        if (null == openIds || openIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<MemberWechatDO> list = wechatDao.findByOpenIds(app, openIds);
        if (list.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, MemberWechatDO> map = new HashMap<>(list.size());
        for (MemberWechatDO memberWechatDO : list) {
            map.put(memberWechatDO.getOpenId(), memberWechatDO);
        }
        return map;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean save(MemberWechatDO obj) {
        if(obj.getId() != null){
            return wechatDao.updateById(obj) > 0;
        }
        if(StringUtils.isNotBlank(obj.getApp()) && StringUtils.isNotBlank(obj.getOpenId())){
            MemberWechatDO old = wechatDao.findByOpenId(obj.getApp(), obj.getOpenId());
            if(old != null){
                obj.setId(old.getId());
               return  wechatDao.updateById(obj) > 0;
            }else {
                wechatDao.insert(obj);
                return true;
            }
        }
        return false;
    }

    @Override
    public MemberWechatDO findById(Integer id) {
        return wechatDao.findByPrimaryKey(id);
    }
}
