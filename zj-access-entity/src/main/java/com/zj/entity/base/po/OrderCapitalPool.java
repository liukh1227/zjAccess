package com.zj.entity.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zj.common.Constant;

public class OrderCapitalPool implements Serializable {
    private String id;

    private String rentOrderId;

    private Integer type;

    private String capitalSideId;

    private BigDecimal amount;

    private Integer status;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCapitalSideId() {
        return capitalSideId;
    }

    public void setCapitalSideId(String capitalSideId) {
        this.capitalSideId = capitalSideId == null ? null : capitalSideId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        sb.append(", type=").append(type);
        sb.append(", capitalSideId=").append(capitalSideId);
        sb.append(", amount=").append(amount);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    @JSONField(serialize=false) 
    public boolean isLocked(){
    	return getType() ==  Constant.ORDERCAPITALPOOLPOJO_LOCKED;
    }
    @JSONField(serialize=false) 
	public void copyFromRentOrder(RentOrder other,String capitalSideId,Integer isLocked) {
		setRentOrderId(other.getId());
		setCapitalSideId(capitalSideId);
		setType(Constant.ORDERCAPITALPOOLPOJO_PREVENTION);	
		setStatus(isLocked);
		//setAmount(other.getPrice());
		
	}
}