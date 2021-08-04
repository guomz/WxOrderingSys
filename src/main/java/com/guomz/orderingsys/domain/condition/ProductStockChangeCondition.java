package com.guomz.orderingsys.domain.condition;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductStockChangeCondition {

    private String productId;
    private BigDecimal changedStock;
}
