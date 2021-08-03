package com.guomz.orderingsys.service.impl;

import com.guomz.orderingsys.dao.ProductCategoryMapper;
import com.guomz.orderingsys.entity.ProductCategory;
import com.guomz.orderingsys.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public ProductCategory getCategoryById(Long id) {
        return null;
    }

    @Override
    public ProductCategory getCategoryByType(Integer categoryType) {
        return null;
    }
}
