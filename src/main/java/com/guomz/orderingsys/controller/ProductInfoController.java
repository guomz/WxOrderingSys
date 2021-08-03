package com.guomz.orderingsys.controller;

import com.guomz.orderingsys.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;
}
