package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.SmsDistributionLog;
import com.zj.entity.sm.form.SmsDistributionLogQueryForm;

public interface SmsDistributionLogMapper extends BaseMapper {
	 public  List<SmsDistributionLog> getSmsDistributionLogPageList(SmsDistributionLogQueryForm form);
}