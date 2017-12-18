package com.yundao.core.dto.log;

/**
 * 日志插入请求
 *
 * @author wupengfei wupf86@126.com
 */
public class LogInsertDto extends LogDto {

    private static final long serialVersionUID = 1L;
    public static final int TYPE = 0;

    /**
     * 记录字段名更改
     *
     * @param fieldName 字段名
     * @param newValue  新值
     */
    public void putField(String fieldName, String newValue) {
        FieldDto fieldDto = new FieldDto();
        fieldDto.setName(fieldName);
        fieldDto.setNewValue(newValue);
        this.addField(fieldDto);
    }
}
