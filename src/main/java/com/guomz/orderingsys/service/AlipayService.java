package com.guomz.orderingsys.service;

import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;

public interface AlipayService {
    /**
     * 支付订单
     * @param orderId
     * @return
     */
    String payOrder(String orderId);

    /**
     * 查询订单支付结果
     * @param orderId
     * @return
     */
    AlipayTradeQueryResponse checkPayment(String orderId);

    /**
     * 更改订单支付状态
     * @param orderId
     */
    void changeOrderPayStatus(String orderId);
}
