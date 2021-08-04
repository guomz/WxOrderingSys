package com.guomz.orderingsys.service;

import com.github.pagehelper.PageInfo;
import com.guomz.orderingsys.domain.dto.OrderDto;
import com.guomz.orderingsys.entity.OrderMaster;

public interface OrderMasterService {

    OrderDto createOrder(OrderDto orderDto);

    OrderDto getOrderById(String orderId);

    PageInfo<OrderDto> getOrderListByOpenId(Integer pageNum, Integer pageSize, String openid);

    OrderDto cancelOrder(OrderDto orderDto);

    OrderDto finishOrder(OrderDto orderDto);

    OrderDto payOrder(OrderDto orderDto);

    OrderMaster getOrderMasterNotNull(String orderId);
}
