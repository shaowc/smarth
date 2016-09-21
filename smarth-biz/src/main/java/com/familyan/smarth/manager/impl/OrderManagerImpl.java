package com.familyan.smarth.manager.impl;

import com.familyan.smarth.dao.MemberCheckerDao;
import com.familyan.smarth.dao.OrderDao;
import com.familyan.smarth.domain.MemberChecker;
import com.familyan.smarth.domain.Order;
import com.familyan.smarth.domain.OrderDTO;
import com.familyan.smarth.manager.OrderManager;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by shaowenchao on 16/9/9.
 */
@Service
public class OrderManagerImpl implements OrderManager {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberCheckerDao memberCheckerDao;

    @Override
    public PageResult<List<Order>> findByPage(OrderDTO order, Integer start, Integer limit, String orderBy) {
        int total = orderDao.countByParams(order);
        if (total == 0) {
            return PageResult.emptyResult(Collections.<Order>emptyList());
        }

        List<Order> data = orderDao.findByParams(order, start, limit, orderBy);
        return new PageResult<>(start, limit, total, data);
    }

    @Override
    public Order findById(Integer id) {
        return orderDao.findById(id);
    }

    @Override
    @Transactional
    public void add(Order order) {
        orderDao.insert(order);
        // 加入我的快检手
        MemberChecker memberChecker = memberCheckerDao.findByMemberIdAndCheckerId(order.getMemberId(), order.getCheckerId());
        if (memberChecker == null) {
            memberChecker = new MemberChecker();
            memberChecker.setMemberId(order.getMemberId());
            memberChecker.setCheckerId(order.getCheckerId());
            memberChecker.setStatus(1);
            memberCheckerDao.insert(memberChecker);
        }

    }

    @Override
    public void modify(Order order) {
        orderDao.update(order);
    }

    @Override
    public Order findByOutTradeNo(String outTradeNo) {
        return orderDao.findByOutTradeNo(outTradeNo);
    }
}
