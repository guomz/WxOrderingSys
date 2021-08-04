package com.guomz.orderingsys.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class ProductInfo {
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal productStock;

    private String productDescription;

    private String productIcon;

    private Integer productCategoryType;

    private Date createTime;

    private Date updateTime;

    private Integer productStatus;

}