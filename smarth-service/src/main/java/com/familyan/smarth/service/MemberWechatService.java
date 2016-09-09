package com.familyan.smarth.service;


import com.familyan.smarth.domain.MemberWechatDTO;

import java.util.Collection;
import java.util.Map;

/**
 * Created by xifeng on 2016/4/9.
 */
public interface MemberWechatService {

    /**
     * 按用户id查询微信绑定信息
     * @param app  指定的app 如union。 一个app 会对应一个微信公众号
     * @param memberId
     * @return
     */
    MemberWechatDTO findByMemberId(String app, Long memberId);

    /**
     * 按openId查询
     * @param app
     * @param openId
     * @return
     */
    MemberWechatDTO findByOpenId(String app, String openId);

    Map<String, MemberWechatDTO> findByOpenIds(String app, Collection<String> openIds);

    /**
     * 添加或者更新
     * @param dto
     */
    MemberWechatDTO save(MemberWechatDTO dto);

}
