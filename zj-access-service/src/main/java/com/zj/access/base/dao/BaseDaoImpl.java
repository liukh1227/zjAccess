package com.zj.access.base.dao;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.zj.access.base.common.PageHelper;
import com.zj.access.base.mapper.BaseMapper;
import com.zj.access.common.FrameworkUtils;
import com.zj.base.common.ItemPage;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.base.entity.base.form.PageQueryForm;


/**
 * @desc baseDao实现类
 * @author liukh
 * @date 2016-11-30 上午11:47:25
 */
@Repository(value = "baseDao")
@Scope("prototype")
public class BaseDaoImpl implements IBaseDao {
	private static final Logger log = Logger.getLogger(BaseDaoImpl.class);

	@Resource
	private SqlSessionTemplate sessionTemplate;
	
	/**
	 * @desc 删除
	 * @author liukh
	 * @date 2016-11-30 下午12:37:37
	 * @param clz
	 * @param id
	 * @return
	 */
	public BaseDto deleteByPrimaryKey(Class<?> mapperClz, String id) {
		BaseDto dto = new BaseDto();
		int result = 0;
		try {
			if(StringUtils.isNotBlank(id)) {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				result = mapper.deleteByPrimaryKey(StringUtils.trim(id));
				if(result > 0) {
					FrameworkUtils.setSuccess(dto);
					log.info("deleteByPrimaryKey success");
				} else {
					log.error("deleteByPrimaryKey error");
				}
			} else {
				log.error("deleteByPrimaryKey error, id is null");
			}
		} catch (Exception e) {
			log.error("deleteByPrimaryKey exception");
			e.printStackTrace();
			throw new RuntimeException("deleteByPrimaryKey exception");
		}
		return dto;
	}

	/**
	 * @desc 新增
	 * @author liukh
	 * @date 2016-11-30 下午12:37:47
	 * @param clz
	 * @param record
	 * @return
	 */
	public <T> BaseDto insert(Class<?> mapperClz, T record) {
		BaseDto dto = new BaseDto();
		int result = 0;
		try {
			if(record != null) {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				result = mapper.insert(record);
				if(result > 0) {
					FrameworkUtils.setSuccess(dto);
					log.info("insert success");
				} else {
					log.error("insert error");
				}
			} else {
				log.error("insert error, record is null");
			}
		} catch (Exception e) {
			log.error("insert exception");
			e.printStackTrace();
			throw new RuntimeException("insert exception");
		}
		return dto;
	}
	/**
	 * @desc 新增有值的字段
	 * @author liukh
	 * @date 2016-11-30 下午12:38:05
	 * @param clz
	 * @param record
	 * @return
	 */
	public <T> BaseDto insertSelective(Class<?> mapperClz, T record) {
		BaseDto dto = new BaseDto();
		int result = 0;
		try {
			if(record != null) {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				result = mapper.insertSelective(record);
				if(result > 0) {
					FrameworkUtils.setSuccess(dto);
					log.info("insertSelective success");
				} else {
					log.error("insertSelective error");
				}
			} else {
				log.error("insertSelective error, record is null");
			}
		} catch (Exception e) {
			log.error("insertSelective exception");
			e.printStackTrace();
			throw new RuntimeException("insertSelective exception");
		}
		return dto;
	}

	/**
	 * @desc 根据id查询对象
	 * @author liukh
	 * @date 2016-11-30 下午12:40:46
	 * @param id
	 * @return
	 */
	@SuppressWarnings(value = {"unchecked"})
	public <T> BaseObjDto<T> selectByPrimaryKey(Class<?> mapperClz, String id) {
		BaseObjDto<T> dto = new BaseObjDto<T>();
		T t = null;
		try {
			if(StringUtils.isNotBlank(id)) {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				t = (T) mapper.selectByPrimaryKey(StringUtils.trim(id));
				if(t != null) {
					dto.setData(t);
					FrameworkUtils.setSuccess(dto);
					log.info("selectByPrimaryKey success");
				} else {
					log.error("selectByPrimaryKey error");
				}
			} else {
				log.error("selectByPrimaryKey error, id is null");
			}
		} catch (Exception e) {
			log.error("selectByPrimaryKey exception");
			e.printStackTrace();
			throw new RuntimeException("selectByPrimaryKey exception");
		}
		return dto;
	}

