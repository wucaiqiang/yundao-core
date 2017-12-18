package com.yundao.core.enums.product;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.yundao.core.utils.ConfigUtils;
import com.yundao.core.utils.FileUtils;

/**
 * 产品类型枚举
 *s
 * @author wupengfei wupf86@126.com
 */
public enum ProductTypeEnum {

    /**
     * 固收类产品类型，如信托
     */
    PRO_TYPE_FIX("pro_type_fix", 410) {
        @Override
        public String getDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/fix/get_formal");
        }

        @Override
        public String getToCDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/fix_to_c/get");
        }

        @Override
        public String getToBDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/fix_to_b/get");
        }

        @Override
        public String getListUrl() {
            return FileUtils.getRealPath(productUrl, "/fix/gets");
        }

    },

    /**
     * 私募类产品
     */
    PRO_TYPE_PRIVATE("pro_type_private", 100) {
        @Override
        public String getDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/private/get_formal");
        }

        @Override
        public String getToCDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/private_to_c/get");
        }

        @Override
        public String getToBDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/private_to_b/get");
        }

        @Override
        public String getListUrl() {
            return FileUtils.getRealPath(productUrl, "/private/gets");
        }
    },

    /**
     * 股权类产品
     */
    PRO_TYPE_STOCK("pro_type_stock", 400) {
        @Override
        public String getDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/stock/get_formal");
        }

        @Override
        public String getToCDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/stock_to_c/get");
        }

        @Override
        public String getToBDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/stock_to_b/get");
        }

        @Override
        public String getListUrl() {
            return FileUtils.getRealPath(productUrl, "/stock/gets");
        }
    },

    /**
     * 现金类产品类型
     */
    PRO_TYPE_CASH("pro_type_cash", 420) {
        @Override
        public String getDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/cash/get_formal");
        }

        @Override
        public String getToCDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/cash_to_c/get");
        }

        @Override
        public String getToBDetailUrl() {
            return FileUtils.getRealPath(productUrl, "/cash_to_b/get");
        }

        @Override
        public String getListUrl() {
            return FileUtils.getRealPath(productUrl, "/cash/gets");
        }

    };

    private static Map<String, ProductTypeEnum> enumMap = new HashMap<>();
    private static String productUrl = ConfigUtils.getValue("pro.url");

    static {
        EnumSet<ProductTypeEnum> set = EnumSet.allOf(ProductTypeEnum.class);
        for (ProductTypeEnum each : set) {
            enumMap.put(each.getName(), each);
        }
    }

    private String name;
    private Integer value;

    ProductTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 获取产品类型枚举
     *
     * @param name
     * @return
     */
    public static ProductTypeEnum getProductTypeEnum(String name) {
        ProductTypeEnum result = enumMap.get(name);
        return result;
    }

    /**
     * 获取所有产品的链接
     *
     * @return
     */
    public static String getAllListUrl() {
        return FileUtils.getRealPath(productUrl, "/product/gets");
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * 获取To C 详情的链接
     *
     * @return
     */
    public abstract String getToCDetailUrl();

    /**
     * 获取To B 详情的链接
     *
     * @return
     */
    public abstract String getToBDetailUrl();

    /**
     * 获取详情的链接
     *
     * @return
     */
    public abstract String getDetailUrl();

    /**
     * 获取列表的链接
     *
     * @return
     */
    public abstract String getListUrl();

}
