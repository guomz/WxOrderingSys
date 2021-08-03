package com.guomz.orderingsys.dao;

import com.guomz.orderingsys.domain.ProductInfoCondition;
import com.guomz.orderingsys.entity.ProductInfo;

import java.util.List;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(String product_id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(String product_id);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    List<ProductInfo> selectByCondition(ProductInfoCondition condition);
}