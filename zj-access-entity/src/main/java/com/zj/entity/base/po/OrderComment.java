package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderComment implements Serializable {
    private String id;

    private String rentOrderId;

    private String commentSideId;

    private String commentSideCompanyName;

    private String content;

    private Integer star;

    private Integer status;

    private String beCommentedCompanyId;

    private String beCommentedCompanyName;

    private String commentator;

    private String commentatorIcon;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRentOrderId() {
        return rentOrderId;
    }

    public void setRentOrderId(String rentOrderId) {
        this.rentOrderId = rentOrderId == null ? null : rentOrderId.trim();
    }

    public String getCommentSideId() {
        return commentSideId;
    }

    public void setCommentSideId(String commentSideId) {
        this.commentSideId = commentSideId == null ? null : commentSideId.trim();
    }

    public String getCommentSideCompanyName() {
        return commentSideCompanyName;
    }

    public void setCommentSideCompanyName(String commentSideCompanyName) {
        this.commentSideCompanyName = commentSideCompanyName == null ? null : commentSideCompanyName.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBeCommentedCompanyId() {
        return beCommentedCompanyId;
    }

    public void setBeCommentedCompanyId(String beCommentedCompanyId) {
        this.beCommentedCompanyId = beCommentedCompanyId == null ? null : beCommentedCompanyId.trim();
    }

    public String getBeCommentedCompanyName() {
        return beCommentedCompanyName;
    }

    public void setBeCommentedCompanyName(String beCommentedCompanyName) {
        this.beCommentedCompanyName = beCommentedCompanyName == null ? null : beCommentedCompanyName.trim();
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator == null ? null : commentator.trim();
    }

    public String getCommentatorIcon() {
        return commentatorIcon;
    }

    public void setCommentatorIcon(String commentatorIcon) {
        this.commentatorIcon = commentatorIcon == null ? null : commentatorIcon.trim();
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
        sb.append(", rentOrderId=").append(rentOrderId);
        sb.append(", commentSideId=").append(commentSideId);
        sb.append(", commentSideCompanyName=").append(commentSideCompanyName);
        sb.append(", content=").append(content);
        sb.append(", star=").append(star);
        sb.append(", status=").append(status);
        sb.append(", beCommentedCompanyId=").append(beCommentedCompanyId);
        sb.append(", beCommentedCompanyName=").append(beCommentedCompanyName);
        sb.append(", commentator=").append(commentator);
        sb.append(", commentatorIcon=").append(commentatorIcon);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}