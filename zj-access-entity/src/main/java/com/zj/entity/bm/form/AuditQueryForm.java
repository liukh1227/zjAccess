package com.zj.entity.bm.form;

import java.io.Serializable;
import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-20 下午5:19:22
 */
public class AuditQueryForm extends PageQueryForm implements Serializable {
	
	private static final long serialVersionUID = -1276982184014145919L;
	private String relatedId;
    private Integer informationType;
	public String getRelatedId() {
		return relatedId;
	}
	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}
	public Integer getInformationType() {
		return informationType;
	}
	public void setInformationType(Integer informationType) {
		this.informationType = informationType;
	}
      
}
