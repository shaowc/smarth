package com.familyan.smarth.web.controller;

import com.familyan.smarth.domain.LoginMember;
import com.lotus.wechat.WechatOpenId;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by shaowenchao on 16/9/9.
 */
@RequestMapping
@Controller
public class HomeController {

    /**
     * 完善个人信息页面，所有未完善信息的用户都会被强制定位到该页面
     *
     * @param loginMember
     * @param openId
     * @param modelMap
     * @return
     */
    @RequestMapping("reg")
    public String reg(LoginMember loginMember, WechatOpenId openId, ModelMap modelMap) {
        return "reg";
    }


    /**
     * 保存后跳转到来时的页面
     *
     * @param loginMember
     * @param openId
     * @param modelMap
     * @return
     */
    @RequestMapping("doreg")
    public String doreg(LoginMember loginMember, WechatOpenId openId, String refer, ModelMap modelMap) {
        if(StringUtils.isNotBlank(refer))
            return "redirect:" + refer;
        return "redirect:/packet/list.htm";
    }

}
