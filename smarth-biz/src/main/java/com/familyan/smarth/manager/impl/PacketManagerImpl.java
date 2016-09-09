package com.familyan.smarth.manager.impl;

import com.familyan.smarth.dao.ItemDao;
import com.familyan.smarth.dao.MemberPacketDao;
import com.familyan.smarth.dao.PacketDao;
import com.familyan.smarth.domain.Item;
import com.familyan.smarth.domain.MemberPacket;
import com.familyan.smarth.domain.Packet;
import com.familyan.smarth.manager.PacketManager;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by shaowenchao on 16/9/9.
 */
@Service
public class PacketManagerImpl implements PacketManager {

    @Autowired
    private PacketDao packetDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private MemberPacketDao memberPacketDao;

    @Override
    public void add(Packet packet, List<Item> items) {
        packetDao.insert(packet);
        for(Item item : items) {
            item.setPacketId(packet.getId());
            itemDao.insert(item);
        }
    }

    @Override
    public void modify(Packet packet, List<Item> items) {
        packetDao.update(packet);
        itemDao.deleteByPacketId(packet.getId());
        for(Item item : items) {
            item.setPacketId(packet.getId());
            itemDao.insert(item);
        }
    }

    @Override
    public List<Packet> findByMemberId(Long memberId) {
        List<MemberPacket> memberPackets = memberPacketDao.findByMemberId(memberId);
        if(memberPackets.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> packetIds = Lists.transform(memberPackets, new Function<MemberPacket, Integer>() {
            @Override
            public Integer apply(MemberPacket input) {
                return input.getPacketId();
            }
        });
        return packetDao.findByIds(packetIds);
    }

    @Override
    public List<Packet> findByIds(List<Integer> ids) {
        return packetDao.findByIds(ids);
    }

    @Override
    public Packet findById(Integer id) {
        return packetDao.findById(id);
    }

    @Override
    public void buy(Long memberId, Integer packetId) {
        //
        MemberPacket memberPacket = new MemberPacket();
        memberPacket.setPacketId(packetId);
        memberPacket.setMemberId(memberId);
        memberPacket.setStatus(1);
        memberPacketDao.insert(memberPacket);
    }

    @Override
    public PageResult<List<Packet>> findByPage(Packet packet, Integer start, Integer limit, String orderBy) {
        int count = packetDao.countByParams(packet);
        if (count == 0) {
            return PageResult.emptyResult(Collections.<Packet>emptyList());
        }

        List<Packet> data = packetDao.findByParams(packet, start, limit, orderBy);
        return new PageResult<>(start, limit, count, data);
    }
}
