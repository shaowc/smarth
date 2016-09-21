package com.familyan.smarth.manager;

import com.familyan.smarth.domain.MemberLocation;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/20.
 */
public interface MemberLocationManager {

    void save(MemberLocation memberLocation);

    MemberLocation findByMemberId(Long memberId);

    List<MemberLocation> findByMemberIds(List<Long> memberIds);

}
