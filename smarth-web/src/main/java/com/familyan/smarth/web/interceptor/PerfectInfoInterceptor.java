package com.familyan.smarth.web.interceptor;

import com.familyan.smarth.domain.LoginMember;
import com.familyan.smarth.domain.MemberDTO;
import com.familyan.smarth.service.MemberService;
import com.lotus.core.web.cookyjar.Cookyjar;
import com.lotus.wechat.WechatApi;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 完善个人信息
 * 除了体检包列表页，都需要完整信息才能访问
 *
 * Created by shaowenchao on 16/9/19.
 */
public class PerfectInfoInterceptor extends HandlerInterceptorAdapter {

    private String redirectUrl = "/perfect-info.htm";

    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //
        Cookyjar cookyjar = (Cookyjar) request.getAttribute(Cookyjar.CookyjarInRequest);
        LoginMember loginMember = cookyjar.getObject(LoginMember.class);
        if (loginMember != null) {
            MemberDTO memberDTO = memberService.findById(loginMember.getId());
            if(StringUtils.isBlank(memberDTO.getRealName()) ) {
                redirectToPerfectInfo(request, response);
            }
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    private void redirectToPerfectInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = getRequestURL(request);
        String redirectTo = redirectUrl+"?url="+ WechatApi.encode(requestUrl);
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

    public void setRedirectUrl(String redirectUrl) {
        if(StringUtils.isBlank(redirectUrl))
            throw new IllegalArgumentException("redirectUrl can not be null or empty ");
        this.redirectUrl = redirectUrl;
    }

}
