package com.familyan.smarth.manager.impl;

import com.familyan.smarth.dao.CheckerDao;
import com.familyan.smarth.dao.MemberDao;
import com.familyan.smarth.domain.Checker;
import com.familyan.smarth.domain.CheckerDTO;
import com.familyan.smarth.domain.Member;
import com.familyan.smarth.manager.CheckerManager;
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
    public PageResult<List<Checker>> findByPage(CheckerDTO checkerDTO, Page page) {
        int total = checkerDao.countByParams(checkerDTO);
        if(total == 0) {
            return PageResult.emptyResult(Collections.<Checker>emptyList());
        }

        List<Checker> data = checkerDao.findByParams(checkerDTO, page.getStart(), page.getPageSize());
        return new PageResult<>( page.getStart(), page.getPageSize(), total, data);
    }
}
