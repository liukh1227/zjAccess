package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Advertisement implements Serializable {
	    private String id;

	    private String title;

	    private String pictureURL;

	    private String pictureContent;

	    private Integer orderNum;

	    private Integer status;

	    private String adaptBSideType;

	    private String adaptTerminal;
	    
	    private Date createTime;
	    
	    private String companyId;

	    private static final long serialVersionUID = 1L;

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id == null ? null : id.trim();
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title == null ? null : title.trim();
	    }
	    @JSONField(name="pictureUrl")
	    public String getPictureURL() {
	        return pictureURL;
	    }

	    public void setPictureURL(String pictureURL) {
	        this.pictureURL = pictureURL == null ? null : pictureURL.trim();
	    }

	    public String getPictureContent() {
	        return pictureContent;
	    }

	    public void setPictureContent(String pictureContent) {
	        this.pictureContent = pictureContent == null ? null : pictureContent.trim();
	    }

	 
	    public Integer getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(Integer orderNum) {
			this.orderNum = orderNum;
		}

		public Integer getStatus() {
	        return status;
	    }

	    public void setStatus(Integer status) {
	        this.status = status;
	    }

	    public String getAdaptBSideType() {
	        return adaptBSideType;
	    }

	    public void setAdaptBSideType(String adaptBSideType) {
	        this.adaptBSideType = adaptBSideType == null ? null : adaptBSideType.trim();
	    }

	    public String getAdaptTerminal() {
	        return adaptTerminal;
	    }

	    public void setAdaptTerminal(String adaptTerminal) {
	        this.adaptTerminal = adaptTerminal == null ? null : adaptTerminal.trim();
	    }
	    @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		
		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		@Override
		public String toString() {
			return "Advertisement [id=" + id + ", title=" + title
					+ ", pictureURL=" + pictureURL + ", pictureContent="
					+ pictureContent + ", orderNum=" + orderNum + ", status="
					+ status + ", adaptBSideType=" + adaptBSideType
					+ ", adaptTerminal=" + adaptTerminal + ", createTime="
					+ createTime + ", companyId=" + companyId + "]";
		}

		

	  
}