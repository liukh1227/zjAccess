package com.zj.access.system;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.zj.access.base.dao.IBaseDao;
import com.zj.access.base.mapper.DataMappingMapper;
import com.zj.base.common.Constant;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.entity.system.DataEntry;
// 未实现，暂时放弃
//@Service("dataMapping")
//@Scope("globalSession")
public class DataMapping implements Serializable {
	
	private static final long serialVersionUID = -7268432062312194349L;
	private static final Logger log = Logger.getLogger(DataMapping.class);
    private Hashtable<String, List<DataEntry>> dataListTable;
    private Hashtable<String, String> dataMappingTable;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat timeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
    
    @Autowired
	private IBaseDao baseDao;

    @PostConstruct
    public void loadDataMapping() {
	log.info("Start load data entry");
	this.dataListTable = new Hashtable<String, List<DataEntry>>();
	this.dataMappingTable = new Hashtable<String, String>();
	
	Map<String, Object> params = new HashMap<String,Object>();
	params.put("catalogString",Constant.ROOT_OF_DATAMAPPING);
	BaseObjDto<List<DataEntry>> dataEntryDto = baseDao.getList(DataMappingMapper.class, DataEntry.class, params,"getDataMappingByCatalogKey");

	List<DataEntry> catalogList =null;
	 catalogList = dataEntryDto.getData();
	List<DataEntry> entryList = null;
	List<DataEntry> enableEntryList = null;

	for (DataEntry catalog : catalogList) {
		params.put("catalogString",Constant.ROOT_OF_DATAMAPPING);
		BaseObjDto<List<DataEntry>> entryDto = baseDao.getList(DataMappingMapper.class, DataEntry.class, params,"getDataMappingByCatalogKey");
	   entryList = entryDto.getData();
	    enableEntryList = new ArrayList<DataEntry>();
	    if ((entryList != null) && (entryList.size() > 0)) {
		for (DataEntry dataEntry : entryList) {
		    if (dataEntry.isEnabled()) {
			enableEntryList.add(dataEntry);
			this.dataMappingTable.put(dataEntry.getCatalogString() + "_"
				+ dataEntry.getKeyString(), dataEntry.getValueString());
		    }
		}
	    }

	    this.dataListTable.put(catalog.getKeyString(), enableEntryList);
	}
	log.info("loaded #0 entry list"+Integer.valueOf(this.dataListTable.size()));
	log.info("loaded #0 entry value"+ Integer.valueOf(this.dataMappingTable.size()));
    }

    public List<DataEntry> getEntryListByCatalog(String catalog) {
	return this.dataListTable.containsKey(catalog) ? this.dataListTable
		.get(catalog) : new ArrayList<DataEntry>();
    }

    public List<DataEntry> getEnabledEntryList() {
	return getEntryListByCatalog(Constant.DATAMAPPING_ROOT_ENABLED);
    }

    public String getValueByCatalogAndKey(String catalog, String key) {
	return key != null && !key.isEmpty()
		&& this.dataMappingTable.containsKey(catalog + "_" + key) ? (String) this.dataMappingTable
		.get(catalog + "_" + key) : "";
    }

    public DataEntry getEntryByCatalogAndKey(String catalog, String key) {
	if (key != null && !key.isEmpty()) {
	    List<DataEntry> entiesInCatalog = getEntryListByCatalog(catalog);
	    for (DataEntry dataEntry : entiesInCatalog) {
		if (dataEntry.getKeyString().equals(key)) {
		    return dataEntry;
		}
	    }
	}
	return null;
    }

    public String getLevelValueByKey(String key) {
	return getValueByCatalogAndKey(Constant.DATAMAPPING_ROOT_SYS_LEVEL, key);
    }

    public String getTorFValueByKey(boolean torf) {
	return getValueByCatalogAndKey(Constant.DATAMAPPING_ROOT_TORF, torf ? "T" : "F");
    }

    public List<DataEntry> getTorFList() {
	return getEntryListByCatalog(Constant.DATAMAPPING_ROOT_TORF);
    }

