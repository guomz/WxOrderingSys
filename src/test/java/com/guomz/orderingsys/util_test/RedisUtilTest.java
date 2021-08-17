package com.guomz.orderingsys.util_test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guomz.orderingsys.entity.ProductInfo;
import com.guomz.orderingsys.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testSetAndGetObj(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123123123");
        productInfo.setProductName("asdfsdf");
        productInfo.setProductPrice(new BigDecimal("10"));
        productInfo.setProductStock(new BigDecimal("1110"));
        productInfo.setProductDescription("dddd");
        productInfo.setProductIcon("sdfse");
        productInfo.setProductCategoryType(0);
        productInfo.setCreateTime(new Date());
        productInfo.setUpdateTime(new Date());
        productInfo.setProductStatus(0);

        redisUtil.setObject("test", productInfo, 1000 * 60 * 10L);

//        ProductInfo productInfoTemp = redisUtil.getObject("test", ProductInfo.class);
        ProductInfo productInfoTemp = redisUtil.getObject("test", new TypeReference<ProductInfo>() {
        });
        System.out.println(productInfoTemp);
    }

    @Test
    public void testSetAndGetListObj(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123123123");
        productInfo.setProductName("asdfsdf");
        productInfo.setProductPrice(new BigDecimal("10"));
        productInfo.setProductStock(new BigDecimal("1110"));
        productInfo.setProductDescription("dddd");
        productInfo.setProductIcon("sdfse");
        productInfo.setProductCategoryType(0);
        productInfo.setCreateTime(new Date());
        productInfo.setUpdateTime(new Date());
        productInfo.setProductStatus(0);

        List<ProductInfo> productInfoList = Arrays.asList(productInfo);
        redisUtil.setObject("testlist", productInfoList, 1000 * 60 * 10L);

        List<ProductInfo> newList = redisUtil.getObject("testlist", new TypeReference<List<ProductInfo>>() {
        });

        newList.forEach(System.out::println);
    }

    @Test
    public void testGetNull(){
        List<ProductInfo> newList = redisUtil.getObject("test2", new TypeReference<List<ProductInfo>>(){});
        System.out.println(newList == null ? "null" : newList.size());
    }
}
