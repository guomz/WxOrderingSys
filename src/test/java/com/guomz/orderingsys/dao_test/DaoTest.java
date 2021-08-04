package com.guomz.orderingsys.dao_test;
import java.math.BigDecimal;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guomz.orderingsys.dao.OrderDetailMapper;
import com.guomz.orderingsys.dao.OrderMasterMapper;
import com.guomz.orderingsys.dao.ProductCategoryMapper;
import com.guomz.orderingsys.dao.ProductInfoMapper;
import com.guomz.orderingsys.domain.condition.ProductCategoryCondition;
import com.guomz.orderingsys.domain.condition.ProductInfoCondition;
import com.guomz.orderingsys.domain.resp.PageResponse;
import com.guomz.orderingsys.entity.OrderDetail;
import com.guomz.orderingsys.entity.OrderMaster;
import com.guomz.orderingsys.entity.ProductCategory;
import com.guomz.orderingsys.entity.ProductInfo;
import com.guomz.orderingsys.enums.OrderStatusEnum;
import com.guomz.orderingsys.enums.PayStatusEnum;
import com.guomz.orderingsys.enums.ProductStatusEnum;
import com.guomz.orderingsys.util.KeyUtil;
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
    @Resource
    private OrderMasterMapper orderMasterMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;

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
        productInfo.setProductId(KeyUtil.generateKey());
        productInfo.setProductName("汉堡");
        productInfo.setProductPrice(new BigDecimal("5"));
        productInfo.setProductStock(new BigDecimal("10"));
        productInfo.setProductDescription("哈啊哈");
        productInfo.setProductIcon("aaa");
        productInfo.setProductStatus(ProductStatusEnum.ON_SALE.getCode());
        productInfo.setProductCategoryType(2);
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

    @Test
    public void testAddOrder(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(KeyUtil.generateKey());
        orderMaster.setBuyerName("guomz");
        orderMaster.setBuyerPhone("123123123");
        orderMaster.setBuyerAddress("home");
        orderMaster.setBuyerOpenid("wx123123123123");
        orderMaster.setOrderAmount(new BigDecimal("10"));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.UNPAID.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());

        orderMasterMapper.insert(orderMaster);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(KeyUtil.generateKey());
        orderDetail.setOrderId(orderMaster.getOrderId());
        orderDetail.setProductId("1627989001484");
        orderDetail.setProductName("汉堡");
        orderDetail.setProductPrice(new BigDecimal("5"));
        orderDetail.setProductQuantity(new BigDecimal("2"));
        orderDetail.setProductIcon("aaa");
        orderDetail.setCreateTime(new Date());
        orderDetail.setUpdateTime(new Date());

        orderDetailMapper.insert(orderDetail);


    }
}
