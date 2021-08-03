package com.guomz.orderingsys.dao;

import com.guomz.orderingsys.entity.OrderMaster;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMasterMapper {
    int deleteByPrimaryKey(String order_id);

    int insert(OrderMaster record);

    int insertSelective(OrderMaster record);

    OrderMaster selectByPrimaryKey(String order_id);

    int updateByPrimaryKeySelective(OrderMaster record);

    int updateByPrimaryKey(OrderMaster record);
}