package com.yundao.core.utils;

import com.yundao.core.constant.CommonConstant;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * freemark工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class FreemarkerUtils {

    private static Version version = new Version("2.3.23");

    /**
     * 生成静态文件
     *
     * @param templatePath 模板源文件路径
     * @param templateName 模板源文件名称
     * @param htmlName     目标文件
     * @param params       数据
     * @throws Exception
     */
    public static void generate(String templatePath, String templateName, String htmlName, Map<String, Object> params)
            throws Exception {
        if (BooleanUtils.isBlank(templatePath) || BooleanUtils.isBlank(templateName)
                || BooleanUtils.isBlank(htmlName)) {
            return;
        }

        Writer out = null;
        try {
            Configuration cfg = new Configuration(version);
            cfg.setDirectoryForTemplateLoading(new File(templatePath));
            cfg.setEncoding(Locale.getDefault(), CommonConstant.UTF_8);
            Template template = cfg.getTemplate(templateName);

            File fileName = new File(htmlName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), CommonConstant.UTF_8));
            template.process(params, out);
        }
        finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 处理字符串模板
     *
     * @param templateKey
     * @param templateValue
     * @param params
     * @return
     * @throws Exception
     */
    public static String process(String templateKey, String templateValue, Map<String, Object> params)
            throws Exception {
        if (BooleanUtils.isBlank(templateKey) || BooleanUtils.isBlank(templateValue)) {
            return null;
        }

        Configuration cfg = new Configuration(version);

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate(templateKey, templateValue);

        cfg.setTemplateLoader(stringLoader);
        cfg.setDefaultEncoding(CommonConstant.UTF_8);

        Template template = cfg.getTemplate(templateKey);
        StringWriter sw = new StringWriter();
        template.setClassicCompatible(true);
        template.process(params, sw);
        return sw.toString();
    }

}
