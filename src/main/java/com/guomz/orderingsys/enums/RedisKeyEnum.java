package com.guomz.orderingsys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKeyEnum {

    ALL_CATEGORY("allcategory");
    String keyName;
}
