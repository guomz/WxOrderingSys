package com.guomz.orderingsys.service;

import com.guomz.orderingsys.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory getCategoryById(Long id);

    ProductCategory getCategoryByType(Integer categoryType);

    void addCategory(ProductCategory productCategory);

    List<ProductCategory> getAllCategory();
}
