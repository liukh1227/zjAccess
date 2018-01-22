package com.zj.base.entity.base.form;
/**
 * 分页form接口
 * @author liukh
 * @date 2017-2-7 下午3:43:09
 */
public interface IPageQueryForm {
	/**
	 * 
	 * @return 当前页，从1开始
	 */
	public int getPageIndex();

	/**
	 * 
	 * @return 分页大小,缺省为10条每页
	 */
	public int getPageSize();

	public void setPageIndex(int pageIndex);

	public void setPageSize(int pageSize);
}
