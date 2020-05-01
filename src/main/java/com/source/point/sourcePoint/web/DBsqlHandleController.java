package com.source.point.sourcePoint.web;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.source.point.sourcePoint.common.RespRlt;
import com.source.point.sourcePoint.common.ResultCode;
import com.source.point.sourcePoint.dto.dbsqlhandle.AutoSqlReq;
import com.source.point.sourcePoint.dto.excel.AutoSqlExcelModel;
import com.source.point.sourcePoint.enums.ResouseType;
import com.source.point.sourcePoint.service.DBsqlHandleService;
import com.source.point.sourcePoint.utils.ExcelUtils;
import com.source.point.sourcePoint.utils.RespUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api")
@Api(description = "sql脚本生成", tags = "DBSQL")
public class DBsqlHandleController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DBsqlHandleService dBsqlHandleService;

	@ApiOperation(value = "生成脚本")
	@PostMapping(value = "/autoSql")
	@ResponseBody
	public RespRlt<?> autoSql(@Valid @RequestBody AutoSqlReq req, BindingResult binRlt) {
		logger.info("autoSql req>>{}", req.toString());
		if (binRlt.hasErrors()) {
			return RespUtil.fail(ResultCode.PARAM_ERROR, String.valueOf(binRlt.getAllErrors()));
		}
		dBsqlHandleService.autoSql(req);
		RespRlt<?> rlt = RespUtil.success();
		logger.info("autoSql resp>>{}", rlt.toString());
		return rlt;
	}
	
//	@ApiOperation(value = "菜单按钮权限脚本")
	@PostMapping(value = "/autoSqlExcel")
	@ResponseBody
	public RespRlt<?> autoSqlExcel(@RequestParam("file") MultipartFile file) {
		logger.info("autoSqlExcel begin");
		List<AutoSqlExcelModel> excelList = ExcelUtils.readExcel("", AutoSqlExcelModel.class, file);
		List<String> list = dBsqlHandleService.buildSqlDataList(excelList);
		if (CollectionUtils.isEmpty(list)) {
			return RespUtil.fail(ResultCode.PARAM_ERROR, "导入的数据为空");
		}
		AutoSqlReq req = new AutoSqlReq();
		req.setResouseType(Integer.valueOf(ResouseType.TYPE_2.getType()));
		req.setSqlName(file.getOriginalFilename().replace(".xlsx", "").replace(".xls", ""));
		req.setList(list);
		dBsqlHandleService.autoSql(req);
		RespRlt<?> rlt = RespUtil.success();
		logger.info("autoSqlExcel end");
		return rlt;
	}
	
}
