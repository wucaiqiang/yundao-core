package com.yundao.core.search.solr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.pagination.PaginationSupport;
import com.yundao.core.search.FullTextSearch;
import com.yundao.core.utils.BooleanUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 * solr实现全文检索
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public class SolrFullTextSearch implements FullTextSearch {

	private static Log log = LogFactory.getLog(PaginationSupport.class);

	@Override
	public void addOrUpdateIndex(Map<String, Object> fieldValue) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(1);
		result.add(fieldValue);
		this.addOrUpdateIndex(result);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addOrUpdateIndex(List<Map<String, Object>> fieldValue) throws Exception {
		int size = 0;
		if (fieldValue == null || (size = fieldValue.size()) == 0) {
			log.info("索引为空");
			return;
		}
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (int i = 0; i < size; i++) {
			Map<String, Object> fieldValueMap = fieldValue.get(i);
			SolrInputDocument doc = new SolrInputDocument();
			for (Map.Entry<String, Object> entry : fieldValueMap.entrySet()) {
				Object valueObj = entry.getValue();
				if (valueObj instanceof List) {
					for (Object value : (List) valueObj) {
						doc.addField(entry.getKey(), value);
					}
				}
				else {
					doc.addField(entry.getKey(), valueObj);
				}
			}
			docs.add(doc);
		}

		UpdateRequest updateReq = new UpdateRequest();
		updateReq.setAction(UpdateRequest.ACTION.COMMIT, false, false);
		updateReq.add(docs);
		updateReq.process(SolrFactory.getHttpSolrClient());
	}

	@Override
	public void deleteIndex(String uniqueKey, Object value) throws Exception {
		HttpSolrClient client = SolrFactory.getHttpSolrClient();
		client.deleteByQuery(uniqueKey + ":" + value);
		client.commit();
	}

	@Override
	public PaginationSupport<Map<String, Object>> search(String query, List<String> filterQuery,
			Map<String, Boolean> sort, PaginationSupport<Map<String, Object>> pagination) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query);

		// 过滤查询
		int size = filterQuery != null ? filterQuery.size() : 0;
		for (int i = 0; i < size; i++) {
			solrQuery.addFilterQuery(filterQuery.get(i));
		}

		// 排序
		if (!BooleanUtils.isEmpty(sort)) {
			for (Map.Entry<String, Boolean> entry : sort.entrySet()) {
				solrQuery.addSort(entry.getKey(), entry.getValue() ? ORDER.asc : ORDER.desc);
			}
		}

		// 设置分页
		if (pagination == null) {
			pagination = new PaginationSupport<Map<String, Object>>();
		}
		else {
			solrQuery.setStart((pagination.getCurrentPage() - 1) * pagination.getPageSize());
			solrQuery.setRows(pagination.getPageSize());
		}

		// 查询
		QueryResponse queryRes = SolrFactory.getHttpSolrClient().query(solrQuery);
		SolrDocumentList docs = queryRes.getResults();
		int totalCount = ((Long) docs.getNumFound()).intValue();
		pagination.setTotalCount(totalCount);

		// 设置查询结果
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>(
				pagination == null ? totalCount : pagination.getPageSize());
		for (SolrDocument doc : docs) {
			Map<String, Object> fieldValue = new HashMap<String, Object>();
			Collection<String> fieldNames = doc.getFieldNames();
			Iterator<String> it = fieldNames.iterator();
			while (it.hasNext()) {
				String name = it.next();
				Object value = doc.getFieldValue(name);
				fieldValue.put(name, value);
			}
			datas.add(fieldValue);
		}
		pagination.setDatas(datas);
		return pagination;
	}

}
