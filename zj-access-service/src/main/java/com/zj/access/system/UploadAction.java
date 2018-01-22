package com.zj.access.system;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.beanutils.BeanUtils;
import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import com.zj.entity.system.DataEntry;

public abstract class UploadAction<E> implements Serializable {
	private static final long serialVersionUID = 6572689509457384426L;
	private static final String PREFIX_KEY_COLUMN = "*";
	    private int uploadAvailable = 1;
	    private File uploadedFile;
	    private int rowCount = 0;
	    protected String key = "";
	    private HashMap<DataEntry, Integer> headerCellMap;
	    private List<E> importObjects;
	    private List<E> existObjects;
	    private HashMap<String, E> existObjectMaps;
	    private HashMap<Long, E> existObjectInMoreKeyMaps;
	    protected HashMap<DataEntry, Cell> requiredMaps;

	
	    private static final Logger log = Logger.getLogger(UploadAction.class);

	   // @In(create = true, required = true)
	    @Autowired
	    protected DataMapping dataMapping;

	    public void startImport() {
		this.uploadAvailable = 1;
		uploadedFile = null;
		rowCount = 0;
		importObjects = null;
		existObjects = null;
		existObjectMaps = null;
		existObjectInMoreKeyMaps = null;
		requiredMaps = null;
	    }

	    public void parseUploadFile() {
		if (getUploadedFile() != null) {
		    System.out.println(getUploadedFile().getName());

		    int excelFileSize = (int) getUploadedFile().length();
		    log.info("begin parse upload file :"+getUploadedFile()  .getPath()+" -"+ excelFileSize);
		    try {
			WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setGCDisabled(true);
			workbookSettings.setInitialFileSize(excelFileSize + 1);
			workbookSettings.setDrawingsDisabled(true);
			Workbook workbook = Workbook.getWorkbook(getUploadedFile(),
				workbookSettings);
			log.info("finish get work book ");
			Sheet sheet = workbook.getSheet(0);

			List<DataEntry> headerList = this.dataMapping.getEntryListByCatalog(getHeaderKey());
			// 分析列头，获取Key列位置
			Cell headerCell = null;
			for (DataEntry dataEntry : headerList) {
			    headerCell = sheet.findCell(dataEntry.getValueString());
			    if (headerCell != null) {
				getHeaderCellMap().put(dataEntry,
					Integer.valueOf(headerCell.getColumn()));
				if (!dataEntry.getKeyString().startsWith(PREFIX_KEY_COLUMN))
				    continue;
				getRequiredMaps().put(dataEntry, headerCell);
			    }
			}
			if (requiredMaps != null && requiredMaps.size() > 0) {
			    for (Map.Entry<DataEntry, Cell> entry : requiredMaps
				    .entrySet()) {
				this.log.info("Find key Column {0} "+ entry.getKey().getValueString()+"at "+ entry.getValue().getColumn());
			    }
			} else {
			    this.log.info("Could not find any key column!Please check the dataMapping.");
			    /*StatusMessages.instance().addFromResourceBundle(
				    StatusMessage.Severity.ERROR,
				    "fileUpload.parse.failed", new Object[0]);*/
			    return;
			}

			// 在分析前，允许子类做一些判断动作。
			if (!checkBeforeParse()) {
			    this.log.info("check before parse return false.");
			    /*StatusMessages.instance().addFromResourceBundle(
				    StatusMessage.Severity.ERROR,
				    "fileUpload.parse.failed", new Object[0]);*/
			    return;
			}

			// 如果需要原数据保留，那么初始化原数据，默认不保留
			if (needCheckExistObject()) {
			    initExistObjects();
			}

			setRowCount(sheet.getRows() > 1 ? sheet.getRows() - 1 : 0);

			E rowObject = null;
			Cell valueCell = null;
			String cellValue = null;

			for (int row = 1; row < getRowCount() + 1; row++) {
			    String keyColumnValue = null;
			    String keyValue = "";
			    Map<String, Object> valueMap  = new HashMap<String, Object>();
			    for (Map.Entry<DataEntry, Cell> entry : requiredMaps
				    .entrySet()) {
				valueCell = sheet.getCell(entry.getValue().getColumn(),
					row);
				// 获得keyColumn的值
				keyColumnValue = valueCell != null ? truncString(
					valueCell.getContents(), entry.getKey().getOrderSeq().intValue()) : null;
				// 获得去除"*"号后的key
				keyValue = entry
					.getKey()
					.getKeyString()
					.substring(
						entry.getKey().getKeyString()
							.indexOf(PREFIX_KEY_COLUMN) + 1);
				if (keyColumnValue == null
					|| keyColumnValue.trim().isEmpty()
					|| keyValue.isEmpty()) {
				    // 检索必填项时跳过空行
				    continue;
				}
				valueMap.put(keyValue, keyColumnValue);
			    }
			    // 跳过空行
			    if(getRequiredMaps().size()>valueMap.size()){
				continue;
			    }
			    

			    // 准备更新对象
			    if (valueMap != null && valueMap.size() > 0) {
				// 兼容原来的必填项只有一个必填项的逻辑
				if (valueMap.size() == 1) {
				    rowObject = getRowObject(keyColumnValue);
				} else {
				    rowObject = getRowObject(valueMap);
				}
			    }

			    for (DataEntry dataEntry : getHeaderCellMap().keySet()) {
				if (!dataEntry.getKeyString().startsWith(PREFIX_KEY_COLUMN)) {
				    valueCell = sheet.getCell(
					    ((Integer) this.getHeaderCellMap().get(
						    dataEntry)).intValue(), row);
				    // 如果cell是数字型，
				    if (valueCell instanceof NumberCell) {
					try {
					    BeanUtils.setProperty(
						    rowObject,
						    dataEntry.getKeyString(),
						    checkNumberCellValue(dataEntry
							    .getKeyString(),
							    ((NumberCell) valueCell)
								    .getValue()));
					} catch (Exception e) {
					    e.printStackTrace();
					    log.error("error in "
						    + dataEntry.getKeyString()
						    + ":"
						    + ((NumberCell) valueCell)
							    .getValue());
					 /*   StatusMessages
						    .instance()
						    .addFromResourceBundle(
							    StatusMessage.Severity.ERROR,
							    "product.fileUpload.failed");*/
					    return;
					}
				    } else {
					cellValue = truncString(
						valueCell.getContents(),
						dataEntry.getOrderSeq() != null ? dataEntry
							.getOrderSeq().intValue() : 0);
					try {
					    BeanUtils.setProperty(
						    rowObject,
						    dataEntry.getKeyString(),
						    checkCellValue(dataEntry.getKeyString(),
							    cellValue));
					} catch (Exception e) {
					    e.printStackTrace();
					    log.error("error in " + dataEntry.getKeyString()
						    + ":" + cellValue);
					   /* StatusMessages
						    .instance()
						    .addFromResourceBundle(
							    StatusMessage.Severity.ERROR,
							    "product.fileUpload.failed");*/
					    return;
					}
				    }
				}
			    }
			    // 处理完一行后，允许子类做一些动作
			    if (valueMap != null && valueMap.size() > 0) {
				// 兼容原来的必填项只有一个的逻辑
				if (valueMap.size() == 1) {
				    doAfterParseRow(keyColumnValue, rowObject);
				} else {
				    doAfterParseRow(valueMap, rowObject);
				}
			    }

			}
			workbook.close();
			setRowCount(getImportObjects().size()
				+ getExistObjects().size());
			log.warn("finish parse file");
		    } catch (Exception e) {
			e.printStackTrace();
			/*StatusMessages.instance().addFromResourceBundle(
				StatusMessage.Severity.ERROR,
				"product.fileUpload.failed");*/
		    }
		} else {
		    log.warn("upload file is null!");
		    /*StatusMessages.instance().addFromResourceBundle(
			    StatusMessage.Severity.ERROR, "product.fileUpload.failed");*/
		}
	    }

