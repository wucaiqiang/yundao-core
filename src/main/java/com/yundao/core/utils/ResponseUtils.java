package com.yundao.core.utils;

import com.yundao.core.constant.CommonConstant;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * response输出工具类
 *
 * @author wupengfei wupf86@126.com
 */
public abstract class ResponseUtils {

    private static Log log = LogFactory.getLog(ResponseUtils.class);

    /**
     * json形式输出内容
     *
     * @param response HttpServletResponse
     * @param content  内容
     */
    public static void printlnJson(HttpServletResponse response, String content) {
        println(response, "application/json", content);
    }

    /**
     * 文本形式输出内容
     *
     * @param response HttpServletResponse
     * @param content  内容
     */
    public static void printlnText(HttpServletResponse response, String content) {
        println(response, "text/plain", content);
    }

    /**
     * html形式输出内容
     *
     * @param response HttpServletResponse
     * @param content  内容
     */
    public static void printlnHtml(HttpServletResponse response, String content) {
        println(response, "text/html", content);
    }

    /**
     * xml形式输出内容
     *
     * @param response HttpServletResponse
     * @param content  内容
     */
    public static void printlnXml(HttpServletResponse response, String content) {
        println(response, "text/xml", content);
    }

    /**
     * 以指定的形式输出内容
     *
     * @param response    HttpServletResponse
     * @param contentType 内容类型
     * @param content     内容
     */
    public static void println(HttpServletResponse response, String contentType, String content) {
        if (response == null || BooleanUtils.isBlank(contentType)) {
            log.begin("contentType=", contentType);
            log.info("参数为空");
            return;
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
            response.setContentType(contentType + ";charset=" + CommonConstant.UTF_8);
            response.setCharacterEncoding(CommonConstant.UTF_8);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            out.println(content);
            out.flush();
        }
        catch (Exception e) {
            log.error("输出内容时异常", e);
        }
        finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 显示字节内容
     *
     * @param response HttpServletResponse
     * @param content  字节内容
     * @throws IOException io异常，如果流关闭
     */
    public static void writeByte(HttpServletResponse response, byte[] content) throws IOException {
        if (response == null || content == null) {
            log.info("参数为空");
            return;
        }
        response.getOutputStream().write(content, 0, content.length);
        response.getOutputStream().close();
    }

}
