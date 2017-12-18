package com.yundao.core.enums;

/**
 * date: 2017年7月15日 下午2:10:01
 *
 * @author:wucq
 * @description:
 */
public enum AppTypeEnum {
    /**
     * B端 pc
     */
    B_PC("b_pc"),
    /**
     * B端 app
     */
    B_APP("b_app"),
    /**
     * C端 h5
     */
    C_H5("c_h5"),

    /**
     * C端 pc
     */
    C_PC("c_pc"),

    /**
     * C端 app
     */
    C_APP("c_app");

    private AppTypeEnum(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

}
