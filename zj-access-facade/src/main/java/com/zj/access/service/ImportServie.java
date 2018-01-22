package com.zj.access.service;

import java.io.InputStream;

public interface ImportServie {
	/**
	 * 设备导入
	 * @author liukh
	 * @date 2017-3-9 下午2:57:01
	 * @param in
	 * @return
	 */
	public String addDevicebyExcelImport(InputStream in);
}