	/**
	 * 更新有值的字段
	 * @desc 
	 * @author liukh
	 * @date 2016-11-30 下午12:26:41
	 * @param record
	 * @return
	 */
	public <T> BaseDto updateByPrimaryKeySelective(Class<?> mapperClz, T record) {
		BaseDto dto = new BaseDto();
		int result = 0;
		try {
			if(record != null) {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				result = mapper.updateByPrimaryKeySelective(record);
				if(result > 0) {
					FrameworkUtils.setSuccess(dto);
					log.info("updateByPrimaryKeySelective success");
				} else {
					log.error("updateByPrimaryKeySelective error");
				}
			} else {
				log.error("updateByPrimaryKeySelective error, record is null");
			}
		} catch(Exception e) {
			log.error("updateByPrimaryKeySelective exception");
			e.printStackTrace();
			throw new RuntimeException("updateByPrimaryKeySelective exception");
		}
		return dto;
	}

	/**
	 * 更新所有的字段
	 * @desc 
	 * @author liukh
	 * @date 2016-11-30 下午12:06:44
	 * @param record
	 * @return
	 */
	public <T> BaseDto updateByPrimaryKey(Class<?> mapperClz, T record) {
		BaseDto dto = new BaseDto();
		int result = 0;
		try {
			if(record != null) {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				result = mapper.updateByPrimaryKey(record);
				if(result > 0) {
					FrameworkUtils.setSuccess(dto);
					log.info("updateByPrimaryKey success");
				} else {
					log.error("updateByPrimaryKey error");
				}
			} else {
				log.error("updateByPrimaryKey error, record is null");
			}
		} catch (Exception e) {
			log.error("updateByPrimaryKey exception");
			e.printStackTrace();
			throw new RuntimeException("updateByPrimaryKey exception");
		}
		return dto;
	}

	public <T> BaseObjDto<ItemPage<T>> getPageList(
			Class<?> mapperClz, Class<T> returnClz, PageQueryForm form, String listMethod) {
		BaseObjDto<ItemPage<T>> dto = new BaseObjDto<ItemPage<T>>();
		try {
			if(form != null) {
				int pageIndex = form.getPageIndex();
				int pageSize = form.getPageSize();
				int startIndex = (pageIndex - 1) * pageSize;
				form.setStartIndex(startIndex);
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				Method listMet = mapperClz.getMethod(listMethod, form.getClass());
				listMet.invoke(mapper, form);
				ItemPage<T> pages = PageHelper.getPages();
				if(pages != null && pages.getItems() != null && pages.getItems().size() > 0) {
					dto.setData(pages);
					FrameworkUtils.setSuccess(dto);
					log.info("getPageList success");
				} else {
					log.error("getPageList error");
				}
			} else {
				log.error("getPageList error, form is null");
			}
		} catch (Exception e) {
			log.error("getPageList exception");
			e.printStackTrace();
			throw new RuntimeException("getPageList exception");
		}
		return dto;
	}

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
	@SuppressWarnings(value = {"unchecked"})
	public <T> BaseObjDto<List<T>> getList(Class<?> mapperClz, Class<T> returnClz, String listMethod, PageQueryForm form) {
		BaseObjDto<List<T>> dto = new BaseObjDto<List<T>>();
		try {
			if(form != null) {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				Method listMet = mapperClz.getMethod(listMethod, form.getClass());
				List<T> list = (List<T>) listMet.invoke(mapper, form);
				if(list != null && list.size() > 0) {
					dto.setData(list);
					FrameworkUtils.setSuccess(dto);
					log.info("getList success");
				} else {
					log.error("getList error");
				}
			} else {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				Method listMet = mapperClz.getMethod(listMethod);
				List<T> list = (List<T>) listMet.invoke(mapper);
				if(list != null && list.size() > 0) {
					dto.setData(list);
					FrameworkUtils.setSuccess(dto);
					log.info("getList success");
				} else {
					log.error("getList error");
				}
			}
		} catch (Exception e) {
			log.error("getList exception");
			e.printStackTrace();
			throw new RuntimeException("getList exception");
		}
		return dto;
	}

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
	public <T> BaseObjDto<List<T>> getList(Class<?> mapperClz, Class<T> returnClz, Map<String, Object> params, String listMethod) {
		BaseObjDto<List<T>> dto = new BaseObjDto<List<T>>();
		try {
			if(params != null) {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				Method listMet = mapperClz.getMethod(listMethod, Map.class);
				List<T> list = (List<T>) listMet.invoke(mapper, params);
				if(list != null && list.size() > 0) {
					dto.setData(list);
					FrameworkUtils.setSuccess(dto);
					log.info("getList success");
				} else {
					log.error("getList error");
				}
			} else {
				BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
				Method listMet = mapperClz.getMethod(listMethod);
				List<T> list = (List<T>) listMet.invoke(mapper);
				if(list != null && list.size() > 0) {
					dto.setData(list);
					FrameworkUtils.setSuccess(dto);
					log.info("getList success");
				} else {
					log.error("getList error");
				}
			}
		} catch (Exception e) {
			log.error("getList exception");
			e.printStackTrace();
			throw new RuntimeException("getList exception");
		}
		return dto;
	}

