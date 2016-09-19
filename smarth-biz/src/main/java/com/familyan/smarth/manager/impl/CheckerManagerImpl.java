package com.familyan.smarth.manager.impl;

import com.familyan.smarth.dao.CheckerDao;
import com.familyan.smarth.dao.MemberCheckerDao;
import com.familyan.smarth.dao.MemberDao;
import com.familyan.smarth.domain.Checker;
import com.familyan.smarth.domain.CheckerDTO;
import com.familyan.smarth.domain.Member;
import com.familyan.smarth.domain.MemberChecker;
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
            memberDO.setFeatures("1");
            memberDao.update(memberDO);
        } else {
            Member memberDO = new Member();
            memberDO.setRealName(checker.getName());
            memberDO.setId(checker.getMemberId());
            memberDO.setGender(checker.getGender());
            //member.setBirthday(checker.getBirthday());
            memberDO.setFeatures("1");
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
    public PageResult<List<Checker>> findByPage(CheckerDTO checkerDTO, Page page) {
        int total = checkerDao.countByParams(checkerDTO);
        if(total == 0) {
            return PageResult.emptyResult(Collections.<Checker>emptyList());
        }

        List<Checker> data = checkerDao.findByParams(checkerDTO, page.getStart(), page.getPageSize());
        return new PageResult<>( page.getStart(), page.getPageSize(), total, data);
    }
}
