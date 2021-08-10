package com.guomz.orderingsys.dao;

import com.guomz.orderingsys.domain.condition.OrderMasterCondition;
import com.guomz.orderingsys.entity.OrderMaster;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderMasterMapper {
    int deleteByPrimaryKey(String order_id);

    int insert(OrderMaster record);

    int insertSelective(OrderMaster record);

    OrderMaster selectByPrimaryKey(String order_id);

    int updateByPrimaryKeySelective(OrderMaster record);

    int updateByPrimaryKey(OrderMaster record);

    List<OrderMaster> selectOrderByOpenId(@Param("openid") String openid);

    /**
     * 变更订单支付状态
     * @param orderId
     * @param preStatus
     * @param nowStatus
     * @param updateTime
     * @return
     */
    int updateOrderPayStatus(@Param("orderId") String orderId,
                             @Param("preStatus") Integer preStatus,
                             @Param("nowStatus") Integer nowStatus,
                             @Param("updateTime") Date updateTime);

    List<OrderMaster> selectByCondition(OrderMasterCondition condition);
}