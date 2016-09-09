package com.familyan.smarth.web.interceptor;

import com.familyan.smarth.domain.LoginMember;
import com.lotus.core.web.cookyjar.Cookyjar;
import com.lotus.core.web.security.SecurityHandlerInterceptor;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shaowenchao on 16/9/9.
 */
public class MemberLoginInterceptor extends SecurityHandlerInterceptor<LoginMember> {


    Long checkInterval = 5 * 60 * 1000l; //默认5分钟检查一次用户状态


    public boolean hasPermission(LoginMember loginMember,String code, HandlerMethod hm, HttpServletRequest request) {
        return true;
    }

    /**
     * 如果nextCheckTime 小于当前时间,说明需要检查
     * @param loginMember
     * @param cookyjar
     * @return
     */
    @Override
    protected boolean preCheck(LoginMember loginMember, Cookyjar cookyjar) {
        long now = System.currentTimeMillis();
        if(loginMember.getNextCheckTime() <= now){
            //更新下一个检查时间点
            loginMember.setNextCheckTime(now+checkInterval);
            cookyjar.set(loginMember);
        }
        return true;
    }

    public void setCheckInterval(Long checkInterval) {
        if(checkInterval == null || checkInterval < 1)
            throw new IllegalArgumentException("checkInterval can not be null or less then 1");
        this.checkInterval = checkInterval;
    }


}
