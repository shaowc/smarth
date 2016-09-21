package com.familyan.smarth.function;

import com.lotus.wechat.WechatException;
import com.lotus.wechat.message.*;


public class KeyValueFunction implements WechatFunction {

    @Override
    public boolean support(WechatMessage message) {
        return (message instanceof TextMessage);
    }

    @Override
    public WechatMessageResponse process(WechatMessage message) throws WechatException {
        String msg = ((TextMessage) message).getContent();
        //精确匹配信息

        return new TextMessageResponse().setContent(msg).setToUserName(message.getFromUserName()).setFromUserName(message.getToUserName());
    }
}
