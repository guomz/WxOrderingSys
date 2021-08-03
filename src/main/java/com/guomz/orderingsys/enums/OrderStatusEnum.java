package com.guomz.orderingsys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    NEW(0,"新订单");

    private Integer code;
    private String message;
}
