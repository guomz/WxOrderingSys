package com.guomz.orderingsys.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class CategoryProductVo {

    private Long categoryId;
    private String categoryName;
    private Integer categoryType;
    List<ProductBriefVo> productList;
}
