package com.familyan.smarth.web.interceptor;

import com.lotus.core.web.control.Control;
import com.lotus.core.web.filter.PlatformFilter;
import com.lotus.wechat.WechatApi;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 自动生成JS签名
 * Created by shaowenchao on 16/6/25.
 */
public class WechatJsInterceptor extends HandlerInterceptorAdapter {

    public static String WECHAT_JS_KEY = "_wechat_js";

    private static Logger logger = Logger.getLogger(WechatJsInterceptor.class);

    @Autowired
    private WechatApi api;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!isControl(request) && StringUtils.equals((String) request.getAttribute(PlatformFilter.key), PlatformFilter.PLATFORM_WEIXIN)
                && !StringUtils.equalsIgnoreCase(request.getMethod(), "post") && !this.isAjax(request)
                // forward
                && request.getAttribute(WECHAT_JS_KEY) == null) {

            WechatJs wechatJs = new WechatJs();
            long timestamp = System.currentTimeMillis() / 1000;
            String nonceStr = UUID.randomUUID().toString();
            wechatJs.setAppId(api.getAppKey()).setNonceStr(nonceStr).setTimestamp(timestamp);
            String signature = WechatApi.sign(api.jsTicket(), nonceStr, timestamp, getFullRequestUrl(request));
            wechatJs.setSignature(signature);
            request.setAttribute(WECHAT_JS_KEY, wechatJs);
            logger.debug("wechat js sign : " + wechatJs);

        }
        return super.preHandle(request, response, handler);
    }

    private boolean isControl(HttpServletRequest request) {
        return request.getAttribute(Control.IS_CONTROL) != null;
    }

    private boolean isAjax(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    private String getFullRequestUrl(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getRequestURL());
        if (StringUtils.isNotBlank(request.getQueryString())) {
            sb.append("?").append(request.getQueryString());
        }
        return sb.toString();
    }
}
