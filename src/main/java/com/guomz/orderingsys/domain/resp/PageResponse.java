package com.guomz.orderingsys.domain.resp;

import com.github.pagehelper.PageInfo;
import com.guomz.orderingsys.enums.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResponse<T> extends BaseResponse{

    private Integer pageNum;
    private Integer pageSize;
    private Long totalCount;
    private Integer totalPage;
    private List<T> data;

    public PageResponse(PageInfo pageInfo){
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalCount = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
        this.data = pageInfo.getList();
    }

    public PageResponse(ResponseEnum responseEnum, PageInfo pageInfo){
        this(pageInfo);
        this.setCode(responseEnum.getCode());
        this.setMsg(responseEnum.getMessage());
    }
}
