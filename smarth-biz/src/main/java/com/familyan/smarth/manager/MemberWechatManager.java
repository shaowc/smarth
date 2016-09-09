package com.familyan.smarth.manager;


import com.familyan.smarth.domain.MemberWechatDO;

import java.util.Collection;
import java.util.Map;

/**
 * Created by xifeng on 2016/4/9.
 */
public interface MemberWechatManager {

    /**
     * 按用户id查询微信绑定信息
     * @param app  指定的app 如union。 一个app 会对应一个微信公众号
     * @param memberId
     * @return
     */
    MemberWechatDO findByMemberId(String app, Long memberId);

    /**
     * 按openId查询
     * @param app
     * @param openId
     * @return
     */
    MemberWechatDO findByOpenId(String app, String openId);

    Map<String, MemberWechatDO> findByOpenIds(String app, Collection<String> openIds);

    /**
     * 添加或者更新
     * @param obj
     */
    boolean save(MemberWechatDO obj);

    MemberWechatDO findById(Integer id);
}
