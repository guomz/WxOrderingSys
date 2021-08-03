package com.guomz.orderingsys.domain.condition;

import lombok.Data;

@Data
public class ProductInfoCondition {

    private String productName;
    private String productId;
    private Integer productCategoryType;
}
