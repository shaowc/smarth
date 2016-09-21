package com.familyan.smarth.web.interceptor;

import com.familyan.smarth.domain.LoginMember;
import com.familyan.smarth.domain.MemberDTO;
import com.familyan.smarth.domain.MemberLocation;
import com.familyan.smarth.manager.MemberLocationManager;
import com.familyan.smarth.service.MemberService;
import com.familyan.smarth.web.domain.Location;
import com.lotus.core.web.cookyjar.Cookyjar;
import com.lotus.wechat.WechatApi;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取用户位置信息，如果公众号未推送消息，则用网页jssdk获取
 *
 * Created by shaowenchao on 16/9/21.
 */
public class LocationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberLocationManager memberLocationManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookyjar cookyjar = (Cookyjar) request.getAttribute(Cookyjar.CookyjarInRequest);
        LoginMember loginMember = cookyjar.getObject(LoginMember.class);
        if (loginMember != null) {
            Location location = cookyjar.getObject(Location.class);
            if(location == null) {
                MemberLocation memberLocation = memberLocationManager.findByMemberId(loginMember.getId());
                if(memberLocation == null) {
                    redirectToLocation(request, response);
                    return false;
                }
                location = new Location();
                location.setCity(memberLocation.getProvince() + memberLocation.getCity() + memberLocation.getCounty());
                location.setLongitude(memberLocation.getLongitude().toString());
                location.setLatitude(memberLocation.getLatitude().toString());
                cookyjar.set(location, 3600);
            }

        }
        return super.preHandle(request, response, handler);
    }

    private void redirectToLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = getRequestURL(request);
        String redirectTo = "/location.htm?url="+ WechatApi.encode(requestUrl);
        response.sendRedirect(redirectTo);
    }

    /**
     * 只支持部署在 80 或者 443 端口， 要求nginx 转发
     * @param request
     * @return
     */
    private String getRequestURL(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme());
        builder.append("://");
        String host = request.getHeader("Host");
        if(StringUtils.isBlank(host))
            host = "localhost";
        builder.append(host);
        String context = request.getContextPath();
        if(!context.equals("/")){
            builder.append(context);
        }

        builder.append(request.getRequestURI());
        if(StringUtils.isNotBlank(request.getQueryString())){
            builder.append("?"+request.getQueryString());
        }

        return builder.toString();
    }


}
