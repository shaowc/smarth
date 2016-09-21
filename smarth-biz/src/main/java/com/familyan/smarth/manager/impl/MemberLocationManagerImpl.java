package com.familyan.smarth.manager.impl;

import com.familyan.smarth.dao.MemberLocationDao;
import com.familyan.smarth.domain.MemberLocation;
import com.familyan.smarth.manager.MemberLocationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by shaowenchao on 16/9/20.
 */
@Service
public class MemberLocationManagerImpl implements MemberLocationManager {

    @Autowired
    private MemberLocationDao memberLocationDao;

    @Override
    public void save(MemberLocation memberLocation) {
        if(memberLocation.getId() == null || memberLocation.getId() == 0) {
            memberLocationDao.insert(memberLocation);
        } else  {
            memberLocationDao.update(memberLocation);
        }
    }

    @Override
    public MemberLocation findByMemberId(Long memberId) {
        return memberLocationDao.findByMemberId(memberId);
    }

    @Override
    public List<MemberLocation> findByMemberIds(List<Long> memberIds) {
        if (memberIds == null || memberIds.isEmpty())
            return Collections.emptyList();

        return memberLocationDao.findByMemberIds(memberIds);
    }
}
