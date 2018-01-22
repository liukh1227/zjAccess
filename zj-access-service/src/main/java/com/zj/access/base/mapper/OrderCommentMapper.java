package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.OrderComment;
import com.zj.entity.tm.form.OrderCommentQueryForm;

public interface OrderCommentMapper  extends BaseMapper{
	
	public  List<OrderComment> getOrderCommentPageList(OrderCommentQueryForm form);
	
	public Long getOrderCommentCount(OrderCommentQueryForm form);
	
	public Long getComprehensiveOrderComment(OrderCommentQueryForm form);
	
}