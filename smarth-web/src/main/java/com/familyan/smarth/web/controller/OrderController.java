package com.familyan.smarth.web.controller;

import com.familyan.smarth.domain.*;
import com.familyan.smarth.manager.CheckerManager;
import com.familyan.smarth.manager.MemberLocationManager;
import com.familyan.smarth.manager.OrderManager;
import com.familyan.smarth.manager.PacketManager;
import com.familyan.smarth.service.MemberService;
import com.familyan.smarth.utils.LocationUtils;
import com.familyan.smarth.web.domain.OrderVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import com.lotus.service.result.Result;
import com.lotus.wechat.WechatOpenId;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private CheckerManager checkerManager;
    @Autowired
    private MemberLocationManager memberLocationManager;

    @InitBinder
    public  void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @Security
    public String list(LoginMember loginMember, ModelMap modelMap) {
        return "order/list";
    }

    @RequestMapping(value = "checkerlist", method = RequestMethod.GET)
    @Security
    public String checkers(LoginMember loginMember, ModelMap modelMap) {
        // 普通用户不允许访问
        if (!loginMember.containsFeature(1l)) {
            return "redirect: /order/list.htm";
        }

        return "order/checkerlist";
    }

    /**
     * 我的订单, 普通用户
     * @param loginMember
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public PageResult list(LoginMember loginMember, Integer type, Page page) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setMemberId(loginMember.getId());
        orderDTO.setStatus(type);
        PageResult<List<Order>> pageResult = orderManager.findByPage(orderDTO, page.getStart(), page.getPageSize(), "gmt_create DESC");
        List<Long> memberIds = Lists.transform(pageResult.getData(), new Function<Order, Long>() {
            @Override
            public Long apply(Order input) {
                return input.getCheckerId();
            }
        });
        List<Checker> checkers = checkerManager.findByMemberIds(memberIds);
        Map<Long, Checker> checkerMap = Maps.uniqueIndex(checkers, new Function<Checker, Long>() {
            @Override
            public Long apply(Checker input) {
                return input.getMemberId();
            }
        });
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<Long, MemberDTO> memberMap = memberService.findByIds(memberIds);
        List<OrderVO> data = new ArrayList<>();
        for(Order order : pageResult.getData()) {
            OrderVO orderVO = new OrderVO();
            orderVO.setId(order.getId());
            orderVO.setDate(dateFormat.format(order.getCheckupTime()));
            orderVO.setState(order.getStatus());
            orderVO.setName(memberMap.get(order.getCheckerId()).getRealName());
            orderVO.setPhone(memberMap.get(order.getCheckerId()).getMobile());
            orderVO.setImg(memberMap.get(order.getCheckerId()).getAvatar());
            orderVO.setAds(checkerMap.get(order.getCheckerId()).getDescription());
            data.add(orderVO);
        }

        return new PageResult<>(pageResult.getStart(), pageResult.getLimit(), pageResult.getTotal(), data);
    }

    /**
     * 我的订单, 普通用户
     * @param loginMember
     * @return
     */
    @RequestMapping(value = "checkerlist", method = RequestMethod.POST)
    @ResponseBody
    public PageResult checkers(LoginMember loginMember, Integer type, Page page) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCheckerId(loginMember.getId());
        orderDTO.setStatus(type);
        PageResult<List<Order>> pageResult = orderManager.findByPage(orderDTO, page.getStart(), page.getPageSize(), "gmt_create DESC");
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

        return new PageResult<>(pageResult.getStart(), pageResult.getLimit(), pageResult.getTotal(), data);
    }

    /**
     * 预约下单
     *
     */
    @RequestMapping("place-packet")
    public String placePacket(LoginMember loginMember, Integer packetId, ModelMap modelMap) {
        Packet packet = packetManager.findById(packetId);
        MemberLocation memberLocation = memberLocationManager.findByMemberId(loginMember.getId());
        modelMap.put("memberLocation", memberLocation);
        List<Checker> checkers = checkerManager.findByPacketId(packetId);
        modelMap.put("checkers", checkers);
        modelMap.put("items", packet.getContent().split(","));

        List<Long> memberIds = Lists.transform(checkers, new Function<Checker, Long>() {
            @Override
            public Long apply(Checker input) {
                return input.getMemberId();
            }
        });

        Map<Long, MemberDTO> memberMap = memberService.findByIds(memberIds);
        modelMap.put("memberMap", memberMap);

        if(!checkers.isEmpty()) {

            final Map<Long, Double> distanceMap = new HashMap<>();
            Map<Long, String> distanceUnitMap = new HashMap<>();
            List<MemberLocation> checkerLocations = memberLocationManager.findByMemberIds(memberIds);
            for (MemberLocation location : checkerLocations) {
                double distance = LocationUtils.getDistance(memberLocation.getLongitude().doubleValue(), memberLocation.getLatitude().doubleValue(), location.getLongitude().doubleValue(), location.getLatitude().doubleValue());
                distanceMap.put(location.getMemberId(), distance);
                String distanceWithUnit;
                if(distance < 1000){
                    distanceWithUnit = String.valueOf(distance) + "m";
                }else{
                    distanceWithUnit = String.valueOf(new BigDecimal(distance / 1000.0).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue()) + "km";
                }
                distanceUnitMap.put(location.getMemberId(), distanceWithUnit);

            }
            modelMap.put("distanceUnitMap", distanceUnitMap);
            Collections.sort(checkers, new Comparator<Checker>() {
                @Override
                public int compare(Checker o1, Checker o2) {
                    double distance1 = distanceMap.get(o1.getMemberId());
                    double distance2 = distanceMap.get(o2.getMemberId());
                    if(distance1 > distance2) {
                        return 1;
                    } else if(distance1 == distance2) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
        }

        modelMap.put("packet", packet);
        return "order/place-order-1";
    }

    /**
     * 预约下单
     *
     */
    @RequestMapping("place-checker")
    public String placeChecker(LoginMember loginMember, Long checkerId, ModelMap modelMap) {
        Checker checker = checkerManager.findByMemberId(checkerId);
        modelMap.put("checker", checker);
        MemberLocation memberLocation = memberLocationManager.findByMemberId(loginMember.getId());
        modelMap.put("memberLocation", memberLocation);
        List<Packet> packets = packetManager.findByMemberId(checkerId);
        modelMap.put("packets", packets);
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
        if(memberDTO.getFeatures() == null || !memberDTO.getFeatures().contains(1l)) {
            return Result.error("未选择快检手");
        }

        Packet packet = packetManager.findById(order.getPacketId());
        if(packet == null) {
            return Result.error("未选择体检包");
        }

        order.setPrice(packet.getPrice());
        order.setStatus(0);
        order.setPackageContent(packet.getContent());
        orderManager.add(order);
        return Result.success(order.getId());
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

        if(!order.getMemberId().equals(loginMember.getId())) {
            // 普通用户
            modelMap.put("msg", "不是您的订单，无权限访问");
            return "error";
        }
        Checker checker = checkerManager.findByMemberId(order.getCheckerId());
        Packet packet = packetManager.findById(order.getPacketId());
        modelMap.put("items", order.getPackageContent().split(","));
        if (order.getStatus() == 3) {
            modelMap.put("results", order.getCheckupResult().split(","));
        }
        modelMap.put("packet", packet);
        modelMap.put("checker", checker);
        modelMap.put("order", order);
        return "order/detail";
    }

    /**
     * 订单详情
     * @param loginMember
     * @param openId
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("checkerdetail")
    public String checkerdetail(LoginMember loginMember, WechatOpenId openId, Integer id, ModelMap modelMap) {
        Order order = orderManager.findById(id);

        if(!order.getCheckerId().equals(loginMember.getId())) {
            // 快检手
            modelMap.put("msg", "不是您的订单，无权限访问");
            return "error";
        }

        Checker checker = checkerManager.findByMemberId(order.getCheckerId());
        Packet packet = packetManager.findById(order.getPacketId());
        modelMap.put("items", order.getPackageContent().split(","));
        if (order.getStatus() == 3) {
            modelMap.put("results", order.getCheckupResult().split(","));
        }
        modelMap.put("packet", packet);
        modelMap.put("checker", checker);
        modelMap.put("order", order);
        return "order/checkerdetail";
    }

    /**
     * 接单，
     * @param loginMember
     * @param openId
     * @param id
     * @return
     */
    @RequestMapping("receive")
    @ResponseBody
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
     * 普通用户取消订单
     * @param loginMember
     * @param openId
     * @param id
     * @return
     */
    @RequestMapping("cancel")
    @ResponseBody
    public Result cancel(LoginMember loginMember, WechatOpenId openId, Integer id) {

        Order order = orderManager.findById(id);
        if(order == null) {
            return Result.error("订单不存在");
        }

        if (!order.getMemberId().equals(loginMember.getId())){
            // 下单人，取消订单
            return Result.error("不是您的订单，您不能取消");
        }

        if(order.getStatus() != 0) {
            return Result.error("该订单已处理过");
        }

        order.setStatus(4);
        orderManager.modify(order);
        return Result.success(order.getId());
    }


    /**
     * 快检手拒绝接单
     * @param loginMember
     * @param openId
     * @param id
     * @return
     */
    @RequestMapping("checkercancel")
    @ResponseBody
    public Result checkercancel(LoginMember loginMember, WechatOpenId openId, Integer id) {

        Order order = orderManager.findById(id);
        if(order == null) {
            return Result.error("订单不存在");
        }

        if (!loginMember.containsFeature(1l) || !order.getCheckerId().equals(loginMember.getId())) {
            // 快检手,拒绝接单
            return Result.error("不是您的订单，您无权限处理");
        }

        if(order.getStatus() != 0) {
            return Result.error("该订单已处理过");
        }

        order.setStatus(4);
        orderManager.modify(order);
        return Result.success(order.getId());
    }



    /**
     * 填写体检报告，限体检手访问
     *
     */
    @RequestMapping(value = "report", method = RequestMethod.GET)
    public String report(LoginMember loginMember, Integer orderId, ModelMap modelMap) {
        Order order = orderManager.findById(orderId);
        List<String> items = Arrays.asList(order.getPackageContent().split(","));
        modelMap.put("items", items);
        MemberDTO member = memberService.findById(order.getMemberId());
        modelMap.put("member", member);
        modelMap.put("order", order);
        return "order/report";
    }

    /**
     * 填写体检报告，限体检手访问
     *
     */
    @RequestMapping(value = "report", method = RequestMethod.POST)
    @ResponseBody
    public Result report(LoginMember loginMember, Integer orderId, Order order) {
        order.setStatus(3);
        orderManager.modify(order);
        return Result.success(1);
    }

}

