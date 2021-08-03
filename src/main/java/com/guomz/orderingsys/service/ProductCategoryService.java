package com.guomz.orderingsys.service;

import com.guomz.orderingsys.entity.ProductCategory;

public interface ProductCategoryService {

    ProductCategory getCategoryById(Long id);

    ProductCategory getCategoryByType(Integer categoryType);

    void addCategory(ProductCategory productCategory);
}
