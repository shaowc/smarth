package com.familyan.smarth.manager;

import com.familyan.smarth.constants.BindType;
import com.familyan.smarth.domain.Member;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/8.
 */
public interface MemberManager {

    /**
     * 按用户id查询
     * @param id
     * @return
     */
    Member findById(Long id );

    /**
     * 按绑定类型查询用户
     * @param type
     * @param loginId
     * @return
     */
    Member findByBindType(BindType type, String loginId);

    /**
     * 批量查询用户
     * @param memberIds
     * @return
     */
    List<Member> findByIds(List<Long> memberIds);

    /**
     * 更新用户信息
     * @param memberDO
     * @return
     */
    boolean update(Member memberDO);

    /**
     * 登录
     * @param type
     * @param loginId
     * @param password
     * @return
     */
    boolean login(BindType type, String loginId, String password);


    /**
     * 重置密码
     * @param memberId
     * @param orginPassword
     * @param newPassword
     * @return
     */
    boolean resetPassword(Long memberId, String orginPassword, String newPassword);

    /**
     * 绑定第三方账号或者手机号，邮箱
     * @param memberId
     * @param bindType
     * @param id
     * @return
     */
    boolean bind(Long memberId, BindType bindType, String id);

    /**
     *
     * 创建用户
     * 1. 检查所有绑定类型中至少包含一种账号，才能继续走注册流程
     * 2. 如果没有用户user_name 生成一个
     * 3. 如果有密码,对密码进行加密
     * 4. 保存到数据库
     * @param memberDO
     */
    boolean regist(BindType type, Member memberDO);

    /**
     * 直接更新更新密码
     * @param memberId
     * @param newPassword
     * @return
     */
    boolean resetPasswordWithoutOrgin(Long memberId, String newPassword);

    /**
     * 根据名字模糊查询
     * @param userName
     * @return
     */
    List<Long> findByLikeName(String userName);
}
