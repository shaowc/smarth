package com.familyan.smarth.web.controller;

import com.lotus.service.result.Result;
import com.lotus.wechat.pay.WechatPayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shaowenchao on 16/9/12.
 */
@RequestMapping("orderPay")
@Controller
public class OrderPayController {

    @RequestMapping(value = "unified", method = RequestMethod.POST)
    @ResponseBody
    public Result createPayParams(Integer orderId, HttpServletRequest request) {
        return Result.success(1);


    }

    /**
     * 处理 微信支付推送过来 支付结果通知
     */
    @RequestMapping(value = "notifyPayResult", method = RequestMethod.POST)
    @ResponseBody
    public String notifyPayResult(HttpServletRequest request) {

        return WechatPayUtils.NOTIFY_FAIL_XML;
    }

}
