package com.yundao.core.search.solr;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import com.yundao.core.config.http.HttpConfigEnum;
import com.yundao.core.utils.ConfigUtils;

/**
 * solr工厂
 * 
 * @author wupengfei wupf86@126.com
 * 
 */
public abstract class SolrFactory {

	private static HttpSolrClient solrClient;

	static {
		solrClient = new HttpSolrClient(ConfigUtils.getValue(HttpConfigEnum.SOLR_URL.getKey()));
		solrClient.setSoTimeout(NumberUtils.toInt(ConfigUtils.getValue(HttpConfigEnum.READ_TIMEOUT.getKey())));
		solrClient.setConnectionTimeout(NumberUtils.toInt(ConfigUtils.getValue(HttpConfigEnum.CONNECTION_TIMEOUT.getKey())));
		solrClient.setDefaultMaxConnectionsPerHost(
				NumberUtils.toInt(ConfigUtils.getValue(HttpConfigEnum.MAX_CONNECTIONS_PER_HOST.getKey())));
		solrClient.setMaxTotalConnections(
				NumberUtils.toInt(ConfigUtils.getValue(HttpConfigEnum.MAX_TOTAL_CONNECTIONS.getKey())));
		solrClient.setFollowRedirects(false);
		solrClient.setAllowCompression(true);
		solrClient.setRequestWriter(new BinaryRequestWriter());
	}

	/**
	 * 获取solr连接
	 * 
	 * @return
	 */
	public static HttpSolrClient getHttpSolrClient() {
		return solrClient;
	}

}