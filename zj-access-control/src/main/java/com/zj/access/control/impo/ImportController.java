package com.zj.access.control.impo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zj.access.control.base.CommonController;
import com.zj.access.service.ImportServie;
@Scope("prototype")
@RequestMapping(value="/import")
@Controller
public class ImportController extends CommonController {
	
	private static final Logger log = Logger.getLogger(ImportController.class);
	@Autowired
	private ImportServie importServie;
	
	@RequestMapping(value = "deviceImport", method = {RequestMethod.POST})
	@ResponseBody
	public String addDevicebyExcelImport(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "file") MultipartFile file) {
		String jsonStr = "";
		try {
			jsonStr=  importServie.addDevicebyExcelImport(file.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}
}
