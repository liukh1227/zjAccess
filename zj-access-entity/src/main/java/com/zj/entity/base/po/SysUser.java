package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {
    private String id;

    private String account;

    private String password;

    private String password_salt;

    private String user_name;

    private String mobile;

    private String email;

    private String status;

    private String company_name;

    private String creater_id;

    private String creater_name;

    private Date createTime;

    private Integer orderNum;

    private Date last_long_time;

    private static final long serialVersionUID = 1L;



    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPassword_salt() {
        return password_salt;
    }

    public void setPassword_salt(String password_salt) {
        this.password_salt = password_salt == null ? null : password_salt.trim();
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name == null ? null : user_name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name == null ? null : company_name.trim();
    }

    public String getCreater_id() {
        return creater_id;
    }

    public void setCreater_id(String creater_id) {
        this.creater_id = creater_id == null ? null : creater_id.trim();
    }

    public String getCreater_name() {
        return creater_name;
    }

    public void setCreater_name(String creater_name) {
        this.creater_name = creater_name == null ? null : creater_name.trim();
    }

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getLast_long_time() {
        return last_long_time;
    }

    public void setLast_long_time(Date last_long_time) {
        this.last_long_time = last_long_time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", password=").append(password);
        sb.append(", password_salt=").append(password_salt);
        sb.append(", user_name=").append(user_name);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", status=").append(status);
        sb.append(", company_name=").append(company_name);
        sb.append(", creater_id=").append(creater_id);
        sb.append(", creater_name=").append(creater_name);
        sb.append(", createTime=").append(createTime);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", last_long_time=").append(last_long_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}