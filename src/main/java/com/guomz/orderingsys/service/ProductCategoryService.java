package com.guomz.orderingsys.service;

import com.guomz.orderingsys.dao.ProductCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;


}
