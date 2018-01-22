package com.zj.base.entity.base.form;

import java.io.Serializable;

public class PageQueryForm implements IPageQueryForm, Serializable {
	
	protected int page;
	protected int numberOfItem;
	
	protected int pageIndex;
	protected int pageSize = 10;
	protected int startIndex = 1;

	protected int secondPageIndex;
	protected int secondPageSize = 10;

	private String sidx; // 排序字段名
	private String sord; // 排序 asc desc
	private static final long serialVersionUID = 1L;

	public int getPageIndex() {
		return pageIndex < 1 ? 1 : pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageSize1() {
		return pageSize;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getSecondPageIndex() {
		return secondPageIndex < 1 ? 1 : secondPageIndex;
	}

	public void setSecondPageIndex(int secondPageIndex) {
		this.secondPageIndex = secondPageIndex;
	}

	public int getSecondPageSize() {
		return secondPageSize < 1 ? 10 : secondPageSize;
	}

	public void setSecondPageSize(int secondPageSize) {
		this.secondPageSize = secondPageSize;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		setPageIndex(page);
	}

	public int getNumberOfItem() {
		return numberOfItem;
	}

	public void setNumberOfItem(int numberOfItem) {
		if(numberOfItem == 0){
			numberOfItem = 100000;
		}
		this.numberOfItem = numberOfItem;
		setPageSize(numberOfItem);
	}
	
}
