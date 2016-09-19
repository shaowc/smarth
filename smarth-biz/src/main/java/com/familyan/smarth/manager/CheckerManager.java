package com.familyan.smarth.manager;

import com.familyan.smarth.domain.Checker;
import com.familyan.smarth.domain.CheckerDTO;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/11.
 */
public interface CheckerManager {

    void save(Checker checker);

    /**
     *
     * @param memberId
     * @return
     */
    Checker findByMemberId(Long memberId);

    Checker findById(Integer id);

    List<Checker> findMemberChecker(Long memberId);

    PageResult<List<Checker>> findByPage(CheckerDTO checkerDTO, Page page);

    List<Checker> findByMemberIds(List<Long> memberId);

}
