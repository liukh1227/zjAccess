package com.zj.access.base.dao;

import java.util.List;
import java.util.Map;

import com.zj.base.common.ItemPage;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.base.entity.base.form.PageQueryForm;



/**
 * @desc baseDao
 * @author liukh
 * @date 2016-11-30 上午11:47:12
 */
public interface IBaseDao {

	/**
	 * @desc 删除
	 * @author liukh
	 * @date 2016-11-30 下午12:37:37
	 * @param clz
	 * @param id
	 * @return
	 */
	public BaseDto deleteByPrimaryKey(Class<?> mapperClz, String id);
	/**
	 * @desc 新增
	 * @author liukh
	 * @date 2016-11-30 下午12:37:47
	 * @param clz
	 * @param record
	 * @return
	 */
	public <T> BaseDto insert(Class<?> mapperClz, T record);
	/**
	 * @desc 新增有值的字段
	 * @author liukh
	 * @date 2016-11-30 下午12:38:05
	 * @param clz
	 * @param record
	 * @return
	 */
	public <T> BaseDto insertSelective(Class<?> mapperClz, T record);

	/**
	 * @desc 根据id查询对象
	 * @author liukh
	 * @date 2016-11-30 下午12:40:46
	 * @param id
	 * @return
	 */
	public <T> BaseObjDto<T> selectByPrimaryKey(Class<?> mapperClz, String id);

	/**
	 * 更新有值的字段
	 * @desc 
	 * @author liukh
	 * @date 2016-11-30 下午12:26:41
	 * @param record
	 * @return
	 */
	public <T> BaseDto updateByPrimaryKeySelective(Class<?> mapperClz, T record);

	/**
	 * 更新所有的字段
	 * @desc 
	 * @author liukh
	 * @date 2016-11-30 下午12:06:44
	 * @param record
	 * @return
	 */
	public <T> BaseDto updateByPrimaryKey(Class<?> mapperClz, T record);

	/**
	 * @desc 获取分页列表
	 * @author liukh
	 * @date 2016-12-1 上午9:56:14
	 * @param mapperClz		mapper
	 * @param returnClz		泛型返回的实体对象
	 * @param form			form
	 * @param listMethod	查询列表的方法字符串
	 * @return
	 */
	public <T> BaseObjDto<ItemPage<T>> getPageList(Class<?> mapperClz, Class<T> returnClz, PageQueryForm form, String listMethod);

	/**
	 * @desc 获取列表
	 * @author liukh
	 * @date 2016-12-1 上午10:17:19
	 * @param mapperClz
	 * @param returnClz
	 * @param form
	 * @param listMethod
	 * @return
	 */
	public <T> BaseObjDto<List<T>> getList(Class<?> mapperClz, Class<T> returnClz, String listMethod, PageQueryForm form);

	/**
	 * @desc 获取列表
	 * @author liukh
	 * @date 2016-12-8 下午2:07:47
	 * @param mapperClz
	 * @param returnClz
	 * @param params
	 * @param listMethod
	 * @return
	 */
	public <T> BaseObjDto<List<T>> getList(Class<?> mapperClz, Class<T> returnClz, Map<String, Object> params, String listMethod);

	/**
	 * @desc 获取个数
	 * @author liukh
	 * @date 2016-12-2 上午10:38:00
	 * @param mapperClz
	 * @param method
	 * @param params
	 * @return
	 */
	public long getCount(Class<?> mapperClz, String method, Object params);

	/**
	 * @desc 获取个数
	 * @author liukh
	 * @date 2016-12-2 上午11:16:05
	 * @param mapperClz
	 * @param method
	 * @param params
	 * @return
	 */
	public long getCount(Class<?> mapperClz, String method, Map<String, Object> params);

	/**
	 * @desc 根据参数获取对象
	 * @author liukh
	 * @date 2016-12-2 下午3:30:12
	 * @param mapperClz
	 * @param method
	 * @param params
	 * @return
	 */
	public <T> BaseObjDto<T> getObjProperty(Class<?> mapperClz, String method, Map<String, Object> params);

}
