package com.zj.access.base.mapper;

/**
 * @desc 基本wapper
 * @author tanjianhua
 * @date 2016-11-30 上午11:38:02
 */
public interface BaseMapper {

	public int deleteByPrimaryKey(String id);

	public int insert(Object record);

	public int insertSelective(Object record);

	public Object selectByPrimaryKey(String id);

	public int updateByPrimaryKeySelective(Object record);

	public int updateByPrimaryKey(Object record);
}
