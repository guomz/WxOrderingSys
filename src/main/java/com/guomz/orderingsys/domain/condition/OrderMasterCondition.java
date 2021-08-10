package com.guomz.orderingsys.domain.condition;

import lombok.Data;

@Data
public class OrderMasterCondition {

    private String orderId;
    private Integer orderStatus;
    private Integer payStatus;
    private String buyerOpenid;
}
