package com.yundao.core.dto.dictionary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典
 *
 * @author jan
 * @create 2017-11-20 16:33
 **/
public class DictionariesDto implements Serializable {


    static final long serialVersionUID = 1L;

    /**
     * 文本
     */
    private String label;

    /**
     * 值
     */
    private String value;

    /**
     * 排序，越小越靠前
     */
    private Integer sequence;

    /**
     * 选项
     */
    private List<DictionariesDto> selections;

    public List<DictionariesDto> getSelections() {
        if (this.selections == null)
            this.selections = new ArrayList<>();
        return selections;
    }

    public void setSelections(List<DictionariesDto> selections) {
        this.selections = selections;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

}
