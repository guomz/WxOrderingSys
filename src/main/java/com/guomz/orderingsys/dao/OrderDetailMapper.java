package com.guomz.orderingsys.dao;

import com.guomz.orderingsys.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailMapper {
    int deleteByPrimaryKey(String order_detail_id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(String order_detail_id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

    List<OrderDetail> selectDetailByOrderId(@Param("orderId") String orderId);

    int insertOrderDetailList(List<OrderDetail> orderDetailList);
}