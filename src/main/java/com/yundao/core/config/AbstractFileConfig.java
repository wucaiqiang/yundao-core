package com.yundao.core.config;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.PropertiesUtils;
import com.yundao.core.resolver.spring.FileResolver;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 加载配置文件
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class AbstractFileConfig {

    private static Log log = LogFactory.getLog(AbstractFileConfig.class);

    private static Map<String, String> configMap = new ConcurrentHashMap<String, String>();

    /**
     * 加载配置文件
     *
     * @param file   文件路径
     * @param prefix 前辍
     */
    protected static void load(String file, String prefix) {
        try {
            doLoad(file, prefix, false);
        }
        catch (Exception e) {
            log.error("加载配置文件失败，file=" + file, e);
        }
    }

    /**
     * 重新加载配置文件
     *
     * @param file   文件路径
     * @param prefix 前辍
     */
    public static void reload(String file, String prefix) {
        try {
            doLoad(file, prefix, true);
        }
        catch (Exception e) {
            log.error("重新加载配置文件失败，file=" + file, e);
        }
    }

    /**
     * 重新加载配置文件
     *
     * @param subclassName 子类名
     * @param classLoader  加载器
     */
    @SuppressWarnings("NullArgumentToVariableArgMethod")
    public static void reload(String subclassName, ClassLoader classLoader) {
        try {
            Class<?> clazz = ClassUtils.forName(subclassName, classLoader);

            // 获取前辍
            Method prefixMethod = ClassUtils.getStaticMethod(clazz, "getKeyPrefix", null);
            String prefix = (String) prefixMethod.invoke(null, null);

            // 获取文件
            Method fileMethod = ClassUtils.getStaticMethod(clazz, "getFile", null);
            String file = (String) fileMethod.invoke(null, null);

            AbstractFileConfig.reload(file, prefix);
        }
        catch (Exception e) {
            log.error("重新加载配置文件失败，subclassName=" + subclassName + ",classLoader=" + classLoader, e);
        }
    }

    /**
     * 获取配置文件中指定key的值
     *
     * @param key 键
     * @return 配置的值
     */
    public static String getValue(String key) {
        return getValue(key, null);
    }

    /**
     * 获取配置文件中指定key的值，若==null则返回默认值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 配置的值
     */
    public static String getValue(String key, String defaultValue) {
        String result = configMap.get(key);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    /**
     * 清空指定前辍的内容
     *
     * @param prefix 前辍
     */
    protected static void clear(String prefix) {
        configMap.entrySet().stream().filter(entry -> entry.getKey().startsWith(prefix)).forEach(entry -> configMap.remove(entry.getKey()));
    }

    /**
     * 获取配置文件的键值对
     *
     * @param subclassName 子类名
     * @param classLoader  加载器
     */
    public static Map<String, String> getKeys(String subclassName, ClassLoader classLoader) {
        try {
            Class<?> clazz = ClassUtils.forName(subclassName, classLoader);

            // 获取前辍
            Method prefixMethod = ClassUtils.getStaticMethod(clazz, "getKeyPrefix", null);
            String prefix = (String) prefixMethod.invoke(null, null);

            return getKeyStartWith(prefix);
        }
        catch (Exception e) {
            log.error("获取配置文件的键值对失败，subclassName=" + subclassName + ",classLoader=" + classLoader, e);
        }
        return null;
    }

    /**
     * 获取以指定前辍开始的键对
     *
     * @param prefix 前辍
     * @return 指定前辍开始的键值对
     */
    public static Map<String, String> getKeyStartWith(String prefix) {
        return configMap.entrySet().stream().filter(entry -> entry.getKey().startsWith(prefix)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * 替换占位符为map中的值
     *
     * @param value  待处理值
     * @param params 参数
     * @return 替换后的值
     */
    public static String replacePlaceholder(String value, Map<String, String> params) {
        String result = value;
        if (!BooleanUtils.isEmpty(params) && !BooleanUtils.isBlank(value)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result = result.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue());
            }
        }
        return result;
    }

    private static void doLoad(String file, String prefix, boolean isReload) throws IOException {
        log.begin("开始加载配置文件，file=", file, "，prefix=", prefix, "，isReload=", isReload);
        if (file == null) {
            return;
        }
        List<URL> files = FileResolver.getFile(file);
        for (URL each : files) {
            Properties prop = PropertiesUtils.load(each.openStream());
            if (prop == null) {
                log.warn("配置文件路径有误，file=" + each);
                continue;
            }
            Map<String, String> map = PropertiesUtils.getToMap(prop, prefix);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                if (isReload || !configMap.containsKey(key)) {
                    String value = entry.getValue().trim();
                    configMap.put(key, value);
                    log.info("配置文件，" + key + "=" + value);
                }
                else {
                    log.warn("配置文件键重复，key=" + key + "，file=" + file);
                }
            }
        }
        log.end();
    }

}
