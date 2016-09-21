package com.familyan.smarth.pay;

import com.familyan.smarth.domain.MemberWechatDO;
import com.familyan.smarth.domain.Order;
import com.familyan.smarth.manager.MemberWechatManager;
import com.familyan.smarth.manager.OrderManager;
import com.lotus.wechat.pay.UnifiedorderReq;
import com.lotus.wechat.pay.UnifiedorderResp;
import com.lotus.wechat.pay.WechatPayApi;
import com.lotus.wechat.pay.WechatPayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by shaowenchao on 16/9/21.
 */
public class WechatOrderPay implements OrderPay {

    private Logger logger = Logger.getLogger(WechatOrderPay.class);

    private String payPrefix = "otn";
    private String refundPrefix = "orn";
    @Value("${app.domain}")
    private String appDomain;

    @Autowired
    private WechatPayApi wechatPayApi;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private MemberWechatManager wechatManager;

    @Override
    public Map<String, String> unifiedorder(Integer orderId, String ip) {
        String prepayId = getPrepayId(orderId, ip);
        if (StringUtils.isNotBlank(prepayId)) {
            return wechatPayApi.createH5PayParams(prepayId);
        }

        return null;
    }

    protected String getPrepayId(Integer orderId, String ip) {
        Order order = orderManager.findById(orderId);
        if (order != null && order.getPayStatus() == 0) {
            Integer payFee = order.getPrice();
            // 先检查缓存的prepayId
            if (StringUtils.isNotBlank(order.getPrepayId()) && order.getGmtPrepayIdValid() != null) {
                if (new Date().before(order.getGmtPrepayIdValid())) {
                    return order.getPrepayId();
                }
            }

            String randStr = createRandStr();
            String outTradeNo = payPrefix + randStr;
            UnifiedorderReq req = new UnifiedorderReq();
            req.setBody("智慧健康-小马快检");
            req.setOutTradeNo(outTradeNo);
            req.setTotalFee(payFee);
            req.setSpbillCreateIp(ip);
            req.setNotifyUrl("http://" + appDomain + "/orderPay/notifyPayResult.htm");
            req.setTradeType("JSAPI");
            MemberWechatDO memberWechat = wechatManager.findByMemberId("smarth", order.getMemberId());
            if(memberWechat == null) {
                return null;
            }
            req.setOpenid(memberWechat.getOpenId());
            UnifiedorderResp resp = wechatPayApi.unifiedorder(req);
            if ("SUCCESS".equals(resp.getReturnCode()) && "SUCCESS".equals(resp.getResultCode()) && StringUtils.isNotBlank(resp.getPrepayId())) {
                Order updateVo = new Order();
                updateVo.setId(orderId);
                updateVo.setOutTradeNo(outTradeNo);
                updateVo.setPrepayId(resp.getPrepayId());
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, 1);
                updateVo.setGmtPrepayIdValid(calendar.getTime());
                orderManager.modify(updateVo);
                return resp.getPrepayId();
            }
        }
        return null;
    }

    protected static String createRandStr() {
        String chars = "ABCDEFJHIJKLMNOPQRJSWVYZYX12345678910abcdefjhijklmnopqrjswvyzyx";
        StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            int rand = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(rand));
        }
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + sb.toString();
    }

    @Override
    public String notify(Map<String, String> payResult) {
        if ("SUCCESS".equals(payResult.get("return_code")) && "SUCCESS".equals(payResult.get("result_code"))) {
            // 支付成功处理
            paySuccess(payResult);
        } else {
            // 支付失败处理
            payFailed(payResult);
        }

        return WechatPayUtils.NOTIFY_SUCCESS_XML;
    }

    protected void paySuccess(Map<String, String> payResult) {
        String outTradeNo = payResult.get("out_trade_no");
        Order order = orderManager.findByOutTradeNo(outTradeNo);
        if (order != null && order.getPayStatus() == 0) {
            Order updateVo = new Order();
            updateVo.setId(order.getId());
            updateVo.setPayStatus(1);
            updateVo.setStatus(2);
            updateVo.setGmtPay(new Date());
            orderManager.modify(updateVo);
        }
    }

    protected void payFailed(Map<String, String> payResult) {
        // ignore
    }
}
