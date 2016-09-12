package com.familyan.smarth.manager.impl;

import com.familyan.smarth.constants.BindType;
import com.familyan.smarth.constants.Constants;
import com.familyan.smarth.dao.MemberDao;
import com.familyan.smarth.domain.Member;
import com.familyan.smarth.manager.MemberManager;
import com.familyan.smarth.utils.UserNameUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by shaowenchao on 16/9/8.
 */
@Service
@Transactional(readOnly = false)
public class MemberManagerImpl implements MemberManager {

    public static Log log = LogFactory.getLog(MemberManager.class);

    @Autowired
    MemberDao memberMapper;

    @Override
    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Member findByBindType(BindType type, String loginId) {
        Member query = new Member();
        switch (type){
            case WEIXIN:
                query.setWeixinId(loginId);
                break;
            case USER_NAME:
                query.setUserName(loginId);
                break;
            case EMAIL:
                query.setEmail(loginId);
                break;
            default:
                //默认手机号码
                query.setMobile(loginId);
        }
        return memberMapper.findByProperty(query);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findByIds(List<Long> memberIds) {
        Set<Long> ids = new HashSet<>(memberIds);
        return memberMapper.findByIds(new ArrayList<>(ids));
    }

    @Override
    public boolean update(Member memberDO) {
        return memberMapper.update(memberDO) > 0;
    }

    @Override
    public boolean login(BindType type, String loginId, String password) {
        Member member = findByBindType(type,loginId);
        if(member == null || member.getStatus() != 1)
            return false;
        switch (type){
            case QQ:
            case WEIBO:
            case WEIXIN:
                //不需要验证密码
                break;
            case EMAIL:
            case MOBILE:
            case USER_NAME:
                if(!member.getPassword().equals(DigestUtils.md5Hex(password)))
                    return false;
        }

        return true;
    }


    @Override
    public boolean resetPassword(Long memberId, String orginPassword, String newPassword) {
        log.info("Reset password:"+memberId);
        Member member = memberMapper.findById(memberId);
        if(StringUtils.isBlank(orginPassword) || StringUtils.isBlank(newPassword))
            return false;
        if(member == null)
            return false;
        //如果只是绑定了微信的，修改密码怎么办?
        if(StringUtils.isBlank(member.getPassword()) || member.getPassword().equals(DigestUtils.md5Hex(orginPassword))){
            Member update = new Member();
            update.setId(memberId);
            update.setPassword(DigestUtils.md5Hex(newPassword));
            return update(update);
        }
        return false;
    }


    @Override
    public boolean bind(Long memberId, BindType bindType, String id) {
        if(memberId == null || StringUtils.isBlank(id))
            return false;
        Member memberDO = findByBindType(bindType, id);
        if(memberDO != null)
            return false;

        Member update = new Member();
        update.setId(memberId);
        switch (bindType) {
            case WEIXIN:
                update.setWeixinId(id);
                break;
            case EMAIL:
                update.setEmail(id);
                break;
            case MOBILE:
                update.setMobile(id);
                break;
            case USER_NAME:
                if(!UserNameUtil.validateUserName(id))
                    return false;
                update.setUserName(id);
        }
        boolean success = update(update);
        return success;
    }

    @Override
    public boolean regist(BindType type , Member memberDO) {
        String id = null;
        switch (type) {
            case QQ:
                // id = memberDO.getQqId();
                // memberDO.setPassword("123456");//默认密码
                break;
            case WEIXIN:
                id = memberDO.getWeixinId();
                //memberDO.setPassword("123456");
                break;
            case WEIBO:
                // id = memberDO.getWeiboId();
                //memberDO.setPassword("123456");
                break;
            case EMAIL:
                id = memberDO.getEmail();
                break;
            case MOBILE:
                id = memberDO.getMobile();
//                if(StringUtils.isBlank(id))
//                    memberDO.setUserName(id);
                break;
            case USER_NAME:
                id = memberDO.getUserName();
                if(!UserNameUtil.validateUserName(id))
                    return false;
        }
        log.info(id);
        if(StringUtils.isBlank(id)) return false;
        if(StringUtils.isBlank(memberDO.getPassword())) {
            memberDO.setPassword("123456");//默认密码
        }
        if(memberDO.getStatus() == null )
            memberDO.setStatus(Constants.MEMBER_STATUS_ENABLE);

        //已经注册过
        if(findByBindType(type, id) != null)
            return false;

        if(StringUtils.isBlank(memberDO.getUserName())){
            //生成一个，试3次
            for(int i = 0 ; i < 3 ;i ++){
                String userName = randomUserName();
                if(findByBindType(BindType.USER_NAME,userName) == null){
                    memberDO.setUserName(userName);
                }
            }
        }
        memberDO.setPassword(DigestUtils.md5Hex(memberDO.getPassword()));
        memberMapper.insert(memberDO);
        log.info("Regist user: " + memberDO.getId());

        return true;
    }

    static char[] chars = ("0123456789abcdefjhijklmnopqrsjuvwxyz").toCharArray();
    private static String randomUserName(){
        String day = new SimpleDateFormat("MMddss").format(new Date());
        java.util.Random randGen = new java.util.Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(chars[randGen.nextInt(chars.length)]);
        }
        builder.append(day);
        return builder.toString();
    }

    @Override
    public boolean resetPasswordWithoutOrgin(Long memberId, String newPassword) {
        if(memberId == null || StringUtils.isBlank(newPassword))
            return false;
        Member update = new Member();
        update.setId(memberId);
        update.setPassword(DigestUtils.md5Hex(newPassword));
        return update(update);
    }

    public static void main(String[] args) {
        for(int i = 0 ; i < 100 ; i++){
            System.out.println(randomUserName());
        }
    }


    @Override
    public List<Long> findByLikeName(String userName) {
        List<Long> ids = new ArrayList<Long>();
        List<Member> memberDOs = memberMapper.findByLikeName(userName);
        if(memberDOs != null && memberDOs.size() > 0){
            for (Member memberDO : memberDOs) {
                ids.add(memberDO.getId());
            }
        }
        return ids;
    }


}
