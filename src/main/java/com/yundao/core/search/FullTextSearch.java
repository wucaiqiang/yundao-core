package com.yundao.core.search;

import java.util.List;
import java.util.Map;

import com.yundao.core.pagination.PaginationSupport;

/**
 * 全文本检索
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public interface FullTextSearch {

	/**
	 * 添加或更新索引
	 * 
	 * @param fieldValue
	 * @throws Exception
	 */
	public void addOrUpdateIndex(Map<String, Object> fieldValue) throws Exception;

	/**
	 * 添加或更新索引
	 * 
	 * @param fieldValue
	 * @throws Exception
	 */
	public void addOrUpdateIndex(List<Map<String, Object>> fieldValue) throws Exception;

	/**
	 * 删除索引
	 * 
	 * @param uniqueKey
	 * @param value
	 * @throws Exception
	 */
	public void deleteIndex(String uniqueKey, Object value) throws Exception;

	/**
	 * 搜索
	 * 
	 * @param query
	 * @param filterQuery
	 * @param sort
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public PaginationSupport<Map<String, Object>> search(String query, List<String> filterQuery,
			Map<String, Boolean> sort, PaginationSupport<Map<String, Object>> pagination) throws Exception;

}
