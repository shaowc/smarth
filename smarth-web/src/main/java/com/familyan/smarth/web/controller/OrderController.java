package com.familyan.smarth.web.controller;

import com.familyan.smarth.domain.*;
import com.familyan.smarth.manager.OrderManager;
import com.familyan.smarth.manager.PacketManager;
import com.familyan.smarth.service.MemberService;
import com.lotus.service.result.PageResult;
import com.lotus.service.result.Result;
import com.lotus.wechat.WechatOpenId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/12.
 */
@RequestMapping("order")
@Controller
public class OrderController {

    @Autowired
    private OrderManager orderManager;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PacketManager packetManager;

    /**
     * 我的订单, 普通用户
     * @param loginMember
     * @param openId
     * @param modelMap
     * @return
     */
    @RequestMapping("list")
    public String list(LoginMember loginMember, WechatOpenId openId, Integer type, ModelMap modelMap) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setMemberId(loginMember.getId());
        PageResult<List<Order>> pageResult = orderManager.findByPage(orderDTO, 1, Integer.MAX_VALUE, "gmt_create DESC");
        modelMap.put("pageResult", pageResult);
        return "order/list";
    }


    /**
     * 预约下单
     *
     */
    @RequestMapping("place-packet")
    public String placePacket(LoginMember loginMember, Integer packetId, ModelMap modelMap) {
        Packet packet = packetManager.findById(packetId);
        modelMap.put("packet", packet);
        return "order/place-order-1";
    }

    /**
     * 预约下单
     *
     */
    @RequestMapping("place-checker")
    public String placeChecker(LoginMember loginMember, Integer packetId, ModelMap modelMap) {
        Packet packet = packetManager.findById(packetId);
        modelMap.put("packet", packet);
        return "order/place-order-2";
    }


    /**
     * 提交订单
     * @param loginMember
     * @param openId
     * @param order
     * @return
     */
    @RequestMapping("submit")
    @ResponseBody
    public Result submit(LoginMember loginMember, WechatOpenId openId, Order order) {
        order.setMemberId(loginMember.getId());
        Long checkerId = order.getCheckerId();
        MemberDTO memberDTO = memberService.findById(checkerId);
        if(memberDTO.getFeatures() == null || !memberDTO.getFeatures().contains(1)) {
            return Result.error("未选择快检手");
        }

        Packet packet = packetManager.findById(order.getPacketId());
        if(packet == null) {
            return Result.error("未选择体检包");
        }

        order.setStatus(0);
        orderManager.add(order);
        return Result.success(1);
    }

    /**
     * 订单详情
     * @param loginMember
     * @param openId
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("detail")
    public String detail(LoginMember loginMember, WechatOpenId openId, Integer id, ModelMap modelMap) {
        Order order = orderManager.findById(id);
        modelMap.put("order", order);
        return "order/detail";
    }

    /**
     * 接单
     * @param loginMember
     * @param openId
     * @param id
     * @return
     */
    @RequestMapping("receive")
    public Result receive(LoginMember loginMember, WechatOpenId openId, Integer id) {
        Order order = orderManager.findById(id);
        if(order == null) {
            return Result.error("订单不存在");
        }

        if(!order.getCheckerId().equals(loginMember.getId())) {
            return Result.error("不是您的订单，您无权限处理");
        }

        if(order.getStatus() != 0) {
            return Result.error("该订单已处理过");
        }

        order.setStatus(1);
        orderManager.modify(order);
        return Result.success(1);
    }

    /**
     * 拒绝接单
     * @param loginMember
     * @param openId
     * @param id
     * @return
     */
    @RequestMapping("refuse")
    public Result refuse(LoginMember loginMember, WechatOpenId openId, Integer id) {
        Order order = orderManager.findById(id);
        if(order == null) {
            return Result.error("订单不存在");
        }

        if(!order.getCheckerId().equals(loginMember.getId())) {
            return Result.error("不是您的订单，您无权限处理");
        }

        if(order.getStatus() != 0) {
            return Result.error("该订单已处理过");
        }

        order.setStatus(4);
        orderManager.modify(order);
        return Result.success(1);
    }

    /**
     * 填写体检报告，限体检手访问
     * @param loginMember
     * @param openId
     * @param order
     * @return
     */
    @RequestMapping("report")
    public Result report(LoginMember loginMember, WechatOpenId openId, Order order) {
        return Result.success(1);
    }

}