	/**
	 * @desc 获取个数
	 * @author liukh
	 * @date 2016-12-2 上午10:33:51
	 * @param mapperClz
	 * @param form
	 * @param method
	 * @return
	 */
	public long getCount(Class<?> mapperClz, String method, Object param) {
		long count = 0L;
		try {
			BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
			Method met = null;
			if(param != null) {
				met = mapperClz.getMethod(method, param.getClass());
			} else {
				met = mapperClz.getMethod(method);
			}
			count = (Long) met.invoke(mapper, param);
		} catch (Exception e) {
			log.error("getCount exception");
			e.printStackTrace();
			throw new RuntimeException("getCount exception");
		}
		return count;
	}

	/**
	 * @desc 获取个数
	 * @author liukh
	 * @date 2016-12-2 上午11:16:05
	 * @param mapperClz
	 * @param method
	 * @param params
	 * @return
	 */
	public long getCount(Class<?> mapperClz, String method, Map<String, Object> params) {
		long count = 0L;
		try {
			BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
			Method met = null;
			if(params != null) {
				if(params.size() > 0) {
					met = mapperClz.getMethod(method, Map.class);
				} else {
					log.error("getCount error , params size is zero");
				}
			} else {
				log.error("getCount error , params is null");
			}
			count = (Long) met.invoke(mapper, params);
		} catch (Exception e) {
			log.error("getCount exception");
			e.printStackTrace();
			throw new RuntimeException("getCount exception");
		}
		return count;
	}

	/**
	 * @desc 根据参数获取对象
	 * @author liukh
	 * @date 2016-12-2 下午3:30:12
	 * @param mapperClz
	 * @param method
	 * @param params
	 * @return
	 */
	@SuppressWarnings(value = {"unchecked"})
	public <T> BaseObjDto<T> getObjProperty(Class<?> mapperClz, String method, Map<String, Object> params) {
		BaseObjDto<T> dto = new BaseObjDto<T>();
		try {
			T t = null;
			BaseMapper mapper = (BaseMapper) sessionTemplate.getMapper(mapperClz);
			Method met = null;
			if(params != null) {
				if(params.size() > 0) {
					met = mapperClz.getMethod(method, Map.class);
				} else {
					log.error("getObjProperty error , params size is zero");
				}
			} else {
				log.error("getObjProperty error , params is null");
			}
			t = (T) met.invoke(mapper, params);
			if(t != null) {
				dto.setData(t);
				FrameworkUtils.setSuccess(dto);
			}
		} catch (Exception e) {
			log.error("getObjProperty exception");
			e.printStackTrace();
			throw new RuntimeException("getObjProperty exception");
		}
		return dto;
	}
}