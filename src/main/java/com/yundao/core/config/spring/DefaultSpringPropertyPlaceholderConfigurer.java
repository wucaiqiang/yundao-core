package com.yundao.core.config.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Map.Entry;
import java.util.Properties;

/**
 * spring配置文件加载
 *
 * @author wupengfei wupf86@126.com
 */
public class DefaultSpringPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

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
    public static String getDBType() {
        return dbType;
    }

    /**
     * 返回是否正式环境
     *
     * @return
     */
    public static boolean isProduct() {
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
}