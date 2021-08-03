package com.guomz.orderingsys.dao_test;
import java.math.BigDecimal;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guomz.orderingsys.dao.ProductCategoryMapper;
import com.guomz.orderingsys.dao.ProductInfoMapper;
import com.guomz.orderingsys.domain.condition.ProductCategoryCondition;
import com.guomz.orderingsys.domain.condition.ProductInfoCondition;
import com.guomz.orderingsys.domain.resp.PageResponse;
import com.guomz.orderingsys.entity.ProductCategory;
import com.guomz.orderingsys.entity.ProductInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class DaoTest {

    @Resource
    private ProductCategoryMapper productCategoryMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;

    @Test
    public void testCategoryDao(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("家具");
        productCategory.setCategoryType(2);
        productCategory.setCreateTime(new Date());
        productCategory.setUpdateTime(new Date());
        productCategoryMapper.insert(productCategory);

        productCategoryMapper.selectByCondition(new ProductCategoryCondition()).forEach(System.out::println);
    }

    @Test
    public void testProductInfoDao(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("薯片");
        productInfo.setProductPrice(new BigDecimal("3"));
        productInfo.setProductStock(10);
        productInfo.setProductDescription("哈哈");
        productInfo.setProductIcon("aaa");
        productInfo.setProductCategoryType(1);
        productInfo.setCreateTime(new Date());
        productInfo.setUpdateTime(new Date());

        productInfoMapper.insert(productInfo);
        productInfoMapper.selectByCondition(new ProductInfoCondition()).forEach(System.out::println);
    }

    @Test
    public void testCategoryByPage(){
        PageHelper.startPage(1,1);
        List<ProductCategory> categoryList = productCategoryMapper.selectByCondition(new ProductCategoryCondition());
        PageInfo pageInfo = new PageInfo(categoryList);
        System.out.println(new PageResponse<ProductCategory>(pageInfo));
    }
}
