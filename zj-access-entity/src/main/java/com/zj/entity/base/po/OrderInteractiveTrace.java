package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zj.common.Constant;

public class OrderInteractiveTrace implements Serializable {
    private String id;

    private String relatedStatementId;

    private String relatedStatementType;

    private String publisherId;

    private String publisherCompanyName;

    private String publishContent;

    private String posterIcon;

    private String posterCompanyId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRelatedStatementId() {
        return relatedStatementId;
    }

    public void setRelatedStatementId(String relatedStatementId) {
        this.relatedStatementId = relatedStatementId == null ? null : relatedStatementId.trim();
    }

    public String getRelatedStatementType() {
        return relatedStatementType;
    }

    public void setRelatedStatementType(String relatedStatementType) {
        this.relatedStatementType = relatedStatementType == null ? null : relatedStatementType.trim();
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId == null ? null : publisherId.trim();
    }

    public String getPublisherCompanyName() {
        return publisherCompanyName;
    }

    public void setPublisherCompanyName(String publisherCompanyName) {
        this.publisherCompanyName = publisherCompanyName == null ? null : publisherCompanyName.trim();
    }

    public String getPublishContent() {
        return publishContent;
    }

    public void setPublishContent(String publishContent) {
        this.publishContent = publishContent == null ? null : publishContent.trim();
    }

    public String getPosterIcon() {
        return posterIcon;
    }

    public void setPosterIcon(String posterIcon) {
        this.posterIcon = posterIcon == null ? null : posterIcon.trim();
    }

    public String getPosterCompanyId() {
        return posterCompanyId;
    }

    public void setPosterCompanyId(String posterCompanyId) {
        this.posterCompanyId = posterCompanyId == null ? null : posterCompanyId.trim();
    }
    @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", relatedStatementId=").append(relatedStatementId);
        sb.append(", relatedStatementType=").append(relatedStatementType);
        sb.append(", publisherId=").append(publisherId);
        sb.append(", publisherCompanyName=").append(publisherCompanyName);
        sb.append(", publishContent=").append(publishContent);
        sb.append(", posterIcon=").append(posterIcon);
        sb.append(", posterCompanyId=").append(posterCompanyId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    @JSONField(serialize=false) 
    public static boolean isType4Inqueryorder(String type){
		return Constant.RELATEDSTATEMENTTYPE_INQUERYORDER.equals(type);
		
	}
    @JSONField(serialize=false) 
	public static boolean isType4Orderstatement(String type){
		return Constant.RELATEDSTATEMENTTYPE_ORDERSTATEMENT .equals(type);
		
	}
    @JSONField(serialize=false) 
	public  boolean isType4Inqueryorder(){
		return Constant.RELATEDSTATEMENTTYPE_INQUERYORDER.equals(getRelatedStatementType());
		
	}
    @JSONField(serialize=false) 
	public  boolean isType4Orderstatement(){
		return Constant.RELATEDSTATEMENTTYPE_ORDERSTATEMENT .equals(getRelatedStatementType());
		
	}
}