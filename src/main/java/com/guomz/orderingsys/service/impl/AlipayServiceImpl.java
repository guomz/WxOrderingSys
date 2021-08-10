package com.guomz.orderingsys.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.guomz.orderingsys.config.AlipayConfiguration;
import com.guomz.orderingsys.dao.OrderMasterMapper;
import com.guomz.orderingsys.entity.OrderMaster;
import com.guomz.orderingsys.enums.PayStatusEnum;
import com.guomz.orderingsys.exception.BusinessException;
import com.guomz.orderingsys.service.AlipayService;
import com.guomz.orderingsys.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayConfiguration alipayConfiguration;
    @Autowired
    private OrderMasterService orderMasterService;
    @Resource
    private OrderMasterMapper orderMasterMapper;

    @Override
    public String payOrder(String orderId) {
        //获取订单信息
        //OrderMaster orderMaster = orderMasterService.getOrderMasterNotNull(orderId);
        //调用sdk支付
        AlipayTradePagePayResponse payResponse;
        try {
            payResponse = Factory.Payment.Page().pay("测试订单", orderId, "1", alipayConfiguration.getReturnUrl());
            if (ResponseChecker.success(payResponse)){
                log.info("调用sdk支付成功");
            }else {
                log.error("调用sdk支付失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用sdk支付失败");
            throw new BusinessException();
        }
        return payResponse.getBody();
    }

    @Override
    public AlipayTradeQueryResponse checkPayment(String orderId) {
        AlipayTradeQueryResponse queryResponse;
        try {
            queryResponse = Factory.Payment.Common().query("1628041097587");
            if (ResponseChecker.success(queryResponse)){
                log.info("查询成功");
                log.info(queryResponse.getMsg());
            }else {
                log.error("查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询交易异常");
            throw new BusinessException();
        }
        return queryResponse;
    }

    @Override
    public void changeOrderPayStatus(String orderId) {
        OrderMaster orderMaster = orderMasterService.getOrderMasterNotNull(orderId);
        orderMasterMapper.updateOrderPayStatus(orderMaster.getOrderId(), PayStatusEnum.PRE_PAID.getCode(), PayStatusEnum.PAY_SUCCESS.getCode(), new Date());
    }
}