    public String getEnableValueByKey(boolean enable) {
	return getValueByCatalogAndKey(Constant.DATAMAPPING_ROOT_ENABLED, enable ? "1": "0");
    }

    public String getValuesByCatalogAndKeys(String catalog, List<String> keys) {
	StringBuffer valueBuffer = new StringBuffer();
	for (String key : keys) {
	    if (key == null) {
		continue;
	    }
	    if (valueBuffer.length() > 0) {
		valueBuffer.append(",");
	    }
	    valueBuffer.append(getValueByCatalogAndKey(catalog, key));
	}
	return valueBuffer.toString();
    }

    public String getKeysByCatalog(String catalog) {
	StringBuffer valueBuffer = new StringBuffer();
	for (DataEntry dataEntry : getEntryListByCatalog(catalog)) {

	    if (valueBuffer.length() > 0) {
		valueBuffer.append(",");
	    }
	    valueBuffer.append(dataEntry.getKeyString());
	}
	return valueBuffer.toString();
    }

    public String getValuesByCatalog(String catalog) {
	StringBuffer valueBuffer = new StringBuffer();
	for (DataEntry dataEntry : getEntryListByCatalog(catalog)) {

	    if (valueBuffer.length() > 0) {
		valueBuffer.append(",");
	    }
	    valueBuffer.append(dataEntry.getValueString());
	}
	return valueBuffer.toString();
    }

    public String getValuesByCatalogAndKeys(String catalog, String[] keys) {
	StringBuffer valueBuffer = new StringBuffer();
	for (String key : keys) {
	    if (key == null) {
		continue;
	    }
	    if (valueBuffer.length() > 0) {
		valueBuffer.append(",");
	    }
	    valueBuffer.append(getValueByCatalogAndKey(catalog, key));
	}
	return valueBuffer.toString();
    }

    public String getValuesByCatalogAndKeyCharacters(String catalog,
	    List<Character> keys) {
	StringBuffer valueBuffer = new StringBuffer();
	for (Character key : keys) {
	    if (key == null) {
		continue;
	    }
	    if (valueBuffer.length() > 0) {
		valueBuffer.append(",");
	    }
	    valueBuffer.append(getValueByCatalogAndKey(catalog,
		    String.valueOf(key)));
	}
	return valueBuffer.toString();
    }



    public String getKeyByCatalogAndValue(String catalog, String value) {
	if (value != null) {
	    List<DataEntry> dataEntries = getEntryListByCatalog(catalog);
	    for (DataEntry dataEntry : dataEntries) {
		if (value.equals(dataEntry.getValueString())) {
		    return dataEntry.getKeyString();
		}
	    }
	}
	return "";
    }

    public String getValuesByCatalogAndKeyArray(String catalog, String keys) {
	StringBuffer valueBuffer = new StringBuffer();
	for (int index = 0; index < keys.length(); index++) {
	    if (index != 0) {
		valueBuffer.append(" ");
	    }
	    valueBuffer.append(getValueByCatalogAndKey(catalog,
		    String.valueOf(keys.charAt(index))));
	}
	return valueBuffer.toString();
    }
    
    public Date getSystemParameter_BeginDate() {
	try {
	    return dateFormat.parse(getValueByCatalogAndKey("SYSPARAM",
		    "SYSBEGINDATE"));
	} catch (Exception e) {
		e.printStackTrace();
	    return null;
	}
    }
    
    public List<DataEntry> getEntryListByCatalogAndKey(String catalog,
	    String key) {
	return this.dataListTable.containsKey(catalog + "-" + key) ? this.dataListTable
		.get(catalog + "-" + key) : new ArrayList<DataEntry>();
    }

    public String formatDate(Date date) {
	return date != null ? dateFormat.format(date) : "";
    }

    public String formatTime(Date date) {
	return date != null ? timeFormat.format(date) : "";
    }

    public static DataMapping instance() {
    	
    	return null;
    }
    

}
