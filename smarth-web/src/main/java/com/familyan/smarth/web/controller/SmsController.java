package com.familyan.smarth.web.controller;

import com.familyan.smarth.manager.VerifyCodeManager;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("sms")
public class SmsController {

    /*
     * 一个手机号一分钟只能发一次
     */
    Cache<String,String> cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(1, TimeUnit.MINUTES).build();

    @Autowired
    VerifyCodeManager verifyCodeManager;

    @Value("${top.domain}")
    String topDomain;

    @Value("${app.domain}")
    String appDomain;

    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> send(final String mobile){

        boolean isMobile = StringUtils.isNotBlank(mobile) &&  UserNameUtil.isMobile(mobile);
        if(isMobile){
//            return Result.success("123456");
            try {
                return Result.success(cache.get(mobile, new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return verifyCodeManager.send(mobile);
                    }
                }));
            } catch (ExecutionException e) {
                return Result.error("服务异常，稍后重试");
            }
        }else{
           return  Result.error("输入正确的手机号");
        }
    }


}
