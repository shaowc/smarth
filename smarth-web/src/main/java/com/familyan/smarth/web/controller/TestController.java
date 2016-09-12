package com.familyan.smarth.web.controller;

import com.lotus.wechat.WechatOpenId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by shaowenchao on 16/9/11.
 */
@RequestMapping("test")
@Controller
public class TestController {

    @RequestMapping("info")
    public String info(WechatOpenId openId, ModelMap modelMap) {

        return "test/info";
    }
}
