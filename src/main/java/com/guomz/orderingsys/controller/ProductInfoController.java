package com.guomz.orderingsys.controller;

import com.guomz.orderingsys.domain.dto.ProductCategoryDto;
import com.guomz.orderingsys.domain.resp.BusinessResponse;
import com.guomz.orderingsys.domain.vo.CategoryProductVo;
import com.guomz.orderingsys.domain.vo.ProductBriefVo;
import com.guomz.orderingsys.entity.ProductCategory;
import com.guomz.orderingsys.entity.ProductInfo;
import com.guomz.orderingsys.enums.ResponseEnum;
import com.guomz.orderingsys.service.ProductCategoryService;
import com.guomz.orderingsys.service.ProductInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/product", produces = "application/json; charset=utf-8")
@Api(tags = "商品相关")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/showcategoryproduct")
    @ApiOperation("获取全部商品")
    public BusinessResponse<List<CategoryProductVo>> getCategoryProductList(){
        //获取全部商品与类目
        List<ProductInfo> productInfoList = productInfoService.getProductInfoOnSale();
        List<ProductCategory> productCategoryList = productCategoryService.getAllCategory();
        //按照类目分类商品
        List<CategoryProductVo> resultList = productCategoryList.stream()
                .map(productCategoryItem -> {
                    CategoryProductVo categoryProductVo = new CategoryProductVo();
                    BeanUtils.copyProperties(productCategoryItem, categoryProductVo);
                    List<ProductBriefVo> productBriefVoList = productInfoList.stream()
                            .filter(productInfoItem -> productInfoItem.getProductCategoryType().equals(productCategoryItem.getCategoryType()))
                            .map(productInfoItem -> {
                                ProductBriefVo productBriefVo = new ProductBriefVo();
                                BeanUtils.copyProperties(productInfoItem,productBriefVo);
                                return productBriefVo;
                            }).collect(Collectors.toList());
                    categoryProductVo.setProductList(productBriefVoList);
                    return categoryProductVo;
                }).collect(Collectors.toList());

        return new BusinessResponse<>(ResponseEnum.OK, resultList);
    }

    @PostMapping("/addcategory")
    @ApiOperation("添加分类")
    public BusinessResponse addProductCategory(@RequestBody ProductCategoryDto productCategoryDto){
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryDto, productCategory);
        productCategoryService.addCategory(productCategory);
        return new BusinessResponse(ResponseEnum.OK);
    }

    @GetMapping("/showallcategory")
    @ApiOperation("展示全部分类")
    public BusinessResponse<List<ProductCategoryDto>> getAllCategory(){
        List<ProductCategory> categoryList = productCategoryService.getAllCategory();
        List<ProductCategoryDto> categoryDtoList = categoryList.stream()
                .map(productCategory -> {
                    ProductCategoryDto dto = new ProductCategoryDto();
                    BeanUtils.copyProperties(productCategory, dto);
                    return dto;
                }).collect(Collectors.toList());

        return new BusinessResponse<>(ResponseEnum.OK, categoryDtoList);
    }
}
