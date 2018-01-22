package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;

import com.zj.entity.system.DataEntry;

public interface DataMappingMapper extends BaseMapper {
	public  List<DataEntry> getAllDataMappingList();
	 public List<DataEntry> getDataMappingByCatalogKey(Map<String,Object> params);
}