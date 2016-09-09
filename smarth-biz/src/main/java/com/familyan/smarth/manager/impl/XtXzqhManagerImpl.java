package com.familyan.smarth.manager.impl;

import com.familyan.smarth.dao.XtXzqhDao;
import com.familyan.smarth.domain.XtXzqh;
import com.familyan.smarth.manager.XtXzqhManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by shaowenchao on 16/9/8.
 */
@Service
public class XtXzqhManagerImpl implements XtXzqhManager {

    @Autowired
    private XtXzqhDao xtXzqhMapper;

    @Override
    public XtXzqh getByCode(int code) {
        return xtXzqhMapper.findByCode(code);
    }

    @Override
    public List<XtXzqh> getByLevel(int level) {
        return xtXzqhMapper.findByLevel(level);
    }

    @Override
    public List<XtXzqh> getByParentCode(int parentCode) {
        return xtXzqhMapper.findByParentCode(parentCode);
    }

    @Override
    public List<XtXzqh> getByDisplayParentCode(int displayParentCode) {
        return xtXzqhMapper.findByDisplayParentCode(displayParentCode);
    }

    @Override
    public List<XtXzqh> findCodeByName(String name,int displayParentCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("displayParentCode",displayParentCode);
        List<XtXzqh> code = xtXzqhMapper.findCodeByName(map);
        return code == null || code.size() <= 0 ? Collections.<XtXzqh>emptyList() : code;
    }

}
