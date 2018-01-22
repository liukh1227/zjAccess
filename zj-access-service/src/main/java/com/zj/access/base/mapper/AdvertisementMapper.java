package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.Advertisement;
import com.zj.entity.sm.form.AdvertisementQueryForm;

public interface AdvertisementMapper  extends BaseMapper{
	public  List<Advertisement> getAdvertisementPageList(AdvertisementQueryForm form);
}