	    protected void initExistObjects() {
		// do nothing
	    }

	    protected boolean needCheckExistObject() {
		return false;
	    }

	    protected void doAfterParseRow(String keyValue, E rowObject) {
		if (needCheckExistObject()
			&& getExistObjectMaps().containsKey(keyValue)) {
		    getExistObjects().add(rowObject);
		} else {
		    getImportObjects().add(rowObject);
		}
	    }

	    protected void doAfterParseRow(Map<String, Object> keyValueMap, E rowObject) {
		if (needCheckExistObject() && checkExistObjectInMoreKey(keyValueMap)) {
		    getExistObjects().add(rowObject);
		} else {
		    getImportObjects().add(rowObject);
		}
	    }

	    protected Object checkCellValue(String keyValue, String cellValue) {
		return cellValue;
	    }

	    protected Object checkNumberCellValue(String keyValue, double cellValue) {
		return cellValue;
	    }

	    protected E getRowObject(String keyValue) {
		if (needCheckExistObject()
			&& getExistObjectMaps().containsKey(keyValue)) {
		    return getExistObjectMaps().get(keyValue);
		} else {
		    return newRowObject(keyValue);
		}
	    }

	    protected E getRowObject(Map<String, Object> keyValueMap) {
		if (needCheckExistObject()
			&& getExistObjectInMoreKeyWhenGetRowObject(keyValueMap) != null) {
		    return getExistObjectInMoreKeyWhenGetRowObject(keyValueMap);
		} else {
		    return newRowObject(keyValueMap);
		}

	    }

	    protected E getExistObjectInMoreKeyWhenGetRowObject(
		    Map<String, Object> keyValueMap) {
		// todo 判断是否存在的已有的对象 并返回
		return null;

	    }

