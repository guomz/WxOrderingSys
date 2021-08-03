package com.guomz.orderingsys.controller;

import com.guomz.orderingsys.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

}
