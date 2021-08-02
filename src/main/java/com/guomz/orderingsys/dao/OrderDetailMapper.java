package com.guomz.orderingsys.dao;

import com.guomz.orderingsys.entity.OrderDetail;

public interface OrderDetailMapper {
    int deleteByPrimaryKey(String order_detail_id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(String order_detail_id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}