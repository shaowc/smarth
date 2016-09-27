package com.familyan.smarth.manager.impl;

import com.familyan.smarth.manager.VerifyCodeManager;
import com.familyan.smarth.utils.RandomUtils;
import com.familyan.smarth.utils.UUIDUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.junc.sms.service.JwsSmsSend;
import com.junc.sms.service.JwsSmsSendServiceLocator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

/**
 * 内存存储验证码，最大并发10000
 * 以后考虑数据库存储
 *
 * Created by shaowenchao on 16/9/27.
 */
@Service
public class VerifyCodeManagerImpl implements VerifyCodeManager, InitializingBean {

    private static final Logger logger = Logger.getLogger(VerifyCodeManager.class);
    private static final String VERIFY_CODE_MSG = "尊敬的用户，您的验证码为%s，本验证码5分钟内有效，感谢您使用";

    String username = "zhjk8";
    String password = DigestUtils.md5Hex(username + DigestUtils.md5Hex("123456"));
    JwsSmsSend jwsSmsSend;

    /*
     * 手机号验证码identifier
     */
    Cache<String,String> identifierCache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(5, TimeUnit.MINUTES).build();

    /*
     * identifier -> 验证码cache
     */
    Cache<String,String> codeCache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(5, TimeUnit.MINUTES).build();

    @Override
    public String send(String mobile) {
        // 未过期，用原有的
        String identifier = identifierCache.getIfPresent(mobile);
        String code = null;
        if(identifier != null) {
            return identifier;
        }

        // 已经过期，重发
        code = RandomUtils.generateRandomNumber(4);
        identifier = UUIDUtils.uuid();
        // 发短信

        String content = String.format(VERIFY_CODE_MSG, code);
        try {
            String ret = jwsSmsSend.sendV3(username, password, content, mobile, null, null, null, null);
            if(Long.valueOf(ret) > 0) {
                identifierCache.put(mobile, identifier);
                codeCache.put(identifier, code);
                return identifier;
            } else {
                logger.error("短信发送失败：" + ret);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean validate(String mobile, String identifier, String code) {
        String id = identifierCache.getIfPresent(mobile);
        if(id == null || !id.equals(identifier)) {
            return false;
        }

        String c = codeCache.getIfPresent(identifier);
        if(c == null || !c.equals(code)) {
            return false;
        }

        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jwsSmsSend = new JwsSmsSendServiceLocator().getSmsSend();
    }

    public static void main(String[] args) {
        String content = "尊敬的用户，您的验证码为3306，本验证码5分钟内有效，感谢您使用";

        try {
            JwsSmsSend jwsSmsSend = new JwsSmsSendServiceLocator().getSmsSend();
            String username= "zhjk8";
            String password = "123456";
            String pwd = DigestUtils.md5Hex(username + DigestUtils.md5Hex(password));
            String ret = jwsSmsSend.sendV3("zhjk8", pwd, content, "15088603572", null, null, null, null);
            System.out.println(ret);
        } catch (ServiceException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
