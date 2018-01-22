package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.Feedback;
import com.zj.entity.sm.form.FeedbackQueryForm;

public interface FeedbackMapper extends BaseMapper{
	public  List<Feedback> getFeedbackPageList(FeedbackQueryForm form);
}