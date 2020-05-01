package com.source.point.sourcePoint.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.source.point.sourcePoint.dto.excel.AutoSqlExcelModel;
import com.source.point.sourcePoint.utils.ExcelUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/excel")
@Api(value = "excel导入导出", tags = "excel导入导出", description = "excel导入导出")
public class ExcelController {

	@RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response) throws IOException {

		List<AutoSqlExcelModel> resultList = null;// demo自行定义

		long t1 = System.currentTimeMillis();
		ExcelUtils.writeExcel(response, resultList, AutoSqlExcelModel.class);
		long t2 = System.currentTimeMillis();
		System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
	}

	@RequestMapping(value = "/readExcel", method = RequestMethod.POST)
	public void readExcel(MultipartFile file) {

		long t1 = System.currentTimeMillis();
		List<AutoSqlExcelModel> list = ExcelUtils.readExcel("", AutoSqlExcelModel.class, file);
		long t2 = System.currentTimeMillis();
		System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
		list.forEach(b -> System.out.println(b));
	}
}
