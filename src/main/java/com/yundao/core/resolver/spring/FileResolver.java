package com.yundao.core.resolver.spring;

import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件解析
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class FileResolver {

    private static Log log = LogFactory.getLog(FileResolver.class);

    /**
     * 获取指定路径的文件
     *
     * @param path
     * @return
     */
    public static List<URL> getFile(String path) {
        List<URL> result = new ArrayList<>();
        try {
            Resource[] resources = getResourceResolver().getResources(path);
            for (Resource resource : resources) {
                if (resource.exists()) {
                    result.add(resource.getURL());
                }
            }
        }
        catch (Exception e) {
            log.warn("获取指定路径的文件时异常path=" + path);
        }
        return result;
    }

    private static ResourcePatternResolver getResourceResolver() {
        return new PathMatchingResourcePatternResolver();
    }

}