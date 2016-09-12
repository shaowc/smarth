package com.familyan.smarth.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.familyan.smarth.domain.Checker;
import com.familyan.smarth.domain.CheckerDTO;
import com.familyan.smarth.manager.CheckerManager;
import com.familyan.smarth.service.CheckerService;
import com.lotus.core.util.TransferUtil;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/11.
 */
@Service
public class CheckerServiceImpl implements CheckerService {

    @Autowired
    private CheckerManager checkerManager;

    @Override
    public PageResult<List<CheckerDTO>> findByPage(CheckerDTO checkerDTO, Page page) {
        PageResult<List<Checker>> pageResult = checkerManager.findByPage(checkerDTO, page);
        List<CheckerDTO> data = TransferUtil.transferList(pageResult.getData(), CheckerDTO.class);
        return new PageResult<>(pageResult.getStart(), pageResult.getLimit(), pageResult.getTotal(), data);
    }
}
