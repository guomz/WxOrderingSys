package com.guomz.orderingsys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatusEnum {

    ON_SALE(0,"上架"),
    OFF_SALE(1, "下架");

    private Integer code;
    private String message;
}
