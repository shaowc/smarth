package com.familyan.smarth.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.familyan.smarth.domain.Packet;
import com.familyan.smarth.domain.PacketDTO;
import com.familyan.smarth.manager.PacketManager;
import com.familyan.smarth.service.PacketService;
import com.lotus.core.util.TransferUtil;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import com.lotus.service.result.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/10.
 */
@Service
public class PacketServiceImpl implements PacketService {

    @Autowired
    private PacketManager packetManager;

    @Override
    public PacketDTO findById(Integer id) {
        Packet packet = packetManager.findById(id);
        if (packet == null)
            return null;
        PacketDTO packetDTO =  TransferUtil.transfer(packet, new PacketDTO());
        return packetDTO;
    }

    @Override
    public List<PacketDTO> findByIds(List<Integer> ids) {
        List<Packet> packets = packetManager.findByIds(ids);
        return TransferUtil.transferList(packets, PacketDTO.class);
    }

    @Override
    public void save(PacketDTO packetDTO) {
        Packet packet = TransferUtil.transfer(packetDTO, new Packet());
        if(packetDTO.getId() == null || packetDTO.getId() == 0) {
            packetManager.add(packet);
        } else {
            packetManager.modify(packet);
        }

    }

    @Override
    public PageResult<List<PacketDTO>> findByPage(PacketDTO packetDTO, Page page, String orderBy) {
        PageResult<List<Packet>> pageResult = packetManager.findByPage(packetDTO, page.getStart(), page.getPageSize(), orderBy);
        List<PacketDTO> data = TransferUtil.transferList(pageResult.getData(), PacketDTO.class);
        return new PageResult<>(pageResult.getStart(), pageResult.getLimit(), pageResult.getTotal(), data);
    }

    @Override
    public List<PacketDTO> findMyPacket(Long memberId) {
        List<Packet> packets = packetManager.findByMemberId(memberId);
        return TransferUtil.transferList(packets, PacketDTO.class);
    }

    @Override
    public Result<Integer> buy(Long memberId, Integer packetId) {
        int r = packetManager.buy(memberId, packetId);
        if (r == 0) {
            return Result.error("该快检手已经购买过");
        }
        return Result.success(r);
    }
}