	    protected abstract E newRowObject(Map<String, Object> keyValueMap);

	    protected abstract E newRowObject(String keyValue);

	    protected boolean checkBeforeParse() {
		return true;
	    }

	    protected boolean checkExistObjectInMoreKey(Map<String, Object> keyValueMap) {
		return false;
	    }

	    protected abstract String getHeaderKey();

	    protected String truncString(String contents, int length) {
		if (contents == null) {
		    return null;
		}
		contents = contents.trim();
		if (length > 0 && contents.length() > length) {
		    contents = contents.substring(0, length);
		}
		return contents;
	    }

/*	    public void listener(UploadEvent event) {
		if (event == null) {
		    log.warn("upload event is null");
		    return;
		}
		UploadItem item = event.getUploadItem();
		if (item != null && uploadAvailable > 0) {
		    setUploadedFile(item.getFile());
		    this.uploadAvailable = 0;
		} else {
		    log.warn("upload item is null");
		}
	    }*/


	    public int getUploadAvailable() {
		return this.uploadAvailable;
	    }

	    public void setUploadAvailable(int uploadAvailable) {
		this.uploadAvailable = uploadAvailable;
	    }

	    public void clearUploadData() {
		if (getUploadedFile() != null) {
		    getUploadedFile().delete();
		}
		setUploadedFile(null);
		setUploadAvailable(1);
		setRowCount(0);
		getHeaderCellMap().clear();
		getImportObjects().clear();
		getExistObjects().clear();
		getExistObjectInMoreKeyMaps().clear();
		getExistObjectMaps().clear();
		getRequiredMaps().clear();
	    }

	    public File getUploadedFile() {
		return this.uploadedFile;
	    }

	    public void setUploadedFile(File uploadedFile) {
		this.uploadedFile = uploadedFile;
	    }

	    public int getRowCount() {
		return this.rowCount;
	    }

	    public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	    }

	    public HashMap<DataEntry, Integer> getHeaderCellMap() {
		if (headerCellMap == null) {
		    headerCellMap = new HashMap<DataEntry, Integer>();
		}
		return this.headerCellMap;
	    }

	    public void setHeaderCellMap(HashMap<DataEntry, Integer> headerCellMap) {
		this.headerCellMap = headerCellMap;
	    }

	    public HashMap<DataEntry, Cell> getRequiredMaps() {
		if (requiredMaps == null) {
		    requiredMaps = new HashMap<DataEntry, Cell>();
		}
		return this.requiredMaps;
	    }

	    public void setRequiredMaps(HashMap<DataEntry, Cell> requiredMaps) {
		this.requiredMaps = requiredMaps;
	    }

	    public List<E> getImportObjects() {
		if (importObjects == null) {
		    importObjects = new ArrayList<E>();
		}
		return importObjects;
	    }

	    public List<E> getExistObjects() {
		if (existObjects == null) {
		    existObjects = new ArrayList<E>();
		}
		return existObjects;
	    }

	    public HashMap<String, E> getExistObjectMaps() {
		if (existObjectMaps == null) {
		    existObjectMaps = new HashMap<String, E>();
		}
		return existObjectMaps;
	    }

	    public HashMap<Long, E> getExistObjectInMoreKeyMaps() {
		if (existObjectInMoreKeyMaps == null) {
		    existObjectInMoreKeyMaps = new HashMap<Long, E>();

		}
		return existObjectInMoreKeyMaps;
	    }

	    public void setExistObjectInMoreKeyMaps(
		    HashMap<Long, E> existObjectInMoreKeyMaps) {
		this.existObjectInMoreKeyMaps = existObjectInMoreKeyMaps;
	    }
	    
	    public void ImportAll(File file){
	    	startImport();
	    	setUploadedFile(file);
	    	parseUploadFile();
	    	insertDB();
	    	clearUploadData();
	    	
	    	
	    }
	    
	    public abstract void insertDB();

	/* @Transactional
	    public void confirmImportAll() {
		getEntityManager().joinTransaction();
		for (E importObject : getImportObjects()) {
		    try {
			getEntityManager().persist(importObject);
		    } catch (Exception e) {
			try {
			    log.warn("error when save product : {0}", BeanUtils.getProperty(importObject, key));
			} catch (Exception e1) {
			    log.warn("error when get product key : {0}", key);
			}
		    }
		}
		getEntityManager().flush();
		clearUploadData();
		StatusMessages.instance().addFromResourceBundle(
			StatusMessage.Severity.INFO, "fileUpload.import.success",
			new Object[0]);
	    }

	    @Transactional
	    public void confirmImportNew() {
		for (E existObject : getExistObjects()) {
		   getEntityManager().refresh(existObject);
		}
		confirmImportAll();
	    }*/
	}
