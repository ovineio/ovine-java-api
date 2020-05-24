package com.igroupes.rtadmin.util;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.igroupes.rtadmin.dto.PageRequest;
import com.igroupes.rtadmin.dto.PageResponse;

public class PageDTOUtil {


    public static void fixPageDTO(PageRequest pageRequest) {
        final int DEFAULT_LIMIT = 50;
        if (null != pageRequest) {
            if (null == pageRequest.getPage() || pageRequest.getPage() < 0) {
                pageRequest.setPage(0);
            }
            if (null == pageRequest.getSize() || pageRequest.getSize() < 0) {
                pageRequest.setSize(DEFAULT_LIMIT);
            }

        }
    }

    public static void setPageResponse(IPage page, PageResponse pageResponse) {
        pageResponse.setCount(page.getTotal());
        pageResponse.setPage(page.getCurrent());
        pageResponse.setSize(page.getSize());
    }
}
