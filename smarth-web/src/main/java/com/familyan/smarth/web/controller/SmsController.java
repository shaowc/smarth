package com.familyan.smarth.web.controller;

import com.familyan.smarth.manager.service.SmsService;
import com.familyan.smarth.utils.UserNameUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lotus.service.result.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by xifeng on 2015/11/19.
 */
@Controller
@RequestMapping("sms")
public class SmsController {

    /*
     * 一个手机号一分钟只能发一次
     */
    Cache<String,String> cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(1, TimeUnit.MINUTES).build();

    @Autowired
    SmsService smsService;

    @Value("${top.domain}")
    String topDomain;

    @Value("${app.domain}")
    String appDomain;

    @RequestMapping("send")
    @ResponseBody
    public Result<String> send(final String mobile,
                               @RequestHeader(name = "Referer",required = false) String referer,
                               HttpServletResponse response){
        if(!setAjaxCrossDomainHeader(referer,response))
            return Result.error("参数错误");

        boolean isMobile = StringUtils.isNotBlank(mobile) &&  UserNameUtil.isMobile(mobile);
        if(isMobile){
            try {
                return Result.success(cache.get(mobile, new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return smsService.sendSmsVerifyCode(mobile);
                    }
                }));
            } catch (ExecutionException e) {
                return Result.error("服务异常，稍后重试");
            }
        }else{
           return  Result.error("输入正确的手机号");
        }
    }

    private boolean setAjaxCrossDomainHeader(String referer, HttpServletResponse response) {
        if(StringUtils.isNotBlank(referer)){
            try {
                URL u = new URL(referer);
                if(u.getHost().endsWith(topDomain)){
                    response.setHeader("Access-Control-Allow-Origin",u.getProtocol()+"://"+u.getHost());
                    return true;
                }
            } catch (MalformedURLException e) {}
        }else{
            response.setHeader("Access-Control-Allow-Origin","http://"+appDomain);
            return true;
        }
        return false;
    }


}