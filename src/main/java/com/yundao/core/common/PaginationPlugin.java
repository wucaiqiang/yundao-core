package com.yundao.core.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;

/**
 * <pre>
 * add pagination using mysql limit.
 * This class is only used in ibator code generator.
 * </pre>
 */
public class PaginationPlugin extends PluginAdapter {

    static List<String> continiuStr = new ArrayList<String>();

    static {
        continiuStr.add("id");
        continiuStr.add("updateTime");
        continiuStr.add("updaterId");
        continiuStr.add("createTime");
        continiuStr.add("creatorId");
        continiuStr.add("tenantId");
        continiuStr.add("isEnabled");
        continiuStr.add("isDelete");
        continiuStr.add("updateDate");
        continiuStr.add("updateUserId");
        continiuStr.add("createDate");
        continiuStr.add("createUserId");
    }

    private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        FullyQualifiedJavaType limitType = new FullyQualifiedJavaType("com.yundao.core.utils.Limit");
        topLevelClass.addImportedType(limitType);
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(limitType);
        field.setName(name);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(topLevelClass.getType());
        method.setName("set" + camel);
        method.addParameter(new Parameter(limitType, name));
        method.addBodyLine("this." + name + "=" + name + ";");
        method.addBodyLine("return this;");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(limitType);
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    private void addLimitParam(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getIntInstance());
        field.setName(name);
        field.setInitializationString("0");
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    private void addLimitXml(XmlElement element) {
        /*
		 * XmlElement isParameterPresenteElemen = (XmlElement) element
		 * .getElements().get(element.getElements().size() - 1);
		 */
        XmlElement if1Element = new XmlElement("if"); //$NON-NLS-1$
        if1Element.addAttribute(new Attribute("test", "limit == null")); //$NON-NLS-1$ //$NON-NLS-2$

        XmlElement sonEleIf = new XmlElement("if"); //$NON-NLS-1$
        sonEleIf.addAttribute(new Attribute("test", "limitStart gt 0 and limitEnd gt 0 ")); //$NON-NLS-1$ //$NON-NLS-2$
        // isNotNullElement.addAttribute(new Attribute("compareValue", "0"));
        // //$NON-NLS-1$ //$NON-NLS-2$
        sonEleIf.addElement(new TextElement("limit ${limitStart} , ${limitEnd}"));
        if1Element.addElement(sonEleIf);

        XmlElement sonEleElse = new XmlElement("if"); //$NON-NLS-1$
        sonEleElse.addAttribute(new Attribute("test", "limitStart lt 1 and limitEnd lt 0 ")); //$NON-NLS-1$ //$NON-NLS-2$
        // isNotNullElement.addAttribute(new Attribute("compareValue", "0"));
        // //$NON-NLS-1$ //$NON-NLS-2$
        sonEleElse.addElement(new TextElement("limit ${limitEnd}"));
        if1Element.addElement(sonEleElse);
        element.addElement(if1Element);

        XmlElement elseSon = new XmlElement("if");
        elseSon.addAttribute(new Attribute("test", "limit != null ")); //$NON-NLS-1$ //$NON-NLS-2$
        // isNotNullElement.addAttribute(new Attribute("compareValue", "0"));
        // //$NON-NLS-1$ //$NON-NLS-2$
        elseSon.addElement(new TextElement("limit ${limit.start} , ${limit.size}"));
        element.addElement(elseSon);
    }

