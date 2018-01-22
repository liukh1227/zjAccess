package com.zj.base.common;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 分页对象
 * 
 * @author tanjh
 * @date 2016-8-18 下午2:57:44
 */
public class ItemPage<T> implements Serializable {

	private static final long serialVersionUID = -5884976706259160221L;
	/**
	 * 上一页
	 */
	private long preIndex;
	/**
	 * 当前页
	 */
	 @JSONField(name="page")  
	private long pageIndex;
	/**
	 * 下一页
	 */
	private long nextIndex;
	/**
	 * 每页条数
	 */
	 @JSONField(name="pagerowcount")  
	private long pageSize;
	/**
	 * 总条数
	 */
	 @JSONField(name="count")  
	private long rowsCount;

	/**
	 * 总页数
	 */
	 @JSONField(name="pagecount")  
	private long pagesCount;
	/**
	 * 对象列表
	 */
    @JSONField(name="data")  
	private List<T> items;

	public void setPreIndex(long preIndex) {
		this.preIndex = preIndex;
	}

	public void setNextIndex(long nextIndex) {
		this.nextIndex = nextIndex;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 
	 * 分页类构建函数
	 * 
	 */
	public ItemPage() {
		updateInfo(0, 0, 0);
	}

	/**
	 * 
	 * 分页类构建函数
	 * 
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页记录数
	 */
	public ItemPage(long pageIndex, long pageSize) {
		updateInfo(pageIndex, pageSize, 0);
	}

	/**
	 * 分页类构建函数
	 * 
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页记录数
	 * @param rowsCount
	 *            记录总数
	 */
	public ItemPage(long pageIndex, long pageSize, long rowsCount) {
		updateInfo(pageIndex, pageSize, rowsCount);
	}

	/**
	 * 获取当前面记录
	 * 
	 * @return
	 */
	public List<T> getItems() {
		return items;
	}

	/**
	 * 设置当前页记录
	 * 
	 * @param items
	 */
	public void setItems(List<T> items) {
		this.items = items;
	}

	/**
	 * 获取当前页码
	 * @author tanjh
	 * @date 2016-8-18 下午3:19:00
	 * @return
	 */
	public long getPageIndex() {
		return pageIndex;
	}

	/**
	 * 获取当前页码
	 * @author tanjh
	 * @date 2016-8-18 下午3:19:12
	 * @param pageIndex
	 */
	public void setPageIndex(long pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 获取下一页码
	 * 
	 * @return
	 */
	public long getNextIndex() {
		return nextIndex;
	}

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public long getPagesCount() {
		return pagesCount;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return
	 */
	public long getPageSize() {
		return pageSize;
	}

	/**
	 * 获取上一页码
	 * 
	 * @return
	 */
	public long getPreIndex() {
		return preIndex;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public long getRowsCount() {
		return rowsCount;
	}

	/**
	 * 获取首页码
	 * 
	 * @return
	 */
	public long getFirstIndex() {
		return 1;
	}

	/**
	 * 获取末页码
	 * 
	 * @return
	 */
	public long getLastIndex() {
		return pagesCount;
	}

	public void updateInfo(long pageIndex, long pageSize, long rowsCount) {

		if (pageSize > 0) {

			this.pageIndex = pageIndex;
			this.rowsCount = rowsCount;
			this.pageSize = pageSize;

			// 确定页数
			pagesCount = (rowsCount + pageSize - 1) / pageSize;
			// 确定当前页码
			if (pageIndex <= 0)
				pageIndex = 1;
			if (pageIndex > pagesCount)
				pageIndex = pagesCount;
			// 确定下一页码
			nextIndex = pageIndex + 1;
			if (nextIndex > pagesCount)
				nextIndex = pagesCount;
			// 确定上一页码
			preIndex = pageIndex - 1;
			if (preIndex <= 0)
				preIndex = 1;
		} else {
			this.preIndex = 1;
			this.pageIndex = 1;
			this.nextIndex = 1;
			this.pageSize = 0;
			this.pagesCount = 1;
		}
	}

	/**
	 * 设置总记录数
	 * 
	 * @param rowsCount
	 */
	public void setRowsCount(long rowsCount) {
		this.rowsCount = rowsCount;
	}

	/**
	 * 设置总页数
	 * 
	 * @param pagesCount
	 */
	public void setPagesCount(long pagesCount) {
		this.pagesCount = pagesCount;
	}
	
	public void copyForm(ItemPage other){
		setNextIndex(other.getNextIndex());
		setPageIndex(other.getPageIndex());
		setPagesCount(other.getPagesCount());
		setPageSize(other.getPageSize());
		setPreIndex(other.getPreIndex());
		setRowsCount(other.getRowsCount());
	}
}
