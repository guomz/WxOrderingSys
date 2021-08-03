package com.guomz.orderingsys.dao;

import com.guomz.orderingsys.domain.ProductCategoryCondition;
import com.guomz.orderingsys.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Long category_id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Long category_id);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> selectByCondition(ProductCategoryCondition condition);
}