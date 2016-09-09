package com.familyan.smarth.web.controller;

import com.familyan.smarth.constants.BindType;
import com.familyan.smarth.domain.LoginMember;
import com.familyan.smarth.domain.MemberDTO;
import com.familyan.smarth.domain.MemberWechatDTO;
import com.familyan.smarth.manager.MemberWechatManager;
import com.familyan.smarth.service.MemberService;
import com.familyan.smarth.service.MemberWechatService;
import com.lotus.core.web.cookyjar.Cookyjar;
import com.lotus.wechat.WechatApi;
import com.lotus.wechat.WechatException;
import com.lotus.wechat.WechatOpenId;
import com.lotus.wechat.WechatUserInfo;
import com.lotus.wechat.message.WechatEngine;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by shaowenchao on 16/9/9.
 */
@RequestMapping
@Controller("wechat")
public class WechatController {


    private final Logger log = Logger.getLogger(WechatController.class);

    @Autowired
    WechatApi api;

    @Autowired
    WechatEngine engine;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberWechatService wechatService;

    /**
     * 微信获取code 之后跳转的页面，按code 获取对于的unionId, 并找出绑定的账号进行登录
     *
     * @param url
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("autoLogin")
    public String autoLogin(String url, String code, String state,
                            Cookyjar cookyjar, LoginMember loginMember, WechatOpenId openId,
                            ModelMap result, HttpServletRequest request) {
        if (loginMember != null && openId != null)
            return "redirect:" + url;
        if (StringUtils.isNotBlank(code)) {
            //请求accessToke 并 获取 openid, unionId,判断是否关注
            try {
                WechatApi.AccessToken accessToken = api.accessToken(code);

                openId = new WechatOpenId();

                openId.setOpenId(accessToken.getOpenId());

                WechatUserInfo info = api.userInfo(accessToken.getOpenId());
                openId.setSubscribe(info.getSubscribe());
                openId.setUnionId(info.getUnionId());
                if (info.getSubscribe() == 1) {
                    openId.setNickName(info.getNickName());
                }

                cookyjar.set(openId , 3600);

                // 如果有相应的账号，自动登录掉
                try {

                    MemberDTO member = memberService.loginNoPassword(BindType.WEIXIN, openId.getUnionId());
                    if (member == null) {
                        //用openId 查关联的用户
                        MemberWechatDTO wechatDTO = wechatService.findByOpenId("smarth", openId.getOpenId());
                        if (wechatDTO != null && wechatDTO.getMemberId() != null) {
                            member = memberService.findById(wechatDTO.getMemberId());
                            //默认userName登录
                            memberService.loginNoPassword(BindType.USER_NAME, member.getUserName());
                        }
                    }
                    if (member != null) {
                        loginMember = new LoginMember();
                        loginMember.setFeatures(member.getFeatures());
                        loginMember.setNextCheckTime(System.currentTimeMillis() + 5 * 60 * 1000); // 5分钟后
                        loginMember.setName(member.getUserName());
                        loginMember.setId(member.getId());

                        cookyjar.set(loginMember);
                    }
                } catch (Exception e) {
                    //can not autologin
                    e.printStackTrace();
                }
                return "redirect:" + url;
            } catch (WechatException e) {
                result.put("msg", e.getMessage());
                return "forward:/error.htm";
            }
        } else {
            result.put("msg", "微信登录错误");
            return "forward:/error.htm";
        }
    }

    /**
     * 微信服务器会请求该接口，需要暴露到外网
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public String validate(String signature, String timestamp, String nonce, String echostr) {
        return echostr;
    }

    @RequestMapping(value = "validate", method = RequestMethod.POST)
    @ResponseBody
    public String response(String signature, String timestamp, String nonce, HttpServletRequest request) {
        try {
            String xml = IOUtils.toString(request.getInputStream());
            return engine.onMessage(xml);
        } catch (WechatException | IOException e) {
            log.error(e);
        }
        return "";
    }


}
