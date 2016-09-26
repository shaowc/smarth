package com.familyan.smarth.web.controller;

import com.familyan.smarth.domain.*;
import com.familyan.smarth.manager.OrderManager;
import com.familyan.smarth.manager.PacketManager;
import com.familyan.smarth.service.MemberService;
import com.familyan.smarth.web.domain.OrderVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import com.lotus.wechat.WechatOpenId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shaowenchao on 16/9/12.
 */
@RequestMapping("packet")
@Controller
public class PacketController {

    @Autowired
    private PacketManager packetManager;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private MemberService memberService;

    /**
     * 体检包列表，商城首页
     *
     * @param loginMember
     * @param openId
     * @param modelMap
     * @return
     */
    @RequestMapping("list")
    public String list(LoginMember loginMember, WechatOpenId openId, PacketDTO packetDTO, Page page, ModelMap modelMap) {
        PageResult<List<Packet>> pageResult = packetManager.findByPage(packetDTO, 0, Integer.MAX_VALUE, "gmt_create" );
        modelMap.put("result", pageResult);
        return "packet/list";
    }

    /**
     * 我的体检包
     *
     * @param loginMember
     * @param openId
     * @param modelMap
     * @return
     */
    @RequestMapping("mylist")
    public String mylist(LoginMember loginMember, WechatOpenId openId, ModelMap modelMap) {
        List<Packet> packets = packetManager.findByMemberId(loginMember.getId());
        modelMap.put("packets", packets);
        return "packet/mylist";
    }

    @RequestMapping("detail")
    public String detail(LoginMember loginMember, WechatOpenId openId, Integer id, ModelMap modelMap) {
        Packet packet = packetManager.findById(id);
        modelMap.put("packet", packet);
        String content = packet.getContent();
        modelMap.put("contents", content.split(","));
        return "packet/detail";
    }

    /**
     * 服务日程，已接单的
     *
     * @param loginMember
     * @param openId
     * @param packetId
     * @param modelMap
     * @return
     */
    @RequestMapping("schedule")
    public String schedule(LoginMember loginMember, WechatOpenId openId, Integer packetId, ModelMap modelMap) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCheckerId(loginMember.getId());
        orderDTO.addStatus(1);
        orderDTO.addStatus(2);
        orderDTO.addStatus(3);
        PageResult<List<Order>> pageResult = orderManager.findByPage(orderDTO, 0, Integer.MAX_VALUE, "checkup_time");
        List<Long> memberIds = Lists.transform(pageResult.getData(), new Function<Order, Long>() {
            @Override
            public Long apply(Order input) {
                return input.getMemberId();
            }
        });
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<Long, MemberDTO> memberMap = memberService.findByIds(memberIds);
        List<OrderVO> data = new ArrayList<>();
        for(Order order : pageResult.getData()) {
            OrderVO orderVO = new OrderVO();
            orderVO.setId(order.getId());
            orderVO.setState(order.getStatus());
            orderVO.setDate(dateFormat.format(order.getCheckupTime()));
            orderVO.setName(memberMap.get(order.getMemberId()).getRealName());
            orderVO.setPhone(memberMap.get(order.getMemberId()).getMobile());
            orderVO.setImg(memberMap.get(order.getMemberId()).getAvatar());
            orderVO.setAds(order.getCity() + order.getAddress());
            data.add(orderVO);
        }
        modelMap.put("orders", data);
        return "packet/schedule";
    }
}
