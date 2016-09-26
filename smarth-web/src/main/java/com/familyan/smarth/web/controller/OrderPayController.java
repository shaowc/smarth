package com.familyan.smarth.web.controller;

import com.familyan.smarth.pay.OrderPay;
import com.familyan.smarth.pay.WechatOrderPay;
import com.lotus.service.result.Result;
import com.lotus.wechat.pay.WechatPayApi;
import com.lotus.wechat.pay.WechatPayUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by shaowenchao on 16/9/12.
 */
@RequestMapping("orderPay")
@Controller
public class OrderPayController {

    @Autowired
    private OrderPay orderPay;
    @Autowired
    private WechatPayApi wechatPayApi;

    @RequestMapping(value = "unified", method = RequestMethod.POST)
    @ResponseBody
    public Result createPayParams(Integer orderId, HttpServletRequest request) {
        Map<String, String> payParams = orderPay.unifiedorder(orderId, request.getRemoteAddr());
        if (payParams == null) {
            return Result.error("调用微信支付失败");
        }
        return Result.success(payParams);
    }

    /**
     * 处理 微信支付推送过来 支付结果通知
     */
    @RequestMapping(value = "notifyPayResult", method = RequestMethod.POST)
    @ResponseBody
    public String notifyPayResult(HttpServletRequest request) {
        try {
            try (InputStream in = request.getInputStream()) {
                String xml = IOUtils.toString(in, "UTF-8");
                Map<String, String> payResult = wechatPayApi.notify(xml);
                return orderPay.notify(payResult);
            }
        } catch (Exception e) {

        }
        return WechatPayUtils.NOTIFY_FAIL_XML;
    }

}
