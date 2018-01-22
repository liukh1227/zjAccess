package com.zj.entity.sm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author five
 * @date 2017年2月23日11:59:20
 */
public class MessageQueryForm extends PageQueryForm implements Serializable {

    private String type;

    private String externalRelatedId;

    private String operatorId;

    private String userId;

    private Integer status;
    
    private static final long serialVersionUID = 1L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getExternalRelatedId() {
        return externalRelatedId;
    }

    public void setExternalRelatedId(String externalRelatedId) {
        this.externalRelatedId = externalRelatedId == null ? null : externalRelatedId.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "MessageQueryForm [type=" + type + ", externalRelatedId="
				+ externalRelatedId + ", operatorId=" + operatorId
				+ ", userId=" + userId + ", status=" + status + "]";
	}
    

}
