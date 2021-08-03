package com.guomz.orderingsys.service;

import com.guomz.orderingsys.domain.dto.OrderDto;

public interface OrderMasterService {

    OrderDto createOrder(OrderDto orderDto);
}
