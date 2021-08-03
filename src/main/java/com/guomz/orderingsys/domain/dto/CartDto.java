package com.guomz.orderingsys.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartDto {
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal productQuantity;
}
