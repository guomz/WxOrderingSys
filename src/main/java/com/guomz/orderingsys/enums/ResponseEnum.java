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
    INVALID_ARGS("999","参数格式不正确"),
    UNKNOW_ERROR("1000", "系统异常");


    private String code;
    private String message;
}
