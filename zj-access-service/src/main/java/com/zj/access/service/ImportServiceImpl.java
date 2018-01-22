package com.zj.access.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zj.access.base.dao.IBaseDao;
import com.zj.access.base.mapper.CompanyMapper;
import com.zj.access.base.mapper.DataMappingMapper;
import com.zj.access.base.mapper.DeviceModelMapper;
import com.zj.access.common.FrameworkUtils;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.entity.base.po.Company;
import com.zj.entity.base.po.DeviceModel;
import com.zj.entity.system.DataEntry;

@Service("importService")
@Scope("prototype")
public class ImportServiceImpl implements ImportServie {
	@Autowired
	private IBaseDao baseDao;

	private static final Logger log = Logger.getLogger(ImportServiceImpl.class);

	public String getAllModelId() {
		String jsonStr = "";
		try {
			BaseObjDto<List<DeviceModel>> modelDto = baseDao.getList(
					DeviceModelMapper.class, DeviceModel.class,
					"getAllDeviceModeIdlList", null);

			if (FrameworkUtils.isSuccess(modelDto)) {
				List<DeviceModel> listModel = modelDto.getData();
				for (DeviceModel model : listModel) {
					System.out.println("deviceModel_id:" + model.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getDeviceList Exception!");
		}
		return jsonStr;
	}

	public List<DataEntry> getDataMappingByCatalog(Map<String, Object> params) {
		List<DataEntry> dataEntryList = null;
		try {
			BaseObjDto<List<DataEntry>> dataEntryDto = baseDao.getList(
					DataMappingMapper.class, DataEntry.class, params,
					"getDataMappingByCatalogKey");
			if (FrameworkUtils.isSuccess(dataEntryDto)) {
				dataEntryList = dataEntryDto.getData();
				for (DataEntry dataEntry : dataEntryList) {
					System.out.println("deviceModel_id:"
							+ dataEntry.getKeyString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getDeviceList Exception!");
		}
		return dataEntryList;
	}

	@Override
	public String addDevicebyExcelImport(InputStream in) {
		try {
			System.out.println(in.available());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getAllModelId();
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("catalogString", "DEVICESXLS");
		List<DataEntry> dataMappingdataList = getDataMappingByCatalog(params);
		String result = "";
		BaseObjDto<JSONObject>  dto = new BaseObjDto<JSONObject>();
		try {
			Workbook rwb = Workbook.getWorkbook(in); 
			
			String companyId = "";
			BaseObjDto<Company> companyDto = baseDao.selectByPrimaryKey(
					CompanyMapper.class, StringUtils.trim(companyId));
			if (!FrameworkUtils.isSuccess(companyDto)) {
				dto.setRcode(0);
				dto.setRinfo(companyId+ ":不存在,请检查传入的公司Id !");
				return JSON.toJSONString(dto);
			}
			
			
			
		} catch (Exception e) {
			result = "{\"rcode\" : 1}";
			log.error("---deviceImport ---- exception ====== ");
			e.printStackTrace();
		}
	return result;
	
	}
}
