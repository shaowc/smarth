package com.familyan.smarth.eventbus;

import com.familyan.smarth.domain.MemberDTO;
import com.familyan.smarth.domain.MemberWechatDO;
import com.familyan.smarth.domain.Order;
import com.familyan.smarth.manager.MemberWechatManager;
import com.familyan.smarth.service.MemberService;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.lotus.wechat.WechatApi;
import com.lotus.wechat.WechatException;
import com.lotus.wechat.WechatTemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by shaowenchao on 16/9/27.
 */
public class OrderEventListener implements EventListener {

    private String templateId = "sDvXHQjcV7Q7_FMaQNQv743W4yDbmLZQujZzyqKVqG4";

    @Value("${app.domain}")
    private String appDomain;

    @Autowired
    private WechatApi api;
    @Autowired
    private MemberWechatManager wechatManager;
    @Autowired
    private MemberService memberService;

    @Subscribe
    public void onEvent(OrderEvent orderEvent) throws WechatException {
        Order order = orderEvent.order;
        int type = orderEvent.type;
        switch (type) {
            case OrderEvent.CREATED: onOrderCreated(order);
                break;
            case OrderEvent.RECEIVED: onReceived(order);
                break;
            case OrderEvent.PAIED: onPaied(order);
                break;
            case OrderEvent.REPORTED: onRepored(order);
                break;
            case OrderEvent.CANCELD: onCanceled(order);
                break;
            case OrderEvent.REFUSED: onRefused(order);
                break;
        }
    }

    private void onOrderCreated(Order order) throws WechatException {
        Map<Long, MemberDTO> memberDTOMap = memberService.findByIds(Lists.newArrayList(order.getMemberId(), order.getCheckerId()));
        WechatTemplateRequest request =null;
        String title;
        String name;
        String remark;
        String url;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = simpleDateFormat.format(order.getCheckupTime());
        String city = order.getCity();
        String address = order.getAddress();
        // 给自己发微信消息
//        MemberWechatDO creator = wechatManager.findByMemberId("smarth", order.getMemberId());
//        request = new WechatTemplateRequest();
//        request.setTouser(creator.getOpenId());
//        request.setTemplateId(templateId);
//        title = "您好，您已成功提交体检预约单";
//        name = memberDTOMap.get(order.getCheckerId()).getRealName();
//        remark = "请耐心等待快检手接单，保持电话畅通";
//        url = "http://" + appDomain + "/order/detail.htm?id=" + order.getId();
//        request.setUrl(url);
//        request.addData("first", new WechatTemplateRequest.Value(title));
//        request.addData("keyword1", new WechatTemplateRequest.Value(name));
//        request.addData("keyword2", new WechatTemplateRequest.Value(time));
//        request.addData("keyword3", new WechatTemplateRequest.Value(city));
//        request.addData("keyword4", new WechatTemplateRequest.Value(address));
//        request.addData("ramark", new WechatTemplateRequest.Value(remark));
//        api.templateMessageSend(request);

        // 给快检手发微信模板消息
        MemberWechatDO checker = wechatManager.findByMemberId("smarth", order.getCheckerId());
        request = new WechatTemplateRequest();
        request.setTouser(checker.getOpenId());
        request.setTemplateId(templateId);
        title = "您好，您有新的订单";
        name = memberDTOMap.get(order.getCheckerId()).getRealName();
        remark = "接单前请联系客户确定具体信息";
        url = "http://" + appDomain + "/order/checkerdetail.htm?id=" + order.getId();
        request.setUrl(url);
        request.addData("first", new WechatTemplateRequest.Value(title));
        request.addData("keyword1", new WechatTemplateRequest.Value(name));
        request.addData("keyword2", new WechatTemplateRequest.Value(time));
        request.addData("keyword3", new WechatTemplateRequest.Value(city));
        request.addData("keyword4", new WechatTemplateRequest.Value(address));
        request.addData("ramark", new WechatTemplateRequest.Value(remark));
        api.templateMessageSend(request);

    }

    private void onReceived(Order order) throws WechatException {
        // 通知客户：您的订单成功接单了 快去支付
        WechatTemplateRequest request =null;
        String title;
        String name;
        String remark;
        String url;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        MemberDTO memberDTO = memberService.findById(order.getMemberId());
        String time = simpleDateFormat.format(order.getCheckupTime());
        String city = order.getCity();
        String address = order.getAddress();
        MemberWechatDO wechatDO = wechatManager.findByMemberId("smarth", order.getMemberId());
        request = new WechatTemplateRequest();
        request.setTouser(wechatDO.getOpenId());
        request.setTemplateId(templateId);
        title = "您的订单成功接单了 快去支付";
        name = memberDTO.getRealName();
        remark = "支付后，请保持手机畅通，方便快检手联系";
        url = "http://" + appDomain + "/order/detail.htm?id=" + order.getId();
        request.setUrl(url);
        request.addData("first", new WechatTemplateRequest.Value(title));
        request.addData("keyword1", new WechatTemplateRequest.Value(name));
        request.addData("keyword2", new WechatTemplateRequest.Value(time));
        request.addData("keyword3", new WechatTemplateRequest.Value(city));
        request.addData("keyword4", new WechatTemplateRequest.Value(address));
        request.addData("ramark", new WechatTemplateRequest.Value(remark));
        api.templateMessageSend(request);
    }

