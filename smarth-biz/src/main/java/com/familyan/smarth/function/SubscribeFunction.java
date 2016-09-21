package com.familyan.smarth.function;

import com.familyan.smarth.constants.BindType;
import com.familyan.smarth.domain.MemberDTO;
import com.familyan.smarth.domain.MemberWechatDTO;
import com.familyan.smarth.service.MemberService;
import com.familyan.smarth.service.MemberWechatService;
import com.google.common.base.Joiner;
import com.lotus.core.util.TransferUtil;
import com.lotus.wechat.WechatApi;
import com.lotus.wechat.WechatException;
import com.lotus.wechat.WechatUserInfo;
import com.lotus.wechat.message.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


/**
 * 关注时推送
 */
public class SubscribeFunction implements WechatFunction {
    /**关注时推送的信息*/
    List<String> welcome ;

    String app = "smarth";

    @Value("${top.domain}")
    String topDomain;

    @Autowired
    WechatApi api;

    @Autowired
    MemberWechatService wechatService;

    @Autowired
    private MemberService memberService;

    @Value("${app.domain}")
    private String appDomain;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    public List<String> getWelcome() {
        return welcome;
    }


    public void setWelcome(List<String> welcome) {
        this.welcome = welcome;
    }

    @Override
    public boolean support(WechatMessage message) {
        if(message instanceof WechatEvent){
            WechatEvent  e  =  ((WechatEvent)message);
            String event = e.getEvent();
            //关注和取消关注
            if(StringUtils.equals(event,WechatEvent.EVENT_SUBSCIBE) || StringUtils.equals(event,WechatEvent.EVENT_UNSUBSCIBE)){
                return true;
            }

            //已关注，但是扫了公众账号带参数二维码
            if(StringUtils.equals(event,WechatEvent.EVENT_SCAN) && StringUtils.isNotBlank(e.getTicket()))
                return true;
        }
        return false;
    }

    @Override
    public WechatMessageResponse process(WechatMessage message) throws WechatException {
        final WechatEvent  e  =  ((WechatEvent)message);
        //1. 取消关注
        if(StringUtils.equals(e.getEvent(),WechatEvent.EVENT_UNSUBSCIBE)){
            //取消关注状态
            MemberWechatDTO update = new MemberWechatDTO();
            update.setApp(app);
            update.setOpenId(message.getFromUserName());
            update.setSubscribe(MemberWechatDTO.UN_SUBSCRIBE);
            wechatService.save(update);
            return null;
        }
        if(StringUtils.equals(e.getEvent(),WechatEvent.EVENT_SUBSCIBE)){
            //保存微信用户信息
            final WechatUserInfo info = api.userInfo(e.getFromUserName());
            MemberWechatDTO saved = saveAndGet(info);

            //普通关注
            if(welcome != null) {
                return new TextMessageResponse().setContent(Joiner.on("\n").skipNulls().join(welcome))
                        .setFromUserName(message.getToUserName()).setToUserName(message.getFromUserName());
            }

        }

        return null;
    }

    private MemberWechatDTO saveAndGet(WechatUserInfo info) throws WechatException {

        //用户是否注册, 没注册, 自动注册
        MemberDTO memberDTO = memberService.findByBindType(BindType.WEIXIN, info.getUnionId());
        if(null == memberDTO){
            MemberDTO newMemberDto = new MemberDTO();
            //newMemberDto.setPassword("123456");
            newMemberDto.setGender(info.getSex());
            newMemberDto.setRealName(info.getNickName());
            newMemberDto.setAvatar(info.getHeadImgUrl());
            newMemberDto.setWeixinId(info.getUnionId());

            memberDTO = memberService.regMember(BindType.WEIXIN,newMemberDto);
        }

        MemberWechatDTO wechatDTO = TransferUtil.transfer(info, new MemberWechatDTO());
        wechatDTO.setApp(app);
        wechatDTO.setMemberId(memberDTO.getId());

        return wechatService.save(wechatDTO);
    }

}
