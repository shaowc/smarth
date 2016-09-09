package com.familyan.smarth.manager;

import com.familyan.smarth.domain.XtXzqh;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/8.
 */
public interface XtXzqhManager {

    public XtXzqh getByCode(int code);

    public List<XtXzqh> getByLevel(int level);

    public List<XtXzqh> getByParentCode(int parentCode);

    public List<XtXzqh> getByDisplayParentCode(int displayParentCode);

    public List<XtXzqh> findCodeByName(String name,int displayParentCode);

}
