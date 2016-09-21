package com.familyan.smarth.manager;

import com.familyan.smarth.domain.Order;
import com.familyan.smarth.domain.OrderDTO;
import com.lotus.service.result.PageResult;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/9.
 */
public interface OrderManager {

    PageResult<List<Order>> findByPage(OrderDTO order, Integer start, Integer limit, String orderBy);

    Order findById(Integer id);

    void add(Order order);

    void modify(Order order);

    Order findByOutTradeNo(String outTradeNo);


}
