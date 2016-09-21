package com.familyan.smarth.web.controller;

import com.familyan.smarth.domain.XtXzqh;
import com.familyan.smarth.manager.XtXzqhManager;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by shaowenchao on 16/9/20.
 */
@RequestMapping("xzqh")
@Controller
public class XtXzqhController {

    @Autowired
    private XtXzqhManager xtXzqhManager;

    @RequestMapping("findByParent")
    @ResponseBody
    public List<XtXzqh> findByParent(int code) {
        List<XtXzqh> xtXzqhs = xtXzqhManager.getByDisplayParentCode(code);
        return xtXzqhs;
    }

    private static String httpGet(String url) {
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(2000);
            InputStream in = connection.getInputStream();
            return IOUtils.toString(in, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("访问百度api失败");
        }
    }


}
