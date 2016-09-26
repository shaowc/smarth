package com.familyan.smarth.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.familyan.smarth.constants.BindType;
import com.familyan.smarth.domain.LoginMember;
import com.familyan.smarth.domain.MemberDTO;
import com.familyan.smarth.domain.MemberLocation;
import com.familyan.smarth.manager.MemberLocationManager;
import com.familyan.smarth.manager.service.SmsService;
import com.familyan.smarth.service.MemberService;
import com.lotus.service.result.Result;
import com.lotus.wechat.WechatOpenId;
import org.apache.commons.io.IOUtils;
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
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by shaowenchao on 16/9/9.
 */
@RequestMapping
@Controller
public class HomeController {

    static String GEOCODING_URL_FORMAT = "http://api.map.baidu.com/geocoder/v2/?ak=71dbfbd705d6005b468ea7c679d827f9&coordtype=wgs84ll&location=%s,%s&output=json";

    private static Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private SmsService smsService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberLocationManager memberLocationManager;

    /**
     * 绑定手机号
     *
     */
    @RequestMapping(value = "bind-phone", method = RequestMethod.GET)
    public String bindPhone(LoginMember loginMember, String url, ModelMap modelMap) {
        modelMap.put("url", url);
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
    public Result bindPhone(LoginMember loginMember, String mobile, String identifier, String code, String latitude, String longitude) {
//        boolean valid = smsService.validateSmsVerifyCode(identifier, code, mobile);
//        if (!valid) {
//            return Result.error("验证码错误");
//        }

        // 绑定手机号
        boolean bind = memberService.bind(loginMember.getId(), BindType.MOBILE, mobile);
        MemberLocation memberLocation = memberLocationManager.findByMemberId(loginMember.getId());
        if(memberLocation == null) {
            MemberDTO memberDTO = memberService.findById(loginMember.getId());
            // 获取当前位置名称
            String ret = httpGet(String.format(GEOCODING_URL_FORMAT, latitude, longitude));
            JSONObject jsonObject = JSON.parseObject(ret);
            if(jsonObject.getIntValue("status") == 0) {
                JSONObject result  = jsonObject.getJSONObject("result");
                if(result != null) {
                    JSONObject addressComponent = result.getJSONObject("addressComponent");
                    String province = addressComponent.getString("province");
                    String city = addressComponent.getString("city");
                    String county = addressComponent.getString("district");
                    memberLocation = new MemberLocation();
                    memberLocation.setMemberId(memberDTO.getId());
                    memberLocation.setType(0);
                    memberLocation.setProvince(province);
                    memberLocation.setCity(city);
                    memberLocation.setCounty(county);
                    memberLocation.setLatitude(new BigDecimal(latitude));
                    memberLocation.setLongitude(new BigDecimal(longitude));
                    memberLocationManager.save(memberLocation);
                }

            }

        }

        return Result.success(1);
    }

    private static String httpGet(String url) {
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(2000);
            InputStream in = connection.getInputStream();
            return IOUtils.toString(in, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("访问百度api失败");
        }
    }

    /**
     * 完善个人信息页面，所有未完善信息的用户都会被强制定位到该页面
     *
     * @param loginMember
     * @param openId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "perfect-info", method = RequestMethod.GET)
    public String perfectInfo(LoginMember loginMember, WechatOpenId openId, String url, ModelMap modelMap) {
        modelMap.put("url", url);
        return "perfect-info";
    }


    /**
     * 保存后跳转到来时的页面
     *
     * @param loginMember
     * @param openId
     * @return
     */
    @RequestMapping(value = "perfect-info", method = RequestMethod.POST)
    @ResponseBody
    public Result perfectInfo(LoginMember loginMember, WechatOpenId openId, MemberDTO memberDTO) {
        memberDTO.setId(loginMember.getId());
        memberService.updateMember(memberDTO);
        return Result.success(1);
    }

}