    /**
     * @param method
     * @param interfaze
     */
    private void addSelectOne(Method method, Interface interfaze) {
        Method selectOne = new Method();
        selectOne.setName("selectOne");
        List<Parameter> parameters = method.getParameters();
        selectOne.setReturnType(parameters.get(0).getType());
        selectOne.addParameter(new Parameter(parameters.get(1).getType(), "example"));
        interfaze.addMethod(selectOne);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement root = document.getRootElement();
        List<Element> eles = root.getElements();
        Element addSelectOneXml = null;
        for (Element element : eles) {
            XmlElement ele = (XmlElement) element;
            if (ele.getAttributes().get(0).getValue().equals("selectByExample")) {
                addSelectOneXml = addSelectOneXml(ele, introspectedTable);
            }
        }
        root.addElement(addSelectOneXml);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * @param method
     * @param interfaze
     */
    private XmlElement addSelectOneXml(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement selectOneEle = new XmlElement(element);
        List<Attribute> as = selectOneEle.getAttributes();
        Attribute resultMap = as.get(1);
        Attribute parameterType = as.get(2);
        as.clear();
        selectOneEle.addAttribute(new Attribute("id", "selectOne"));
        selectOneEle.addAttribute(resultMap);
        selectOneEle.addAttribute(parameterType);
        List<Element> eles = selectOneEle.getElements();
        Iterator<Element> elesIt = eles.iterator();
        while (elesIt.hasNext()) {
            Element e = elesIt.next();
            if (e instanceof XmlElement) {
                XmlElement ele = (XmlElement) e;
                if (ele.getFormattedContent(1).contains("limit") || ele.getFormattedContent(1).contains("distinct")) {
                    elesIt.remove();
                }
            }
        }
        selectOneEle.addElement(new TextElement(" limit 1"));
        return selectOneEle;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        addSelectOne(method, interfaze);
        return super.clientUpdateByExampleWithBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }

    /**
     * 去掉V 前缀
     *
     * @param name
     * @return
     * @author Jon Chiang
     * @create_date 2014-5-8 下午3:44:00
     */
    private String getJavaName(String cname, String name) {
        if (cname.matches("._.*")) {
            char firstChar = Character.toLowerCase(name.charAt(1));
            name = name.substring(2, name.length());
            name = firstChar + name;
        }
        return name;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        for (IntrospectedColumn introspectedColumn : columns) {
            String javaProperty = this.getJavaName(introspectedColumn.getActualColumnName(), introspectedColumn.getJavaProperty());
            introspectedColumn.setJavaProperty(javaProperty);
        }
        super.initialized(introspectedTable);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("java.io.Serializable");
        topLevelClass.addImportedType("com.yundao.core.base.model.BaseModel");
        topLevelClass.addSuperInterface(new FullyQualifiedJavaType("java.io.Serializable"));
        topLevelClass.setSuperClass(new FullyQualifiedJavaType("com.yundao.core.base.model.BaseModel"));

        Field field = new Field();
        // private static final long serialVersionUID = 1L;
        field.setName("serialVersionUID");
        field.setType(new FullyQualifiedJavaType("long"));
        field.setStatic(true);
        field.setFinal(true);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setInitializationString("1L");
        topLevelClass.addField(field);
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // add field, getter, setter for limit clause
        addLimitParam(topLevelClass, introspectedTable, "limitStart");
        addLimitParam(topLevelClass, introspectedTable, "limitEnd");
        addLimit(topLevelClass, introspectedTable, "limit");

        List<Method> methods = topLevelClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals("createCriteria")) {
                method.addBodyLine(4, "criteria.andIsDeleteEqualTo(0);");
            }
        }
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        if (continiuStr.indexOf(field.getName()) > -1) {
            return false;
        }
        FullyQualifiedJavaType byteType = new FullyQualifiedJavaType("java.lang.Byte");
        if (field.getType().equals(byteType)) {
            field.setType(new FullyQualifiedJavaType("java.lang.Integer"));
        }
        String remarks = introspectedColumn.getRemarks();
        if (null != remarks && remarks.length() > 0) {
            //field.addJavaDocLine("//" + remarks);
            StringBuilder comment = new StringBuilder();
            comment.append("/**");
            comment.append("\n\t * ").append(remarks);
            comment.append("\n\t */");
            field.addJavaDocLine(comment.toString());
        }
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (continiuStr.indexOf(handleMethod(method.getName())) > -1) {
            return false;
        }
//		FullyQualifiedJavaType byteType = new FullyQualifiedJavaType("java.lang.Byte");
//		if(introspectedColumn.getFullyQualifiedJavaType().equals(byteType)){
//			String name = method.getParameters().get(0).getName();
//			List<Parameter> params = new ArrayList<Parameter>();
//			params.add(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"),name));
//			Parameter methodParameters = method.getParameters().get(0);
//		}
        return true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (continiuStr.indexOf(handleMethod(method.getName())) > -1) {
            return false;
        }
        return true;
    }

    private String handleMethod(String name) {
        if (name != null) {
            name = name.substring(3);
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        return name;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.mybatis.generator.api.PluginAdapter#setContext(org.mybatis.generator
     * .config.Context)
     */
    @Override
    public void setContext(Context context) {
        CommentGeneratorConfiguration cgc = new CommentGeneratorConfiguration();
        cgc.addProperty("suppressAllComments", "true");
        context.setCommentGeneratorConfiguration(cgc);
        // GeneratedXmlFile
        super.setContext(context);
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		/*
		 * XmlElement element = new XmlElement("cache");
		 * element.addAttribute(new Attribute("type",
		 * "com.zcmall.dts.security.redis.MyBatisLoggingRedisCache"));
		 * element.addAttribute(new Attribute("eviction", "LRU"));
		 * sqlMap.getDocument().getRootElement().addElement(element);
		 */
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.addAttribute(new Attribute("keyProperty", introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));// /设置主键返回策略
        element.addAttribute(new Attribute("useGeneratedKeys", "true"));
        return super.sqlMapInsertElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {// 设置主键返回策略
        element.addAttribute(new Attribute("keyProperty", introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));
        element.addAttribute(new Attribute("useGeneratedKeys", "true"));
        return super.sqlMapInsertSelectiveElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        // addSelectOneXml(element, introspectedTable);
        addLimitXml(element);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        // addSelectOneXml(element, introspectedTable);
        addLimitXml(element);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean validate(List<String> arg0) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getElements().add(new TextElement(" AND is_delete=0"));
        return true;
    }

}
