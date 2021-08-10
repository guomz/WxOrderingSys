package com.guomz.orderingsys.task;

import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.guomz.orderingsys.dao.OrderMasterMapper;
import com.guomz.orderingsys.domain.condition.OrderMasterCondition;
import com.guomz.orderingsys.entity.OrderMaster;
import com.guomz.orderingsys.enums.PayStatusEnum;
import com.guomz.orderingsys.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class AlipayOrderCheckTask {

    @Resource
    private OrderMasterMapper orderMasterMapper;
    @Autowired
    private AlipayService alipayService;

    /**
     * 查询全部预支付状态的订单是否完成支付
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void checkPrePaidOrder(){
        log.info("批量查询订单任务开始");
        //找出全部预支付的订单
        OrderMasterCondition condition = new OrderMasterCondition();
        condition.setPayStatus(PayStatusEnum.PRE_PAID.getCode());
        List<OrderMaster> orderMasterList = orderMasterMapper.selectByCondition(condition);
        log.info("{}条记录需要查询", orderMasterList.size());
        for (OrderMaster orderMaster : orderMasterList) {
            //调用sdk接口查询支付结果
            AlipayTradeQueryResponse queryResponse = alipayService.checkPayment(orderMaster.getOrderId());
            log.info("查询订单支付状态: {}", queryResponse.getMsg());
            if (queryResponse.getMsg().equals("Success")){
                orderMasterMapper.updateOrderPayStatus(orderMaster.getOrderId(),PayStatusEnum.PRE_PAID.getCode(), PayStatusEnum.PAY_SUCCESS.getCode(), new Date());
            }
        }
        log.info("批量查询订单任务结束");
    }
}
