package com.zj.entity.sm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-3-8 上午11:36:17
 */
public class FeedbackQueryForm extends PageQueryForm implements Serializable {

    private String publisherId;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId == null ? null : publisherId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
