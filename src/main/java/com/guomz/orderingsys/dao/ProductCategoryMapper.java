package com.guomz.orderingsys.dao;

import com.guomz.orderingsys.domain.condition.ProductCategoryCondition;
import com.guomz.orderingsys.entity.ProductCategory;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Long category_id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Long category_id);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> selectByCondition(ProductCategoryCondition condition);
}