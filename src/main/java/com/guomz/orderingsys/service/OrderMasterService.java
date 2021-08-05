package com.guomz.orderingsys.service;

import com.github.pagehelper.PageInfo;
import com.guomz.orderingsys.domain.dto.OrderDto;

public interface OrderMasterService {

    String createOrder(OrderDto orderDto);

    OrderDto getOrderByIdCheck(String orderId, String openid);

    PageInfo<OrderDto> getOrderListByOpenId(Integer pageNum, Integer pageSize, String openid);

    void cancelOrder(String orderId, String openid);

    void finishOrder(String orderId, String openid);

    void payOrder(String orderId, String openid);

}
