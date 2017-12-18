package com.yundao.core.pagination;

import java.io.Serializable;

/**
 * 分页参数
 *
 * @author wupengfei wupf86@126.com
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private Integer currentPage = 1;

    /**
     * 每页显示数
     */
    private Integer pageSize = 10;

    /**
     * @return 分页的偏移量
     */
    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
