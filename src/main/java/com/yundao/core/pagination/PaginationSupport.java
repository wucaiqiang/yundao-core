package com.yundao.core.pagination;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import org.apache.commons.lang.math.NumberUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 分页
 *
 * @author wupengfei wupf86@126.com
 */
public class PaginationSupport<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Log log = LogFactory.getLog(PaginationSupport.class);

    /**
     * 每页显示数
     */
    private Integer pageSize = 10;

    /**
     * 总数
     */
    private int totalCount = 0;

    /**
     * 当前页
     */
    private Integer currentPage = 1;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 数据
     */
    private List<T> datas;

    /**
     * 扩展参数
     */
    private Map<String, Object> map;


    /**
     * 创建默认的分页
     *
     * @param page 分页的参数
     * @return 分页对象
     */
    public static <T> PaginationSupport<T> newDefault(Page page) {
        PaginationSupport<T> result = new PaginationSupport<>();
        result.setCurrentPage(page.getCurrentPage());
        result.setPageSize(page.getPageSize());

        // 修正分页信息
        page.setCurrentPage(result.getCurrentPage());
        page.setPageSize(result.getPageSize());
        return result;
    }

    /**
     * 创建默认的分页
     *
     * @param params 请求： 【当前页码：currentPage，每页显示的条数：pageSize】
     *               返回：【分页偏移量：offset，更正后每页显示的条数：pageSize】
     * @return
     */
    public static <T> PaginationSupport<T> newDefault(Map<String, Object> params) {
        PaginationSupport<T> result = new PaginationSupport<T>();

        Object currentPage = params.get("currentPage");
        Object pageSize = params.get("pageSize");
        result.setCurrentPage(NumberUtils.toInt(currentPage == null ? null : currentPage.toString()));
        result.setPageSize(NumberUtils.toInt(pageSize == null ? null : pageSize.toString()));

        // 设置分页参数
        params.put("offset", (result.getCurrentPage() - 1) * result.getPageSize());
        params.put("pageSize", result.getPageSize());

        return result;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 1) {
            log.info("每页显示的条数非法，pageSize=" + pageSize);
        }
        else {
            this.pageSize = pageSize;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        return this.totalPage;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
