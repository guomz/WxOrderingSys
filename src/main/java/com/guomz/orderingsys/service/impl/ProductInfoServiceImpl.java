package com.guomz.orderingsys.service.impl;

import com.guomz.orderingsys.dao.ProductInfoMapper;
import com.guomz.orderingsys.service.ProductInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Resource
    private ProductInfoMapper productInfoMapper;
}
