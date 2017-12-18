/**
 *
 */
package com.yundao.core.utils;

import com.yundao.core.disconf.common.DownloadFileBean;
import com.yundao.core.ed.config.EDFileConfig;
import com.yundao.core.resolver.spring.FileResolver;
import com.yundao.core.config.spring.SpringPropertyPlaceholderConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Jon Chiang
 * @date 2016年7月11日
 */
@Component
public class ConfigUtils {

    /**
     * 所有工程内的配置文件
     */
    private static final String FILE = "classpath*:**/*.properties";
    private static final Map<String, String> configMap = new HashMap<>();
    //不多解释
    static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
    static SpringPropertyPlaceholderConfigurer placeholderConfigurer;

    static {
        try {
            doLoad(FILE);//兼容本地测试调用

            String common = DownloadFileBean.getDownloadPath() + "/**/*.properties";
            logger.info("开始加载公共资源common=" + common);
            doLoad(common);
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error(FILE);
            logger.error("初始化ConfigUtils出错了！", e);
        }
    }

    /**
     * 返回是否正式环境
     *
     * @return
     */
    public static boolean isProduct() {
        return placeholderConfigurer.isProduct();
    }

    /**
     * 提供出来的主要方法
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        String localValue = configMap.get(key);
        if (placeholderConfigurer == null) {
            return localValue;
        }
        Object value = placeholderConfigurer.getValue(key);
        if (value == null) {
            return localValue;
        }
        else {
            return value.toString();
        }
    }

    /**
     * 重载的一个方法，拿不到值取默认
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getValue(String key, String defaultValue) {
        String value = getValue(key);
        return value == null ? defaultValue : value;
    }

    /**
     * load 非配置中心的配置。
     *
     * @param fileName
     * @throws IOException
     */
    private static void doLoad(String fileName) throws IOException {
        if (fileName == null) {
            return;
        }
        String dbTypeValue = ".0";//默认选择 xxx.0
        // 设置数据库的值
        if (placeholderConfigurer != null) {
            dbTypeValue = "." + placeholderConfigurer.getDBType();
        }
        List<URL> urls = FileResolver.getFile(fileName);
        for (URL url : urls) {
            logger.info(">>>>>>>>>>>>>>> configUtils 加载文件 " + url.getPath() + " <<<<<<<<<<<<<<<1");
            Properties prop = PropertiesUtils.load(url.openStream());
            if (prop == null) {
                logger.info(url.getPath() + " 不能读取到");
                continue;
            }
            // 获取当前数据库类型的值
            Map<String, String> map = PropertiesUtils.getToMap(prop, "");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().trim();
                if (key.endsWith(dbTypeValue)) {
                    key = key.substring(0, key.length() - dbTypeValue.length());
                }
                configMap.put(key, value);
                logger.debug(" ConfigUtils load localProperties " + key + ":" + value);
            }
        }

        // 重新加载加解密
        EDFileConfig.reload();
    }

    @Autowired(required = false)
    public void setPlaceholderConfigurer(SpringPropertyPlaceholderConfigurer placeholderConfigurer) {
        ConfigUtils.placeholderConfigurer = placeholderConfigurer;
        try {
            doLoad(FILE);//当托管配置容器注入进来的时候，初始化这个
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error("初始化ConfigUtils出错了！", e);
        }
    }
}
