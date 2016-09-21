package com.familyan.smarth.function;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.familyan.smarth.domain.Member;
import com.familyan.smarth.domain.MemberDTO;
import com.familyan.smarth.domain.MemberLocation;
import com.familyan.smarth.domain.MemberWechatDO;
import com.familyan.smarth.manager.MemberLocationManager;
import com.familyan.smarth.manager.MemberWechatManager;
import com.familyan.smarth.service.MemberService;
import com.lotus.wechat.WechatException;
import com.lotus.wechat.message.WechatEvent;
import com.lotus.wechat.message.WechatFunction;
import com.lotus.wechat.message.WechatMessage;
import com.lotus.wechat.message.WechatMessageResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by shaowenchao on 6/17/16.
 */
public class LocationEventFunction implements WechatFunction {

    static String GEOCODING_URL_FORMAT = "http://api.map.baidu.com/geocoder/v2/?ak=71dbfbd705d6005b468ea7c679d827f9&coordtype=wgs84ll&location=%s,%s&output=json";

    @Autowired
    private MemberWechatManager memberWechatManager;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberLocationManager memberLocationManager;

    @Override
    public boolean support(WechatMessage message) {
        if(message instanceof WechatEvent) {
            WechatEvent e = ((WechatEvent)message);
            String event = e.getEvent();
            if(StringUtils.equals(event, WechatEvent.EVENT_LOCATION)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public WechatMessageResponse process(WechatMessage message) throws WechatException {
        WechatEvent event = (WechatEvent)message;
        MemberWechatDO memberWechatDO = memberWechatManager.findByOpenId("smarth", event.getFromUserName());
        MemberDTO memberDTO = memberService.findById(memberWechatDO.getMemberId());
        MemberLocation memberLocation = memberLocationManager.findByMemberId(memberDTO.getId());
        if(memberLocation == null) {
            // 获取当前位置名称
            String ret = httpGet(String.format(GEOCODING_URL_FORMAT, event.getLatitude(), event.getLongitude()));
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
                    memberLocation.setType(memberDTO.getFeatures().contains(1) ? 1:0);
                    memberLocation.setProvince(province);
                    memberLocation.setCity(city);
                    memberLocation.setCounty(county);
                }

            }
        }
        memberLocation.setLongitude(new BigDecimal(event.getLongitude()));
        memberLocation.setLatitude(new BigDecimal(event.getLatitude()));
        memberLocationManager.save(memberLocation);
        return null;
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

    public static void main(String[] args) {

    }

}
