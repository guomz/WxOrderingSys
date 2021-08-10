package com.guomz.orderingsys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    UNPAID(0, "未支付"),
    PRE_PAID(2, "预支付"),
    PAY_SUCCESS(3, "支付成功"),
    PAID(1, "已支付");

    private Integer code;
    private String message;
}
