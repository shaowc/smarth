package com.familyan.smarth.service;

import com.familyan.smarth.constants.BindType;
import com.familyan.smarth.domain.MemberDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by shaowenchao on 16/9/8.
 */
public interface MemberService {

    /**
     * 按id查询详细信息
     * @param memberId
     * @return
     */
    MemberDTO findById(Long memberId);

    /**
     * 按绑定类型来查询账号信息
     * @param type
     * @param loginId
     * @return
     */
    MemberDTO findByBindType(BindType type, String loginId);

    /**
     * 批量获取详细信息
     * @param memberIds
     * @return ID -> MemberDTO
     */
    Map<Long,MemberDTO> findByIds(List<Long> memberIds);

    /**
     * 创建用户
     * 1. 检查所有绑定类型中至少包含一种账号，才能继续走注册流程
     * 2. 如果没有用户user_name 生成一个
     * 3. 如果有密码,对密码进行加密
     *
     * 4. 保存到数据库
     * @param type 不填默认 MOBILE
     * @param member
     * @return 一个包含当前生成的账号id，userName 等数据的账号对象
     */
    MemberDTO regMember(BindType type ,MemberDTO member);

    /**
     * 更新账号信息除feature字段外的其它字段
     * @param update
     */
    boolean updateMember(MemberDTO update);

    /**
     * 添加一批用户标记
     * @param memberId
     * @param features
     * @return
     */
    boolean addFeature(Long memberId,Long ... features);

    /**
     * 删除用户标记
     * @param memberId
     * @param features
     * @return
     */
    boolean removeFeature(Long memberId,Long ... features);

    /**
     * 登录方式
     * @param type 绑定账号类型
     * @param loginId 该绑定类型的id
     * @param password 非加密密码
     * @return
     */
    boolean login(BindType type, String loginId, String password);

    /**
     * 无密码登录
     * @param type
     * @param loginId
     * @return 返回null 登录失败， 返回MemberDTO 登录成功
     */
    MemberDTO loginNoPassword(BindType type, String loginId);

    /**
     * 密码重置
     * @param memberId
     * @param orginPassword 非加密密码
     * @param newPassword 非加密密码
     * @return
     */
    boolean resetPassword(Long memberId, String orginPassword,String newPassword);

    /**
     * 密码重置
     * @param memberId
     * @param newPassword 非加密密码
     * @return
     */
    boolean resetPasswordWithoutOrgin(Long memberId, String newPassword);

    /**
     * 绑定账号
     * @param memberId
     * @param bindType
     * @param id
     * @return
     */
    boolean bind(Long memberId,BindType bindType, String id);

}
