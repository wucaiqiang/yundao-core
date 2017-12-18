package com.yundao.core.config.spring;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.baidu.disconf.client.addons.properties.PropertiesReloadedEvent;
import com.baidu.disconf.client.addons.properties.ReloadingPropertyPlaceholderConfigurer;

/**
 * 
 * @author Jiang Liang
 * @date 2016年10月24日上午10:53:53
 */
public class SpringPropertyPlaceholderConfigurer extends ReloadingPropertyPlaceholderConfigurer {

    public static final String RD = "0";
    public static final String QA = "1";
    public static final String ONLINE = "2";
    private static final String DB_TYPE = "db.type";
    private static String dbType = RD;

    /**
     * 获取数据库的类型
     *
     * @return
     */
    public String getDBType() {
        return dbType;
    }

    /**
     * 返回是否正式环境
     *
     * @return
     */
    public boolean isProduct() {
        return getDBType().equals(ONLINE);
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        // 获取当前数据库类型的值
        for (Entry<Object, Object> entry : props.entrySet()) {
            if (DB_TYPE.equals(entry.getKey().toString())) {
                dbType = String.valueOf(entry.getValue());
                break;
            }
        }

        // 设置数据库的值
        String dbTypeValue = "." + dbType;
        Properties processProps = new Properties();
        for (Entry<Object, Object> entry : props.entrySet()) {
            String key = String.valueOf(entry.getKey());
            if (key.endsWith(dbTypeValue)) {
                key = key.substring(0, key.length() - dbTypeValue.length());
            }
            processProps.put(key, entry.getValue());
        }
        super.processProperties(beanFactoryToProcess, processProps);
    }
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
	private Properties properties;

	public SpringPropertyPlaceholderConfigurer() {
		super();
		init();
	}

	void init() {
		try {
			this.properties = super.mergeProperties();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("", e);
		}
	}

	public Object getValue(String key) {
		if (properties.isEmpty()) {
			init();
		}
		return properties.get(key);
	}

	@Override
	public void propertiesReloaded(PropertiesReloadedEvent event) {
		super.propertiesReloaded(event);
		init();
		logger.info("init_by update reload");
	}

}