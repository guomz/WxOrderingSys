package com.guomz.orderingsys.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guomz.orderingsys.dao.ProductCategoryMapper;
import com.guomz.orderingsys.domain.condition.ProductCategoryCondition;
import com.guomz.orderingsys.entity.ProductCategory;
import com.guomz.orderingsys.enums.RedisKeyEnum;
import com.guomz.orderingsys.service.ProductCategoryService;
import com.guomz.orderingsys.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ProductCategory getCategoryById(Long id) {
        return null;
    }

    @Override
    public ProductCategory getCategoryByType(Integer categoryType) {
        return null;
    }

    @Override
    public void addCategory(ProductCategory productCategory) {
        productCategoryMapper.insert(productCategory);
        redisUtil.delKey(RedisKeyEnum.ALL_CATEGORY.getKeyName());
    }

    @Override
    public List<ProductCategory> getAllCategory() {
        List<ProductCategory> categoryList = redisUtil.getObject(RedisKeyEnum.ALL_CATEGORY.getKeyName(), new TypeReference<List<ProductCategory>>() {});
        if (categoryList == null || categoryList.isEmpty()){
            ProductCategoryCondition condition = new ProductCategoryCondition();
            categoryList = productCategoryMapper.selectByCondition(condition);
            redisUtil.setObject(RedisKeyEnum.ALL_CATEGORY.getKeyName(), categoryList,1000*60*10L);
        }
        return categoryList;
    }
}
