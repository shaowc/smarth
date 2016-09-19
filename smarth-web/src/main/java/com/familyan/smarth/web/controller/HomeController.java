package com.familyan.smarth.web.controller;

import com.familyan.smarth.constants.BindType;
import com.familyan.smarth.domain.LoginMember;
import com.familyan.smarth.manager.service.SmsService;
import com.familyan.smarth.service.MemberService;
import com.lotus.service.result.Result;
import com.lotus.wechat.WechatOpenId;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shaowenchao on 16/9/9.
 */
@RequestMapping
@Controller
public class HomeController {

    private static Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private SmsService smsService;
    @Autowired
    private MemberService memberService;

    /**
     * 绑定手机号
     *
     */
    @RequestMapping(value = "bind-phone", method = RequestMethod.GET)
    public String bindPhone(LoginMember loginMember, ModelMap modelMap) {
        return "bind-phone";
    }

    /**
     * 验证短信验证码，绑定成功后，跳转
     *
     * @param loginMember
     * @param mobile
     * @param identifier
     * @param code
     * @return
     */
    @RequestMapping(value = "bind-phone", method = RequestMethod.POST)
    @ResponseBody
    public Result bindPhone(LoginMember loginMember, String mobile, String identifier, String code, HttpServletResponse response, String url) {
//        boolean valid = smsService.validateSmsVerifyCode(identifier, code, mobile);
//        if (!valid) {
//            return Result.error("验证码错误");
//        }

        // 绑定手机号
        boolean bind = memberService.bind(loginMember.getId(), BindType.MOBILE, mobile);

        if (StringUtils.isNotBlank(url)) {
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return Result.success(1);
    }

    /**
     * 完善个人信息页面，所有未完善信息的用户都会被强制定位到该页面
     *
     * @param loginMember
     * @param openId
     * @param modelMap
     * @return
     */
    @RequestMapping("perfect-info")
    public String reg(LoginMember loginMember, WechatOpenId openId, ModelMap modelMap) {
        return "perfect-info";
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
