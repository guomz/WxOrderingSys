package com.guomz.orderingsys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
    OK("000","success"),
    PRODUCT_NOT_EXIST("001","商品不存在"),
    PRODUCT_PRICE_NOT_VALID("002","商品价格不正确"),
    ORDER_PRICE_NOT_CORRECT("003","订单金额不正确"),
    ORDER_NOT_EXIST("004","订单不存在"),
    ORDER_DETAIL_NOT_EXIST("005","订单明细不存在"),
    ORDER_STATUS_NOT_CORRECT("006","订单状态不正确"),
    ORDER_DETAIL_PRODUCT_INFO_NOT_CORRECT("007","订单明细商品信息不正确"),
    INVALID_ARGS("999","参数格式不正确"),
    UNKNOW_ERROR("1000", "系统异常");


    private String code;
    private String message;
}
