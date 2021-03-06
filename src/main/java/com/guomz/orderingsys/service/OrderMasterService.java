package com.guomz.orderingsys.service;

import com.github.pagehelper.PageInfo;
import com.guomz.orderingsys.domain.condition.OrderMasterCondition;
import com.guomz.orderingsys.domain.dto.OrderDto;
import com.guomz.orderingsys.domain.vo.OrderVo;
import com.guomz.orderingsys.entity.OrderDetail;
import com.guomz.orderingsys.entity.OrderMaster;

import java.util.List;

public interface OrderMasterService {

    String createOrder(OrderDto orderDto);

    OrderDto getOrderByIdCheck(String orderId, String openid);

    PageInfo<OrderDto> getOrderListByOpenId(Integer pageNum, Integer pageSize, String openid);

    void cancelOrder(String orderId, String openid);

    void finishOrder(String orderId, String openid);

    void payOrder(String orderId, String openid);

    OrderMaster getOrderMasterNotNull(String orderId);

    List<OrderDetail> getOrderDetailListByOrderId(String orderId);

    List<OrderVo> getOrderVoByCondition(OrderMasterCondition condition);
}
