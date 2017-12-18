package com.yundao.core.controller;

import com.yundao.core.code.Result;
import com.yundao.core.config.AbstractFileConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Set;

/**
 * 文件控制类
 *
 * @author wupengfei wupf86@126.com
 */
@Controller
@RequestMapping("/file")
@ResponseBody
public class FileController {

    /**
     * 重新加载
     *
     * @param className 配置类名
     * @return 是否成功
     */
    @RequestMapping(value = "/reload")
    public Result<Boolean> reload(String className) {
        AbstractFileConfig.reload(className, this.getClass().getClassLoader());
        return Result.newSuccessResult(true);
    }

    /**
     * 获取配置文件的键
     *
     * @param className 配置类名
     * @return 配置文件的键集合
     */
    @RequestMapping(value = "/get/keys")
    public Result<Set<String>> getKeys(String className) {
        Set<String> result = null;
        Map<String, String> configMap = AbstractFileConfig.getKeys(className, this.getClass().getClassLoader());
        if (configMap != null) {
            result = configMap.keySet();
        }
        return Result.newSuccessResult(result);
    }

}
