package com.familyan.smarth.web.controller;

import com.familyan.smarth.domain.Checker;
import com.familyan.smarth.domain.LoginMember;
import com.familyan.smarth.manager.CheckerManager;
import com.familyan.smarth.manager.service.SmsService;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.Result;
import com.lotus.wechat.WechatOpenId;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by shaowenchao on 16/9/11.
 */
@RequestMapping("checker")
@Controller
public class CheckerController {

    @Autowired
    private CheckerManager checkerManager;
    @Autowired
    private SmsService smsService;

    @RequestMapping("reg")
    @Security
    public String reg(LoginMember loginMember, ModelMap modelMap) {
        return "checker/reg";
    }

    /**
     * 注册体检手，绑定手机号，完善个人信息
     *
     * @param loginMember
     * @param openId
     * @param checker
     * @param code
     * @param verifyCode
     * @return
     */
    @RequestMapping(value = "reg", method = RequestMethod.POST)
    @Security
    public Result doreg(LoginMember loginMember, WechatOpenId openId, Checker checker, String code, String verifyCode) {
        // 检查验证码
        if(StringUtils.isBlank(code) || StringUtils.isBlank(verifyCode)
                || !smsService.validateSmsVerifyCode(code,verifyCode, checker.getMobile())) {
            return Result.error("请填写正确的验证码");
        }
        checker.setMemberId(loginMember.getId());
        checkerManager.save(checker);
        return Result.success(1);
    }


    /**
     * 我的体检手
     *
     * @param loginMember
     * @param openId
     * @param modelMap
     * @return
     */
    @RequestMapping("list")
    public String list(LoginMember loginMember, WechatOpenId openId, ModelMap modelMap) {
        return "checker/list";
    }

}
