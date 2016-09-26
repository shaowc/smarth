package com.familyan.smarth.web.controller;

import com.familyan.smarth.domain.Checker;
import com.familyan.smarth.domain.LoginMember;
import com.familyan.smarth.domain.MemberDTO;
import com.familyan.smarth.domain.MemberLocation;
import com.familyan.smarth.manager.CheckerManager;
import com.familyan.smarth.manager.MemberLocationManager;
import com.familyan.smarth.manager.service.SmsService;
import com.familyan.smarth.service.MemberService;
import com.familyan.smarth.utils.LocationUtils;
import com.familyan.smarth.utils.WechatImgTransfer;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.Result;
import com.lotus.wechat.WechatOpenId;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberLocationManager memberLocationManager;
    @Autowired
    private WechatImgTransfer wechatImgTransfer;

    @RequestMapping(value = "reg", method = RequestMethod.GET)
    @Security
    public String reg(LoginMember loginMember, ModelMap modelMap) {
        Checker checker = checkerManager.findByMemberId(loginMember.getId());
        if(checker != null) {
            return "redirect:/packet/mylist.htm";
        }
        MemberLocation memberLocation = memberLocationManager.findByMemberId(loginMember.getId());
        modelMap.put("memberLocation", memberLocation);
        return "checker/reg";
    }

    /**
     * 注册体检手，绑定手机号，完善个人信息
     * 完善个人信息时，已经绑定手机号
     *
     * @param loginMember
     * @param openId
     * @param checker
     * @return
     */
    @RequestMapping(value = "reg", method = RequestMethod.POST)
    @ResponseBody
    @Security
    public Result doreg(LoginMember loginMember, WechatOpenId openId, Checker checker, String wexinpic) {
        Checker exist = checkerManager.findByMemberId(loginMember.getId());
        MemberDTO memberDTO = memberService.findById(loginMember.getId());
        if (exist != null) {
            checker.setId(exist.getId());
        }
        checker.setMemberId(loginMember.getId());
        checker.setGender(memberDTO.getGender());
        checker.setName(memberDTO.getRealName());
        checker.setMobile(memberDTO.getMobile());
        List<String> newImgs = Arrays.asList(wexinpic.split(","));
//        List<Integer> pics = wechatImgTransfer.transfer(newImgs);
        checker.setIdentifyPic(wechatImgTransfer.transfer(newImgs.get(0)));
        checker.setIdentifyPic1(wechatImgTransfer.transfer(newImgs.get(0)));
        checker.setIdentifyPic2(wechatImgTransfer.transfer(newImgs.get(0)));
        checker.setQualificationPic(wechatImgTransfer.transfer(newImgs.get(0)));

        checkerManager.save(checker);
        memberService.addFeature(checker.getMemberId(), 1l);
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
        List<Checker> checkers = checkerManager.findMemberChecker(loginMember.getId());
        MemberLocation memberLocation = memberLocationManager.findByMemberId(loginMember.getId());
        modelMap.put("checkers", checkers);
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
                    if (distance1 > distance2) {
                        return 1;
                    } else if (distance1 == distance2) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
        }
        return "checker/list";
    }

}
