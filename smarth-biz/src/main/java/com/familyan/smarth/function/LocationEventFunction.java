package com.familyan.smarth.function;

import com.lotus.wechat.WechatException;
import com.lotus.wechat.message.WechatEvent;
import com.lotus.wechat.message.WechatFunction;
import com.lotus.wechat.message.WechatMessage;
import com.lotus.wechat.message.WechatMessageResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by shaowenchao on 6/17/16.
 */
public class LocationEventFunction implements WechatFunction {


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
        // 更新用户当前位置

        return null;
    }

    private static String httpGet(String url) {
        try {
            URL u = new URL(url);
            URLConnection connection = u.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(2000);
            InputStream in = connection.getInputStream();
            return IOUtils.toString(in);
        } catch (IOException e) {
            throw new RuntimeException("访问百度api失败");
        }
    }

    public static void main(String[] args) {

    }

}
