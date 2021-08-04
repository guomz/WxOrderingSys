package com.guomz.orderingsys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISH(1, "订单完成"),
    CANCEL(2, "取消订单");

    private Integer code;
    private String message;
}
