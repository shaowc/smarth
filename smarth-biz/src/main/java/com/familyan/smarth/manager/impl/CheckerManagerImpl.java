package com.familyan.smarth.manager.impl;

import com.familyan.smarth.dao.*;
import com.familyan.smarth.domain.*;
import com.familyan.smarth.manager.CheckerManager;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by shaowenchao on 16/9/11.
 */
@Service
public class CheckerManagerImpl implements CheckerManager {

    @Autowired
    private CheckerDao checkerDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberCheckerDao memberCheckerDao;
    @Autowired
    private MemberLocationDao memberLocationDao;
    @Autowired
    private MemberPacketDao memberPacketDao;

    @Override
    @Transactional
    public void save(Checker checker) {
        if(checker.getId() == null || checker.getId() == 0) {
            checkerDao.insert(checker);
            Member memberDO = new Member();
            memberDO.setRealName(checker.getName());
            memberDO.setId(checker.getMemberId());
            memberDO.setGender(checker.getGender());
            //member.setBirthday(checker.getBirthday());
            memberDao.update(memberDO);
            memberLocationDao.updateType(checker.getMemberId(), 1);
        } else {
            Member memberDO = new Member();
            memberDO.setRealName(checker.getName());
            memberDO.setId(checker.getMemberId());
            memberDO.setGender(checker.getGender());
            //member.setBirthday(checker.getBirthday());
            memberDao.update(memberDO);
            checkerDao.update(checker);
        }
    }

    @Override
    public Checker findByMemberId(Long memberId) {
        return checkerDao.findByMemberId(memberId);
    }

    @Override
    public Checker findById(Integer id) {
        return checkerDao.findById(id);
    }

    @Override
    public List<Checker> findMemberChecker(Long memberId) {
        List<MemberChecker> list = memberCheckerDao.findByMemberId(memberId);
        if(list.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<Long> checkerIds = Lists.transform(list, new Function<MemberChecker, Long>() {
            @Override
            public Long apply(MemberChecker input) {
                return input.getCheckerId();
            }
        }); 
        return findByMemberIds(checkerIds);
    }

    public List<Checker> findByMemberIds(List<Long> checkerIds) {
        return checkerDao.findByMemberIds(checkerIds);
    }

    @Override
    public List<Checker> findByPacketId(Integer packetId) {
        List<MemberPacket> memberPackets = memberPacketDao.findByPacketId(packetId);
        if(memberPackets.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> checkerIds = Lists.transform(memberPackets, new Function<MemberPacket, Long>() {
            @Override
            public Long apply(MemberPacket input) {
                return input.getMemberId();
            }
        });
        return findByMemberIds(checkerIds);
    }

    @Override
    public PageResult<List<Checker>> findByPage(CheckerDTO checkerDTO, Page page) {
        int total = checkerDao.countByParams(checkerDTO);
        if(total == 0) {
            return PageResult.emptyResult(Collections.<Checker>emptyList());
        }

        List<Checker> data = checkerDao.findByParams(checkerDTO, page.getStart(), page.getPageSize());
        return new PageResult<>( page.getStart(), page.getPageSize(), total, data);
    }
}
