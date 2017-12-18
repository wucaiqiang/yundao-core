package com.yundao.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yundao.core.log.Log;
import com.yundao.core.code.config.CommonCode;
import com.yundao.core.exception.BaseRuntimeException;
import com.yundao.core.log.LogFactory;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * json工具类
 *
 * @author wupengfei wupf86@126.com
 */
@SuppressWarnings("rawtypes")
public abstract class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Log log = LogFactory.getLog(JsonUtils.class);

    static {
        MAPPER.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setDateFormat(new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 获取数组对应的实体类
     *
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static JavaType getCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClasses) {
        return MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClasses);
    }

    /**
     * json字符串转化为对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        if (BooleanUtils.isBlank(json) || clazz == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        }
        catch (Exception e) {
            log.error("转化异常", e);
            throw new BaseRuntimeException(CommonCode.COMMON_1007, e);
        }
    }

    /**
     * json字符串转化为数组对象
     *
     * @param json
     * @param javaType
     * @return
     */
    public static <T> T jsonToObject(String json, JavaType javaType) {
        if (BooleanUtils.isBlank(json) || javaType == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, javaType);
        }
        catch (Exception e) {
            log.error("转化异常", e);
            throw new BaseRuntimeException(CommonCode.COMMON_1007, e);
        }
    }

    /**
     * json字符串转化为泛型对象
     *
     * @param json
     * @param typeReference
     * @return
     */
    public static <T> T jsonToObject(String json, TypeReference typeReference) {
        if (BooleanUtils.isBlank(json) || typeReference == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, typeReference);
        }
        catch (Exception e) {
            log.error("转化异常", e);
            throw new BaseRuntimeException(CommonCode.COMMON_1007, e);
        }
    }

    /**
     * 对象转化为json字符串
     *
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        if (object == null) {
            return null;
        }
        if (BooleanUtils.isSimpleType(object)) {
            return "{\"value\":\"" + object.toString() + "\"}";
        }

        StringWriter sw = new StringWriter();
        try {
            MAPPER.writeValue(sw, object);
        }
        catch (Exception e) {
            log.error("转化异常", e);
            throw new BaseRuntimeException(CommonCode.COMMON_1007, e);
        }
        return sw.toString();
    }

}
