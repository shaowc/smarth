package com.familyan.smarth.manager;

import com.familyan.smarth.domain.Item;
import com.familyan.smarth.domain.Packet;
import com.lotus.service.result.PageResult;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/9.
 */
public interface PacketManager {

    /**
     * 添加体检包
     * 添加基本信息，添加体检项目
     * @param packet
     * @param items
     */
    void add(Packet packet, List<Item> items);

    /**
     * 修改体检包
     * 修改基本信息
     * 删除旧的体检项目，增加新的体检项目
     * @param packet
     * @param items
     */
    void modify(Packet packet, List<Item> items);

    /**
     * 按用户ID查找体检包，我的体检包
     *
     * @param memberId
     * @return
     */
    List<Packet> findByMemberId(Long memberId);

    List<Packet> findByIds(List<Integer> ids);

    Packet findById(Integer id);

    /**
     * 体检手购买体检包
     *
     * @param memberId
     * @param packetId
     */
    void buy(Long memberId, Integer packetId);

    PageResult<List<Packet>> findByPage(Packet packet, Integer start, Integer limit, String orderBy);


}
