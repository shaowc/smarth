package com.familyan.smarth.service;

import com.familyan.smarth.domain.PacketDTO;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import com.lotus.service.result.Result;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/10.
 */
public interface PacketService {

    PacketDTO findById(Integer id);


    List<PacketDTO> findByIds(List<Integer> ids);

    /**
     * 保存接口
     * @param packetDTO
     */
    void save(PacketDTO packetDTO);

    /**
     * 体检包分页列表
     *
     * @param packetDTO
     * @param page
     * @param orderBy
     * @return
     */
    PageResult<List<PacketDTO>> findByPage(PacketDTO packetDTO, Page page, String orderBy);

    /**
     * 我的体检包接口
     *
     * @param memberId
     * @return
     */
    List<PacketDTO> findMyPacket(Long memberId);

    /**
     * 快检手购买体检包接口
     *
     * @param memberId
     * @param packetId
     * @return
     */
    Result<Integer> buy(Long memberId, Integer packetId);

}
