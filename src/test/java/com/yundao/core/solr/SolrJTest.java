package com.yundao.core.solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import com.yundao.core.search.solr.SolrFactory;

/**
 * solr测试类
 * 
 * @author wupengfei wupf86@126.com
 *
 */
public class SolrJTest {

	private static HttpSolrClient solrClient;

	//@BeforeClass
	public static void beforeClassClass() throws Exception {
		solrClient = SolrFactory.getHttpSolrClient();
	}

	//@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	//@Before
	public void setUp() throws Exception {

	}

	//@After
	public void tearDown() throws Exception {

	}

	//@Test
	public void query() throws Exception {/*
		SolrQuery query = new SolrQuery();

		// 主查询
		query.setQuery("title:'标题'");

		// 采用过滤器查询可以提高性能
		query.addFilterQuery("subject:名字0");

		// 区间查询
		query.addFilterQuery("id:[0 TO 3]");

		// 添加排序
		query.addSort("id", ORDER.desc);

		// 高亮
		// query.set("q", "title:name1");
		query.setHighlight(true);// 开启高亮功能
		query.addHighlightField("title");
		query.addHighlightField("subject");
		// query.setHighlightSnippets(10);

		// 渲染标签
		query.setHighlightSimplePre("&amp;lt;font color=\"red\"&amp;gt;");
		query.setHighlightSimplePost("&amp;lt;/font&amp;gt;");
		query.set("hl.usePhraseHighlighter", true);
		query.set("hl.highlightMultiTerm", true);
		query.set("hl.snippets", 3);// 三个片段,默认是1
		query.set("hl.fragsize", 50);// 每个片段50个字，默认是100
		query.setParam("hl.fl", "subject,title");

		// query.set("qt", "/spell");
		// query.set("spellcheck", "on");
		// query.set("spellcheck.q", "title");
		// query.set("spellcheck.collate", "true");
		// query.set("spellcheck.dictionary", "file");
		// query.set("spellcheck.build", "true");

		// query.set("facet", "on");
		// query.set("facet.field", "title");
		query.setFacet(true).setFacetMinCount(1).setFacetLimit(5)// 段
				.addFacetField("title");// 分片字段

		// 分页返回结果
		query.setStart(0);
		query.setRows(30);

		QueryResponse rsp = solrClient.query(query);

		Map<String, Map<String, List<String>>> hlMap = rsp.getHighlighting();
		System.out.println("----------------size()=" + hlMap.size());
		for (Map.Entry<String, Map<String, List<String>>> entry : hlMap.entrySet()) {
			System.out.println("index=" + entry.getKey());
			Map<String, List<String>> valueMap = entry.getValue();
			for (Map.Entry<String, List<String>> listEntry : valueMap.entrySet()) {
				System.out.println("field=" + listEntry.getKey());
				List<String> list = listEntry.getValue();
				for (String str : list) {
					System.out.println("value=" + str);
				}
			}
		}
		System.out.println("----------------size()=" + hlMap.size());

		List<Product> beans = rsp.getBeans(Product.class);
		System.out.println("--------beans.size()=" + beans.size());
		// 输出符合条件的结果数
		System.out.println("NumFound: " + rsp.getResults().getNumFound());

		// 输出结果
		for (int i = 0; i < beans.size(); i++) {
			Product product = beans.get(i);
			System.out.println(product.toString());
		}

		System.out.println("=====================");
		List<SolrDocument> docs = rsp.getResults();
		for (SolrDocument doc : docs) {
			System.out.println(doc.getFieldValueMap());
		}

		System.out.println("=====================");
		List<FacetField> ffs = rsp.getLimitingFacets();
		for (FacetField ff : ffs) {
			System.out.println(ff.getName());
			System.out.println(ff.toString());

			List<Count> values = ff.getValues();
			System.out.println(values.toString());
		}
	*/}

//	@Test
//	public void deleteIndex() throws Exception {
//		solrClient.deleteByQuery("*:*");
//		solrClient.commit();
//	}
//
//	@Test
//	public void addIndex() throws Exception {
//		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//		for (int i = 0; i < 5; i++) {
//			docs.add(this.getDocument1(i));
//		}
//
//		UpdateRequest req = new UpdateRequest();
//		req.setAction(UpdateRequest.ACTION.COMMIT, false, false);
//		req.add(docs);
//		UpdateResponse rsp = req.process(solrClient);
//		System.out.println(rsp.getStatus());
//	}
//
//	private SolrInputDocument getDocument1(int index) {
//		SolrInputDocument result = new SolrInputDocument();
//		result.addField("id", index);
//		result.addField("title", "标题" + index);
//		result.addField("subject", "名字" + index);
//		result.addField("description", "描述" + index);
//		return result;
//	}

}