    private void onPaied(Order order) throws WechatException {
        // 通知快检手：客户订单已支付，赶快联系客户
        WechatTemplateRequest request;
        String title;
        String name;
        String remark;
        String url;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        MemberDTO memberDTO = memberService.findById(order.getCheckerId());
        MemberWechatDO wechatDO = wechatManager.findByMemberId("smarth", order.getCheckerId());

        String time = simpleDateFormat.format(order.getCheckupTime());
        String city = order.getCity();
        String address = order.getAddress();
        request = new WechatTemplateRequest();
        request.setTouser(wechatDO.getOpenId());
        request.setTemplateId(templateId);
        title = "您的订单成功接单了 快去支付";
        name = memberDTO.getRealName();
        remark = "支付后，请保持手机畅通，方便快检手联系";
        url = "http://" + appDomain + "/order/checkerdetail.htm?id=" + order.getId();
        request.setUrl(url);
        request.addData("first", new WechatTemplateRequest.Value(title));
        request.addData("keyword1", new WechatTemplateRequest.Value(name));
        request.addData("keyword2", new WechatTemplateRequest.Value(time));
        request.addData("keyword3", new WechatTemplateRequest.Value(city));
        request.addData("keyword4", new WechatTemplateRequest.Value(address));
        request.addData("ramark", new WechatTemplateRequest.Value(remark));
        api.templateMessageSend(request);
    }

    private void onRepored(Order order) throws WechatException {
        // 通知客户：您的订单已有体检结果
        WechatTemplateRequest request;
        String title;
        String name;
        String remark;
        String url;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        MemberDTO memberDTO = memberService.findById(order.getMemberId());
        MemberWechatDO wechatDO = wechatManager.findByMemberId("smarth", order.getMemberId());

        String time = simpleDateFormat.format(order.getCheckupTime());
        String city = order.getCity();
        String address = order.getAddress();
        request = new WechatTemplateRequest();
        request.setTouser(wechatDO.getOpenId());
        request.setTemplateId(templateId);
        title = "您的订单已有体检结果";
        name = memberDTO.getRealName();
        remark = "如有不解，请联系快检手";
        url = "http://" + appDomain + "/order/detail.htm?id=" + order.getId();
        request.setUrl(url);
        request.addData("first", new WechatTemplateRequest.Value(title));
        request.addData("keyword1", new WechatTemplateRequest.Value(name));
        request.addData("keyword2", new WechatTemplateRequest.Value(time));
        request.addData("keyword3", new WechatTemplateRequest.Value(city));
        request.addData("keyword4", new WechatTemplateRequest.Value(address));
        request.addData("ramark", new WechatTemplateRequest.Value(remark));
        api.templateMessageSend(request);
    }

    private void onCanceled(Order order) {

    }

    private void onRefused(Order order) throws WechatException {
        // 通知客户，订单被拒绝，试试其他体检手
        WechatTemplateRequest request;
        String title;
        String name;
        String remark;
        String url;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        MemberDTO memberDTO = memberService.findById(order.getMemberId());
        MemberWechatDO wechatDO = wechatManager.findByMemberId("smarth", order.getMemberId());

        String time = simpleDateFormat.format(order.getCheckupTime());
        String city = order.getCity();
        String address = order.getAddress();
        request = new WechatTemplateRequest();
        request.setTouser(wechatDO.getOpenId());
        request.setTemplateId(templateId);
        title = "订单被拒绝，试试其他体检手";
        name = memberDTO.getRealName();
        remark = "";
        url = "http://" + appDomain + "/order/detail.htm?id=" + order.getId();
        request.setUrl(url);
        request.addData("first", new WechatTemplateRequest.Value(title));
        request.addData("keyword1", new WechatTemplateRequest.Value(name));
        request.addData("keyword2", new WechatTemplateRequest.Value(time));
        request.addData("keyword3", new WechatTemplateRequest.Value(city));
        request.addData("keyword4", new WechatTemplateRequest.Value(address));
        request.addData("ramark", new WechatTemplateRequest.Value(remark));
        api.templateMessageSend(request);
    }

    public static class OrderEvent {

        public static final int CREATED = 0;
        public static final int RECEIVED = 1;
        public static final int PAIED = 2;
        public static final int REPORTED = 3;
        public static final int CANCELD = 4; // 取消订单
        public static final int REFUSED = 5; // 拒绝接单

        Order order;

        int type;

        public OrderEvent(Order order, int type) {
            this.order = order;
            this.type = type;
        }



    }


}
