package com.zj.base.entity.base.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * @author:tjhua
 * @date:2015-8-21 下午4:41:01
 * <p>description:</p>
 */
public class Pages<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 第几页
	 */
	private Integer page = 1;
	/**
	 * 总共多少页
	 */
	private Integer pagecount = 0;
	/**
	 * 一页多少条
	 */
	private Integer pagerowcount = 10;
	
	/**
	 * 总共多少条
	 */
	private Integer count = 0;
	/**
	 * 数据列表
	 */
	private List<T> data;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPagecount() {
		return pagecount;
	}
	public void setPagecount(Integer pagecount) {
		this.pagecount = pagecount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public Integer getPagerowcount() {
		return pagerowcount;
	}
	public void setPagerowcount(Integer pagerowcount) {
		this.pagerowcount = pagerowcount;
	}
}
