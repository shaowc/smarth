package com.familyan.smarth.service;

import com.familyan.smarth.domain.CheckerDTO;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/11.
 */
public interface CheckerService {

    PageResult<List<CheckerDTO>> findByPage(CheckerDTO checkerDTO, Page page);

}
