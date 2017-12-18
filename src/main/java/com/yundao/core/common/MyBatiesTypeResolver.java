package com.yundao.core.common;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * Created by gjl on 2017/6/19.
 */
public class MyBatiesTypeResolver extends JavaTypeResolverDefaultImpl {

    @Override
    public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType temp = new FullyQualifiedJavaType("java.lang.Byte");
        if(temp.equals(introspectedColumn.getFullyQualifiedJavaType())){
            introspectedColumn.setFullyQualifiedJavaType(new FullyQualifiedJavaType("java.lang.Integer"));
        }
        return super.calculateJdbcTypeName(introspectedColumn);
    }
}
