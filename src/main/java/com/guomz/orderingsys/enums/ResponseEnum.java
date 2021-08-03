package com.guomz.orderingsys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
    OK("000","success"),
    INVALID_ARGS("999","参数格式不正确"),
    UNKNOW_ERROR("1000", "系统异常");


    private String code;
    private String message;
}
